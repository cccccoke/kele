package com.wioyber.kele.core.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import com.wioyber.kele.core.exception.BaseException;
import com.wioyber.kele.core.support.excel.handler.CustomRowStyleHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 导出工具类
 *
 * @author cjg
 * @since 2023/2/13
 */
@Slf4j
public class ExcelWriteUtil {

    private static final ThreadLocal<List<WriteCellStyle>> cellStyleCache = ThreadLocal.withInitial(() -> {
        ArrayList<WriteCellStyle> writeCellStyles = new ArrayList<>();
        WriteCellStyle cellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        cellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont font = new WriteFont();
        // 字体大小
        font.setFontHeightInPoints((short) 20);
        cellStyle.setWriteFont(font);
        writeCellStyles.add(cellStyle);
        return writeCellStyles;
    });


    public static <V> void simpleWrite(HttpServletResponse response,
                                       String fileName,
                                       Class<V> vClass,
                                       Collection<V> data) {
        write(response, fileName, null, 0, vClass, data, null);
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
            if (excludeColumns != null && !excludeColumns.isEmpty()) {
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
                /*
                     模版写入时重写单元格控制器
                     非模版写入也可使用
                     官方默认实现中可使用：
                     EasyExcel.write(fileName, DemoData.class)
                              .registerWriteHandler(new CustomCellWriteHandler())
                              .sheet("模板")
                              .doWrite(data());
                 */
//                builder.registerWriteHandler(new CustomCellWriteHandler());
//                builder.inMemory(Boolean.TRUE);
//                builder.registerWriteHandler(new CustomRowWriteHandler());
                builder.registerWriteHandler(new CustomRowStyleHandler(cellStyleCache.get()));
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
        } finally {
            cellStyleCache.remove();
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

    public static void resetResponse(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }

    public static <V> void writeTemplate(HttpServletResponse response,
                                         String fileName,
                                         String templatePath,
                                         Class<V> vClass,
                                         Collection<V> data) {
        write(response, fileName, templatePath, 0, vClass, data, null);
    }
}
