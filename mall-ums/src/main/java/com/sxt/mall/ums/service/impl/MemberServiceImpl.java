package com.sxt.mall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.ums.entity.Member;
import com.sxt.mall.ums.mapper.MemberMapper;
import com.sxt.mall.ums.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author
 * @since 2019-12-06
 */
@Service
@Component
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member login(String username, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Member member = this.memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("username", username)
                .eq("password", md5Password));
        return member;
    }
}
