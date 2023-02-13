package com.wioyber.kele.core.enums.exception;

import com.wioyber.kele.core.common.sys.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cjg
 * @since 2023/2/3
 */
@Getter
@AllArgsConstructor
public enum CustomExceptionEnum implements BaseExceptionEnum {
    DONOTALLOW(SystemConstant.SYSTEM_ERROR_CODE, "不允许的操作"),
    ABNORMAL_TEMPLATE(SystemConstant.SYSTEM_ERROR_CODE, "模版异常"),
    GENERALEXCEPTION(SystemConstant.SYSTEM_ERROR_CODE, "系统异常");

    private final int code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
