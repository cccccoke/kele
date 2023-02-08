package com.wioyber.kele.core.enums;

import lombok.Getter;

/**
 * @author cjg
 * @since 2023/1/11
 */
@Getter
public enum LockEnum {
    DEFAULT("DEFAULT", "DEFAULT-"),
    A_WORK("ACCOUNT-WORK-00001", "ACCOUNT-WORK-00001-"),
    ;

    private final String key;

    private final String value;


    LockEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
