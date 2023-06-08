package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.dto.DishDto;
import com.pro.www.dto.R;
import com.pro.www.entity.Category;
import com.pro.www.entity.Dish;
import com.pro.www.entity.DishFlavor;
import com.pro.www.service.impl.CategoryServiceImpl;
import com.pro.www.service.impl.DishFlavorServiceImpl;
import com.pro.www.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/dish")
@Api(tags = "菜品管理")
public class DishController {
    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private DishFlavorServiceImpl dishFlavorService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    @ApiOperation(value = "添加菜品")
    public R<String> save(@RequestBody DishDto dishDto){

        dishService.saveWithFlavor(dishDto);
        log.info("[INFO] 新增菜品成功,{}",dishDto.toString());

        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    @ApiOperation("查询菜品")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.eq(Dish::getIsDeleted,0);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> recoreds = pageInfo.getRecords();
        List<DishDto> list = recoreds.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if(category==null){
                return dishDto;
            }

            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIDWithFlavor(id);
        return R.success(dishDto);
    }
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public R<String> update(@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);
        log.info("[INFO] 新增菜品成功,{}",dishDto.toString());

        return R.success("新增菜品成功");
    }
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改菜品状态")
    public R<String> status(@PathVariable int status,@RequestParam Long[] ids){
        List<Dish> dishList =dishService.listByIds(Arrays.asList(ids));
        dishList.stream().map((item)->{
           item.setStatus(status);
           return item;
        }).collect(Collectors.toList());
        dishService.updateBatchById(dishList);
        log.info("[INFO] 修改菜品状态成功");
        return R.success("修改成功");

    }

    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public R<String> status(@RequestParam List<Long> ids){
        dishService.removeWithDish(ids);
        log.info("[INFO] 删除套餐成功");
        return R.success("删除成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询不同种类菜品")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);



        List<DishDto> dishDtos = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categpryId = item.getCategoryId();
            Category category = categoryService.getById(categpryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        log.info("[INFO] 查询菜品以及口味信息成功");
        return R.success(dishDtos);

    }

}
