package com.commucation.demo.mapper;

import com.commucation.demo.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionMapper {
    /**
     * 查找用户主界面的群会话(最近10条)
     */
    List<Session> findSession(String identityNo);
    /**
     * 查找群聊天记录
     */
    List<GroupHistory> findGroupHistoryById(Integer group_id, String identityNo);

    /**
     * 查找两用户的私聊历史记录
     */
    List<Userchat> findPrivateChat(String user_id, String send_id, Integer room_id);
    
    /**
     * 查找两用户的room_id
     */
    Userchat findRoomId(String user_id, String send_id);

    /**
     * 查找用户主界面的私聊会话(最近10条)
     */
    List<Session> findChat(String identityNo);

    /**
     *  查询用户在对应群的未读消息数目
     */
    Session findGroupUnreadNum(Integer group_id, String identityNo);
    /**
     * 查询用户对应私聊的未读消息数目
     */
    Session findChatUnreadNum(Integer room_id, String identityNo);
    /**
     * 修改群消息的未读状态
     */
    void changGroupUnread(Integer group_id, String identityNo);
    /**
     * 修改私聊消息的未读状态
     */
    void changChatUnread(Integer room_id, String identityNo);
    /**
     * 查找系统最近一条消息
     */
    Session findSystemNoteOfLate(String identityNo);
    /**
     * 查找用户未读系统消息数目
     */
    Integer findSystemNoteUnreadNum(String identityNo);
    /**
     * 查找群公告
     */
    List<GroupHistory> findGroupNotice(Integer group_id);
    /**
     * 查询已读群公告的用户列表
     */
    List<User> findReadUserList(Integer group_id, LocalDateTime gmt_create);
    /**
     * 查询所有系统消息
     */
    List<SystemNote> findAllSystemNotice(String identityNo);
    /**
     * 修改系统消息的未读状态
     */
    void changeSystemNoticeUnreadNum(Integer id);
}
