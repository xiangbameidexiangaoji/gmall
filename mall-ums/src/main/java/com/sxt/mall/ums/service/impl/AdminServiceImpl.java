package com.sxt.mall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.ums.entity.Admin;
import com.sxt.mall.ums.entity.UmsAdminParam;
import com.sxt.mall.ums.mapper.AdminMapper;
import com.sxt.mall.ums.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Component
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("username", username).eq("password", s);
        Admin admin = this.adminMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public Admin getUserInfo(String userName) {
        return this.adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", userName));
    }

    @Override
    public Admin register(UmsAdminParam umsAdminParam) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(umsAdminParam, admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        //查询是否有相同用户名的用户
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("username", admin.getUsername());
        Integer integer = this.adminMapper.selectCount(wrapper);
        if(integer > 0){
            return null;
        }
        //将密码进行加密操作
        String password = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
        admin.setPassword(password);
        this.adminMapper.insert(admin);
        return admin;
    }

    @Override
    public Integer delete(Long id) {
        //先将用户信息查询出来
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>().eq("id", id);
        Admin admin = this.adminMapper.selectOne(queryWrapper);
        if(admin != null){
            admin.setStatus(0); //将账号设置成未启用状态
            return this.adminMapper.update(admin, queryWrapper);
        }
        return null;
    }
}
