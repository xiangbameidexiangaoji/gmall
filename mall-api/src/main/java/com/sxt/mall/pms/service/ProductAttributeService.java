package com.sxt.mall.pms.service;

import com.sxt.mall.pms.entity.ProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.vo.PageInfoVo;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageSize, Integer pageNum);
}
