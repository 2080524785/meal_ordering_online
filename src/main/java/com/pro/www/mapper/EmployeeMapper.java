package com.pro.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.www.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
