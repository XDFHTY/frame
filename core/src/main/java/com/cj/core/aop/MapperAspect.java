package com.cj.core.aop;

import com.cj.core.config.datasource.DynamicDataSourceHolder;
import com.cj.core.config.datasource.TargetDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 使用aop监控Mapper执行时间
 * Created by me on 2017/2/20.
 */
@Aspect
@Component
@Slf4j
public class MapperAspect {
    private static final Logger logger = LoggerFactory.getLogger(MapperAspect.class);

    @AfterReturning("execution(* com.cj.*.mapper.*Mapper.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        logger.info("Completed: " + joinPoint);
    }


    /**
     * 监控com.caiyi.financial.nirvana..*Mapper包及其子包的所有public方法
     */
    @Pointcut("execution(* com.cj.*.mapper.*Mapper.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 声明环绕通知
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.nanoTime();
        Object obj = pjp.proceed();
        long end = System.nanoTime();

        logger.info("\n调用Mapper方法：{}，" +
                        "\n参数：{}，" +
//                        "\n执行耗时：{}纳秒，" +
                        "\r\n耗时：{}毫秒",
                pjp.getSignature().toString(),
                Arrays.toString(pjp.getArgs()),
//                (end - begin),
                (end - begin) / 1000000);
        return obj;
    }

//    @Before("pointCutMethod()")
//    public void before(JoinPoint joinPoint) {
//        Object target = joinPoint.getTarget();
//        String method = joinPoint.getSignature().getName();
//        Class<?>[] clazz = target.getClass().getInterfaces();
//        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
//        try {
//            Method m = clazz[0].getMethod(method, parameterTypes);
//            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
//            if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
//                TargetDataSource data = m.getAnnotation(TargetDataSource.class);
//                String dataSourceName = data.value();
//                DynamicDataSourceHolder.putDataSource(dataSourceName);
//                log.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
//                System.out.println("============使用数据源："+dataSourceName);
//            } else {
//                log.debug("switch datasource fail,use default");
//                System.out.println("============未配置数据源，使用默认数据源");
//            }
//        } catch (Exception e) {
//            log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
//            System.out.println("==============数据源切换失败，使用默认数据源");
//        }
//    }
//
//    //执行完切面后，将线程共享中的数据源名称清空
//    @After("pointCutMethod()")
//    public void after(JoinPoint joinPoint){
//        DynamicDataSourceHolder.removeDataSource();
//    }
}