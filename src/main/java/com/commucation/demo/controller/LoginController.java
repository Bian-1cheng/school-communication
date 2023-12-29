package com.commucation.demo.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.ChangePasswordDto;
import com.commucation.demo.entity.User;
import com.commucation.demo.mapper.PersonalMapper;
import com.commucation.demo.mapper.RelationMapper;
import com.commucation.demo.mapper.UserMapper;
import com.commucation.demo.util.PictureModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/")
public class LoginController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PersonalMapper personalMapper;

    @PostMapping ("/login")
    public Result login(@Validated @RequestBody User user){

        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getIdentity_no()), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        if (user.getIdentity_no() == null || user.getPassword() == null) {
            return Result.fail("用户名或密码为空");
        }
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            return Result.fail("密码错误!");
        }catch (AuthenticationException e){
            return Result.fail("用户不存在");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            User temp = userMapper.findByUsername(user.getIdentity_no());
            if (temp.getIs_online() == 0) {
                userMapper.changeLoginStatus(user.getIdentity_no());
            }
            if (temp.getOnline_devices() == null) {
                userMapper.updateUserOnlineDevices(1, temp.getIdentity_no());
            } else {
                userMapper.updateUserOnlineDevices(temp.getOnline_devices() + 1, temp.getIdentity_no());
            }
            User userInfo = userMapper.findByUsername(user.getIdentity_no());
            userInfo.setHead(new PictureModel(userInfo.getHeadDir()).getPic());
            userInfo.setToken(subject.getSession().getId());
            if (userInfo.getRole_id() == 2) {
                userInfo.setBelong_department(personalMapper.getMyInfo_teacher(userInfo.getIdentity_no()).getBelong_department());
            } else if (userInfo.getRole_id() == 3) {
                userInfo.setClass_no(userMapper.findStudentInfo(userInfo.getId()).getClass_no());
                userInfo.setMajor(userMapper.findStudentInfo(userInfo.getId()).getMajor());
            }

            SecurityUtils.getSubject().getSession().setTimeout(168 * 60 * 60 * 1000L);
            MapUtil.builder().put("token", subject.getSession().getId()).map();
//            return Result.success(MapUtil.builder().put("token", subject.getSession().getId()).map());
            return Result.success(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发生错误");
        }
        // return Result.success(MapUtil.builder().put("token", subject.getSession().getId()).map());

//        try {
//            User checkUser = userMapper.loginCheck(user.getIdentity_no(), user.getPassword());
//            if (checkUser != null) {
//                checkUser.setHead(new PictureModel(checkUser.getHeadDir()).getPic());
//                User user1 = userMapper.findByUsername(user.getIdentity_no());
//                if (user1.getIs_online() == 0) {
//                    userMapper.changeLoginStatus(user.getIdentity_no());
//                }
//                return Result.success(checkUser);
//            } else {
//                return Result.fail("用户名或密码不正确");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.fail("登录失败");
//        }
    }

    @GetMapping("/logout/{identityNo}")
    public Result logout(@PathVariable(name = "identityNo") String identityNo){
        if (identityNo == null) {
            return Result.fail("用户名为空");
        }
        try {
            User user = userMapper.findByUsername(identityNo);
            userMapper.updateUserOnlineDevices(user.getOnline_devices() - 1, user.getIdentity_no());
            if (user.getOnline_devices() == 1) {
                userMapper.changeLoginStatus(identityNo);
            }
            SecurityUtils.getSubject().logout();
            return Result.success("登出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("登出失败");
        }
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        UsernamePasswordToken token = new UsernamePasswordToken(changePasswordDto.getIdentityNo(), changePasswordDto.getBeforePassword());
        //获取认证主体
        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
//            return Result.fail("密码错误");
//        } catch (LockedAccountException e) {
//            return Result.fail("账号被冻结");
//        } catch (AuthenticationException e) {
//            return Result.fail("用户不存在");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        User user = userMapper.findByUsername(changePasswordDto.getIdentityNo());
        if (user == null) {
            return Result.fail("错误学号/教工号！");
        }

        String newPassword = (new SimpleHash("md5", changePasswordDto.getNewPassword(), user.getIdentity_no(), 14).toString());
        userMapper.changePassword(changePasswordDto.getIdentityNo(), newPassword);

        return Result.success("已成功修改密码！");
    }


}
