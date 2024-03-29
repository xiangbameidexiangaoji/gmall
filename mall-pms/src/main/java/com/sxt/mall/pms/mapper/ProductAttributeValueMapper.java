package com.sxt.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.mall.pms.entity.ProductAttribute;
import com.sxt.mall.pms.entity.ProductAttributeValue;
import com.sxt.mall.to.es.EsProductAttributeValue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 存储产品参数信息的表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Repository
public interface ProductAttributeValueMapper extends BaseMapper<ProductAttributeValue> {

    List<EsProductAttributeValue> selectProductBaseAttrAndValue(Long id);

    List<ProductAttribute> selectProductSaleAttrName(Long id);
}
