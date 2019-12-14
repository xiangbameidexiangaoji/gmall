package com.sxt.mall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.ums.entity.Admin;
import com.sxt.mall.ums.entity.UmsAdminParam;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的Token
     */
    Admin login(String username, String password);

    /**
     * 获取当前登录用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    Admin getUserInfo(String userName);

    /**
     * 注册功能
     */
    Admin register(UmsAdminParam umsAdminParam);

    /**
     * 删除用户,逻辑删除。
     * @param id
     * @return
     */
    Integer delete(Long id);

}
