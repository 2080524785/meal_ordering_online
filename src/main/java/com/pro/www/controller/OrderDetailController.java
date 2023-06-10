package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pro.www.dto.R;
import com.pro.www.entity.OrderDetail;
import com.pro.www.entity.Orders;
import com.pro.www.service.impl.OrderDetailServiceImpl;
import com.pro.www.service.impl.OrdersServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/orderDetail")
public class OrderDetailController {


}
