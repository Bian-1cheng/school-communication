package com.commucation.demo.entity;




import java.io.Serializable;

public class Student implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String stu_no;
    private String class_no;
    //已完成的课程
    private String finished_course;
    private String major;
    private String college;
    private Integer is_deleted;

    public Student() {}

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStu_no() {
        return stu_no;
    }

    public void setStu_no(String stu_no) {
        this.stu_no = stu_no;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public String getFinished_course() {
        return finished_course;
    }

    public void setFinished_course(String finished_course) {
        this.finished_course = finished_course;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
