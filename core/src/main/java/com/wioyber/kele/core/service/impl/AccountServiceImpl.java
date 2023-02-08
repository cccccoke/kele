package com.wioyber.kele.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wioyber.kele.core.annotation.RedisLock;
import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.dao.AccountDao;
import com.wioyber.kele.core.entity.po.Account;
import com.wioyber.kele.core.enums.LockEnum;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import com.wioyber.kele.core.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author cjg
 * @since 2023/1/6
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements IAccountService {

    @Override
    @RedisLock(lockEnum = LockEnum.A_WORK, expireTime = 20L)
    public void testAnnotationLock() {
        try {
            Thread.sleep(1000 * 5);
            log.info("业务处理...");
        } catch (Exception e) {
            log.error("代码错误");
        }

    }

    @Override
    public void testException() {
        Result.throwBaseException(CustomExceptionEnum.GENERALEXCEPTION);
    }

}
