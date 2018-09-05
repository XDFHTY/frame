package com.cj.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * function 系统访问数据信息增强工具类-aop
 * Created by duyuxiang on 2017/9/22.
 * version v1.0
 */
@SuppressWarnings("ALL")
@Component
@Aspect
public class ControllerAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //匹配com.cj.tangtuan.controller包及其子包下的所有类的所有方法
    @Pointcut("execution(* com.cj.*.controller.*..*(..))")
    public void executeService() {
    }


    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("收到请求：Around ====================================================");

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if (result == null) {
                //如果切到了 没有返回类型的void方法，这里直接返回
                return null;
            }
            long end = System.currentTimeMillis();

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null){
                return result;
            }
            HttpServletRequest request = attributes.getRequest();
            StringBuffer Url = request.getRequestURL();
            if (Url.indexOf("/api/") == -1 || Url.indexOf("/ws") !=-1) {
                return result;
            }


            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("name");
            if (name == null || name.length() < 1) {
                name = request.getParameter("adminName");
                if(name == null || name.length() < 1){
                    name = request.getParameter("userName");
                }
            }
            //nginx后的真实IP
            String ip = request.getHeader("X-Real-IP");

            if (ip == null || ip == "") {
                ip = request.getRemoteAddr();
            }

            Object[] args = joinPoint.getArgs();// 参数
            int argsSize = args.length;
            String argsTypes = "";

            String typeStr = joinPoint.getSignature().getDeclaringType().toString().split(" ")[0];
            String returnType = joinPoint.getSignature().toString().split(" ")[0];


            // 记录下请求内容
            logger.info("请求 URL : " + request.getRequestURL().toString());
            logger.info("IP  地址 : " + ip);
            logger.info("请求方式 : " + request.getMethod());
            logger.info("请求时间 : " + sdf.format(start));
            logger.info("请求用户 : " + name);


            logger.info("参数个数:" + argsSize);

            if (argsSize > 0) {
                // 拿到参数的类型
                for (Object object : args) {
                    if (object != null) {
                        argsTypes += object.getClass().getTypeName().toString() + ", ";
                    }
                }
                logger.info("参数类型：" + argsTypes);
            }
            logger.info("执行方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("执行参数 : " + Arrays.toString(joinPoint.getArgs()));

            logger.info("返回类型:" + returnType);

//            //获取所有参数方法一：
//            Enumeration<String> enu=request.getParameterNames();
//            while(enu.hasMoreElements()){
//                String paraName=(String)enu.nextElement();
//                System.out.println("======================"+paraName);
//            }


            String description = getControllerMethodDescription(joinPoint);
            logger.info("执    行 : " + description);


            Map map = (Map) result;
            // 处理完请求，返回内容
            logger.info("执行结果: " + (map.get("msg")));
            Long total = end - start;
            logger.info("Around耗时: " + total + " ms!");
            logger.info("执行完成:==========================================================================================");

            return result;

        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info("====around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : "
                    + e.getMessage());
            throw e;
        }

    }

    @Before("executeService()")
    public void doBefore (JoinPoint joinPoint){

    }

    @AfterReturning(value = "executeService()", returning = "obj")
    public void doAfterReturning (JoinPoint joinPoint, Object obj){


    }

    // 通过反射获取参入的参数
    public static String getControllerMethodDescription (JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        String description = "";

        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();

                if (clazzs.length == arguments.length) {
                    if(method.getAnnotation(Log.class) != null){
                        description = method.getAnnotation(Log.class).name();

                    }
                    break;
                }
            }
        }
        return description;
    }



}
