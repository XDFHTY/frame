package com.cj.common.security.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 用户角色权限不足异常
 */
public class MyAccessUrlDeniedException extends AccessDeniedException {
    public MyAccessUrlDeniedException(String msg) {
        super(msg);
    }
}
