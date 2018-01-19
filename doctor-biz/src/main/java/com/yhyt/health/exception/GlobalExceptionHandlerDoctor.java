/*
package com.yhyt.health.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.servlet.ModelAndView;

import com.yhyt.health.contans.ResultCode;
import com.yhyt.health.result.AppResult;

*/
/**
* localadmin 作者: 
* @version 创建时间：2017年8月21日 下午4:07:18 
* 类说明 
*//*

@ControllerAdvice
public class GlobalExceptionHandler {
	
	public static final String DEFAULT_ERROR_VIEW = "error";
	
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public AppResult defaultErrorHandler(HttpServletRequest req, CustomException e) throws Exception {
    	AppResult appResult=new AppResult();
    	appResult.setResultCode(ResultCode.valueOf(e.getMessage()));
        return appResult;
    }
//    
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public AppResult defaultErrorHandler(HttpServletRequest req, MissingServletRequestParameterException e) throws Exception {
    	AppResult appResult=new AppResult();
    	appResult.getStatus().setCode("100007");
    	appResult.getStatus().setMessage("系统异常，请联系管理员");
        return appResult;
    }
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public AppResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	AppResult appResult=new AppResult();
    	appResult.getStatus().setCode("100008");
    	appResult.getStatus().setMessage("系统异常，请联系管理员");
        return appResult;
    }

}
*/
