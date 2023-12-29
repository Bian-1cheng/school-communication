package com.commucation.demo.mapper;

import com.commucation.demo.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TransportMapper {

    /**
     * 向MySql中插入新的用户信息
     */
    void insertUserInfo(Integer role_id, String username, LocalDateTime gmt_create, String identity_no, String password, String phone);
    /**
     * 向MySql中插入新的学生信息
     */
    void insertStudentInfo(String stu_no, String class_no, String college, String major, String dqszj);
    /**
     * 向MySql中插入新的教师信息
     */
    void insertTeacherInfo(String teacher_no, String belong_department);
    /**
     * 向MySql中插入新的课程信息
     */
    void insertCourseInfo(String xkkh, String teacher_id, String teacher_name, String course_name, String class_time, String class_add);
    /**
     * 通过选课课号查找课程Id
     */
    Integer findCourseIdByXKKH(String xkkh);
    /**
     * 创建新的课程群
     */
    void createGroup(String group_name, Integer course_id);
    /**
     * 通过课程Id查找对应课程群Id
     */
    Integer findGroupIdByCourseId(Integer course_id);
    /**
     *
     */
    void addGroupIdToCourseInfo(Integer group_id, Integer course_id);





    /**
     * 在MySql中用用户名查询老师
     */
    Teacher findTeacherByYHM(String yhm);
    /**
     * 在MySql中用学号查询学生
     */
    Student findStudentByXH(String xh);
    /**
     * 在MySql中用选课课号查询课程
     */
    Course findCourseByXKKH(String xkkh);
    /**
     * 在MySql中用选课课号和学号来查询选课记录
     */
    StuCourse findStuCourseByXHAndXKKH(String xh, String xkkh);
    /**
     * 在MySql中用选课课号和学号来查询选课记录s
     */
    List<StuCourse> findStuCourseByXHAndXKKHs(String xh, String xkkh);
    /**
     * 向MySql中插入新的学生选课信息
     */
    void insertStuCourseInfo(Integer stu_id, Integer course_id, LocalDateTime xksj);
    /**
     * 将一条选课记录逻辑删除
     */
    void deleteStuCourseById(Integer id);
    /**
     * 修改成绩录入状态
     */
    void changeGradeStatus(Integer course_id, String status);
    /**
     * 修改MySql中学生成绩
     */
    void changeGrade(String identity_no, String xkkh, String cj, String jd);
    /**
     * 插入系统消息
     */
    void insertSystemNote (String content, LocalDateTime gmt_create, String sender, Integer user_id, String dir, String title);
    /**
     * 查找系统消息的最大id
     */
    Integer findMaxIdOfSystemNote();
    /**
     * 通过消息内容和发布时间查询系统消息
     */
    SystemNote findSystemNoteByContentAndTime(String content, LocalDateTime gmt_create);
    /**
     * 查找教师数量
     */
    Integer findTeacherNum();
    /**
     * 修改教师数量
     */
    void updateTeacherNum(Integer num);
    /**
     * 查找学生数量
     */
    Integer findStudentNum();
    /**
     * 修改学生数量
     */
    void updateStudentNum(Integer num);
    /**
     * 查找教学任务数量
     */
    Integer findJXRWBVIEWNum();
    /**
     * 修改教学任务数量
     */
    void updateJXRWBVIEWNum(Integer num);
    /**
     * 插入用户与群组的关系信息
     */
    void insertUserGroupInfo(Integer user_id, Integer group_id);
    /**
     *
     */
    void updateStrip(String xn, String xq);
}
