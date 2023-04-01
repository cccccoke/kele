package com.wioyber.kele.core.common.sys;

/**
 * @author cjg
 * @since 2023/2/3
 */
public class SystemConstant {

    public static final Integer SYSTEM_ERROR_CODE = -1;

    public static final Integer SYSTEM_SUCCESS_CODE = 200;
    public static final Integer SYSTEM_UN_LOGIN = 401;
    public static final Integer SYSTEM_UN_AUTH = 403;

    public static final String SYSTEM_SUCCESS_MSG = "success";

    public final static String USER_INFO_TOKEN_PREFIX = "kele:shiro:info:tk:";
    public final static String SESSION_USER_INFO_KEY = "userInfoKey";

    public final static String AES_LOGIN_KEY = "_C_Aes_coke_key_";


}
