package com.cj.common.security.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 请求方式验证失败异常
 */
public class MyAccessMethodDeniedException extends AccessDeniedException {
    public MyAccessMethodDeniedException(String msg) {
        super(msg);
    }

}
