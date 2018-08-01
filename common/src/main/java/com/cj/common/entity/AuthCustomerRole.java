package com.cj.common.entity;

import lombok.Data;

@Data
public class AuthCustomerRole {
    /**
     * 用户—角色关系表
     */
    private Long id;

    /**
     * 用户id-包括admin和user ID
     */
    private Long customerId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户—角色关系表
     * @return id 用户—角色关系表
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户—角色关系表
     * @param id 用户—角色关系表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id-包括admin和user ID
     * @return customer_id 用户id-包括admin和user ID
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 用户id-包括admin和user ID
     * @param customerId 用户id-包括admin和user ID
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}