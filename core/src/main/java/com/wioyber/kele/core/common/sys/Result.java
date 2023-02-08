package com.wioyber.kele.core.common.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wioyber.kele.core.enums.exception.BaseExceptionEnum;
import com.wioyber.kele.core.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cjg
 * @since 2023/2/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {


    private Integer code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success() {
        return success(null, SystemConstant.SYSTEM_SUCCESS_MSG);
    }

    public static <T> Result<T> success(T t, String msg) {
        return new Result<>(SystemConstant.SYSTEM_SUCCESS_CODE, msg, t);
    }

    public static <T> Result<T> success(T t) {
        return success(t, SystemConstant.SYSTEM_SUCCESS_MSG);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> error(BaseExceptionEnum exceptionEnum) {
        return error(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public static void throwBaseException(BaseExceptionEnum ex){
        throw new BaseException(ex);
    }
}
