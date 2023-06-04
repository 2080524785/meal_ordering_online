package com.pro.www.service.impl;

import com.pro.www.entity.OrderDetail;
import com.pro.www.mapper.OrderDetailMapper;
import com.pro.www.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
