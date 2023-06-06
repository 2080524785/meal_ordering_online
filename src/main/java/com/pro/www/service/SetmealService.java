package com.pro.www.service;

import com.pro.www.dto.DishDto;
import com.pro.www.dto.SetmealDto;
import com.pro.www.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
    public void updateWithFlavor(SetmealDto setmealDto);
    public SetmealDto getByIDWithFlavor(Long id);
}
