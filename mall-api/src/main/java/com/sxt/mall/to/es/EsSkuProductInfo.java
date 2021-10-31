package com.sxt.mall.to.es;

import com.sxt.mall.pms.entity.SkuStock;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sku详情
 */
@Data
public class EsSkuProductInfo extends SkuStock implements Serializable {
    /**
     * sku 的特定标题
     */
    private String skuTitle;
    /**
     * 黑色，
     * 128G
     * 每个 sku 不同的属性以及它的值
     */
    List<EsProductAttributeValue> attributeValues;
}
