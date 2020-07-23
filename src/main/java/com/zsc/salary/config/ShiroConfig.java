package com.zsc.salary.config;

import com.zsc.salary.bean.AdminRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * shiro 配置类
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/26 11:40
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger-resources","anon");
        filterMap.put("/v2/api-docs","anon");
        filterMap.put("/v2/api-docs-ext","anon");
        filterMap.put("/doc.html","anon");
        filterMap.put("/webjars/**","anon");

        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }


    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("adminRealm") AdminRealm adminRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminRealm);
        return securityManager;
    }

    @Bean
    public AdminRealm adminRealm(){
        return new AdminRealm();
    }
}
