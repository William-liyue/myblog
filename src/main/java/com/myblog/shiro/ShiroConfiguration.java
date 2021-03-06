package com.myblog.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * shiro总配置类
 * @author li192
 */
@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        // 未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 静态资源不拦截
        filterChainDefinitionMap.put("/backadmin/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");

        // 后台页面不拦截
        filterChainDefinitionMap.put("/admin/registry", "anon");
        // 前台页面不拦截
        filterChainDefinitionMap.put("/article/**", "anon");
        filterChainDefinitionMap.put("/about/**", "anon");
        filterChainDefinitionMap.put("/page/**", "anon");
        filterChainDefinitionMap.put("/links/**", "anon");
        filterChainDefinitionMap.put("/category/**", "anon");
        filterChainDefinitionMap.put("/archives/**", "anon");
        filterChainDefinitionMap.put("/search/**", "anon");
        filterChainDefinitionMap.put("/tag/**", "anon");
        filterChainDefinitionMap.put("/comment/**", "anon");
        filterChainDefinitionMap.put("/soulPainter.html/**", "anon");
        filterChainDefinitionMap.put("/custom/**", "anon");

        // swagger页面不拦截
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");


        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/admin/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        // 配置SecurityManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 将realm交给SecurityManager管理
        securityManager.setRealm(shiroRealm());
        // 将RememberMe交给SecurityManager管理
        securityManager.setRememberMeManager(rememberMeManager());
        // 开启redis缓存
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        // Shiro生命周期处理器
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroRealm shiroRealm() {
        // 配置Realm，需自己实现
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    /**
     * cookie对象
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input id="checkbox-signup" name="rememberMe" type="checkbox"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * 创建cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4bac2f5c17bbbb20ffd1248518fcbfe4=="));
        return cookieRememberMeManager;
    }


    /**
     * 开启@RequiresPermissions，@RequiresRoles，@RequiresGuest，@RequiresUser，@RequiresAuthentication等注解的使用
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * thymeleaf-shiro标签的使用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启redis缓存，用来缓存权限角色等信息
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        redisManager.getJedisPoolConfig().setMaxWaitMillis(-1);
        redisManager.getJedisPoolConfig().setMaxIdle(8);
        redisManager.getJedisPoolConfig().setMinIdle(0);
        return redisManager;
    }

    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

}
