package com.cj.admin.service.impl;


import com.cj.admin.mapper.AdminMapper;
import com.cj.admin.service.AdminService;
import com.cj.common.entity.Admin;
import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.entity.AuthRole;
import com.cj.common.entity.Key64;
import com.cj.common.mapper.Key64Mapper;
import com.cj.common.service.AuthCustomerRoleService;
import com.cj.common.utils.domain.ApiResult;
import com.cj.common.utils.domain.MemoryData;
import com.cj.common.utils.jwt.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.cj.common.utils.md5.Md5Utils.MD5Encode;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private Key64Mapper key64Mapper;

    @Resource
    private AuthCustomerRoleService authCustomerRoleService;






    //添加账号
    @Override
    public ApiResult addAdmin(Admin admin) {
        ApiResult apiResult;
        //检查账号是否已存在
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        long time = System.currentTimeMillis();

        //账号已存在
        if(oldAdmin != null){
            apiResult = ApiResult.account_repeat;
            return apiResult;
        }
        //获取唯一主键
        Key64 key64 = new Key64();
        key64.setStub("a");
        //获取key-adminId
        key64Mapper.addKey64(key64);

        admin.setAdminId(key64.getId());
        //生成盐值
        String uuid = UUID.randomUUID().toString();
        admin.setSaltVal(uuid);
        admin.setAdminType("1");  //系统管理员
        admin.setCreateTime(new Date(time));

        int i = adminMapper.insertSelective(admin);

        //加密密码，MD5（主键+盐值+密码）
        String adminPass = MD5Encode(admin.getAdminId()+admin.getSaltVal()+admin.getAdminPass(),"UTF-8",true);

        admin.setAdminPass(adminPass);

        //更新密码
       int j = adminMapper.updateByPrimaryKey(admin);

       //添加角色
        AuthCustomerRole authCustomerRole = new AuthCustomerRole();
        authCustomerRole.setCustomerId(key64.getId());
        authCustomerRole.setRoleId(1l);
        int k = authCustomerRoleService.addCustomerRole(authCustomerRole);


       if(i ==1 && j==1 && k==1){
           apiResult = ApiResult.ADD_SUCCESS;
           return apiResult;
       }else {
           apiResult = ApiResult.ADD_FAIL;
           return apiResult;
       }

    }

    @Override
    public int updateAdmin(Admin admin) {
        ApiResult apiResult;
        Admin oldAdmin = adminMapper.findAdminByName(admin);
        String newAdminPass = MD5Encode(oldAdmin.getAdminId()+oldAdmin.getSaltVal()+admin.getAdminPass(),"UTF-8",true);
        oldAdmin.setAdminPass(newAdminPass);
        int i = adminMapper.updateAdminPass(admin);

        return i;
    }

    @Override
    public int delete(Long adminId) {
        return adminMapper.deleteAdmin(adminId);
    }

    @Override
    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }


    //登录
    @Override
    public ApiResult ifLogin(Admin admin) {
        ApiResult apiResult = null;

        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        Map map = new HashMap();
        if(oldAdmin == null){
            apiResult = ApiResult.account_not_find;

            return  apiResult;

        }else if(oldAdmin.getAdminPass().equals(MD5Encode(oldAdmin.getAdminId()+oldAdmin.getSaltVal()+admin.getAdminPass(),"UTF-8",true))){  //密码正确
            String token = "";

            Long adminId = oldAdmin.getAdminId();
            String adminName = oldAdmin.getAdminName();

            String tokenKey = adminId.toString();

            //查询账号角色信息
            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);



            //设置token，有效期
            token = JwtUtil.getToken(adminId,adminName,oldAdmin.getAdminType(),roles);



            if (!MemoryData.getTokenMap().containsKey(tokenKey)) { //不存在，首次登陆，放入Map
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
            }else if( MemoryData.getTokenMap().containsKey(tokenKey) && !StringUtils.equals(token, MemoryData.getTokenMap().get(tokenKey))){
                MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
            }



            map.put("token",token);
            map.put("issuedAt",new Date(time));
//            map.put("expiration",new Date(time+1000*60*60*2));
            apiResult = ApiResult.SUCCESS;
            apiResult.setData(map);
            System.out.println(apiResult);
            return apiResult;
        }else {

            apiResult = ApiResult.account_not_find;
            return  apiResult;

        }


    }

    //注销
    @Override
    public int ifLogout(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("id");
        String tokenKey = adminId.toString();
        if(MemoryData.getTokenMap().containsKey(tokenKey)){
            MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
        }
        return 1;
    }



}