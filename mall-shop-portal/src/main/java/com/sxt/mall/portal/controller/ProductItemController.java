package com.sxt.mall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sxt.mall.pms.service.ProductService;
import com.sxt.mall.to.CommonResult;
import com.sxt.mall.to.es.EsProduct;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {""})
public class ProductItemController {

    @Reference
    private ProductService productService;

    /**
     * 商品详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public CommonResult productInfo(@PathVariable("id")Long id){
        EsProduct esProduct = this.productService.productAllInfo(id);
        return new CommonResult().success(esProduct);
    }

    @GetMapping("/detail/sku/{id}")
    public CommonResult productSkuInfo(@PathVariable("id") Long id){
        EsProduct esProduct = this.productService.productSkuInfo(id);
        return new CommonResult().success(esProduct);
    }
}
