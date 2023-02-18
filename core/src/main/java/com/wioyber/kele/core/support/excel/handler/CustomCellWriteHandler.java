package com.wioyber.kele.core.support.excel.handler;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author cjg
 * @since 2023/2/18
 */
@Slf4j
public class CustomCellWriteHandler implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        log.info("第{}行，第{}列写入完成。", context.getCell().getRowIndex(), context.getCell().getColumnIndex());
        WriteCellData<?> cellData = context.getFirstCellData();
        if (checkType(cellData.getType(), cellData, "程建国")) {
            WriteCellStyle style = cellData.getOrCreateStyle();
            //字体
            WriteFont font = style.getWriteFont() == null ? new WriteFont() : style.getWriteFont();
            cellData.setType(CellDataTypeEnum.STRING);
            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.
            style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            font.setColor(IndexedColors.RED.getIndex());
            style.setWriteFont(font);
            //默认会将 WriteCellStyle 设置到 cell里面去
        }
//        List<WriteCellData<?>> cellDataList = context.getCellDataList();
//
//        cellDataList.stream()
//                /*
//                 *  检索所有单元格
//                 */
////                .filter(i-> checkType(i.getType(),i,"程建国"))
//                /*
//                 * 检索固定Row行/Column列的单元格  忽略表头(默认0)
//                 *
//                 */
//                .filter(i-> i.getColumnIndex() == 0 && checkType(i.getType(),i,"程建国"))
//                .forEach(i -> {
//                    // 样式
//                    WriteCellStyle style = i.getOrCreateStyle();
//                    // 字体
//                    WriteFont font =  style.getWriteFont() == null ? new WriteFont() : style.getWriteFont();
//                    i.setType(CellDataTypeEnum.STRING);
//                    // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.
//                    style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//                    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//                    font.setColor(IndexedColors.RED.getIndex());
//                    style.setWriteFont(font);
//                    i.setWriteCellStyle(style);
//                });
    }

    /**
     * 根据单元格数据类型，判断数据格式的简单实现
     *
     * @param cellDataTypeEnum the cell data type enum
     * @param cellData         the cell data
     * @param o                the o
     * @return the boolean
     */
    private Boolean checkType(CellDataTypeEnum cellDataTypeEnum,WriteCellData<?> cellData,Object o){
        switch (cellDataTypeEnum){
            case STRING:
                return cellData.getStringValue().equals(o);
            case DATE:
                return cellData.getDateValue().equals(o);
            case NUMBER:
                return cellData.getNumberValue().equals(o);
            case BOOLEAN:
                return cellData.getBooleanValue().equals(o);
            case RICH_TEXT_STRING:
                return cellData.getRichTextStringDataValue().equals(o);
            case ERROR:
            case EMPTY:
            default:
                return false;
        }
    }
}
