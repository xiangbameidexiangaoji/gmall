package com.sxt.mall.pms.service;

import com.sxt.mall.pms.entity.ProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.vo.PageInfoVo;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    PageInfoVo pageInfo(Integer pageSize, Integer pageNum);
}
