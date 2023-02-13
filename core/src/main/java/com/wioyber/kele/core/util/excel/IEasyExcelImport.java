package com.wioyber.kele.core.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wioyber.kele.core.exception.BaseException;
import com.wioyber.kele.core.util.excel.listener.AbstractExcelImportListener;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 使用时需要实现该类
 *
 * @author cjg
 * @since 2023/2/10
 */
public interface IEasyExcelImport<P extends Serializable, M extends BaseMapper<P>> {

    default <V extends IBaseExcel, L extends AbstractExcelImportListener<P, V, M>> void readFile(MultipartFile file, Class<V> vClass, L listener){
        readFile(file,vClass,listener,1);
    }


    /**
     * 导入文件
     *
     * @param <L>      自定义listener类型
     * @param <V>      将要读取的Vo 需实现IBaseExcel接口
     * @param file     文件
     * @param vClass   将要读取的vClass
     * @param listener 自定义listener
     * @param headRow  开始行数默认 1 开始（默认文件存在一行头）
     */
    default <L extends AbstractExcelImportListener<P, V, M>, V extends IBaseExcel> void readFile(MultipartFile file, Class<V> vClass, L listener, Integer headRow) {
        try {
            EasyExcelFactory.read(file.getInputStream(), vClass, listener)
                    .sheet()
                    .headRowNumber(headRow)
                    .doReadSync();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(-1, "文件解析失败，请联系管理员");
        }
    }
}
