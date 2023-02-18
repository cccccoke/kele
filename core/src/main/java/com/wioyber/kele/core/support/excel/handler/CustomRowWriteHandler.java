package com.wioyber.kele.core.support.excel.handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

/**
 * @author cjg
 * @since 2023/2/18
 */
@Slf4j
public class CustomRowWriteHandler implements RowWriteHandler {


    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        Workbook workbook = context.getWriteContext().writeWorkbookHolder().getWorkbook();
        Row row = context.getRow();
        CellStyle cellStyle = row.getRowStyle() == null ? workbook.createCellStyle() : row.getRowStyle();
//        row.iterator().forEachRemaining(i -> {
//            if (i.toString().equals("程建国")) {
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//            }
//        });
        //fixme:无法填充已经有值的行，只会填充后面空白的单元格
        row.setRowStyle(cellStyle);
    }
}
