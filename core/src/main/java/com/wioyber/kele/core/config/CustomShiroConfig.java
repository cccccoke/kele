package com.wioyber.kele.core.config;

import com.wioyber.kele.core.security.CustomAuthorization;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjg
 * @since 2023/1/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shiro.filter")
public class CustomShiroConfig {

    Map<String, List<String>> path;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", new CustomShiroFilter());
        filterFactoryBean.setFilters(filters);

        LinkedHashMap<String, String> filterLinkedMap = new LinkedHashMap<>();
        path.forEach((k, v) -> {
            if (null != v && v.size() > 0) {
                v.forEach(i -> {
                    filterLinkedMap.put(i, k);
                });
            }
        });
        filterFactoryBean.setFilterChainDefinitionMap(filterLinkedMap);
        return filterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(
            @Qualifier("customAuthorization") CustomAuthorization customAuthorization,
            @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setSessionManager(sessionManager);
        webSecurityManager.setRealm(customAuthorization);
        return webSecurityManager;
    }

    @Bean
    public SessionManager sessionManager(@Qualifier("redisSessionDao") RedisSessionDao redisSessionDao) {
        CustomSessionManager sessionManager = new CustomSessionManager();
        Cookie cookie = new SimpleCookie("tk");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionDAO(redisSessionDao);
        return sessionManager;
    }

    @Bean
    public CustomAuthorization customAuthorization(@Qualifier("CustomHashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomAuthorization customAuthorization = new CustomAuthorization();
        customAuthorization.setCredentialsMatcher(hashedCredentialsMatcher);
        return customAuthorization;
    }

    @Bean("CustomHashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        //散列次数
        matcher.setHashIterations(1024);
        return matcher;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
