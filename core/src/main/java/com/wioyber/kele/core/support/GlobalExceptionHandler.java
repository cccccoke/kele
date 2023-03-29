package com.wioyber.kele.core.support;

import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import com.wioyber.kele.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cjg
 * @since 2023/2/3
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    public Result<BaseException> authenticationExceptionResolvers(AuthenticationException ex) {
        printLog(ex);
        return Result.error(CustomExceptionEnum.SYS_USER_NAME_PASSWORD_ERR);
    }

    @ExceptionHandler({Exception.class})
    public Result<BaseException> commonExceptionHandler(Exception ex) {
        printLog(ex);
        return Result.error(CustomExceptionEnum.GENERALEXCEPTION);
    }



    @ExceptionHandler({BaseException.class})
    public Result<BaseException> commonExceptionHandler(BaseException ex) {
        printLog(ex);
        return Result.error(ex.getCode(), ex.getMsg());
    }


    private <T extends Exception> void printLog(T t) {
        t.printStackTrace();
    }

}
