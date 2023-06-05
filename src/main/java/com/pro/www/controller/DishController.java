package com.pro.www.controller;


import com.pro.www.dto.DishDto;
import com.pro.www.dto.R;
import com.pro.www.service.impl.DishFlavorServiceImpl;
import com.pro.www.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Controller
@Slf4j
@RequestMapping("/dish")
@Api(tags = "菜品管理")
public class DishController {
    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private DishFlavorServiceImpl dishFlavorService;

    @PostMapping
    @ApiOperation(value = "添加菜品")
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("[INFO] 新增菜品成功,{}",dishDto.toString());

        return null;
    }
}
