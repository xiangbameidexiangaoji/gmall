package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.ProductAttribute;
import com.sxt.mall.pms.mapper.ProductAttributeMapper;
import com.sxt.mall.pms.service.ProductAttributeService;
import com.sxt.mall.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
@Component
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Override
    public PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        IPage<ProductAttribute> productAttributeIPage = this.productAttributeMapper.selectPage(new Page<ProductAttribute>(pageNum, pageSize),
                new QueryWrapper<ProductAttribute>()
                        .eq("product_attribute_category_id", cid)
                        .eq("type", type));
        return PageInfoVo.getPage(productAttributeIPage, pageSize.longValue());
    }
}
