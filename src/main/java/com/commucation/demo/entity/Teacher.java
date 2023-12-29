package com.commucation.demo.entity;




import java.io.Serializable;

public class Teacher implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String teacher_no;
    //所属单位
    private String belong_department;
    //执教课程
    private String tutor_course;
    //执教班级
    private String tutor_class;
    //已完成教学班
    private String finished_class;
    //教师职称
    private String teacher_title;
    //个人介绍
    private String self_introduce;
    private Integer is_deleted;

    public Teacher() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacher_no() {
        return teacher_no;
    }

    public void setTeacher_no(String teacher_no) {
        this.teacher_no = teacher_no;
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

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
