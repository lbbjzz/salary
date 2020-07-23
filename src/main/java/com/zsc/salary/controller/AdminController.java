package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.pojo.Admin;
import com.zsc.salary.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author D
 * @since 2020-07-23
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping("/adminLogin")
    public GlobalResponse adminLogin (@RequestParam(value = "username", required = true) String username,
                                     @RequestParam(value = "password", required = true) String password) {

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        log.info(String.valueOf(subject));
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            Admin admin = adminService.getAdmin(username);
            log.warn(String.valueOf(admin));

            Map<String, Object> map = new HashMap<>();
            map.put("adminId", admin.getId());
            map.put("username", admin.getUsername());
            map.put("position", admin.getPosition());
            //生成token 并返回给前端
            map.put("token", adminService.createToken(admin));
            return GlobalResponse.success().data(map).message("登录成功！");
        }catch (UnknownAccountException e){
            return GlobalResponse.failed().code(4001).message("用户名错误！");
        }catch (IncorrectCredentialsException e){
            return GlobalResponse.failed().code(4002).message("密码错误！");
        }
    }

    @PostMapping("/changePassword")
    public GlobalResponse changePassword(String oldPassword, String password, String username) {

        if(password.equals(oldPassword)) {
            return GlobalResponse.failed().code(4002).message("两次密码一致！");
        }
        Boolean isRight = adminService.judgePassword(oldPassword, username);
        if(!isRight) {
            return GlobalResponse.failed().message("原始密码错误！");
        }
        int flag = adminService.updatePassword(password, username);
        if(flag == 1) {
            return GlobalResponse.success();
        }else {
            return GlobalResponse.failed().code(4001);
        }
    }

    @GetMapping("/logOut")
    public GlobalResponse logOut(Integer userId) {
        adminService.logOut(userId);
        return GlobalResponse.success().message("登出成功！");
    }
}

