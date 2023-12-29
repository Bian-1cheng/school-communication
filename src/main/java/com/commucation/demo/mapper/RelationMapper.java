package com.commucation.demo.mapper;

import com.commucation.demo.entity.*;

import java.util.List;

public interface RelationMapper {
    /**
     * 查询用户所加入的课程群
     */
    List<Group> findGroups(String identityNo);
    /**
     * 查询用户已完成的课程
     * */
    Student findMyFinishedCourse(String identityNo);
    /**
     * 通过教师查询课程
     */
    List<Course> searchCourseByTeacherName(String name);
    /**
     * 通过课程名查询课程
     */
    List<Course> searchCourseByCourseName(String name);
    /**
     * 通过群id查找群头像
     */
    Group findGroupHeadByGroupId(Integer group_id);
    /**
     * 查询教师所在群聊
     */
    List<Group> findGroupsByTeacherNo(String identityNo);
    /**
     *
     */
    List<SearchDialogResult> searchDialog(String name, String identityNo);
}
