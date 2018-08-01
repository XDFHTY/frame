package com.cj.common.mapper;

import com.cj.common.entity.Key64;

public interface Key64Mapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(Key64 record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Key64 record);

    /**
     *
     * @mbggenerated
     */
    Key64 selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Key64 record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Key64 record);

    //替换写入数据，获得系统唯一主键
    public int addKey64(Key64 key64);
}