package com.cj.admin.mapper;

import com.cj.stcommon.entity.Produce;

public interface ProduceMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long produceId);

    /**
     *
     * @mbggenerated
     */
    int insert(Produce record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Produce record);

    /**
     *
     * @mbggenerated
     */
    Produce selectByPrimaryKey(Long produceId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Produce record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Produce record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Produce record);

    /**统计数据库记录条数
     * @return
     */
    int queryProduceCount();

    /**获取数据库中第一条记录
     * @return
     */
    Produce selectProduceInfo();
}