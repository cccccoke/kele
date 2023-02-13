package com.wioyber.kele.core.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wioyber.kele.core.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * T
 * @author cjg
 * @since 2023/2/10
 */
public interface IEasyExcelImport<P extends Serializable, M extends BaseMapper<P>> {

    default <V extends IBaseImport, L extends AbstractExcelImportListener<P, V, M>> void importFile(MultipartFile file, Class<V> vClass, L listener){
        importFile(file,vClass,listener,1);
    }


    default <L extends AbstractExcelImportListener<P, V, M>, V extends IBaseImport> void importFile(MultipartFile file, Class<V> vClass, L listener, Integer headRow) {
        try {
            EasyExcelFactory.read(file.getInputStream(), vClass, listener)
                    .sheet()
                    .headRowNumber(headRow)
                    .doReadSync();
        } catch (BaseException e) {
            throw e;
        } catch (ExcelAnalysisException e) {
            if (e.getCause().getClass().equals(BaseException.class)) {
                e.getCause().printStackTrace();
                throw new BaseException(-1, e.getCause().getMessage());
            } else {
                e.printStackTrace();
                throw new BaseException(-1, "文件解析失败，请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(-1, "文件解析失败，请联系管理员");
        }
    }
}
