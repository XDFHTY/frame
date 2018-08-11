package com.cj.user.service.impl;

import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.entity.AuthRole;
import com.cj.common.entity.Key64;
import com.cj.common.entity.User;
import com.cj.common.mapper.Key64Mapper;
import com.cj.common.service.AuthCustomerRoleService;
import com.cj.common.utils.domain.ApiResult;
import com.cj.common.utils.domain.MemoryData;
import com.cj.common.utils.jwt.JwtUtil;
import com.cj.user.mapper.UserMapper;
import com.cj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.cj.common.utils.md5.Md5Utils.MD5Encode;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Resource
    private Key64Mapper key64Mapper;


    @Resource
    private AuthCustomerRoleService authCustomerRoleService;

    /**
     * =========================================Admin========================================================
     */

    /**
     * 添加用户账号
     * @param user
     * @return
     */
    @Override
    public ApiResult addUser(User user) {
        ApiResult apiResult = null;
        //检查账号是否存在
        User oldUser = userMapper.findUserName(user);
        if(oldUser != null){
            apiResult =  ApiResult.account_repeat;
            return apiResult;
        }
        //获取唯一主键
        Key64 key64 = new Key64();
        key64.setStub("a");
        //获取key-adminId
        key64Mapper.addKey64(key64);

        long time = System.currentTimeMillis();
        //生成盐值（UUID）
        String uuid = UUID.randomUUID().toString();
        user.setSaltVal(uuid);
        user.setCreateTime(new Date(time));
        user.setUserId(key64.getId());
        if(user.getUserType() == null){
            user.setUserType("1"); //普通用户
        }
        if(user.getValidTime() == null){
            Date date = new Date(time);
            date.setMonth(date.getMonth()+1);
            user.setValidTime(date);
        }

        //添加账号
        int i = userMapper.insertSelective(user);

        //加密密码，MD5（主键+盐值+密码）
        String userPass = MD5Encode(user.getUserId()+user.getSaltVal()+user.getUserPass(),"UTF-8",true);

        user.setUserPass(userPass);
        //更新密码
        int j = userMapper.updateByPrimaryKeySelective(user);

        //添加角色
        AuthCustomerRole authCustomerRole = new AuthCustomerRole();
        authCustomerRole.setCustomerId(key64.getId());
        authCustomerRole.setRoleId(2l);
        int k = authCustomerRoleService.addCustomerRole(authCustomerRole);

        if(i ==1 && j==1 && k==1){
            apiResult =  ApiResult.ADD_SUCCESS;
            return apiResult;
        }else {
            apiResult =  ApiResult.ADD_FAIL;
            return apiResult;
        }

    }

    /**
     * =========================================User========================================================
     */


    //用户登录
    @Override
    public ApiResult login  (User user) {
        ApiResult apiResult = null;

        User oldUser = userMapper.findUserNameByState(user);
        if(oldUser == null){
            apiResult =  ApiResult.account_not_find;
            return apiResult;
        }

        //加密密码，MD5（盐值+主键+密码）
        String userPass = MD5Encode(oldUser.getUserId()+oldUser.getSaltVal()+user.getUserPass(),"UTF-8",true);

        //检验密码
        if (oldUser.getUserPass().equals(userPass)){
            String token = "";
            Long userId = oldUser.getUserId();
            String userName = oldUser.getUserName();

            String tokenKey = userId.toString();

            long time  = System.currentTimeMillis();

            List<AuthRole> roles =  authCustomerRoleService.findCustomerRoleById(userId);

            //设置token，有效期
            token = JwtUtil.getToken(userId,userName,oldUser.getUserType(),roles);

            if (!MemoryData.getTokenMap().containsKey(tokenKey)) { //不存在，首次登陆，放入Map
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
                Map map = MemoryData.getTokenMap();
                System.out.println();
            }else if( MemoryData.getTokenMap().containsKey(tokenKey) && !StringUtils.equals(token, MemoryData.getTokenMap().get(tokenKey))){
                MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
                System.out.println();
            }

            Map map = new HashMap();
            map.put("token",token);
            map.put("issuedAt",new Date(time));
//            map.put("expiration",new Date(time+1000*60*60*2));
            apiResult = ApiResult.LOGIN_SUCCESS;
            apiResult.setData(map);


        }else {
            apiResult = ApiResult.account_not_find;
        }

        return apiResult;
    }

    //用户注销
    @Override
    public ApiResult logout(HttpSession session) {
        ApiResult apiResult;
        Integer userId = (Integer) session.getAttribute("id");
        String tokenKey = userId.toString();
        if(MemoryData.getTokenMap().containsKey(tokenKey)){
            MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
        }
        apiResult = ApiResult.LOGOUT_SUCCESS;
        return apiResult;
    }


    //用户修改密码
    @Override
    public ApiResult updateUserPass(Map map,HttpSession session) {
        ApiResult apiResult;
        String userName = (String) session.getAttribute("name");
        String oldUserPass = (String) map.get("oldUserPass");
        String newUserPass = (String)map.get("newUserPass");
        User user = new User();
        user.setUserName(userName);
        User oldUser = userMapper.findUserNameByState(user);
        //校验原密码
        if(oldUser.getUserPass().equals(
                MD5Encode(oldUser.getUserId()+oldUser.getSaltVal()+oldUserPass,"UTF-8",true))){

            oldUser.setUserPass(MD5Encode(oldUser.getUserId()+oldUser.getSaltVal()+newUserPass,"UTF-8",true));
            int i = userMapper.updateByPrimaryKeySelective(user);
            if(i ==1){
                apiResult = ApiResult.UPDATE_SUCCESS;
            }else {
                apiResult = ApiResult.UPDATE_FAIL;
            }
        }else {
            apiResult = ApiResult.userPass_error;
        }



        return apiResult;
    }



    @Override
    public ApiResult getUserInfo(Integer id) {
        ApiResult apiResult =null;
        //Long tempId=Long.getLong(id.toString());
        User user = userMapper.selectByPrimaryKey(id.longValue());
        if (null == user) {
            apiResult = ApiResult.FIND_FAIL;
        } else {
            apiResult=ApiResult.FIND_SUCCESS;
        }
        return apiResult;
    }
}
