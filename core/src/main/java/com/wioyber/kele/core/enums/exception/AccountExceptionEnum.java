package com.wioyber.kele.core.enums.exception;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountExceptionEnum implements BaseExceptionEnum {

    SYS_ERR(-1, "未知错误，请联系管理员"),

    SYS_MULTIPLE_DEPART(-2, "登陆帐户同属同个部门，不允许登录，请联系管理员"),

    SYS_MULTIPLE_ROLE(-3, "登陆帐户拥有多个角色，不允许登录，请联系管理员"),

    SYS_SUC(0,"SUCCESS"),

    SYS_LOGIN_ERROR(400,"登录失败"),

    SYS_LOGIN_FAIL(401, "请先登录"),

    SYS_LOGIN_OUT_TIME(4011, "登录超时"),

    SYS_NO_AUTHOR(403, "您没有权限"),

    SYS_LOGIN_LIMIT_ADMIN(402,"仅允许超级管理员登录"),

    SYS_LOGIN_LIMIT_FREQUENCE(404,"短时间内账户密码重试次数过多，请联系管理员"),

    ;

    private int code;
    private String msg;


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
