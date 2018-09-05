package com.cj.websocket.service.impl;

import com.cj.common.utils.domain.ApiResult;
import com.cj.websocket.service.WebSocketService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Gson gson = new Gson();

    @Override
    public void sendMessage() throws Exception {
        ApiResult apiResult;
        for(int i=0;i<10;i++)
        {
            apiResult = ApiResult.SUCCESS;
            Map map = new HashMap();
            map.put("time",sdf.format(new Date()));
            map.put("msg","====第"+(i+1)+"次推送");
            apiResult.setData(map);

            String json = gson.toJson(apiResult.toMap());

            Thread.sleep(500);
            template.convertAndSend("/topic/getResponse",json);
            System.out.println("----------------------send"+i);
        }
    }



    /**
     * 广播
     * 发给所有在线用户
     *
     * @param //msg
     */
    @Override
    public void sendMsg( ApiResult apiResult) {
        String json = gson.toJson(apiResult);
        template.convertAndSend("/topic/getResponse", json);
    }

    /**
     * 发送给指定用户
     * @param users
     * @param msg
     */
    @Override
    public void send2Users(List<String> users, String msg) {
        Map map = new HashMap();
        map.put("time",sdf.format(new Date()));
        map.put("msg",msg);
        ApiResult apiResult = null;
        apiResult = ApiResult.SUCCESS;
        apiResult.setData(map);
        String json = gson.toJson(apiResult);
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, "/topic/getResponse", json);
        });
    }



}
