package com.wioyber.kele.core.support.excel.handler;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * @author cjg
 * @since 2023/2/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CustomRowStyleHandler extends AbstractCellStyleStrategy {

    private List<WriteCellStyle> writeCellStyleList;

    /**
     * 检查的列下标(默认从0开始)
     */
    private final Integer columnIndex = 0;

    /**
     * 按照循环每个单元格去设置的样式
     *
     * @param context the context
     */
    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (stopProcessing(context)) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        //业务需求： 根据某列的值判断
        //todo: plan1：判断值是否击中，击中后缓存下行数，最后去除缓存 !!重点是缓存在何时移除
        //todo: plan2: 先去找击中的行下标
        //plan3: 循环每行，时间复杂度较高
        context.getRow().forEach(c -> {
            if (checkData(c)) {
                //  todo:官方说需要缓存  优化需要把style缓存起来
                WriteCellStyle.merge(writeCellStyleList.get(0), cellData.getOrCreateStyle());
            }
        });

    }


    /**
     * 数据的检查方法
     *
     * @param cell the cell
     * @return the boolean
     */
    private Boolean checkData(Cell cell) {
        return columnIndex.equals(cell.getColumnIndex()) && cell.toString().equals("程建国");
    }


    protected boolean stopProcessing(CellWriteHandlerContext context) {
        return context.getFirstCellData() == null;
    }

}
