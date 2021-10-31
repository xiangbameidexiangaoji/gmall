package com.sxt.mall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sxt.mall.constant.EsConstant;
import com.sxt.mall.search.service.SearchProductService;
import com.sxt.mall.vo.search.SearchParam;
import com.sxt.mall.vo.search.SearchResponse;
import com.sxt.mall.vo.search.SearchResponseAttrVo;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Service
public class TestSearchProductServiceImpl implements SearchProductService {
    @Autowired
    private JestClient jestClient;



    @Override
    public SearchResponse searchProduct(SearchParam searchParam) throws IOException {

        //1、构建检索条件
        String dslSql = buildDsl(searchParam);
        //2、数据检索
        Search build = new Search.Builder("")
                .addIndex(EsConstant.PRODUCT_ES_INDEX)
                .addType(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        SearchResult execute = this.jestClient.execute(build);
        //3、将返回的SearchResult转为SearchResponse
        SearchResponse searchResponse = buildSearchResponse(execute);
        searchResponse.setPageNum(searchParam.getPageNum());
        searchParam.setPageSize(searchParam.getPageSize());
        return null;
    }

    private SearchResponse buildSearchResponse(SearchResult execute) {
        SearchResponse searchResponse = new SearchResponse();
//        searchResponse.setCatelog();可供选择的分类
        MetricAggregation aggregations = execute.getAggregations();
        TermsAggregation brand_agg = aggregations.getTermsAggregation("brand_agg");
        List<String> brandNames = new ArrayList<>();
        brand_agg.getBuckets().forEach((bucket)->{
            brandNames.add(bucket.getKeyAsString());    //将品牌的名字放到集合中
            TermsAggregation brandId = bucket.getTermsAggregation("brandId");
            brandId.getBuckets().forEach((s)->{
                s.getKey();
            });
        });
        SearchResponseAttrVo attrVo = new SearchResponseAttrVo();
        attrVo.setName("品牌");
        attrVo.setValue(brandNames);
        searchResponse.setCatelog(attrVo);
//        searchResponse.setProducts();//将查到的记录封装
//        searchResponse.setTotal(execute.getTotal());
//        searchResponse.setAttrs();//所有可以筛选的属性
//        searchResponse.setBrand(); //可供选择的品牌
        return searchResponse;
    }

    private String buildDsl(SearchParam searchParam) {
        //查询
            //1、must 必须
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(StringUtils.isNotEmpty(searchParam.getKeyword())){
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("skuProductInfos.skuTitle", searchParam.getKeyword());
            NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("skuProductInfos", matchQuery, ScoreMode.None);
            boolQuery.must(nestedQuery);
        }
            //2、filter 过滤
        if(searchParam.getCatelog3() != null && searchParam.getCatelog3().length > 0){
            //按照三级分类
            boolQuery.filter(QueryBuilders.termsQuery("productCategoryId", searchParam.getCatelog3()));
        }
        if(searchParam.getBrand() != null && searchParam.getBrand().length > 0){
            //按照品牌分类
            boolQuery.filter(QueryBuilders.termsQuery("brandName.keyword",searchParam.getBrand()));
        }
        if(searchParam.getProps() != null && searchParam.getProps().length > 0){
            //按照所有的筛选属性进行过滤
            //props=2:全高清&  如果前端想传入很多值    props=2:青年-老人-女士
            String[] props = searchParam.getProps();
            Arrays.stream(props)
                    .forEach((proList)->{
                        String[] split = proList.split(":");
                        BoolQueryBuilder must = QueryBuilders.boolQuery();
                        must.must(QueryBuilders.matchQuery("attrValueList.productAttributeId", split[0]));
                        must.must(QueryBuilders.termsQuery("attrValueList.value.keyword",split[1].split("-")));
                        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrValueList", must, ScoreMode.None);
                        boolQuery.filter(nestedQuery);
                    });
        }
        if(searchParam.getPriceFrom() != null || searchParam.getPriceTo() != null){
            //价格区间过滤
            RangeQueryBuilder price = QueryBuilders.rangeQuery("price");
            if(searchParam.getPriceFrom() != null ){
                price.gte(searchParam.getPriceFrom());  //大于
            }
            if(searchParam.getPriceTo() != null){
                price.lte(searchParam.getPriceTo());    //小于
            }
            boolQuery.filter(price);
        }
        builder.query();
        //聚合
            //按品牌聚合
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg").field("brandName.keyword");
        brand_agg.subAggregation(AggregationBuilders.terms("brandId").field("brandId"));
        builder.aggregation(brand_agg);
            //按分类聚合
        TermsAggregationBuilder category_agg = AggregationBuilders.terms("catgory_agg").field("productCategoryName.keyword");
        category_agg.subAggregation(AggregationBuilders.terms("categoryId_agg").field("productCategoryId"));
        builder.aggregation(category_agg);
            //按属性
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrValueList");
        NestedAggregationBuilder attrName_agg = attr_agg.subAggregation(AggregationBuilders.terms("attrName_agg").field("attrValueList.name"));
        //聚合看attrValue的值
        attrName_agg.subAggregation(AggregationBuilders.terms("attrValue_agg").field("attrValueList.value.keyword"));
        //聚合看attrId的值
        attrName_agg.subAggregation(AggregationBuilders.terms("attrId_agg").field("attrValueList.productAttributeId"));
        attr_agg.subAggregation(attrName_agg);
        builder.aggregation(attr_agg);
        //高亮
        if(StringUtils.isNotEmpty(searchParam.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuProductInfos.skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            builder.highlighter(highlightBuilder);
        }

        //分页
        builder.from((searchParam.getPageNum()-1) * searchParam.getPageSize());
        builder.size(searchParam.getPageSize());

        //排序
        if(StringUtils.isNotEmpty(searchParam.getOrder())){
            // order=1:asc  排序规则
            // 0：综合排序  1：销量  2：价格
            String order = searchParam.getOrder();
            String[] split = order.split(":");
            if(split[0] .equals("0")){

            }
            if(split[0].equals("1")){
                //设置排序字段，销量
                FieldSortBuilder sale = SortBuilders.fieldSort("sale");
                if(split[1].equalsIgnoreCase("asc")){
                    sale.order(SortOrder.ASC);
                }else{
                    sale.order(SortOrder.DESC);
                }
            }
            if(split[0].equals("2")){
                FieldSortBuilder price = SortBuilders.fieldSort("price");
                if(split[1].equalsIgnoreCase("asc")){
                    price.order(SortOrder.ASC);
                }else{
                    price.order(SortOrder.DESC);
                }
            }
        }
        return null;
    }
}
