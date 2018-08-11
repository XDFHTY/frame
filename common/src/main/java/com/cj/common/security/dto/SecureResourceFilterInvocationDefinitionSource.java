package com.cj.common.security.dto;

import com.cj.common.domain.AuthModulars;
import com.cj.common.domain.AuthRoleModulars;
import com.cj.common.utils.domain.MemoryData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SecureResourceFilterInvocationDefinitionSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
    List<AuthRoleModulars> authRoleModulars=null;
    PathMatcher matcher=null;
    //储存访问url所需的角色
    Collection<ConfigAttribute> attsno=null;
    /**
     * 初始化用户权限，为了简便操作没有从数据库获取
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化匹配工具

    }

    /**当访问的Url和资源路径url匹配时，返回该Url所需要的权限
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //标志
        boolean flag = false;
        matcher =new AntPathMatcher();
        //安全配置信息集合
        attsno =new ArrayList<ConfigAttribute>();
        //memorydata中获取当前用户的对应权限信息和角色信息
        authRoleModulars = (List<AuthRoleModulars>) MemoryData.getRoleModularMap().get("modulars");
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //拦截请求url
        String requestURI = filterInvocation.getRequestUrl();
        //拦截请求方法
        String requestMethod = filterInvocation.getHttpRequest().getMethod();
        HttpServletResponse response = filterInvocation.getHttpResponse();
        //循环资源路径，当访问的Url和资源路径url匹配时，返回该Url所需要的权限
        Iterator<AuthRoleModulars> itr1 = authRoleModulars.iterator();
        System.out.println();
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
                    //数据库中接口信息
                    AuthModulars authModulars00 = itm0.next();
                    //接口url
                    String authUrl=authModulars00.getPageUrl();
                    //对应方法
                    String authMethod=authModulars00.getPageMethod();


                    //验证url和方法头是否匹配
                    if (matcher.match(requestURI, authUrl) && validUrlMethod(requestMethod,authMethod) ) {
                        //将访问此url所需的角色添加到attsno
                        attsno.add(new SecurityConfig(authRoleModulars1.getRoleName()));
                        System.out.println("==========访问此url所需的角色"+authRoleModulars1.getRoleName() );
                        //匹配成功
                        if (!flag)
                            flag=true;

                    }

                }

            }

        }
/*        Gson gson =new Gson();
        String strJson = gson.toJson(apiResult.toMap());
        try {
            HttpUtil.doReturn(response,strJson);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return attsno;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**返回此配置信息源是否可用
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {

        return true;


    }

    /**
     * @param reqMethod
     * @param authMethod
     * @return
     */
    public boolean validUrlMethod(String reqMethod,String authMethod){
        //标志
        boolean status =false;
        //如果角色下该接口无请求方式限制
        if ("ALL".equals(authMethod) ) status=true;
        //如果请求方式匹配
        if ( reqMethod.equals(authMethod))status=true;
        //否则
        return status;
    }
}
