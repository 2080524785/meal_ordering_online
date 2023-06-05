package com.pro.www.service.impl;

import com.pro.www.entity.User;
import com.pro.www.mapper.UserMapper;
import com.pro.www.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
