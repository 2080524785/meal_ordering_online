package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.dto.DishDto;
import com.pro.www.dto.R;
import com.pro.www.dto.SetmealDto;
import com.pro.www.entity.Category;
import com.pro.www.entity.Dish;
import com.pro.www.entity.Setmeal;
import com.pro.www.service.impl.CategoryServiceImpl;
import com.pro.www.service.impl.SetmealDishServiceImpl;
import com.pro.www.service.impl.SetmealServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishServiceImpl setmealDishService;
    @Autowired
    private SetmealServiceImpl setmealService;
    @Autowired
    private CategoryServiceImpl categoryService;


    @PostMapping
    @ApiOperation("套餐添加")
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        log.info("[INFO] 套餐添加成功,{}",setmealDto.toString());
        return R.success("套餐添加成功");
    }

    @GetMapping("/page")
    @ApiOperation(value = "查询套餐")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> page1 = new Page<>(page,pageSize);
        Page<SetmealDto> page2 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.eq(Setmeal::getIsDeleted,0);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(page1,queryWrapper);
        BeanUtils.copyProperties(page1,page2,"records");
        List<Setmeal> records = page1.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        page2.setRecords(list);
        log.info("[INFO] 套餐查询成功");
        return R.success(page2);

    }

    @DeleteMapping
    @ApiOperation(value = "删除套餐")
    public R<String> delete(@RequestParam List<Long> ids){

        setmealService.removeWithDish(ids);

        log.info("[INFO] 套餐删除成功");
        return R.success("套餐删除成功");
    }
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改套餐状态")
    public R<String> status(@PathVariable int status,@RequestParam List<Long> ids){
        List<Setmeal> list = setmealService.listByIds(ids);
        list.stream().map((item)->{
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        setmealService.updateBatchById(list);
        log.info("[INFO] 修改套餐状态成功");
        return R.success("修改成功");

    }
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询套餐")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getByIDWithFlavor(id);
        return R.success(setmealDto);
    }
    @PutMapping
    @ApiOperation(value = "修改套餐")
    public R<String> update(@RequestBody SetmealDto setmealDto){

        setmealService.updateWithFlavor(setmealDto);
        log.info("[INFO] 新增菜品成功,{}",setmealDto.toString());

        return R.success("新增菜品成功");
    }

}
