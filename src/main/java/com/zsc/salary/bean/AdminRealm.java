package com.zsc.salary.bean;

import com.zsc.salary.model.pojo.Admin;
import com.zsc.salary.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

/**
 * <p>
 * 配置 shiro 授权和认证
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/26 11:18
 */
@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("授权");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Admin currentUser = (Admin) subject.getPrincipal();
        simpleAuthorizationInfo.addStringPermission(currentUser.getPosition());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证");

        UsernamePasswordToken userToken =(UsernamePasswordToken) authenticationToken;
        log.info(String.valueOf(userToken));

        Admin admin = adminService.getAdmin(userToken.getUsername());

        if(admin == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(admin,admin.getPassword(),"");
    }
}
