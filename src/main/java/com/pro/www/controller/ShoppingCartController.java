package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pro.www.common.BaseContext;
import com.pro.www.dto.R;
import com.pro.www.entity.ShoppingCart;
import com.pro.www.service.ShoppingCartService;
import com.pro.www.service.impl.ShoppingCartServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
@Api(tags = "购物车")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @GetMapping("/list")
    @ApiOperation(value = "查询购物车")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        log.info("[INFO] 查询购物车成功");
        return R.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation(value = "清空购物车")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);
        log.info("[INFO] 购物车清空成功");
        return R.success("购物车清空成功");
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加购物车数据")
    public R<ShoppingCart> addShort(@RequestBody ShoppingCart shoppingCart){
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //TODO
        Long dishId = shoppingCart.getDishId();
        return R.success(null);
    }
}
