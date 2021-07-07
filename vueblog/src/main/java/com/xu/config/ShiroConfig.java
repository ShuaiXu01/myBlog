package com.xu.config;

import com.xu.shiro.AccountRealm;
import com.xu.shiro.JwtFilter;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    
    @Autowired
    JwtFilter jwtFilter;

    /**
     * 会话管理，用户登录后，在没有退出之前，所有信息都在会话中
     * @param redisSessionDAO
     * @return
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) { //报红不用管
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //注入 redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * 配置安全管理器
     * 引入RedisSessionDAO和RedisCacheManager，让shiro的权限数据和会话信息能够保存到redis中，实现会话共享
     * @param accountRealm
     * @param sessionManager
     * @param redisCacheManager
     * @return
     */
    @Bean
    public SessionsSecurityManager securityManager(AccountRealm accountRealm, SessionManager sessionManager,
                                                   RedisCacheManager redisCacheManager) {
        //关联 AccountRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
        //注入 sessionManager
        securityManager.setSessionManager(sessionManager);
        //注入 redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * Shiro拦截器
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        
        Map<String,String> filterMap = new LinkedHashMap<>();
        //授权，所有请求需要 jwt 认证
        filterMap.put("/**","jwt");
        chainDefinition.addPathDefinitions(filterMap);
        
        return chainDefinition;
    }

    /**
     * ShiroFilterFactoryBean Shiro过滤器配置 连接前端
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //关联 SecurityManager，设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        
        //自定义过滤 jwt -> jwtFilter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        filterFactoryBean.setFilters(filters);
        
        //filterMap -> <"/**","jwt">
        Map<String,String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        
        //关联拦截器 shiroFilterChainDefinition  所有请求需要 jwt 认证
        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return filterFactoryBean;
    }
    
}
