package com.cj.common.mapper;

import com.cj.common.domain.AuthRoleModulars;
import com.cj.common.entity.AuthRoleModular;
import com.cj.core.config.datasource.TargetDataSource;

import java.util.List;
import java.util.Map;

public interface AuthRoleModularMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthRoleModular record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthRoleModular record);

    /**
     *
     * @mbggenerated
     */
    AuthRoleModular selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthRoleModular record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthRoleModular record);

    //查询角色-权限信息，树形结构封装
    @TargetDataSource("shentu")
    public List<AuthRoleModulars> findRoleModular();
}