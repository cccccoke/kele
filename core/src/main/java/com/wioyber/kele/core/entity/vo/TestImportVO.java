package com.wioyber.kele.core.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wioyber.kele.core.util.excel.IBaseImport;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjg
 * @since 2023/2/11
 */
@Data
public class TestImportVO implements IBaseImport {

    @ExcelProperty(index = 0, value = "名称")
    @NotNull(message = "名称未填写")
    private String username;

    @ExcelProperty(index = 1, value = "年龄")
    @NotNull(message = "年龄未填写")
    private Integer age;

}
