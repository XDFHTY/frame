package com.cj.core.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 2、实现动态数据源AbstractRoutingDataSource
 * spring为我们提供了AbstractRoutingDataSource，即带路由的数据源。继承后我们需要实现它的determineCurrentLookupKey()，
 * 该方法用于自定义实际数据源名称的路由选择方法，由于我们将信息保存到了ThreadLocal中，所以只需要从中拿出来即可。
 *
 * 动态数据源实现类
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    //数据源路由，此方用于产生要选取的数据源逻辑名称
    @Override
    protected Object determineCurrentLookupKey() {
        //从共享线程中获取数据源名称
        return DynamicDataSourceHolder.getDataSource();
    }
}