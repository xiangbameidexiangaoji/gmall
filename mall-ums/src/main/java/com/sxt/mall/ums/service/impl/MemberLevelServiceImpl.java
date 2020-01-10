package com.sxt.mall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.ums.entity.MemberLevel;
import com.sxt.mall.ums.mapper.MemberLevelMapper;
import com.sxt.mall.ums.service.MemberLevelService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 会员等级表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
@Component
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

}
