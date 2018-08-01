package com.cj.common.service.impl;

import com.cj.common.domain.AuthModulars;
import com.cj.common.domain.AuthRoleModulars;
import com.cj.common.utils.clone.CloneUtil;
import com.cj.common.domain.Modular;
import com.cj.common.mapper.AuthModularMapper;
import com.cj.common.mapper.AuthRoleModularMapper;
import com.cj.common.service.AuthRoleModularService;
import com.cj.common.utils.entity.MemoryData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class AuthRoleModularServiceImpl implements AuthRoleModularService {

    @Resource
    private AuthRoleModularMapper authRoleModularMapper;

    @Resource
    private AuthModularMapper authModularMapper;
    @Override
    public List<AuthRoleModulars> findRoleModular() {

        //查询系统所有权限
        AuthModulars authModulars = authModularMapper.findAllAuthModulars();
        //查询系统所有角色的权限信息
        List<AuthRoleModulars> authRoleModulars = authRoleModularMapper.findRoleModular();

        //将系统权限添加到角色权限对象集合
        for (AuthRoleModulars authRoleModulars1 : authRoleModulars){
            AuthModulars authModulars1 = null;
            try {
                authModulars1 = CloneUtil.clone(authModulars);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("========clone失败============");
            }
            authRoleModulars1.setAuthModulars(authModulars1);
        }

        //清理角色没有的权限
        Iterator<AuthRoleModulars> itr1 = authRoleModulars.iterator();
        while (itr1.hasNext()){
            AuthRoleModulars authRoleModulars1 = itr1.next();
            AuthModulars authModulars1 = new AuthModulars();
            //迭代获得2级目录
            Iterator<AuthModulars> itm2 = authRoleModulars1.getAuthModulars().getChildren().iterator();
            while (itm2.hasNext()){
                AuthModulars authModulars2 = itm2.next();
                //迭代获得权限列表
                Iterator<AuthModulars> itm0 =  authModulars2.getChildren().iterator();
                while (itm0.hasNext()){
                    AuthModulars authModulars00 = itm0.next();
                    //是否删除此元素
                    boolean b = true;
                    //遍历此角色权限集合
                    for (Modular modular : authRoleModulars1.getModularIds()){
                        if(authModulars00.getModularId() == modular.getModularId()){
                            b = false;
                        }
                    }
                    if(b){
                        itm0.remove();
                    }
                }
                //删除没有子节点的父节点
                if(authModulars2.getChildren().size() == 0){
                    itm2.remove();
                }
            }
        }

        MemoryData.getRoleModularMap().put("modulars",authRoleModulars);
        return authRoleModulars;
    }
}
