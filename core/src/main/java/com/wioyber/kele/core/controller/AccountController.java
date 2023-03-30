package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.entity.vo.AccountVO;
import com.wioyber.kele.core.service.IAccountService;
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

    @PostMapping("/login")
    public Result<AccountVO> login(String username, String password) {
        return Result.success(iAccountService.login(username, password));
    }
}
