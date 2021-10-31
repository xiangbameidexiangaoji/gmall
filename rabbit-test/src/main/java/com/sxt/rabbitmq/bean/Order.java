package com.sxt.rabbitmq.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 李涵林
 * @data 2020/7/14 19:26
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 购买商品的id
     */
    private Long skuId;

    /**
     * 购买商品的数量
     */
    private Integer num;

    /**
     * 购买者的id
     */
    private Integer memberId;
}
