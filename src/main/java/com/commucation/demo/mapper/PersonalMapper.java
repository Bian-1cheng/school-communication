package com.commucation.demo.mapper;

import com.commucation.demo.entity.Course;
import com.commucation.demo.entity.User;
import com.commucation.demo.entity.UserInfo;

import java.util.List;

public interface PersonalMapper {
    /**
     *查找用户课表
     */
    List<Course> getCurriculumStu(String identity_no);
    /**
     *
     */
    List<Course> getCurriculumTea(String identity_no);
    /**
     * 获取个人信息
     */
    UserInfo getMyInfo_student(String identityNo);

    /**
     * 获取教师信息
     */
    UserInfo getMyInfo_teacher(String identityNo);
    UserInfo getMyInfo_admin(String identityNo);
}
