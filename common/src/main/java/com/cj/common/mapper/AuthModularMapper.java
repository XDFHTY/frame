package com.cj.common.mapper;

import com.cj.common.domain.AuthModulars;
import com.cj.common.entity.AuthModular;

public interface AuthModularMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthModular record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthModular record);

    /**
     *
     * @mbggenerated
     */
    AuthModular selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthModular record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthModular record);


    //查询系统所有权限-树形结构封装
    public AuthModulars findAllAuthModulars();
}