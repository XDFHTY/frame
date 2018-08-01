package com.cj.admin.mapper;

import com.cj.stcommon.entity.RotateImg;

import java.util.List;

public interface RotateImgMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long rotateImgId);

    /**
     *
     * @mbggenerated
     */
    int insert(RotateImg record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(RotateImg record);

    /**
     *
     * @mbggenerated
     */
    RotateImg selectByPrimaryKey(Long rotateImgId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RotateImg record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RotateImg record);

    /**统计当前数据库中的数量
     * @return
     */
    int selectRotateImgCount();

    /**浏览轮播图信息
     * @return
     */
    List<RotateImg> selectAllRotateImg();

}