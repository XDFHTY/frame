package com.cj.common.service.impl;

import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.entity.AuthRole;
import com.cj.common.mapper.AuthCustomerRoleMapper;
import com.cj.common.mapper.AuthRoleMapper;
import com.cj.common.service.AuthCustomerRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AuthCustomerRoleServiceImpl implements AuthCustomerRoleService {

    @Resource
    private AuthCustomerRoleMapper authCustomerRoleMapper;

    @Resource
    private AuthRoleMapper authRoleMapper;


    @Override
    public int addCustomerRole(AuthCustomerRole authCustomerRole) {
        return authCustomerRoleMapper.insertSelective(authCustomerRole);
    }


    @Override
    public List<AuthRole> findCustomerRoleById(long customerId) {
        return authRoleMapper.findCustomerRoleById(customerId);
    }
}
