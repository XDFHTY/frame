package com.cj.common.startup;

import com.cj.common.service.AuthRoleModularService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 开机启动加载角色-权限列表
 */
@Component
@Order(2147483647)
public class LoadPowerList implements CommandLineRunner {

    @Resource
    private AuthRoleModularService authRoleModularService;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("=================初始化角色-权限数据====================");
        authRoleModularService.findRoleModular();

    }
}
