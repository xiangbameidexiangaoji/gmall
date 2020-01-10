package com.sxt.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.mall.pms.entity.ProductLadder;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Repository
public interface ProductLadderMapper extends BaseMapper<ProductLadder> {

}
