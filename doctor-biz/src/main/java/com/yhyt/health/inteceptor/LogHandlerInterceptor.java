package com.yhyt.health.inteceptor;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class LogHandlerInterceptor implements HandlerInterceptor{

    //日志
     private static final Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    private final RedisService redisService;

    private final DoctorConstant doctorConstant;

    public LogHandlerInterceptor(RedisService redisService,DoctorConstant doctorConstant) {
        this.redisService=redisService;
        this.doctorConstant=doctorConstant;
    }

    /**
     *进入方法前执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());*/
        // 获取用户token
        /*String token = request.getHeader("token");
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"),token);
        if(null != doctor ){
           logger.info("token合法");
        }else{
            logger.info("token查不到");
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
