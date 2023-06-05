package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.entity.Category;
import com.pro.www.dto.R;
import com.pro.www.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/category")
@Api(tags = "分类管理")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    @ApiOperation(value = "新增分类")
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        log.info("[INFO] 保存新增分类成功");
        return R.success("添加成功");

    }

    @GetMapping("/page")
    @ApiOperation(value = "分类查询")
    public R<Page> page(int page, int pageSize){
        log.info("[INFO] page = {},pageSize = {}" ,page,pageSize);
        Page pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);

    }

    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public R<String> delete(@RequestParam(value = "ids") Long id){
        categoryService.remove(id);
        log.info("[INFO] 删除成功");
        return R.success("删除成功");
    }
    @PutMapping
    @ApiOperation(value = "修改分类")
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        log.info("[INFO] 修改成功，{}",category.toString());
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据条件查询分类")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list =  categoryService.list(queryWrapper);
        return R.success(list);
    }

}
