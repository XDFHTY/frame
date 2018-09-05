package com.cj.websocket.entity;

import lombok.Data;

import java.security.Principal;

@Data
public class WsUser implements Principal {
    private String userName;

    @Override
    public boolean equals(Object another) {
        if(this.userName == null){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.userName;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return this.userName;
    }
}
