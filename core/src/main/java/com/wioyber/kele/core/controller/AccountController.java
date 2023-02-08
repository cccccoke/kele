package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.annotation.SubmitLock;
import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.service.IAccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/1/6
 */
@RestController
@RequestMapping("/info")
public class AccountController {

    @Resource
    private IAccountService iAccountService;


    @GetMapping("/get")
    @SubmitLock(ttl = 2L)
    public Result<String> getOne() {
//        iAccountService.testException();
        return Result.success("aaa");
    }

    @GetMapping("/work")
    public Result<String> work() {
//        iAccountService.testException();
        return Result.success();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return "登录成功";
        } catch (Exception e) {
            return "登录失败";
        }
    }
}
