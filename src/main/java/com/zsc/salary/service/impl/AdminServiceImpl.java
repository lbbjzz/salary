package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zsc.salary.model.pojo.Admin;
import com.zsc.salary.mapper.AdminMapper;
import com.zsc.salary.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.utils.RedisUtil;
import com.zsc.salary.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-23
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private TokenUtil tokenUtil;

    /**
     * 保存用户token的key值 token:ID
     */
    private final String tokenKey = "token:";

    /**
     * 保存用户key值 admin:ID
     */
    private final String adminKey = "admin:";

    @Override
    public Admin getAdmin(String username) {
        log.info(username);
        Admin admin = null;
        String key = adminKey + username;

        //判断缓存是否存在
        if(redisUtil.hasKey(key)) {
            log.info("缓存");
            admin = (Admin)redisUtil.get(key);
        }else {
            //从数据库中查找
            admin = adminMapper.selectOne(
                    new QueryWrapper<Admin>().
                            eq("username", username)
            );
            if(admin !=null ){
                //存入缓存 24小时
                redisUtil.set(key, admin, 24);
            }

        }
        return admin;
    }

    @Override
    public String createToken(Admin admin) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", admin.getId());
        map.put("username", admin.getUsername());
        map.put("password", admin.getPassword());
        map.put("position", admin.getPosition());
        String token = tokenUtil.createToken(map);

        String key = tokenKey + admin.getId();
        //设置保存时间24小时
        redisUtil.set(key, token, 24);
        return token;
    }

    @Override
    public Boolean judgePassword(String oldPassword, String username) {
        Admin admin = new Admin();
        String key = adminKey + username;
        if(redisUtil.hasKey(key)) {
            admin = (Admin) redisUtil.get(key);
        }else {
            admin = adminMapper.selectOne(new QueryWrapper<Admin>()
                    .eq("username", username));
        }
        return oldPassword.equals(admin.getPassword());
    }

    @Override
    public int updatePassword(String password, String username) {
        String key = adminKey + username;
        int flag = adminMapper.update(null, new UpdateWrapper<Admin>()
                .eq("username", username)
                .set("password", password));
        if(redisUtil.hasKey(key)) {
            redisUtil.del(key);
        }
        return flag;
    }

    @Override
    public void logOut(Integer userId) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String key = tokenKey + userId;
        redisUtil.del(key);
    }
}
