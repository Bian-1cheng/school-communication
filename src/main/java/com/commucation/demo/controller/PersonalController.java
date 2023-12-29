package com.commucation.demo.controller;


import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.User;
import com.commucation.demo.entity.UserInfo;
import com.commucation.demo.mapper.PersonalMapper;
import com.commucation.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;


@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Resource
    private PersonalMapper personalMapper;
    @Resource
    private UserMapper userMapper;

    @GetMapping("/getMyCurriculum/{identity_no}")
    public Result getCurriculum(@PathVariable(name = "identity_no") String identity_no) {
        if (identity_no == null) {
            return Result.fail("用户名为空");
        }
        try {
            User userInfo= userMapper.findByUsername(identity_no);
            ArrayList<Object> curriculum = null;
            if (userInfo.getRole_id() == 3) {
                curriculum = new ArrayList<Object>(personalMapper.getCurriculumStu(identity_no));
            } else if (userInfo.getRole_id() == 2) {
                curriculum = new ArrayList<Object>(personalMapper.getCurriculumTea(identity_no));
            }
            return Result.success(curriculum);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/getMyInfo/{identity_no}")
    public Result getMyInfo(@PathVariable(name = "identity_no") String identity_no){
        if (identity_no == null) {
            return Result.fail("用户名为空");
        }
        try {
            User user = userMapper.findByUsername(identity_no);
            UserInfo myInfo = new UserInfo();
            if (user != null) {
                if (user.getRole_id() == 2) {
                    myInfo = personalMapper.getMyInfo_teacher(identity_no);
                } else if (user.getRole_id() == 3) {
                    myInfo = personalMapper.getMyInfo_student(identity_no);
                }
                return Result.success(myInfo);
            } else {
                return Result.fail("用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }
}
