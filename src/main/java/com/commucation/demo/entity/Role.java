package com.commucation.demo.entity;




import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String role_name;
    private Integer creatorId;
    private String creatorName;
    private LocalDateTime createDate;
    private Integer is_deleted;
}
