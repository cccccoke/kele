package com.wioyber.kele.core.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjg
 * @since 2023/4/1
 */
@Data
public class LoginVo {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
