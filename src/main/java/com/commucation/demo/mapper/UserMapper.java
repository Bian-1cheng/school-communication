package com.commucation.demo.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.commucation.demo.entity.*;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 查找用户(根据id)
     * @param id
     * @return
     */
    User findById(Integer id);
    /**
     * 查找用户：一个List
     * @return
     */
    List<User> findAll();
    /**
     * 查找用户(根据名字)
     *
     */
    User findByUsername(String identityNo);
    /**
     * 检查用户登录
     */
    User loginCheck(String identityNo, String password);
    /**
     * 修改登录状态
     */
    void changeLoginStatus(String identityNo);
    /**
     * 查找用户(根据群id)
     */
    List<User> findUserByGroupId(Integer id);
    /**
     *
     * 查找用户的私聊信息
     */
    User findChatInfo(Integer room_id, String identityNo);
    /**
     *
     * 修好用户头像
     */
    void changeHead(String identityNo, String dir);

    /**
     * 改变密码
     * @param identityNo
     * @param password
     */
    void changePassword(String identityNo, String password);
    /**
     * 查找学生信息
     */
    Student findStudentInfo(Integer user_id);
    /**
     * 更新用户在线设备数量
     */
    void updateUserOnlineDevices(Integer num, String identityNo);
}