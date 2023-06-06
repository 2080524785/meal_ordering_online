package com.pro.www.dto;

import com.pro.www.entity.Setmeal;
import com.pro.www.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
