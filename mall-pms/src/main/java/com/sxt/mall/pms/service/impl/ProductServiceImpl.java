package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.*;
import com.sxt.mall.pms.mapper.*;
import com.sxt.mall.pms.service.ProductService;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductParam;
import com.sxt.mall.vo.product.PmsProductQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    private ProductAttributeValueMapper productAttributeValueMapper;    //属性值 Mapper
    @Autowired
    private ProductFullReductionMapper productFullReductionMapper;
    @Autowired
    private ProductLadderMapper productLadderMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    //当前线程共享同样数据
    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (param.getBrandId() != null) {
            wrapper.eq("brand_id", param.getBrandId());
        }
        if (StringUtils.isNotEmpty(param.getKeyword())) {
            wrapper.like("name", param.getKeyword());
        }
        if (param.getProductCategoryId() != null) {
            wrapper.eq("product_category_id", param.getProductCategoryId());
        }
        if (StringUtils.isNotEmpty(param.getProductSn())) {
            wrapper.like("product_sn", param.getProductSn());
        }
        if (param.getPublishStatus() != null) {
            wrapper.eq("publish_status", param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null) {
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveProduct(PmsProductParam productParam) {
        //代理对象
        ProductServiceImpl proxy = (ProductServiceImpl) AopContext.currentProxy();
        //保存商品基本信息
        proxy.saveBaseInfo(productParam);
        //商品属性值
        proxy.saveProductAttributeValue(productParam);
        //满减信息
        proxy.saveFullReduction(productParam);
        //满减
        proxy.saveProductLadder(productParam);
        //库存
        proxy.saveSkuStock(productParam);
    }

    /**
     * 抽取方法，保存商品基础信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveBaseInfo(PmsProductParam productParam){
        Product product = com.sxt.util.BeanUtils.copyPropertiesChaining(productParam, Product::new);
        this.productMapper.insert(product);
        threadLocal.set(product.getId());   //将商品 id 存放到线程中。
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductAttributeValue(PmsProductParam productParam){
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach((item) -> {
            item.setId(threadLocal.get());
            this.productAttributeValueMapper.insert(item);
        });
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFullReduction(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach((reduction) -> {
            reduction.setProductId(threadLocal.get());
            this.productFullReductionMapper.insert(reduction);
        });
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductLadder(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach((ladder) -> {
            ladder.setProductId(threadLocal.get());
            this.productLadderMapper.insert(ladder);
        });
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSkuStock(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        skuStockList.forEach((skuStock) -> {
            //如果没有 sku ,生成一个随机 sku 值
            if (org.springframework.util.StringUtils.isEmpty(skuStock.getSkuCode())) {
                //生成规则，每一个 sku 关联一个商品，每一个商品 id 加上 sku 自增 id。
            }
            skuStock.setProductId(threadLocal.get());
            this.skuStockMapper.insert(skuStock);
        });
    }
}
