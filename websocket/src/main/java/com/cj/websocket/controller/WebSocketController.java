package com.cj.websocket.controller;

import com.cj.common.utils.domain.ApiResult;
import com.cj.websocket.entity.WsMessage;
import com.cj.websocket.service.WebSocketService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/wsu")
@ApiIgnore
public class WebSocketController {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    Gson gson = new Gson();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/ws")
    public String toWs(){
        return "/ws";
    }
    @GetMapping("/ws1")
    public String toWs1(){
        return "/ws1";
    }
    @GetMapping("/chat")
    public ModelAndView toWs2(
            String userName,
            String toName
    ) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/chat");
        mav.addObject("userName",userName);
        mav.addObject("toName",toName);
        return mav;
    }

    //发送广播通知
    @MessageMapping("/addNotice")   //接收客户端发来的消息，客户端发送消息地址为：/app/addNotice
    @SendTo("/topic/notice")
    @ResponseBody//向客户端发送广播消息（方式一），客户端订阅消息地址为：/topic/notice
    public Map notice(String notice, Principal principal) {
        //TODO 业务处理
        WsMessage msg = new WsMessage();
        msg.setUserName(principal.getName());
        msg.setMsg(notice);


        //向客户端发送广播消息（方式二），客户端订阅消息地址为：/topic/notice
//        messagingTemplate.convertAndSend("/topic/notice", msg);


        ApiResult apiResult = ApiResult.SUCCESS;
        Map map = new HashMap();
        map.put("time",sdf.format(new Date()));
        map.put("msg",msg.getUserName()+"--发送: "+msg.getMsg());
        apiResult.setData(map);


        return apiResult.toMap();
    }

    //发送点对点消息
    @MessageMapping("/msg")         //接收客户端发来的消息，客户端发送消息地址为：/app/msg
    @SendToUser("/queue/msg/result") //向当前发消息客户端（就是自己）发送消息的发送结果，客户端订阅消息地址为：/user/queue/msg/result
    public void sendMsg(@RequestBody WsMessage message){
        //TODO 业务处理

        ApiResult apiResult = null;
        apiResult = ApiResult.SUCCESS;
        Map map = new HashMap();
        map.put("time",sdf.format(new Date()));
        map.put("msg",message.getUserName()+"--发送: "+message.getMsg());
        apiResult.setData(map);
        String json = gson.toJson(apiResult.toMap());



        String returnUrl = "/queue/msg/result/"+message.getToName()+"-"+message.getUserName();
        System.out.println("===============returnUrl="+returnUrl);
        //向指定客户端发送消息，第一个参数Principal.name为前面websocket握手认证通过的用户name（全局唯一的），客户端订阅消息地址为：/user/queue/msg/new
        messagingTemplate.convertAndSendToUser(
                message.getToName(),returnUrl , json);
    }
}