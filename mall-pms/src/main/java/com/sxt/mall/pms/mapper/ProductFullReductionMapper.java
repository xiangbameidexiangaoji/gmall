package com.sxt.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.mall.pms.entity.ProductFullReduction;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 产品满减表(只针对同商品) Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Repository
public interface ProductFullReductionMapper extends BaseMapper<ProductFullReduction> {

}
