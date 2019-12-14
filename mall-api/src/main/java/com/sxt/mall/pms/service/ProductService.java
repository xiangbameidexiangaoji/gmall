package com.sxt.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.pms.entity.Product;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductQueryParam;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface ProductService extends IService<Product> {
    /**
     * 根据复杂查询条件,返回分页数据
     * @param pmsProductQueryParam
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam pmsProductQueryParam);
}
