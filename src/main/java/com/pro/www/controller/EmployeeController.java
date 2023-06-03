package com.pro.www.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pro.www.entity.Employee;
import com.pro.www.pojo.R;
import com.pro.www.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        // md5 转码
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp==null){
            return R.error("帐号不存在，登陆失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误，登陆失败");
        }
        if(emp.getStatus()==0){
            return R.error("账号已禁用，登陆失败");
        }

        request.getSession().setAttribute("employee",emp.getId());
        log.info("[INFO] 登录成功，用户为"+emp.getName());
        return R.success(emp);





    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // clean Session
        request.getSession().removeAttribute("employee");
        log.info("[INFO] 退出成功");
        return R.success("退出成功");



    }
}
