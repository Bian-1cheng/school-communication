package com.commucation.demo.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer role_id;
    //学生信息
    private String stu_no;
    private String username;
    private String class_no;
    private String college;
    private String major;
    //教师信息
    private String belong_department;
    private String tutor_course;
    private String tutor_class;
    private String finished_class;
    private String teacher_title;
    private String self_introduce;
    //管理员信息
    private Integer level;
    private String work_no;

    public UserInfo() {}

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getStu_no() {
        return stu_no;
    }

    public void setStu_no(String stu_no) {
        this.stu_no = stu_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBelong_department() {
        return belong_department;
    }

    public void setBelong_department(String belong_department) {
        this.belong_department = belong_department;
    }

    public String getTutor_course() {
        return tutor_course;
    }

    public void setTutor_course(String tutor_course) {
        this.tutor_course = tutor_course;
    }

    public String getTutor_class() {
        return tutor_class;
    }

    public void setTutor_class(String tutor_class) {
        this.tutor_class = tutor_class;
    }

    public String getFinished_class() {
        return finished_class;
    }

    public void setFinished_class(String finished_class) {
        this.finished_class = finished_class;
    }

    public String getTeacher_title() {
        return teacher_title;
    }

    public void setTeacher_title(String teacher_title) {
        this.teacher_title = teacher_title;
    }

    public String getSelf_introduce() {
        return self_introduce;
    }

    public void setSelf_introduce(String self_introduce) {
        this.self_introduce = self_introduce;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }
}
