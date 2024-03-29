package com.sxt.mall.ums.service;

import com.sxt.mall.ums.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface MemberService extends IService<Member> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Member login(String username, String password);
}
