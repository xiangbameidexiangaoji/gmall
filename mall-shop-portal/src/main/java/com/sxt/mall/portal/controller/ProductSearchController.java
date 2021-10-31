package com.sxt.mall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sxt.mall.search.service.SearchProductService;
import com.sxt.mall.vo.search.SearchParam;
import com.sxt.mall.vo.search.SearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 商品检索
 */
@RestController
@Api(tags = {"检索服务"},description = "用于 es 数据检索")
public class ProductSearchController {
    @Reference
    private SearchProductService searchProductService;

    @GetMapping("/search")
    @ApiOperation(value = "获取es数据")
    public SearchResponse productSearchResponse(SearchParam searchParam) throws IOException {
        SearchResponse searchResponse = this.searchProductService.searchProduct(searchParam);
        return searchResponse;
    }

}
