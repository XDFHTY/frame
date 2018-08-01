package com.cj.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 配置 tomcat 端口及连接池
 */

//@Configuration
public class WebServerConfiguration{
    @Value("${server.port}")
    int port;
    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory(){
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.setPort(port);
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return tomcatFactory;
    }
}


