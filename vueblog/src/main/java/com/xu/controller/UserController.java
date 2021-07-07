package com.xu.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.common.lang.Result;
import com.xu.entity.User;
import com.xu.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    
    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.success(user);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result save(@Validated @RequestBody User user) {
        User temp = new User();
        if (userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername())) != null){
            return Result.fail("该用户已被注册！");
        }else {
            temp.setPassword(SecureUtil.md5(user.getPassword()));
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
            BeanUtil.copyProperties(user, temp, "password", "created", "status");
            userService.saveOrUpdate(temp);
            return Result.success(temp);
        }
    }
}
