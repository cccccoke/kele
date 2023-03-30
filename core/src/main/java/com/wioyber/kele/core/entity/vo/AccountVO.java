package com.wioyber.kele.core.entity.vo;

import lombok.Data;

/**
 * @author cjg
 * @since 2023/3/30
 */
@Data
public class AccountVO {
    private Integer id;
    private String username;
    private String perms;
    private String role;


    private String tk;
}
