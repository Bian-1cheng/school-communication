package com.commucation.demo.mapper;


public interface GroupMapper {
    /**
     * 修改群头像
     */
    void changHead(Integer id, String dir);
    /**
     * 修改群名称
     */
    void changeGroupName(Integer id, String group_name);
}
