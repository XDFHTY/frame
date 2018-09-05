package com.cj.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsMessage {
    //消息发送人，对应认证用户Principal.name（全局唯一）
    private String userName;

    //消息接收人，对应认证用户Principal.name（全局唯一）
    private String toName;
    //消息内容
    private Object msg;

}