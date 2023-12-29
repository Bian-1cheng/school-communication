package com.commucation.demo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    //父编号
    private Integer pid;
    //身份编号
    private Integer role_id;
    private String username;
    private Integer sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmt_create;
    private String is_blocked;
    private String phone;
    private String email;
    //头像
    private String headDir;
    private byte[] head;
    //身份识别码
    private String identity_no;
    private String password;
    private Integer is_online;
    private Integer is_deleted;

    private Serializable token;

    private String major;
    private String class_no;

    private Integer online_devices;

    private String belong_department;

    public Integer getOnline_devices() {
        return online_devices;
    }

    public void setOnline_devices(Integer online_devices) {
        this.online_devices = online_devices;
    }

    public String getBelong_department() {
        return belong_department;
    }

    public void setBelong_department(String belong_department) {
        this.belong_department = belong_department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public Serializable getToken() {
        return token;
    }

    public void setToken(Serializable token) {
        this.token = token;
    }

    public String getHeadDir() {
        return headDir;
    }

    public void setHeadDir(String headDir) {
        this.headDir = headDir;
    }

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public LocalDateTime getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity_no() {
        return identity_no;
    }

    public void setIdentity_no(String identity_no) {
        this.identity_no = identity_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIs_online() {
        return is_online;
    }

    public void setIs_online(Integer is_online) {
        this.is_online = is_online;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
