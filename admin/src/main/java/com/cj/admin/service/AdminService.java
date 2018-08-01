package com.cj.admin.service;



import com.cj.common.entity.Admin;
import com.cj.common.utils.entity.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {

    /**
     * =====================================Admin===================================================
     */

    /**
     * 新增管理员账号
     */
    public ApiResult addAdmin(Admin admin);


    /**
     * 修改管理员密码
     */
    public int updateAdmin(Admin admin);

    /**
     * 删除管理员账号
     */
    public int delete(Long adminId);



    /**
     * 查询所有管理员账号
     */
    public List<Admin> findAllAdmin();


    /**
     * 管理员登录
     */
    public ApiResult ifLogin(Admin admin);

    /**
     * 管理员注销
     */
    public int ifLogout(HttpSession session);



}
