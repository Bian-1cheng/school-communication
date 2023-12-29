package com.commucation.demo.entity;




import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StuCourse implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer stu_id;
    private Integer course_id;
    private Integer is_deleted;
    //是否重修
    private Integer is_redo;

    private String cj;
    private String jd;
    private String sycj;
    private LocalDateTime xksj;
}
