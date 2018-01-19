package com.yhyt.health.exception;
/** 
* localadmin 作者: 
* @version 创建时间：2017年8月21日 下午4:13:37 
* 类说明 
*/
public class CustomException extends RuntimeException {
	
	private CustomException(String code) {
            super(code);
	}

}
