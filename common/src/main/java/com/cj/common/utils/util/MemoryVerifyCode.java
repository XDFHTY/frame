package com.cj.common.utils.util;

import com.cj.common.utils.domain.ExpiryMap;

/**
 * created by zy
 * 用于存储用户手机信息和验证码新信息
 */
public class MemoryVerifyCode {
    private MemoryVerifyCode(){}
    //用于存储 用户-token，默认2小时过期
    private volatile static ExpiryMap<String, String> codeMap =null;
    public  static ExpiryMap getCodeMap(){
        if (codeMap == null) {
            synchronized (MemoryVerifyCode.class) {
                if (codeMap == null) {
                    //键值对默认存储时间为5分钟
                    codeMap = new ExpiryMap<>(1000*60*5);
                }
            }
        }
        return codeMap;
    }


}
