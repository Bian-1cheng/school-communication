package com.commucation.demo.controller;


import com.commucation.demo.common.config.WebSocketServer;
import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.*;
import com.commucation.demo.mapper.*;
import com.commucation.demo.util.PageModel;
import com.commucation.demo.util.PictureModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {
    @Resource
    private SessionMapper sessionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RelationMapper relationMapper;
    @Resource
    private SendMessageMapper sendMessageMapper;
    @Resource
    private GroupMapper groupMapper;

    @GetMapping("/findMySession/{identityNo}")
    public Result findMySession(@PathVariable(name = "identityNo") String identityNo){
        if (identityNo == null) {
            return Result.fail("用户名为空");
        }
        try {
            List<Session> sessions = sessionMapper.findSession(identityNo);
            List<Session> chat = sessionMapper.findChat(identityNo);
            Session systemNote = sessionMapper.findSystemNoteOfLate(identityNo);
            if (systemNote != null) {
                systemNote.setSessionType("系统消息");
            } else {
                systemNote = new Session();
            }
            if (sessions != null) {
                for (Session session : sessions) {
                    session.setHead(session.getHeadDir() == null ? new byte[0] : new PictureModel(session.getHeadDir()).getPic());
                    session.setSessionType("群聊");
                }
            } else {
                sessions = new ArrayList<Session>();
            }
            if (chat != null) {
                for (Session session : chat) {
                    session.setSessionType("私聊");

                    session.setHead(session.getHeadDir() == null ? new byte[0] : new PictureModel(session.getHeadDir()).getPic());
                }
            } else {
                chat = new ArrayList<Session>();
            }
            if (!chat.isEmpty()) {
                sessions.addAll(chat);
            }
            if (systemNote.getSendTime() != null) {
                sessions.add(systemNote);
            }
            // 按时间排序
            if (!sessions.isEmpty()) {
                sessions.sort(Comparator.comparing(Session::getSendTime).reversed());
            }
            return Result.success(sessions);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/findGroupHistoryById/{group_id}/{identityNo}")
    public Result findGroupHistoryById(@PathVariable(name = "group_id") Integer group_id, @PathVariable(name = "identityNo") String identityNo, @RequestParam(defaultValue = "1") Integer currentPage) {
        if (group_id == null) {
            return Result.fail("群id为空");
        }
        try {
            List<GroupHistory> groupHistories = sessionMapper.findGroupHistoryById(group_id, identityNo);
            if (groupHistories != null) {
                for (GroupHistory groupHistory : groupHistories) {
                    groupHistory.setUser_identity_no(identityNo);
                    groupHistory.setUser_id(userMapper.findByUsername(identityNo).getId());

                    groupHistory.setSend_head(new PictureModel(userMapper.findById(groupHistory.getSend_id()).getHeadDir()).getPic());
                }
            }
            PageModel<GroupHistory> pm = new PageModel(groupHistories, 20);
            List<GroupHistory> subGroupHistories = pm.getObjects(currentPage);
            if (currentPage <= pm.getTotalPages()) {
                return Result.success(subGroupHistories);
            } else {
                return Result.success(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
    }

    @GetMapping("/findPrivateChat/{user_identityNo}/{send_identityNo}")
    public Result findPrivateChat(@PathVariable(name = "user_identityNo") String user_identityNo, @PathVariable(name = "send_identityNo") String send_identityNo, @RequestParam(defaultValue = "1") Integer currentPage) {
        if (user_identityNo == null || send_identityNo == null) {
            return Result.fail("用户为空");
        }
        try {
            User user = userMapper.findByUsername(user_identityNo);
            List<Userchat> userchats = sessionMapper.findPrivateChat(user_identityNo, send_identityNo, sessionMapper.findRoomId(user_identityNo, send_identityNo).getRoom_id());
            if (userchats != null) {
                for (Userchat userchat : userchats) {
                    userchat.setUser_id(user.getId());
                    userchat.setUser_identity_no(user_identityNo);

                    userchat.setSend_head(new PictureModel(userMapper.findById(userchat.getSend_id()).getHeadDir()).getPic());
                }
            }
            System.out.println("test UserChat-----------------------------------------------KKkkkkkkkkkkkkkk");
            System.out.println(userchats);
            PageModel<GroupHistory> pm = new PageModel(userchats, 20);
            List<GroupHistory> subGroupHistories = pm.getObjects(currentPage);

            if (currentPage <= pm.getTotalPages()) {
                return Result.success(subGroupHistories);
            } else {
                return Result.success(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/changGroupUnread/{group_id}/{identityNo}")
    public Result changGroupUnread(@PathVariable(name = "group_id") Integer group_id, @PathVariable(name = "identityNo") String identityNo) {
        if (group_id == null || identityNo == null) {
            return Result.fail("群号或用户名为空");
        }
        try {
            sessionMapper.changGroupUnread(group_id, identityNo);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    @GetMapping("/changePrivateChatUnread/{user_identityNo}/{send_identityNo}")
    public Result changePrivateChatUnread(@PathVariable(name = "user_identityNo") String user_identityNo, @PathVariable(name = "send_identityNo") String send_identityNo){
        if (user_identityNo == null || send_identityNo == null) {
            return Result.fail("用户为空");
        }
        try {
            sessionMapper.changChatUnread(sessionMapper.findRoomId(user_identityNo, send_identityNo).getRoom_id(), user_identityNo);
            return Result.success("修改成功");
        } catch (Exception e) {

            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    @PostMapping("/publishGroupNotice")
    private Result publishGroupNotice(@RequestBody GroupHistory message) {
        if (message == null) {
            return Result.fail("发布内容错误");
        }
        try {
            List<User> userList = userMapper.findUserByGroupId(message.getGroup_id());
            User send_user = userMapper.findByUsername(message.getSend_identity_no());
            LocalDateTime localDateTime = LocalDateTime.now();

            for (int i = 0; i < userList.size(); i++) {
                sendMessageMapper.insertMessage(message.getGroup_id(), userList.get(i).getId(), send_user.getId(), message.getContent(), localDateTime, userList.get(i).getId() == send_user.getId() ? 1 : 0, 1);
                if (userList.get(i).getIs_online() == 1) {
                    GroupHistory groupHistory = sendMessageMapper.findNewGroupHistory(userList.get(i).getId(), message.getGroup_id());
                    groupHistory.setUser_identity_no(userMapper.findById(groupHistory.getUser_id()).getIdentity_no());
                    groupHistory.setSend_identity_no(userMapper.findById(groupHistory.getSend_id()).getIdentity_no());
                    groupHistory.setSend_username(userMapper.findById(groupHistory.getSend_id()).getUsername());

                    groupHistory.setSend_head(new PictureModel(userMapper.findById(groupHistory.getSend_id()).getHeadDir()).getPic());

                    log.info(groupHistory.toString());

                    WebSocketServer.sendInfo(groupHistory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发布失败");
        }
        return Result.success("发布成功");
    }

    @GetMapping("/findGroupNotice/{group_id}")
    private Result findGroupNotice(@PathVariable(name = "group_id")Integer group_id) {
        if (group_id == null) {
            return Result.fail("群id为空");
        }
        try {
            List<GroupHistory> noticeList = sessionMapper.findGroupNotice(group_id);
            for (int i = 0; i < noticeList.size(); i++) {
                noticeList.get(i).setSend_username(userMapper.findById(noticeList.get(i).getSend_id()).getUsername());
                noticeList.get(i).setSend_head(new PictureModel(userMapper.findById(noticeList.get(i).getSend_id()).getHeadDir()).getPic());

                List<User> userList = sessionMapper.findReadUserList(noticeList.get(i).getGroup_id(), noticeList.get(i).getGmt_create());
                for (int j = 0; j < userList.size(); j++) {
                    userList.get(j).setHead(new PictureModel(userList.get(j).getHeadDir()).getPic());
                }
                noticeList.get(i).setReadUserList(userList);
                noticeList.get(i).setGroup_notice_read_num(userList.size());
            }
            return Result.success(noticeList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    /**
     * 修改群组名称
     * @param group
     * @return
     */
    @PostMapping("/changeGroupName")
    public Result changeGroupName(@RequestBody Group group) {
        if (group == null || group.getId() == null ||group.getGroup_name() == null) {
            return Result.fail("数据为空");
        }
        try {
            groupMapper.changeGroupName(group.getId(), group.getGroup_name());
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    @GetMapping("/findAllSystemNotice/{identityNo}")
    public Result findAllSystemNotice(@PathVariable(name = "identityNo")String identityNo) {
        if (identityNo == null) {
            return Result.fail("用户名为空");
        }
        try {
            List<SystemNote> systemNoteList = sessionMapper.findAllSystemNotice(identityNo);
            return Result.success(systemNoteList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/changeSystemNoticeUnreadNum/{id}")
    private Result changeSystemNoticeUnreadNum(@PathVariable(name = "id")Integer id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        try {
            sessionMapper.changeSystemNoticeUnreadNum(id);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }
}
