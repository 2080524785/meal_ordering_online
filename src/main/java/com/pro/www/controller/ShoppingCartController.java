package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        log.info("[INFO] {}",userId.toString());
        shoppingCart.setUserId(userId);


        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        if(shoppingCart.getDishId()!=null){
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);
        if(shoppingCart1!=null){
            shoppingCart1.setNumber(shoppingCart1.getNumber()+shoppingCart.getNumber());
            shoppingCartService.updateById(shoppingCart1);
            log.info("[INFO] 已存在该菜品，数量+原来的");
            return R.success(shoppingCart1);
        }else{
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            log.info("[INFO] 添加菜品成功");
            return R.success(shoppingCart);
        }
    }
    @PostMapping("/sub")
    @ApiOperation("减少购物车数据")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        if(dishId!=null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
            ShoppingCart dishCart = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
            dishCart.setNumber(dishCart.getNumber()-1);
            if(dishCart.getNumber()>0){
                shoppingCartService.updateById(dishCart);
            }else{
                shoppingCartService.removeById(dishCart);
            }
            log.info("[INFO] 更新成功");
            return R.success(dishCart);
        }

        if(setmealId!=null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId,setmealId);
            ShoppingCart setmealCart = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
            setmealCart.setNumber(setmealCart.getNumber()-1);
            if(setmealCart.getDishId()>0){
                shoppingCartService.updateById(setmealCart);
            }else{
                shoppingCartService.removeById(setmealCart);
            }
            log.info("[INFO] 更新成功");
            return R.success(setmealCart);
        }
        return  R.error("更新失败");
    }
}
