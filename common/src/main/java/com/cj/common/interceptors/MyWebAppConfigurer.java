package com.cj.common.interceptors;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 拦截器配置，相当于web.xml
 */

@Configuration
@EnableWebMvc
@ComponentScan
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    private ApplicationContext applicationContext;

    public MyWebAppConfigurer(){
        super();
    }

    @Value("${web.upload-path}")
    String path;

    //重写addResourceHandlers（）后，/static/、/templates/ 下的静态资源可以访问了
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/img/**").addResourceLocations("file:///"+path);
//        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

        super.addResourceHandlers(registry);
    }

//    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截（传入字符串数组）

//        String[] exUser = new String[]{  //UserInterceptors 不校验交给 AdminInterceptors 检验 的url
//                "/api/v1/user/addUser"
//        };
//
//        registry.addInterceptor(new AdminInterceptors()).addPathPatterns("/api/v1/admin/**");
//        registry.addInterceptor(new AdminInterceptors()).addPathPatterns(exUser);
//
//        registry.addInterceptor(new UserInterceptors()).addPathPatterns("/api/v1/st/**");
//        registry.addInterceptor(new UserInterceptors()).addPathPatterns("/api/v1/user/**")
//                .excludePathPatterns(exUser);

        super.addInterceptors(registry);
    }


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

//    /**
//     * @author zhangcunli
//     * <p>
//     * 解决跨域问题
//     */
//    @Configuration
//    public class Cors extends WebMvcConfigurerAdapter {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedHeaders("*")
//                    .allowedOrigins("*")
//                    .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
//                    .allowCredentials(true).maxAge(3600);
//        }
//
//    }


    /**
     * 解决JS跨域
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 解决PUT传参问题
     * @return
     */
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }


    //自定义页面跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/static/main.html");
//        registry.addViewController("/").setViewName("redirect:/static/bx/bx-chzluru.html");
//        registry.addViewController("/boss").setViewName("redirect:/static/index.html");
        registry.addViewController("/api").setViewName("redirect:/swagger-ui.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }


}