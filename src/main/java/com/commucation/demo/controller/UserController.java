package com.commucation.demo.controller;

import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.Test;
import com.commucation.demo.entity.User;
import com.commucation.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/findUser/{id}")
    public Result findUser(@PathVariable(name = "id") Integer id){

        User user = userMapper.findById(id);
        return Result.success(user);
    }

    @GetMapping("/findAll")
    public Result findUser(){
        return Result.success((List<Object>) new ArrayList<Object>(userMapper.findAll()));
    }
}