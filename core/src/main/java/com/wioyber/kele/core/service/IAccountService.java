package com.wioyber.kele.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wioyber.kele.core.entity.po.Account;
import com.wioyber.kele.core.entity.vo.AccountVO;

import java.util.List;

/**
 * @author cjg
 * @since 2023/1/6
 */
public interface IAccountService extends IService<Account> {

    void testAnnotationLock();

    void testException();

    AccountVO login(String username, String password);

    void insertTest(List<Account> list);

}
