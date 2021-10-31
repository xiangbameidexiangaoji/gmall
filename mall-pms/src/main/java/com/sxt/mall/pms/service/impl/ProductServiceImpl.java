package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.constant.EsConstant;
import com.sxt.mall.pms.entity.*;
import com.sxt.mall.pms.mapper.*;
import com.sxt.mall.pms.service.ProductService;
import com.sxt.mall.to.es.EsProduct;
import com.sxt.mall.to.es.EsProductAttributeValue;
import com.sxt.mall.to.es.EsSkuProductInfo;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductParam;
import com.sxt.mall.vo.product.PmsProductQueryParam;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author
 * @since 2019-12-06
 */
@Service
@Slf4j
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
    //es
    @Autowired
    private JestClient jestClient;
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

    /**
     * 批量上下架
     *
     * @param ids           商品id数组
     * @param publishStatus 商品状态
     */
    @Override
    public void updatePublishStatus(List<Long> ids, Integer publishStatus) {
        if (publishStatus == 0) { //下架
            ids.forEach((id) -> {
                //1、修改商品上下架 ->数据库 '上架状态：0->下架；1->上架',
                setProductPublishStatus(publishStatus, id);
                //2、删除
                deleteProductFromEs(id);
            });
        } else {
            ids.forEach((id) -> {
                //1、修改商品上下架 ->数据库 '上架状态：0->下架；1->上架',
                setProductPublishStatus(publishStatus, id);
                //2、添加到 es
                saveProductToEs(id);
            });
        }
    }

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @Override
    public EsProduct productAllInfo(Long id) {
        EsProduct esProduct = null;
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("id", id));

        Search build = new Search.Builder(builder.toString())
                .addIndex(EsConstant.PRODUCT_ES_INDEX)
                .addType(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        try {
            SearchResult execute = this.jestClient.execute(build);
            List<SearchResult.Hit<EsProduct, Void>> hits = execute.getHits(EsProduct.class);
            esProduct = hits.get(0).source;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esProduct;
    }

    /**
     * 商品详情 sku
     *
     * @param id
     * @return
     */
    @Override
    public EsProduct productSkuInfo(Long id) {
        EsProduct esProduct = null;
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.nestedQuery("skuProductInfos", QueryBuilders.termQuery("skuProductInfos.id", id), ScoreMode.None));
        Search build = new Search.Builder(builder.toString())
                .addIndex(EsConstant.PRODUCT_ES_INDEX)
                .addType(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        try {
            SearchResult execute = this.jestClient.execute(build);
            List<SearchResult.Hit<EsProduct, Void>> hits = execute.getHits(EsProduct.class);
            esProduct = hits.get(0).source;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esProduct;
    }

    /**
     * 删除 es
     *
     * @param id
     */
    private void deleteProductFromEs(Long id) {
        Delete delete = new Delete.Builder(id.toString())
                .index(EsConstant.PRODUCT_ES_INDEX)
                .type(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        try {
            DocumentResult execute = this.jestClient.execute(delete);
            System.out.println(execute);
            if (execute.isSucceeded()) {
                log.info("Es商品下架成功,商品:{}", id);
            } else {
                log.error("Es商品下架失败,商品:{}", id);
            }
        } catch (IOException e) {
            log.error("Es商品下架失败,商品:{}", id);
        }
    }

    /**
     * 添加 es
     */
    private void saveProductToEs(Long id) {
        //查询商品详情
        Product productInfo = productInfo(id);
        //创建 Es 的传输对象
        EsProduct esProduct = new EsProduct();
        //复制基本信息
        BeanUtils.copyProperties(productInfo, esProduct);

        //2、对于 es 要保存的商品,先查询数据库
        List<SkuStock> stocks = this.skuStockMapper.selectList(new QueryWrapper<SkuStock>().eq("product_id", id));
        List<EsSkuProductInfo> esSkuProductInfos = new ArrayList<>(stocks.size());

        //查询当前商品的 sku 属性
        List<ProductAttribute> skuAttributeNames = this.productAttributeValueMapper.selectProductSaleAttrName(id);
        stocks.forEach((skuStock) -> {
            EsSkuProductInfo info = new EsSkuProductInfo();
            BeanUtils.copyProperties(skuStock, info);

            // sku 名称
            String subTitle = esProduct.getName();
            if (!StringUtils.isEmpty(skuStock.getSp1())) {
                subTitle += " " + skuStock.getSp1();
            }
            if (!StringUtils.isEmpty(skuStock.getSp2())) {
                subTitle += " " + skuStock.getSp2();
            }
            if (!StringUtils.isEmpty(skuStock.getSp3())) {
                subTitle += " " + skuStock.getSp3();
            }
            // sku 的特色标题
            info.setSkuTitle(esProduct.getName());

            // sku 的销售属性、颜色、尺码
            List<EsProductAttributeValue> skuAttributeValues = new ArrayList<>();

            for (int i = 0; i < skuAttributeNames.size(); i++) {
                EsProductAttributeValue value = new EsProductAttributeValue();
                value.setName(skuAttributeNames.get(i).getName());
                value.setProductId(id);
                value.setProductAttributeId(skuAttributeNames.get(i).getId());
                value.setType(skuAttributeNames.get(i).getType());
                if (i == 0) {
                    value.setValue(skuStock.getSp1());
                }
                if (i == 1) {
                    value.setValue(skuStock.getSp2());
                }
                if (i == 2) {
                    value.setValue(skuStock.getSp3());
                }
                skuAttributeValues.add(value);
            }

            info.setAttributeValues(skuAttributeValues);
            //sku有多个销售属性；颜色，尺码
            esSkuProductInfos.add(info);
        });
        //查出销售属性名称
        esProduct.setSkuProductInfos(esSkuProductInfos);

        List<EsProductAttributeValue> attributeValues = this.productAttributeValueMapper.selectProductBaseAttrAndValue(id);
        esProduct.setAttrValueList(attributeValues);


        try {
            //将商品保存到 es 中
            Index build = new Index.Builder(esProduct)
                    .index(EsConstant.PRODUCT_ES_INDEX)
                    .type(EsConstant.PRODUCT_INFO_ES_TYPE)
                    .id(id.toString())
                    .build();
            DocumentResult execute = this.jestClient.execute(build);
            boolean succeeded = execute.isSucceeded();
            if (succeeded) {
                log.info("ES中,id为{}商品上架完成", id);
            } else {
                log.error("ES中商品出错,未保存成功,开启重试");
            }

        } catch (Exception e) {
            log.error("ES商品id{}的数据保存异常:{}", id, e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setProductPublishStatus(Integer publishStatus, Long id) {
        Product product = new Product();
        //默认所有属性为null
        product.setId(id);
        product.setPublishStatus(publishStatus);
        this.productMapper.updateById(product);
    }

    @Override
    public Product productInfo(Long id) {
        return this.productMapper.selectById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveProduct(PmsProductParam productParam) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveBaseInfo(PmsProductParam productParam) {
        Product product = com.sxt.util.BeanUtils.copyPropertiesChaining(productParam, Product::new);
        this.productMapper.insert(product);
        threadLocal.set(product.getId());   //将商品 id 存放到线程中。
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductAttributeValue(PmsProductParam productParam) {
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
//        List<SkuStock> skuStockList = productParam.getSkuStockList();
//        skuStockList.forEach((skuStock) -> {
//            //如果没有 sku ,生成一个随机 sku 值
//            if (org.springframework.util.StringUtils.isEmpty(skuStock.getSkuCode())) {
//                //生成规则，每一个 sku 关联一个商品，每一个商品 id 加上 sku 自增 id。
//            }
//            System.out.println("---------");
//            skuStock.setProductId(threadLocal.get());
//            this.skuStockMapper.insert(skuStock);
//        });
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 1; i <= skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i - 1);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                //skuCode必须有  1_1  1_2 1_3 1_4
                //生成规则  商品id_sku自增id
                skuStock.setSkuCode(threadLocal.get() + "_" + i);
            }
            skuStock.setProductId(threadLocal.get());
            skuStockMapper.insert(skuStock);
        }
        log.debug("当前线程....{}-->{}", Thread.currentThread().getId(), Thread.currentThread().getName());
    }
}
