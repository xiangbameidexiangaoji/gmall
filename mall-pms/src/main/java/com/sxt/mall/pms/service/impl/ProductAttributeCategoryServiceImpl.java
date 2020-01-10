package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.ProductAttributeCategory;
import com.sxt.mall.pms.mapper.ProductAttributeCategoryMapper;
import com.sxt.mall.pms.service.ProductAttributeCategoryService;
import com.sxt.mall.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
@Component
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {

    @Autowired
    private ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public PageInfoVo pageInfo(Integer pageSize, Integer pageNum) {

        IPage<ProductAttributeCategory> productAttributeCategoryIPage = this.productAttributeCategoryMapper.selectPage(new Page<ProductAttributeCategory>(pageNum, pageSize), null);

        return PageInfoVo.getPage(productAttributeCategoryIPage, pageSize.longValue());
    }
}
