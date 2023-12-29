package com.commucation.demo.entity;


import java.io.Serializable;
public class Test implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String password;

    public Test() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer Id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
