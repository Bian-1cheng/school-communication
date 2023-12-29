package com.commucation.demo.entity;


import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;


@Data

public class EduTest implements Serializable {
    private Integer id;
    private String value;
}
