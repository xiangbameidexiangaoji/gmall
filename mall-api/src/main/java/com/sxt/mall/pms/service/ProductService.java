package com.sxt.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.pms.entity.Product;
import com.sxt.mall.to.es.EsProduct;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductParam;
import com.sxt.mall.vo.product.PmsProductQueryParam;

import java.util.List;

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
     * 查询商品详情
     * @param id 商品 id
     * @return
     */
    Product productInfo(Long id);

    /**
     * 根据复杂查询条件,返回分页数据
     * @param pmsProductQueryParam
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam pmsProductQueryParam);

    /**
     * 保存商品数据
     * @param productParam
     */
    void saveProduct(PmsProductParam productParam);

    /**
     * 批量上下架
     * @param ids
     * @param publishStatus
     */
    void updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 商品详情
     * @param id
     * @return
     */
    EsProduct productAllInfo(Long id);

    /**
     * 商品sku
     * @param id
     * @return
     */
    EsProduct productSkuInfo(Long id);
}
