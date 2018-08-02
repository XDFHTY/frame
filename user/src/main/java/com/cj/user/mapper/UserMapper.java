package com.cj.user.mapper;


import com.cj.stcommon.entity.User;

public interface UserMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long userId);

    /**
     *
     * @mbggenerated
     */
    int insert(User record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(User record);

    /**
     *
     * @mbggenerated
     */
    User selectByPrimaryKey(Long userId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(User record);


    //检查账号是否存在
    public User findUserName(User user);

    //检查账号是否存在
    public User findUserNameByState(User user);
}