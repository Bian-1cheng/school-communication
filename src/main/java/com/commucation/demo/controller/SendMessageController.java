package com.commucation.demo.controller;

import ch.qos.logback.classic.Logger;
import cn.hutool.json.JSONUtil;
import com.commucation.demo.common.config.WebSocketServer;
import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.GroupHistory;
import com.commucation.demo.entity.User;
import com.commucation.demo.entity.Userchat;
import com.commucation.demo.mapper.*;
import com.commucation.demo.util.PictureModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/sendMessage")
public class SendMessageController {
    @Resource
    private SendMessageMapper sendMessageMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SessionMapper sessionMapper;

    @PostMapping("/send")
    public Result sendMessage(GroupHistory message, MultipartFile file) {
        if (message == null) {
            return Result.fail("发送内容错误");
        }
        LocalDateTime localDateTime = LocalDateTime.now();

        try {
            User sender = userMapper.findByUsername(message.getSend_identity_no());
            sendMessageMapper.insertMessage(message.getGroup_id(), sender.getId(), sender.getId(), message.getContent(), localDateTime, 1, 0);

            GroupHistory groupHistory = sendMessageMapper.findNewGroupHistory(sender.getId(), message.getGroup_id());
            groupHistory.setSend_head(groupHistory.getHeadDir() == null ? new byte[0] : new PictureModel(groupHistory.getHeadDir()).getPic());

            WebSocketServer.sendInfo(groupHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<User> users = userMapper.findUserByGroupId(message.getGroup_id());
        User send_user = userMapper.findByUsername(message.getSend_identity_no());
        for (int i = 0; i < users.size(); i++) {
            try {
                if (!users.get(i).getId().equals(send_user.getId())) {
                    if (message.getContent() != null) {
                        sendMessageMapper.insertMessage(message.getGroup_id(), users.get(i).getId(), send_user.getId(), message.getContent(), localDateTime,  0, 0);
                    } else {
                        //生成uuid
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        //得到上传时的文件名
                        String filename = file.getOriginalFilename();
                        //上传目录地址
                        String property = System.getProperty("user.dir");
                        String uploadDir = property + "/src/main/resources/static/groupHistory/picture";

                        //如果目录不存在，自动创建文件夹
                        File dir = new File(uploadDir);
                        if(!dir.exists()){
                            dir.mkdir();
                        }

                        String pathname = uploadDir+"\\"+ uuid + localDateTime.toString().replace(".", "-").replace(":", "-") + filename;
                        File serverFile = new File(pathname);
                        file.transferTo(serverFile);

                        sendMessageMapper.insertMessage(message.getGroup_id(), users.get(i).getId(), send_user.getId(), pathname, localDateTime, users.get(i).getId() == send_user.getId() ? 1 : 0, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("发送失败");
            }
            if (!users.get(i).getId().equals(send_user.getId()) && users.get(i).getIs_online() == 1) {
                try {
                    GroupHistory groupHistory = sendMessageMapper.findNewGroupHistory(users.get(i).getId(), message.getGroup_id());

                    groupHistory.setSend_head(groupHistory.getHeadDir() == null ? new byte[0] : new PictureModel(groupHistory.getHeadDir()).getPic());
                    if (groupHistory.getContent().contains("/src/main/resources/static/groupHistory/picture")) {
                        groupHistory.setContent(new String(new PictureModel(groupHistory.getContent()).getPic()));
                    }

                    WebSocketServer.sendInfo(groupHistory);
                } catch (EncodeException | IOException e) {
                    e.printStackTrace();
                    return Result.fail("发送失败");
                }
            }
        }
        return Result.success("发送成功");
    }

    @PostMapping("/sendToUser")
    public Result sendMessageToUser(Userchat message, MultipartFile file) {

        System.out.println("11111111111111111111111111111111111111");
        System.out.println(message);

        ArrayList<User> users = new ArrayList<User>();
        users.add(userMapper.findByUsername(message.getUser_identity_no()));
        users.add(userMapper.findByUsername(message.getSend_identity_no()));

        LocalDateTime localDateTime = LocalDateTime.now();

        for (int i = 0; i < users.size(); i++) {
            try {
                if (message.getContent() != null) {
                    int room_id = 0;
                    if (sessionMapper.findRoomId(message.getUser_identity_no(), message.getSend_identity_no()) == null) {
                        room_id = 1;
                    } else {
                        room_id = sessionMapper.findRoomId(message.getUser_identity_no(), message.getSend_identity_no()).getRoom_id();
                    }
                    sendMessageMapper.insertPrivateMessage
                            (users.get(i).getId(), users.get(1).getId(), message.getContent(),
                                    localDateTime, i == 1 ? 1 : 0, room_id);
                } else if (file != null) {
                    //生成uuid
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    //得到上传时的文件名
                    String filename = file.getOriginalFilename();
                    //上传目录地址
                    String property = System.getProperty("user.dir");
                    String uploadDir = property + "/src/main/resources/static/chatHistory/picture";

                    //如果目录不存在，自动创建文件夹
                    File dir = new File(uploadDir);
                    if(!dir.exists()){
                        dir.mkdir();
                    }

                    String pathname = uploadDir+"\\"+ uuid + localDateTime.toString().replace(".", "-").replace(":", "-") + filename;
                    File serverFile = new File(pathname);
                    file.transferTo(serverFile);

                    sendMessageMapper.insertPrivateMessage(users.get(i).getId(), users.get(1).getId(), pathname, localDateTime, i == 1 ? 1 : 0, sessionMapper.findRoomId(message.getUser_identity_no(), message.getSend_identity_no()).getRoom_id());
                } else {
                    return Result.fail("发送失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("发送失败");
            }
            if (users.get(i).getIs_online() == 1) {
                try {
                    Userchat userchat = sendMessageMapper.findNewPrivateMessage(users.get(i).getId(), sessionMapper.findRoomId(message.getUser_identity_no(), message.getSend_identity_no()).getRoom_id());
                    userchat.setUser_identity_no(userMapper.findById(userchat.getUser_id()).getIdentity_no());
                    userchat.setSend_identity_no(userMapper.findById(userchat.getSend_id()).getIdentity_no());
                    userchat.setSend_username(userMapper.findById(userchat.getSend_id()).getUsername());

                    userchat.setSend_head(new PictureModel(userMapper.findById(userchat.getSend_id()).getHeadDir()).getPic());
                    if (userchat.getContent().contains("/src/main/resources/static/chatHistory/picture")) {
                        userchat.setContent(new String(new PictureModel(userchat.getContent()).getPic()));
                    }

                    log.info(userchat.toString());

                    WebSocketServer.sendInfoToUser(userchat);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail("发送失败");
                }
            }
        }

        return Result.success("发送成功");
    }
}
