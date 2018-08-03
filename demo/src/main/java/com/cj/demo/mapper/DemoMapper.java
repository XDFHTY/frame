package com.cj.demo.mapper;

import com.cj.common.entity.Admin;
import com.cj.core.config.datasource.TargetDataSource;

public interface DemoMapper {


    public int addAdmin(Admin admin);

    @TargetDataSource("shentu")
    public int addAdmin2(Admin admin);
}
