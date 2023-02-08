//package com.wioyber.kele.core.config;
//
//import com.wioyber.kele.core.security.CustomAuthorization;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//
///**
// * @author cjg
// * @since 2023/1/13
// */
//@Configuration
//public class CustomShiroConfig {
//
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
//        /*
//            anon：无需认证就可以访问
//            authc：必须认证了才能让问
//            user：必须拥有，记住我功能，才能访问
//            perms：拥有对某个资源的权限才能访问
//            role：拥有某个角色权限才能访问
//         */
//        LinkedHashMap<String, String> filterLinkedMap = new LinkedHashMap<>();
//        filterLinkedMap.put("/info/login", "anon");
//        filterLinkedMap.put("/info/**", "authc");
//
//
//        filterFactoryBean.setFilterChainDefinitionMap(filterLinkedMap);
//        return filterFactoryBean;
//    }
//
//
//    @Bean
//    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("customAuthorization") CustomAuthorization customAuthorization) {
//        return new DefaultWebSecurityManager(customAuthorization);
//    }
//
//    @Bean
//    public CustomAuthorization customAuthorization() {
//        return new CustomAuthorization();
//    }
//}
