package com.pro.www.service;

import com.pro.www.dto.DishDto;
import com.pro.www.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
public interface DishService extends IService<Dish> {
    // 新增菜品，添加口味
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIDWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
}
