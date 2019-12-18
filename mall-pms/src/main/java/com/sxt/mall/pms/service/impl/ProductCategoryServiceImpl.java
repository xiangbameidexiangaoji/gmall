package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.ProductCategory;
import com.sxt.mall.pms.mapper.ProductCategoryMapper;
import com.sxt.mall.pms.service.ProductCategoryService;
import com.sxt.mall.vo.product.PmsProductCategoryWithChildrenItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Override
    public List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i) {

        return this.productCategoryMapper.listCatelogWithChilder(i);
    }
}
