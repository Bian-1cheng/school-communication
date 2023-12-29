package com.commucation.demo.common.config;

import com.commucation.demo.common.lang.Result;
import com.commucation.demo.entity.*;
import com.commucation.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{sid}", encoders = {EncoderClassVo.class})
@Component
@Slf4j
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";

/**
 * 连接建立成功调用的方法 */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        try {
            sendMessage(Result.success("连接成功"));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
            log.error("websocket IO异常");
        }
    }

/**
 * 连接关闭调用的方法 */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

/**
 * 收到客户端消息后调用的方法
 *
 * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {

    }

/**
 *
 * @param session
 * @param error
 */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        error.printStackTrace();
    }

/**
 * 实现服务器主动推送
 */
    public void sendMessage(Result result) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(result);
    }

/**
 * 群发自定义消息
 * */
    public static void sendInfo(GroupHistory message) throws IOException, EncodeException {
        log.info("推送消息到窗口"+message.getUser_id()+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (item.sid.equals(message.getUser_identity_no())) {
                    item.sendMessage(Result.success(message));
                }
            } catch (IOException | EncodeException e) {
                item.sendMessage(Result.fail("发送失败"));
            }
        }
    }

    public static void sendInfoToUser(Userchat message) throws IOException, EncodeException {
        log.info("推送消息到窗口"+message.getUser_id()+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (item.sid.equals(message.getUser_identity_no())) {
                    item.sendMessage(Result.success(message));
                }
            } catch (IOException | EncodeException e) {
                item.sendMessage(Result.fail("发送失败"));
            }
        }
    }

    public static void sendNote(SystemNote message) throws IOException, EncodeException {
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                item.sendMessage(Result.success(message));
            } catch (IOException | EncodeException e) {
                item.sendMessage(Result.fail("发送失败"));
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() { return webSocketSet; }
}
