package com.wioyber.kele.core.util.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.Maps;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import com.wioyber.kele.core.exception.BaseException;
import com.wioyber.kele.core.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

/**
 * P ---> po V ---> vo M ---> mapper
 *
 * @author cjg
 * @since 2023/2/10
 */
@Slf4j
public abstract class AbstractExcelImportListener<P extends Serializable, V extends IBaseImport, M extends BaseMapper<P>>
        extends AnalysisEventListener<V> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2;

    private static final int ERROR_INFO_MAX = 5;
    /**
     * 缓存的错误信息
     */
    private final ThreadLocal<Map<Integer, List<String>>> cacheError = new ThreadLocal<>();
    /**
     * 缓存的list信息
     */
    private final ThreadLocal<List<P>> cacheData = new ThreadLocal<>();
    private final ThreadLocal<Integer> cacheInsertCount = new ThreadLocal<>();

    protected abstract M getM();

    protected void checkHead(Integer headRow, String headFlag, Map<Integer, String> headMap, AnalysisContext context) {
        if (context.readRowHolder().getRowIndex().equals(headRow)) {
            if (!headFlag.equals(headMap.get(headMap.size() - 1))) {
                throw new BaseException(CustomExceptionEnum.ABNORMAL_TEMPLATE);
            }
        }
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invoke(V v, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(v));
        // 检查数据
        checkRow(v, analysisContext.readRowHolder());
        /*
         * 查验错误信息
         * 跳出
         * 防止NullPointer
         */
        if (null != cacheError.get() && cacheError.get().containsKey(analysisContext.readRowHolder().getRowIndex() + 1)) {
            return;
        }
        // 用户自定义检查
        customCheck(v, analysisContext.readRowHolder());
        // 转换数据
        P p = getP().get();
        BeanUtil.copyProperties(v, p);
        // 存入
        initCacheDataIsEmpty();
        List<P> ts = cacheData.get();
        log.info("-----> 存入数据,{}", transformData(p, v));
        ts.add(transformData(p, v));
        log.info("-----> 数据,{}", cacheData.get());
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cacheData.get().size() >= BATCH_COUNT) {
            log.info("-----> 导入数据,{}", cacheData.get());
            insert(cacheData.get());
            // 存储完成清理缓存
            log.info("-----> 清空data缓存");
            cacheData.remove();
        }
    }

    private void checkRow(V v, ReadRowHolder readRowHolder) {
        int rowNo = readRowHolder.getRowIndex() + 1;
        final Map<Field, NotNull> fieldsMap = Maps.newHashMap();
        for (Field field : v.getClass().getDeclaredFields()) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            if (null != notNull) {
                fieldsMap.put(field, notNull);
            }
        }
        for (Map.Entry<Field, NotNull> e : fieldsMap.entrySet()) {
            Object fieldValue = ReflectUtil.getFieldValue(v, e.getKey());
            if (!CommonUtil.checkValidIgnoreZ(fieldValue)) {
                //存入错误信息
                initCacheErrorIsEmpty();
                if (cacheError.get().entrySet().size() < ERROR_INFO_MAX) {
                    putError(rowNo, e.getValue().message());
                }
            }
        }
    }

    public void putError(Integer rowNo, String errorMsg) {
        if (!cacheError.get().containsKey(rowNo)) {
            cacheError.get().put(rowNo, new ArrayList<>());
        }
        cacheError.get().get(rowNo).add(errorMsg);
    }

    private void initCacheErrorIsEmpty() {
        if (null == cacheError.get()) {
            cacheError.set(new HashMap<>());
        }
    }

    /**
     * 业务重写
     *
     * @param v             the v
     * @param readRowHolder the read row holder
     */
    protected void customCheck(V v, ReadRowHolder readRowHolder) {

    }

    protected abstract Supplier<P> getP();

    protected void insert(Collection<P> list) {
        if (null != list && !list.isEmpty()) {
            list.forEach(getM()::insert);
            cacheInsertCount.set(cacheInsertCount.get() + 1);
        }
    }

    /**
     * 业务重写
     *
     * @param p the p
     * @param v the v
     * @return the p
     */
    protected P transformData(P p, V v) {
        return p;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            if (cacheError.get() != null && !cacheError.get().isEmpty()) {
                throw new BaseException(-1, getError());
            }
            if ((cacheData.get() == null || cacheData.get().isEmpty()) && cacheInsertCount.get() == 0) {
                throw new BaseException(-1, "未找到可用数据");
            }
            // 最后一次插入数据
            log.info("-----> 最后导入数据");
            insert(cacheData.get());
        } finally {
            cacheClean();
        }
    }

    private String getError() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<String>> e : cacheError.get().entrySet()) {
            if (e.getKey() != null) {
                sb.append("第").append(e.getKey()).append("行：");
            }
            for (int i = 0; i < e.getValue().size(); i++) {
                sb.append(e.getValue().get(i));
                if (i < e.getValue().size() - 1) {
                    sb.append(",");
                }
            }
            sb.append(";\n");
        }
        return sb.toString();
    }

    private void cacheClean() {
        cacheError.remove();
        cacheData.remove();
        cacheInsertCount.remove();
        log.info("-----> 清空缓存");
    }

    private void initCacheDataIsEmpty() {
        if (null == cacheData.get()) {
            cacheData.set(new ArrayList<>());
        }
        if (null == cacheInsertCount.get()) {
            cacheInsertCount.set(0);
        }
    }
}
