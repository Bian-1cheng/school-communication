package com.commucation.demo.entity;




import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    //课程号
    private String course_id;
    private String course_name;
    private String teacher_id;
    private String acdemic_id;
    //选课总人数
    private String total_stu;
    //群聊id
    private String group_id;
    private Integer is_deleted;
    private String class_time;
    private String class_add;
    private String teacher_name;
    private String xkkh;
    private String lrsz;
    private String xf;
}
