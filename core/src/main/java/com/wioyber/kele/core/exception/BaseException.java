package com.wioyber.kele.core.exception;

import com.wioyber.kele.core.enums.exception.BaseExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author cjg
 * @since 2023/2/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException implements Serializable {

    private Integer code;
    private String msg;

    public BaseException(BaseExceptionEnum baseException) {
        super(baseException.getMsg());
        this.code = baseException.getCode();
        this.msg = baseException.getMsg();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

}
