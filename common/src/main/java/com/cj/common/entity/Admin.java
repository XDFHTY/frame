package com.cj.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 最后更新时间
     */
    private Date updateTime;

}