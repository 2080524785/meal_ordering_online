package com.pro.www.service.impl;

import com.pro.www.entity.Category;
import com.pro.www.mapper.CategoryMapper;
import com.pro.www.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
