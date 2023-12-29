package com.commucation.demo.entity;


import java.io.Serializable;

public class CourseGroup implements  Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String group_name;
    private String course_id;
    //选课总人数
    private String selected_num;
    //群组总人数
    private String total_num;
    //在线总人数
    private String online_num;
    private Integer is_deleted;

    public CourseGroup(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getSelected_num() {
        return selected_num;
    }

    public void setSelected_num(String selected_num) {
        this.selected_num = selected_num;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getOnline_num() {
        return online_num;
    }

    public void setOnline_num(String online_num) {
        this.online_num = online_num;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
