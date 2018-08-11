package com.cj.common.security.config;

import com.cj.common.entity.AuthModular;
import com.cj.common.security.dto.MyAccessDecisionManager;
import com.cj.common.security.dto.SecureResourceFilterInvocationDefinitionSource;
import com.cj.common.security.filter.CorsFilter;
import com.cj.common.security.filter.LoginFilter;
import com.cj.common.security.filter.PowerFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

import java.util.HashMap;
import java.util.List;

/**
 * Spring Security配置类
 * @author Niu Li
 * @since 2017/6/16
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  /**
   * 不校验登录和权限的URL
   * 比如js，国际化文件，页面等
   */
  private static final String[] AUTH_WHITELIST = {

          "/i18n/**",
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/webjars/**",
          "/configuration/ui",
          "/configuration/security",

          "/**",
          "/api",
          "/swagger-ui.html",
          "/docs.html",
          "/img/**",
          "/static/**",
          "/test/**",

          "/api/v1/demo/**",
          //管理员登录
          "/api/v1/admin/ifLogin",
          //用户验证码获取
          "/api/v1/user/getVerifyCode",
          //用户注册接口
          "/api/v1/user/registerUser",
          //用户登录
          "/api/v1/user/login"

  };
  /**
   * 在此配置不过滤的请求
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    //每一个请求对应一个空的filter链,这里一般不要配置过多,
    // 因为查找处是一个for循环,过多就导致每个请求都需要循环一遍直到找到
    web.ignoring()
            .antMatchers(AUTH_WHITELIST);
  }

  //键值对存储角色和接口信息初始化

  HashMap<String,List<AuthModular>> role_modular=new HashMap<String,List<AuthModular>>();
  /**
   * 在此配置过滤链
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
   // addRole_modaler(http,role_modular);
    PowerFilter powerFilter =new PowerFilter();
    //获取访问当前URl所需要的角色，添加到过滤器
    powerFilter.setSecurityMetadataSource(new SecureResourceFilterInvocationDefinitionSource());

    powerFilter.setAccessDecisionManager(new MyAccessDecisionManager());

    http
            //异常处理,可以再此使用entrypoint来定义错误输出
            .exceptionHandling().and()
            //不需要session来控制,所以这里可以去掉
            .securityContext().securityContextRepository(new NullSecurityContextRepository()).and()
            //开启匿名访问
            .anonymous().and()
            //退出登录自己来控制
            .logout().disable()
            //因为没用到cookies,所以关闭cookies,防止循环定向
            .csrf().disable()
            //允许跨域
//            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
            //验证登录
            .addFilterBefore(new LoginFilter(), UsernamePasswordAuthenticationFilter.class)
            //验证权限
            .addFilterBefore(powerFilter, FilterSecurityInterceptor.class);


  }
}
