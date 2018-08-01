package com.cj.admin.mapper;

import com.cj.stcommon.entity.VideoHelp;

public interface VideoHelpMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long helpId);

    /**
     *
     * @mbggenerated
     */
    int insert(VideoHelp record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(VideoHelp record);

    /**
     *
     * @mbggenerated
     */
    VideoHelp selectByPrimaryKey(Long helpId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(VideoHelp record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(VideoHelp record);

    /**获取当前视频信息数量
     * @return
     */
    int getVideoHelperCount();

    /**获取第一条视频信息
     * @return
     */
    VideoHelp getFirstVideoHelperInfo();
}