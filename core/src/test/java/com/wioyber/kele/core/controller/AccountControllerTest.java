package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.KeleApplicationTests;
import com.wioyber.kele.core.common.sys.Result;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/2/3
 */
class AccountControllerTest extends KeleApplicationTests {


    @Resource
    private AccountController accountController;
    @Test
    void getOne() {
        Result<String> one = accountController.getOne();
        System.out.println(one);
    }
}