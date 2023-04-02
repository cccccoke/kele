package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.entity.po.SysUserRole;
import com.wioyber.kele.core.entity.vo.LoginRtnVo;
import com.wioyber.kele.core.entity.vo.LoginVo;
import com.wioyber.kele.core.entity.vo.UserRoleResVo;
import com.wioyber.kele.core.service.ISysUserRoleService;
import com.wioyber.kele.core.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cjg
 * @since 2023/4/1
 */
@RestController
public class SysLoginController {

    @Resource
    private ISysUserService iSysUserService;


    @Resource
    private ISysUserRoleService iSysUserRoleService;

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

    @GetMapping("/test/{id}")
    public Result<UserRoleResVo> getUserInfo(@PathVariable Long id) {
        return Result.success(iSysUserService.getUserInfo(id));
    }


    @GetMapping("/test/test/1")
    public Result<List<SysUserRole>> getTest() {
        return Result.success(iSysUserRoleService.getList());
    }

}
