package com.pro.www.service.impl;

import com.pro.www.entity.Orders;
import com.pro.www.mapper.OrdersMapper;
import com.pro.www.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
