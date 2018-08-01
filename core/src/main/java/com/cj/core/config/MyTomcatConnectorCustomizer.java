package com.cj.core.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

/**
 * 配置 tomcat 连接池
 */
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {

    @Value("${server.tomcat.max-threads}")
    private String maxThreads;
    @Value("${server.tomcat.max-connections}")
    private String maxConnections;
    @Value("${server.tomcat.connection_timeout}")
    private String connectionTimeout;

    public void customize(Connector connector){
        System.out.println("========="+maxThreads);
        System.out.println("========="+maxConnections);
        System.out.println("========="+connectionTimeout);
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        //设置最大连接数
        protocol.setMaxConnections(Integer.parseInt(maxConnections));
        //设置最大线程数
        protocol.setMaxThreads(Integer.parseInt(maxThreads));
        //超时时间
        protocol.setConnectionTimeout(Integer.parseInt(connectionTimeout));

    }
}