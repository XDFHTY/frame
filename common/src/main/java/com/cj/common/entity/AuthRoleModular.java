package com.cj.common.entity;

import lombok.Data;

@Data
public class AuthRoleModular {
    /**
     * 角色-模块化关系表
     */
    private Long id;

    /**
     * 角色表主键id
     */
    private Long roleId;

    /**
     * 模块化表主键id
     */
    private Long modularId;

    /**
     * 角色-模块化关系表
     * @return id 角色-模块化关系表
     */
    public Long getId() {
        return id;
    }

    /**
     * 角色-模块化关系表
     * @param id 角色-模块化关系表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 角色表主键id
     * @return role_id 角色表主键id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色表主键id
     * @param roleId 角色表主键id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 模块化表主键id
     * @return modular_id 模块化表主键id
     */
    public Long getModularId() {
        return modularId;
    }

    /**
     * 模块化表主键id
     * @param modularId 模块化表主键id
     */
    public void setModularId(Long modularId) {
        this.modularId = modularId;
    }
}