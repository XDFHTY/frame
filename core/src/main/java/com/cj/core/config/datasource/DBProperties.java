package com.cj.core.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * 5.1、定义多个数据源
 * 之前我们假设中访问两个库两个表，假设test库数据源我们命名为test1，test2库数据源我们命名为test2。
 * 我们先定义一个实际数据源配置类
 *
 * 实际数据源配置
 */
@Component
@Data
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {

    private HikariDataSource frame;
    private HikariDataSource shentu;
}