package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.Product;
import com.sxt.mall.pms.mapper.ProductMapper;
import com.sxt.mall.pms.service.ProductService;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author
 * @since 2019-12-06
 */
@Service
@Component
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (param.getBrandId() != null) {
            wrapper.eq("brand_id", param.getBrandId());
        }
        if(StringUtils.isNotEmpty(param.getKeyword())){
            wrapper.like("name", param.getKeyword());
        }
        if(param.getProductCategoryId() != null){
            wrapper.eq("product_category_id", param.getProductCategoryId());
        }
        if(StringUtils.isNotEmpty(param.getProductSn())){
            wrapper.like("product_sn", param.getProductSn());
        }
        if(param.getPublishStatus() != null){
            wrapper.eq("publish_status", param.getPublishStatus());
        }
        if(param.getVerifyStatus() != null){
            wrapper.eq("verify_status", param.getVerifyStatus());
        }

        IPage<Product> page = this.productMapper.selectPage
                (new Page<Product>(param.getPageNum(), param.getPageSize()), wrapper);

        PageInfoVo pageInfoVo = new PageInfoVo(
                page.getTotal()
                , page.getPages()
                , param.getPageSize()
                , page.getRecords()
                , page.getCurrent());

        return pageInfoVo;
    }
}
