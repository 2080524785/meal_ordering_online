package com.pro.www.controller;


import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.common.BaseContext;
import com.pro.www.dto.OrdersDto;
import com.pro.www.dto.R;
import com.pro.www.entity.OrderDetail;
import com.pro.www.entity.Orders;
import com.pro.www.entity.ShoppingCart;
import com.pro.www.entity.User;
import com.pro.www.service.impl.OrderDetailServiceImpl;
import com.pro.www.service.impl.OrdersServiceImpl;
import com.pro.www.service.impl.ShoppingCartServiceImpl;
import com.pro.www.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.awt.image.BandCombineOp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@Api(tags = "订单管理")
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersServiceImpl ordersService;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/submit")
    @ApiOperation("提交订单")
    public R<Orders> submit(@RequestBody Orders orders){
        orders = ordersService.saveWithOrder(orders);
        log.info("[INFO] 下单成功");
        return R.success(orders);
    }

    @GetMapping("/userPage")
    @ApiOperation("查询历史订单")
    public R<Page> userPage(int page, int pageSize){
        Long userId = BaseContext.getCurrentId();
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();

        ordersLambdaQueryWrapper.eq(userId!=null,Orders::getUserId,userId);
        ordersLambdaQueryWrapper.orderByAsc(Orders::getStatus).orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo,ordersLambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        List<OrdersDto> list = pageInfo.getRecords().stream().map((item)->{
            OrdersDto ordersDto = new OrdersDto();
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId,orderId);
            List<OrderDetail> details = orderDetailService.list(queryWrapper);
            BeanUtils.copyProperties(item,ordersDto);
            ordersDto.setOrderDetails(details);
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);
        log.info("[INFO] 查询订单记录成功");
        return R.success(ordersDtoPage);


    }
    @GetMapping("/page")
    @ApiOperation("查询订单明细")
    public R<Page> page(int page, int pageSize, Long number, String beginTime, String endTime){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();

        ordersLambdaQueryWrapper.orderByAsc(Orders::getStatus).orderByDesc(Orders::getOrderTime);
        ordersLambdaQueryWrapper.eq(number!=null,Orders::getNumber,number);
        ordersLambdaQueryWrapper.ge(beginTime!=null,Orders::getOrderTime,beginTime);
        ordersLambdaQueryWrapper.ge(endTime!=null,Orders::getOrderTime,endTime);

        ordersService.page(pageInfo,ordersLambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        List<OrdersDto> list = pageInfo.getRecords().stream().map((item)->{
            OrdersDto ordersDto = new OrdersDto();
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId,orderId);
            List<OrderDetail> details = orderDetailService.list(queryWrapper);
            BeanUtils.copyProperties(item,ordersDto);
            ordersDto.setOrderDetails(details);
            Long uesrId = ordersDto.getUserId();
            User user = userService.getById(uesrId);
            if (StringUtils.isNotEmpty(user.getPhone())){
                ordersDto.setUserName(user.getPhone());
            }


            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);
        log.info("[INFO] 查询订单记录成功");
        return R.success(ordersDtoPage);


    }
    @PostMapping("/again")
    @ApiOperation("再来一单")
    public R<String> again(@RequestBody Map<String,String> map){
        Long orderId = Long.valueOf(map.get("id"));
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,orderId);
        List<OrderDetail> details = orderDetailService.list(queryWrapper);

        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = details.stream().map((item)->{
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(item,shoppingCart);
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return  shoppingCart;
        }).collect(Collectors.toList());
        shoppingCartService.saveBatch(shoppingCartList);
        log.info("[INFO] 再次下单成功");
        return R.success("再次下单成功");

    }
    @PutMapping
    @ApiOperation("订单状态修改")
    public R<String> changeStatus(@RequestBody Map<String,String> map){
        int status = Integer.parseInt(map.get("status"));
        Long orderId = Long.valueOf(map.get("id"));
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId,orderId);
        updateWrapper.set(Orders::getStatus,status);
        ordersService.update(updateWrapper);
        return R.success("订单修改状态成功");
    }


    @PostMapping("/pay")
    @ApiOperation("支付订单")
    public R<String> pay(@RequestBody Orders orders){
        orders.setStatus(2);
        orders.setCheckoutTime(LocalDateTime.now());
        ordersService.updateById(orders);
        log.info("[INFO] 支付成功");
        return R.success("支付成功");
    }


}
