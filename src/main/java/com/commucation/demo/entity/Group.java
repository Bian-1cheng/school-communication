package com.commucation.demo.entity;


import java.io.Serializable;
import java.util.List;

public class Group implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer course_id;
    private String group_name;
    private List<User> groupMember;
    private String head_dir;
    private byte[] head;

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public String getHead_dir() {
        return head_dir;
    }

    public void setHead_dir(String head_dir) {
        this.head_dir = head_dir;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<User> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(List<User> groupMember) {
        this.groupMember = groupMember;
    }

    public Group() {}

}
