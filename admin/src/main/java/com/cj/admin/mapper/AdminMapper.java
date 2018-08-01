package com.cj.admin.mapper;



import com.cj.common.entity.Admin;

import java.util.List;

public interface AdminMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(Admin record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Admin record);

    /**
     *
     * @mbggenerated
     */
    Admin selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Admin record);

    /**
     * 删除账号
     */
    public  int deleteAdmin(Long adminId);

    /**
     * 批量删除账号
     */
    public int deleteAdmins(List<Long> adminIds);

    /**
     * 查询所有账号
     */
    public List<Admin> findAllAdmin();

    /**
     * 根据用户名查询账号
     */
    public Admin findAdminByUserName(Admin admin);

    /**
     * 修改密码
     */
    public int updateAdminPass(Admin admin);

    /**
     * 查询用户名是否已存在
     */
    public Admin findAdminByName(Admin admin);





}