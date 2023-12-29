package com.commucation.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SystemNote implements Serializable {
    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmt_create;
    private String sender;
    private Integer user_id;
    private Integer is_read;
    private Integer is_deleted;
    private String title;
    private String dir;
}
