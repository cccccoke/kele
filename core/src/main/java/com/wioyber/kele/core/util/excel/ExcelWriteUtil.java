package com.wioyber.kele.core.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import com.wioyber.kele.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

/**
 * 导出工具类
 * @author cjg
 * @since 2023/2/13
 */
@Slf4j
public class ExcelWriteUtil {


    public static <V> void simpleWrite(HttpServletResponse response,
                                 String fileName,
                                 Class<V> vClass,
                                 Collection<V> data) {
        write(response, fileName, null, 0, vClass, data, null);
    }

    public static <V> void writeTemplate(HttpServletResponse response,
                                             String fileName,
                                             String templatePath,
                                             Class<V> vClass,
                                             Collection<V> data) {
        write(response, fileName, templatePath, 0, vClass, data, null);
    }


    /**
     * 标准写入
     *
     * @param <V>            数据类型
     * @param response       响应流
     * @param fileName       文件名称
     * @param templatePath   模版地址
     * @param sheet          sheet页码
     * @param vClass         数据class
     * @param data           数据
     * @param excludeColumns 忽略的列名
     */
    public static <V> void write(HttpServletResponse response,
                                              String fileName,
                                              String templatePath,
                                              Integer sheet,
                                              Class<V> vClass,
                                              Collection<V> data,
                                              Collection<String> excludeColumns) {
        try {
            setResponse(response, fileName);
            ExcelWriterBuilder builder = EasyExcelFactory.write(response.getOutputStream(), vClass);
            if(excludeColumns != null && !excludeColumns.isEmpty()){
                builder.excludeColumnFieldNames(excludeColumns);
            }
            if (StringUtils.isNotBlank(templatePath)) {
                InputStream templateStream = ExcelWriteUtil.class.getResourceAsStream(templatePath);
                if (templateStream != null) {
                    builder.withTemplate(templateStream);
                    // 模版不重写head
                    builder.needHead(false);
                } else {
                    builder.withTemplate(templatePath);
                }
            }
            builder
                   .autoCloseStream(Boolean.FALSE) //取消自动关闭，返回JSON信息
                   .sheet(sheet)
                   .doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
            // 重置response类型为JSON
            resetResponse(response);
            throw new BaseException(CustomExceptionEnum.EXPORTFAILURE);
        }
        log.info("----->导出{}条数据", data.size());
    }

    public static void setResponse(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            fileName += ".xlsx";
        }
        try {
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName,
                            "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void resetResponse(HttpServletResponse response){
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }
}
