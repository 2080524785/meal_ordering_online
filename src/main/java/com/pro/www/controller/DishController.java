package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.dto.DishDto;
import com.pro.www.dto.R;
import com.pro.www.entity.Category;
import com.pro.www.entity.Dish;
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
}
