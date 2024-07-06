package com.lys.web.chat;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.chat.service.ImsingleService;
import com.lys.web.notice.entity.Notice;
import com.lys.web.notice.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.SessionScope;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@Component
@ServerEndpoint(value = "/imserverOrder/")
public class WebSocketOrderService implements InitializingBean {
    public static final Logger log= LoggerFactory.getLogger(WebSocketOrderService.class);
    /**
     * 记录当前在线连接数
     * @param chatService
     * @param mineService
     */
    public static final Map<String,Session> sessionMap=new ConcurrentHashMap<>();

    private static NoticeService noticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService){
        WebSocketOrderService.noticeService = noticeService;
    }


    /**
     * 建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        sessionMap.put(session.getId(),session);
        log.info("[onOpen]新建连接,session={},当前人数:{}",session.getId(),sessionMap.size());
    }

    @OnClose
    public void onClose(Session session){
        sessionMap.remove(session.getId(),session);
        log.info("[onOpen]新建连接,session={},当前人数:{}",session.getId(),sessionMap.size());
    }


    /**
     *
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message,Session fromSession){
        log.info("服务端收到消息:{}",message);
        Notice notice= JSONUtil.toBean(message, Notice.class);
        notice.setTime(new DateTime());

        noticeService.save(notice);
        String json = JSON.toJSONStringWithDateFormat(notice,"yyyy-M    M-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);

        String jsonStr = JSONUtil.toJsonStr(notice);
        this.sendAllMessage(json);
        log.info(jsonStr);

    }

    @OnError
    public void onError (Session session,Throwable error){
        log.error("发生错误:{}",error.getMessage());

    }

    private void sendAllMessage(String message){
        try{
            for(Session session:sessionMap.values()){
                log.info("服务端给客户端发送消息");
                session.getBasicRemote().sendText(message);
            }
        }catch (Exception e)
        {
            log.error("失败",e);
        }
    }





    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

