package com.wioyber.kele.core.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wioyber.kele.core.dao.AccountDao;
import com.wioyber.kele.core.entity.po.Account;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/1/6
 */
@Slf4j
public class CustomAuthorization extends AuthorizingRealm {

    @Resource
    private AccountDao accountDao;


    /**
     * 授权
     *
     * @param principalCollection the principal collection
     * @return the authorization info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行了------>{}", "授权");
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken the authentication token
     * @return the authentication info
     * @throws AuthenticationException the authentication exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        try {
            return loginAuthentication(authenticationToken);
        } catch (Exception e) {
            throw new AuthenticationException();
        }
    }

    private AuthenticationInfo loginAuthentication(AuthenticationToken token) {
        Account account = null;
        try {
            account = accountDao.selectOne(Wrappers.<Account>lambdaQuery()
                    .eq(Account::getUsername, token.getPrincipal()));
        } catch (Exception e) {
            log.error("用户登录失败:{},{},{}", e.getStackTrace(), e.getMessage(), e.getCause());
        }
        if (account == null) {
            throw new AuthenticationException("您未登录");
        }
        return new SimpleAuthenticationInfo(
                token.getPrincipal(), account.getPassword(), getName());
    }
}
