package com.wioyber.kele.core.service.impl;

import com.wioyber.kele.core.KeleApplicationTests;
import com.wioyber.kele.core.service.IAccountService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjg
 * @since 2023/3/31
 */
class AccountServiceImplTest extends KeleApplicationTests {

    @Resource
    private IAccountService iAccountService;


    @Test
    void insertTest() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(2);
        account.setUsername("测试数据");
        account.setPassword("123");
        Account account1 = new Account();
        account1.setId(2);
        account1.setUsername("cjg123");
        account1.setPassword("123");
        accounts.add(account);
        accounts.add(account1);
        iAccountService.insertTest(accounts);
    }
}