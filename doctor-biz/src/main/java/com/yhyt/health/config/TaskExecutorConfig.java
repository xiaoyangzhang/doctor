package com.yhyt.health.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** 
* localadmin 作者: 
* @version 创建时间：2017年8月17日 下午3:01:04 
* 类说明 
*/
@Configuration
@ComponentScan("com.yhyt.health.service")
@EnableAsync //开启异步任务支持 
public class TaskExecutorConfig implements AsyncConfigurer {
	
	  @Override
	    public Executor getAsyncExecutor() {//实现AsyncConfigurer接口并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，这样我们就获得了一个基于线程池TaskExecutor
	         ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	            taskExecutor.setCorePoolSize(5);
	            taskExecutor.setMaxPoolSize(10);
	            taskExecutor.setQueueCapacity(25);
	            taskExecutor.initialize();
	            return taskExecutor;
	    }

	    @Override
	    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
	        return null;
	    }

}
