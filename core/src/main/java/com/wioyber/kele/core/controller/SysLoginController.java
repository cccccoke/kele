package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.entity.vo.LoginRtnVo;
import com.wioyber.kele.core.entity.vo.LoginVo;
import com.wioyber.kele.core.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/4/1
 */
@RestController
public class SysLoginController {

    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("/login")
    public Result<LoginRtnVo> login(@RequestBody LoginVo loginVo) {
        return Result.success(iSysUserService.login(loginVo));
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }

}
