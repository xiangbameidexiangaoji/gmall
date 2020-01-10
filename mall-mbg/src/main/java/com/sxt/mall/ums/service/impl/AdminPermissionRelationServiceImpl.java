package com.sxt.mall.ums.service.impl;

import com.sxt.mall.ums.entity.AdminPermissionRelation;
import com.sxt.mall.ums.mapper.AdminPermissionRelationMapper;
import com.sxt.mall.ums.service.AdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
public class AdminPermissionRelationServiceImpl extends ServiceImpl<AdminPermissionRelationMapper, AdminPermissionRelation> implements AdminPermissionRelationService {

}
