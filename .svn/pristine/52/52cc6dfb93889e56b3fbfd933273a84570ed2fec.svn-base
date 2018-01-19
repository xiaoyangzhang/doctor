package com.yhyt.health.config;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.inteceptor.LogHandlerInterceptor;
import com.yhyt.health.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 * @author gsh
 * @create 2017-08-29 20:41
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{

    private  static  final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private DoctorConstant doctorConstant;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new LogHandlerInterceptor(redisService,doctorConstant))
                .addPathPatterns("/**")
                .excludePathPatterns("/doctors","/verificationCodes","/webjars/**","/swagger-resources/**","/v2/api-docs");
        super.addInterceptors(registry);
    }
}
