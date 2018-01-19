package com.yhyt.health.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class SMSutil {
	
	private static Logger logger=LoggerFactory.getLogger(SMSutil.class);
	
	//常量配置
//	private static int socketTimeout = 30000;// 请求超时时间  
//	private static int connectTimeout = 30000;// 传输超时时间  
//	private static String userId="j70336";
//	private static String password="563369";
//	private static String address="http://61.135.198.131:8023/MWGate/wmgw.asmx";
//	private static String pszSubPort="10690329150503";
//	private static String msg="验证码：%s，有效期%s分钟!";
	
	private static int socketTimeout = 30000;// 请求超时时间  
	private static int connectTimeout = 30000;// 传输超时时间  
	private static String SMSuserId;
	private static String SMSpassword;
	private static String SMSaddress;
	private static String SMSpszSubPort;
	private static String SMSmsg;
    //发送短信
    public static String sendMessage(String mobiles,String message,String expire) {
    	 String retStr = "";  
   	  // 创建HttpClientBuilder  
       HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
       // HttpClient  
       CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
       //拼装报文
       String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
               + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
               + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
               + " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
               + " <soap12:Body>"
               + " <MongateCsSpSendSmsNew xmlns=\"http://tempuri.org/\">"
               + " <userId>"+SMSuserId+"</userId>"
               + " <password>"+SMSpassword+"</password>"
               + " <pszMobis>"+mobiles+"</pszMobis>"
               + " <pszMsg>"+String.format(SMSmsg, message,expire)+"</pszMsg>"
               + " <iMobiCount>"+mobiles.split(",").length+"</iMobiCount>"
               + " <pszSubPort>"+SMSpszSubPort+"</pszSubPort>"
               + " </MongateCsSpSendSmsNew>" 
               + "</soap12:Body>"
               + " </soap12:Envelope>";
   //  设置请求和传输超时时间  
       RequestConfig requestConfig = RequestConfig.custom()
               .setSocketTimeout(socketTimeout)  
               .setConnectTimeout(connectTimeout).build(); 
       HttpPost httppost = new HttpPost(SMSaddress);
       httppost.setConfig(requestConfig);
       try {
       	 httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
//       	 httppost.setHeader("SOAPAction", soapAction);  
            StringEntity data = new StringEntity(soapRequestData,Charset.forName("UTF-8"));
            httppost.setEntity(data);  
            CloseableHttpResponse response = closeableHttpClient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {  
                // 打印响应内容  
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("返回信息："+retStr);
            }  
            // 释放资源  
            closeableHttpClient.close();  
		} catch (Exception e) {
			logger.error("发送短信出错,手机号："+mobiles,e);
		}
       return retStr;
    }

    @Value("${constant.sms.userId}") 
	public void setUserId(String userId) {
    	SMSuserId = userId;
	}

    @Value("${constant.sms.password}") 
	public void setPassword(String password) {
    	SMSpassword = password;
	}

    @Value("${constant.sms.address}")
	public void setAddress(String address) {
    	SMSaddress = address;
	}

    @Value("${constant.sms.pszSubPort}")
	public void setPszSubPort(String pszSubPort) {
    	SMSpszSubPort = pszSubPort;
	}
    
    @Value("${constant.sms.msg}")
	public void setMsg(String msg) {
    	SMSmsg = msg;
	}



	/**
     * @param argsv
     * @throws IOException
     * @throws HttpException
     */
	public static void main(String[] args) throws HttpException, IOException {
		sendMessage("15600163301","144456","1");
    }

}