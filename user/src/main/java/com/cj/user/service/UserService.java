package com.cj.user.service;


import com.cj.common.entity.User;
import com.cj.common.utils.entity.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {


    /**
     * 添加用户账号
     */
    public ApiResult addUser(User user);




    //登录
    public ApiResult login(User user);

    //注销
    public ApiResult logout(HttpSession session);



    //用户修改密码
    public ApiResult updateUserPass(Map map, HttpSession session);


    //获取用户信息
    public ApiResult getUserInfo(Integer id);
}
