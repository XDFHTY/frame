package com.cj.demo.service.impl;

import com.cj.common.entity.Admin;
import com.cj.core.config.datasource.TargetDataSource;
import com.cj.demo.mapper.DemoMapper;
import com.cj.demo.service.DemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Override
    public void testTra(String name) {

        Admin admin = new Admin();
        admin.setAdminName("ccc");
        admin.setAdminPass("123456");
        admin.setAdminType("1");

        demoMapper.addAdmin2(admin);
        demoMapper.addAdmin(admin);

    }
}
