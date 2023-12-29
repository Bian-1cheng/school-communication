package com.commucation.demo.entity;

/**
 * Created by
 *
 * @Author:jzs
 * @Date: 2021/2/20
 * @Time: 20:54
 */
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Userchat implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer user_id;
    private String user_identity_no;
    private Integer send_id;
    private String send_identity_no;
    private String send_username;
    private byte[] send_head;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmt_create;
    private Integer is_deleted;
    //是否撤回
    private Integer is_withdrawn;
    private Integer is_read;
    private Integer room_id;

    public Integer getRoom_id() {
        return room_id;
    }

    public byte[] getSend_head() {
        return send_head;
    }

    public void setSend_head(byte[] send_head) {
        this.send_head = send_head;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_identity_no() {
        return user_identity_no;
    }

    public void setUser_identity_no(String user_identity_no) {
        this.user_identity_no = user_identity_no;
    }

    public String getSend_username() {
        return send_username;
    }

    public void setSend_username(String send_username) {
        this.send_username = send_username;
    }

    @Override
    public String toString() {
        return "Userchat{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", user_identity_no='" + user_identity_no + '\'' +
                ", send_id=" + send_id +
                ", send_identity_no='" + send_identity_no + '\'' +
                ", send_username='" + send_username + '\'' +
                ", send_head=" + Arrays.toString(send_head) +
                ", content='" + content + '\'' +
                ", gmt_create=" + gmt_create +
                ", is_deleted=" + is_deleted +
                ", is_withdrawn=" + is_withdrawn +
                ", is_read=" + is_read +
                ", room_id=" + room_id +
                '}';
    }

    public Integer getSend_id() {
        return send_id;
    }

    public void setSend_id(Integer send_id) {
        this.send_id = send_id;
    }

    public String getSend_identity_no() {
        return send_identity_no;
    }

    public void setSend_identity_no(String send_identity_no) {
        this.send_identity_no = send_identity_no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Integer getIs_withdrawn() {
        return is_withdrawn;
    }

    public void setIs_withdrawn(Integer is_withdrawn) {
        this.is_withdrawn = is_withdrawn;
    }

    public Integer getIs_read() {
        return is_read;
    }

    public void setIs_read(Integer is_read) {
        this.is_read = is_read;
    }
}
