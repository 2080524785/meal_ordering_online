package com.pro.www.controller;


import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pro.www.common.BaseContext;
import com.pro.www.dto.R;
import com.pro.www.dto.SMS;
import com.pro.www.entity.User;
import com.pro.www.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "用户登录")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/sendMsg")
    @ApiOperation(value = "发送验证码")
    public R<String> sendMsg(@RequestParam String phone, HttpSession session){

        if(StringUtils.isNotEmpty(phone)){

            try {
                SMS sms = new SMS();
                sms.msgSend(phone);

                redisTemplate.opsForValue().set(phone,sms.getCode(),5, TimeUnit.MINUTES);
                log.info("[INFO] 验证码发送成功");
                return  R.success("验证码发送成功");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.error("[ERROR] 验证码发送失败");
        return R.error("验证码发送失败");

    }

    @GetMapping("/sendMsgTest")
    @ApiOperation(value = "发送验证码测试")
    // 在后端获取验证码测试，避免使用阿里云短信服务(还剩90）
    public R<String> sendMsgTest(@RequestParam String phone, HttpSession session){

        if(StringUtils.isNotEmpty(phone)){

            try {
                SMS sms = new SMS();
                sms.msgSendTest(phone);


                redisTemplate.opsForValue().set(phone,sms.getCode(),5,TimeUnit.MINUTES);
                log.info("[INFO] 验证码发送成功,{}",sms.getCode());
                return  R.success("验证码发送成功");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.error("[ERROR] 验证码发送失败");
        return R.error("验证码发送失败");

    }


    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        log.info("[INFO] 获取成功，电话为{}",phone);
        Object codeInSession =redisTemplate.opsForValue().get(phone);
        if(codeInSession!=null&&codeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user==null){
                log.info("[INFO] 新用户注册成功");
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);

            }
            session.setAttribute("user",user.getId());
            log.info("[INFO] 登录成功");
            BaseContext.setCurrentId(user.getId());
            redisTemplate.delete(phone);

            return R.success(user);
        }
        log.error("[ERROR] 验证码错误");
        return R.error("验证码错误");


    }

    @PostMapping("/loginout")
    @ApiOperation("用户登出")
    public R<String> logout(HttpServletRequest request){
        log.info("[INFO] 用户{} 登出",request.getSession().getAttribute("user"));
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }
}
