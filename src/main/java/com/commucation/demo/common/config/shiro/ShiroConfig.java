package com.commucation.demo.common.config.shiro;

import com.commucation.demo.common.config.filter.CORSAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(14);
        return hashedCredentialsMatcher;
    }
    /**
     * 注入自定义的 Realm
     * @return myRealm
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm myRealm = new ShiroRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        log.info("========myRealm注册完成============");
        return myRealm;
    }
    @Bean
    public SessionManager sessionManager(){
        ShiroSession shiroSession = new ShiroSession();
        shiroSession.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSession;
    }
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(shiroRealm());
        securityManager.setSessionManager(sessionManager());
        log.info("====securityManager注册完成====");
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("ShiroConfig.shirFilter() start ...");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> customFilterMap = new LinkedHashMap<>();
        customFilterMap.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(customFilterMap);

        shiroFilterFactoryBean.setLoginUrl("/unauth");

        // 配置顺序拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/static/**", "anon");

        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/changePassword", "anon");
        filterChainDefinitionMap.put("/websocket/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("ShiroConfig.shirFilter() end ...");
        return shiroFilterFactoryBean;
    }
    /**
     * 异常处理
     **/
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "403");
        exceptionResolver.setExceptionMappings(mappings);
        exceptionResolver.setDefaultErrorView("error");
        exceptionResolver.setExceptionAttribute("ex");
        return exceptionResolver;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }
}
