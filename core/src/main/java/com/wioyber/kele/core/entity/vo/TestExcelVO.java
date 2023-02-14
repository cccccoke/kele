package com.wioyber.kele.core.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wioyber.kele.core.support.excel.IBaseExcel;
import com.wioyber.kele.core.support.excel.converter.GenderConverter;
import com.wioyber.kele.core.support.excel.converter.LocalDateTimeConverter;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author cjg
 * @since 2023/2/11
 */
@Data
public class TestExcelVO implements IBaseExcel {

    @ExcelProperty(index = 0, value = "名称")
    @NotNull(message = "名称未填写")
    private String username;

    @ExcelProperty(index = 1, value = "年龄")
    @NotNull(message = "年龄未填写")
    private Integer age;

    @ExcelProperty(index = 2, value = "时间",converter = LocalDateTimeConverter.class)
    @NotNull(message = "时间未填写")
    private LocalDateTime localTime;

    @ExcelProperty(index = 3, value = "性别",converter = GenderConverter.class)
    @NotNull(message = "性别")
    private Integer gender;

}
