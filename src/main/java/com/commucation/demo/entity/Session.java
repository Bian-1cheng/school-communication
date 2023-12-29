package com.commucation.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Session implements Serializable{
    private static final long serialVersionUID = 1L;

    private String sessionType;
    private Integer groupId;
    private String groupName;
    private String chatName;
    private String chatIdentityNo;
    private String sendUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;
    private String content;
    private Integer is_read;

    private Integer user_id;
    private Integer send_id;
    private Integer room_id;

    private Integer unread_num;

    private String headDir;
    private byte[] head;

    public Session() {}
}
