//package com.wioyber.kele.core.security;
//
//import com.wioyber.kele.core.entity.po.Account;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//
///**
// * @author cjg
// * @since 2023/1/6
// */
//@Slf4j
//public class CustomAuthorization extends AuthorizingRealm {
//
//    /**
//     * 授权
//     *
//     * @param principalCollection the principal collection
//     * @return the authorization info
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        log.info("执行了------>{}", "授权");
//        return null;
//    }
//
//    /**
//     * 认证
//     *
//     * @param authenticationToken the authentication token
//     * @return the authentication info
//     * @throws AuthenticationException the authentication exception
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String username = (String) authenticationToken.getPrincipal();
//        Account account = new Account();
//        account.setUsername("cjg");
//        account.setPassword("123");
//        log.info("执行了------>{}", "认证");
////        return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), getName());
//        return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), getName());
//    }
//}
