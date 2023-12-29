package com.commucation.demo.entity;




import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserGroup implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer user_id;
    private Integer group_id;
    //最后刷新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refresh_time;
    private Integer is_mute;
    private Integer is_deleted;

    public UserGroup() {}

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

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public LocalDateTime getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(LocalDateTime refresh_time) {
        this.refresh_time = refresh_time;
    }

    public Integer getIs_mute() {
        return is_mute;
    }

    public void setIs_mute(Integer is_mute) {
        this.is_mute = is_mute;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
