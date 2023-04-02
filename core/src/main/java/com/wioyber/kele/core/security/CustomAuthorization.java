package com.wioyber.kele.core.security;

import com.wioyber.kele.core.common.sys.SystemConstant;
import com.wioyber.kele.core.entity.po.SysUser;
import com.wioyber.kele.core.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/1/6
 */
@Slf4j
public class CustomAuthorization extends AuthorizingRealm {


    @Resource
    @Lazy
    private ISysUserService iSysUserService;

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
        SysUser sysUser = null;
        try {
            sysUser = iSysUserService.getByName((String) token.getPrincipal());
        } catch (Exception e) {
            log.error("用户登录失败:{},{},{}", e.getStackTrace(), e.getMessage(), e.getCause());
        }
        if (sysUser == null) {
            throw new AuthenticationException("您未登录");
        }

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(SystemConstant.SESSION_USER_INFO_KEY, sysUser);
        //密码加密对比
        return new SimpleAuthenticationInfo(
                token.getPrincipal(),
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getUsername()),
                getName());
    }
}
