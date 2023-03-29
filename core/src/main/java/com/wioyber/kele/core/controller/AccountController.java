package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.common.sys.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjg
 * @since 2023/1/6
 */
@RestController
@RequestMapping("/info")
public class AccountController {

    @PostMapping("/login")
    public Result<Account> login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return Result.success();
    }
}
