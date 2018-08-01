package com.cj.common.entity;

import lombok.Data;

@Data
public class AuthRole {
    /**
     * 权限角色表
     */
    private Long roleId;

    /**
     * 系统使用的角色名ROLE_XXX
     */
    private String roleName;

    /**
     * 角色名用户查看用
     */
    private String roleDescriptionName;

    /**
     * 状态 0-禁用 1-使用 默认1
     */
    private String roleState;

    /**
     * 权限角色表
     * @return role_id 权限角色表
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 权限角色表
     * @param roleId 权限角色表
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 系统使用的角色名ROLE_XXX
     * @return role_name 系统使用的角色名ROLE_XXX
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 系统使用的角色名ROLE_XXX
     * @param roleName 系统使用的角色名ROLE_XXX
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 角色名用户查看用
     * @return role_description_name 角色名用户查看用
     */
    public String getRoleDescriptionName() {
        return roleDescriptionName;
    }

    /**
     * 角色名用户查看用
     * @param roleDescriptionName 角色名用户查看用
     */
    public void setRoleDescriptionName(String roleDescriptionName) {
        this.roleDescriptionName = roleDescriptionName == null ? null : roleDescriptionName.trim();
    }

    /**
     * 状态 0-禁用 1-使用 默认1
     * @return role_state 状态 0-禁用 1-使用 默认1
     */
    public String getRoleState() {
        return roleState;
    }

    /**
     * 状态 0-禁用 1-使用 默认1
     * @param roleState 状态 0-禁用 1-使用 默认1
     */
    public void setRoleState(String roleState) {
        this.roleState = roleState == null ? null : roleState.trim();
    }
}