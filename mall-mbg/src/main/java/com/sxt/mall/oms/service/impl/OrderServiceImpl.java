package com.sxt.mall.oms.service.impl;

import com.sxt.mall.oms.entity.Order;
import com.sxt.mall.oms.mapper.OrderMapper;
import com.sxt.mall.oms.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
