package com.sxt.mall.pms.service;

import com.sxt.mall.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.vo.product.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i);
}
