package com.cj.common.mapper;

import com.cj.common.entity.AuthRole;

import java.util.List;

public interface AuthRoleMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthRole record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthRole record);

    /**
     *
     * @mbggenerated
     */
    AuthRole selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthRole record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthRole record);

    //根据用户 consumerId 查询账号角色信息
    public List<AuthRole> findCustomerRoleById(Long consumerId);
}