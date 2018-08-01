package com.cj.common.mapper;

import com.cj.common.entity.AuthCustomerRole;

public interface AuthCustomerRoleMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    AuthCustomerRole selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthCustomerRole record);
}