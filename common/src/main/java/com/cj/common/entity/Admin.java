package com.cj.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    /**
     * 管理员基础表
     */
    private Long adminId;

    /**
     * 管理员账号
     */
    private String adminName;

    /**
     * 管理员密码
     */
    private String adminPass;

    /**
     * 盐值
     */
    private String saltVal;

    /**
     * 管理员分类，1-系统管理员
     */
    private String adminType;

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     */
    private String adminState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 管理员基础表
     * @return admin_id 管理员基础表
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * 管理员基础表
     * @param adminId 管理员基础表
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 管理员账号
     * @return admin_name 管理员账号
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * 管理员账号
     * @param adminName 管理员账号
     */
    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    /**
     * 管理员密码
     * @return admin_pass 管理员密码
     */
    public String getAdminPass() {
        return adminPass;
    }

    /**
     * 管理员密码
     * @param adminPass 管理员密码
     */
    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass == null ? null : adminPass.trim();
    }

    /**
     * 盐值
     * @return salt_val 盐值
     */
    public String getSaltVal() {
        return saltVal;
    }

    /**
     * 盐值
     * @param saltVal 盐值
     */
    public void setSaltVal(String saltVal) {
        this.saltVal = saltVal == null ? null : saltVal.trim();
    }

    /**
     * 管理员分类，1-系统管理员
     * @return admin_type 管理员分类，1-系统管理员
     */
    public String getAdminType() {
        return adminType;
    }

    /**
     * 管理员分类，1-系统管理员
     * @param adminType 管理员分类，1-系统管理员
     */
    public void setAdminType(String adminType) {
        this.adminType = adminType == null ? null : adminType.trim();
    }

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     * @return admin_state 状态，1-正常，0-禁用（删除），-1-停用
     */
    public String getAdminState() {
        return adminState;
    }

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     * @param adminState 状态，1-正常，0-禁用（删除），-1-停用
     */
    public void setAdminState(String adminState) {
        this.adminState = adminState == null ? null : adminState.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 最后更新时间
     * @return update_time 最后更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime 最后更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}