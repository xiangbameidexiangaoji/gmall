package com.sxt.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.mall.pms.entity.ProductCategory;
import com.sxt.mall.vo.product.PmsProductCategoryWithChildrenItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Repository
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i);
}
