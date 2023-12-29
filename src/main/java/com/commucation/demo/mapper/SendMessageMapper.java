package com.commucation.demo.mapper;

import com.commucation.demo.entity.GroupHistory;
import com.commucation.demo.entity.Userchat;

import java.time.LocalDateTime;

public interface SendMessageMapper {
    /**
     * 插入群聊历史记录
     */
    void insertMessage(Integer group_id, Integer user_id, Integer send_id, String content, LocalDateTime gmt_create, Integer is_read, Integer is_group_notice);

    /**
     * 查找最新的一条群聊历史记录
     */
    GroupHistory findNewGroupHistory(Integer user_id, Integer group_id);

    /**
     * 插入私聊历史记录
     */
    void insertPrivateMessage(Integer user_id, Integer send_id, String content, LocalDateTime gmt_create, Integer is_read, Integer room_id);

    /**
     * 查找最新的一条私聊历史记录
     */
    Userchat findNewPrivateMessage(Integer user_id, Integer room_id);
}
