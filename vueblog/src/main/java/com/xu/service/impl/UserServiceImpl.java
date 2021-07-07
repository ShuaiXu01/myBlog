package com.xu.service.impl;

import com.xu.entity.User;
import com.xu.mapper.UserMapper;
import com.xu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
