package com.sxt.mall.search.service;

import com.sxt.mall.vo.search.SearchParam;
import com.sxt.mall.vo.search.SearchResponse;

import java.io.IOException;

/**
 * 商品检索服务
 */
public interface SearchProductService {
    SearchResponse searchProduct(SearchParam searchParam) throws IOException;
}
