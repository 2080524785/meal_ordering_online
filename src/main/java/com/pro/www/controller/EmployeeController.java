package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.www.entity.Employee;
import com.pro.www.pojo.R;
import com.pro.www.service.EmployeeService;
import com.pro.www.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工管理")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp == null){
            return R.error("登录失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }


        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "员工退出登录")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    @PostMapping
    @ApiOperation(value = "添加员工")
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);

        return R.success("新增员工成功");
    }


    @GetMapping("/page")
    @ApiOperation(value = "员工信息查询")
    public R<Page> page(int page, int pageSize, String name){
        log.info("[INFO] page = {},pageSize = {},name = {}" ,page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
    @PutMapping
    @ApiOperation(value = "员工信息修改")
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        Long id = (Long) request.getSession().getAttribute("employee");

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(id);
        employeeService.updateById(employee);

        log.info("[INFO] 修改员工信息成功");

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询员工信息")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);

        log.info("[INFO] 查询成功,员工信息:{}",employee.toString());
        if(employee!=null){
            log.info("[INFO] 查询成功,员工信息:{}",employee.toString());

            return R.success(employee);
        }
        log.error("[ERROR] 查询失败");

        return R.error("查询失败");
    }


}
