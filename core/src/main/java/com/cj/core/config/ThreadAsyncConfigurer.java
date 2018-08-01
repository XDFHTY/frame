package com.cj.core.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class ThreadAsyncConfigurer  implements AsyncConfigurer {


    @Value("${thread.corePoolSize}")
    private int corePoolSize;//线程池维护线程的最少数量

    @Value("${thread.maxPoolSize}")
    private int maxPoolSize;//线程池维护线程的最大数量

    @Value("${thread.queueCapacity}")
    private int queueCapacity; //缓存队列

    @Value("${thread.keepAlive}")
    private int keepAlive ;//允许的空闲时间

//    @Async("getAsyncExecutor")
// getAsyncExecutor 即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池,返回值为void时用

    @Bean
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(corePoolSize);
        //设置最大线程数
        threadPool.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(queueCapacity);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(keepAlive);
        //  线程名称前缀
        threadPool.setThreadNamePrefix("MyAsync-");
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}