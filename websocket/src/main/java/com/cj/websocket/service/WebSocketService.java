package com.cj.websocket.service;


import com.cj.common.utils.domain.ApiResult;

import java.util.List;

public interface WebSocketService {

    public void sendMessage() throws Exception;


    /**
     * 发送给所有在线用户
     * @param apiResult
     */
    public void sendMsg(ApiResult apiResult);

    /**
     * 发送给指定用户
     * @param users
     * @param msg
     */
    public void send2Users(List<String> users, String msg);
}
