package com.commucation.demo.entity;



import lombok.Data;

import java.io.Serializable;

@Data
public class AcdemicPerson implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer user_id;
    //权限级别
    private Integer level;
    //职工号
    private String work_no;
    private Integer is_deleted;

}
