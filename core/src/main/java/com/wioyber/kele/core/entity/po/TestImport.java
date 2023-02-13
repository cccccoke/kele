package com.wioyber.kele.core.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cjg
 * @since 2023/2/11
 */
@Data
public class TestImport implements Serializable {

    private Integer id;
    private String username;
    private Integer age;
    private String password;
    private LocalDateTime localTime;
    private Integer gender;
}
