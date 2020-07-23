package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author D
 * @since 2020-07-23
 */
public interface AdminService extends IService<Admin> {

    /**
     * 根据账号获取管理员信息
     * @param username 账号
     * @return 用户信息
     */
    Admin getAdmin(String username);

    /**
     * 登录成功生成一个token ,并存在redis中
     * @param admin 管理员信息
     * @return token字符串
     */
    String createToken(Admin admin);

    /**
     * 判断的原始密码是否正确
     * @param oldPassword 旧密码
     * @param username 账号
     * @return 密码是否正确
     */
    Boolean judgePassword(String oldPassword, String username);

    /**
     *  修改密码
     * @param password 新密码
     * @param username 账号
     * @return 影响行数
     */
    int updatePassword(String password, String username);

    /**
     * 退出登录 删除缓存中的token
     * @param userId 用户id
     */
    void logOut(Integer userId);
}
