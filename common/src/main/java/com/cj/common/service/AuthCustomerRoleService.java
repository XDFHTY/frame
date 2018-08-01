package com.cj.common.service;

import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.entity.AuthRole;

import java.util.List;

/**
 * 用户-角色-关系
 */
public interface AuthCustomerRoleService {

    //添加角色
    public int addCustomerRole(AuthCustomerRole authCustomerRole);

    //查询用户角色
    public List<AuthRole> findCustomerRoleById(long customerId);
}
