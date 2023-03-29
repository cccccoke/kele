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
    SYS_LOGIN_FAIL(SystemConstant.SYSTEM_UN_LOGIN, "请登录"),
    SYS_NO_AUTHOR(SystemConstant.SYSTEM_UN_LOGIN, "您未授权"),
    SYS_USER_NAME_PASSWORD_ERR(SystemConstant.SYSTEM_ERROR_CODE, "用户名或密码错误"),
    ABNORMAL_TEMPLATE(SystemConstant.SYSTEM_ERROR_CODE, "模版异常"),
    EXPORTFAILURE(SystemConstant.SYSTEM_ERROR_CODE, "下载文件失败"),
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
