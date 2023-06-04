package com.pro.www.service.impl;

import com.pro.www.entity.Employee;
import com.pro.www.mapper.EmployeeMapper;
import com.pro.www.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
