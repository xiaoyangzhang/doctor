package com.yhyt.health.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yhyt.health.config.PathConfiguration;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.ObstetricsService;
import com.yhyt.health.util.DownLoadUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 产科登记接口
 * @author localadmin
 *
 */
@Api(value="",description = "产科登记相关操作")
@RestController
public class ObstetricsController {
	
	
    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;

    @Autowired
    private PathConfiguration pathConfiguration;
	
	@Autowired
	private ObstetricsService obstetricsService;
	 @ApiOperation(value = "自动发送孕产信息邮件", notes = "自动发送孕产信息邮件")
	@GetMapping("/getObstetricsDetailListMail")
	    public AppResult getObstetricsDetailListMail() {
	    	
		 AppResult appResult = new AppResult();
	      List diseaseAddedList=obstetricsService.getObstetricsDetailListMail();
	    	
	      
	      
	        Map<String, Object> mapPara = new HashMap<String, Object>();
	        mapPara.put("dictCode", "800");
	      com.yhyt.health.spring.AppResult result
          =restTemplate.getForObject(pathConfiguration.getSystemUrl()+"/dictionary/dictionary/?dictCode={dictCode}", com.yhyt.health.spring.AppResult.class, mapPara);

	      List newlist=new ArrayList();
	      List<Map> lm=(List<Map>) result.getBody();
	      for(int im=0;im<lm.size();im++) {
	    	  Map p=lm.get(im);
	    	  newlist.add(p.get("itemName").toString());
//	    	  System.out.println(p.get("itemName"));
	      }
	      
	      DownLoadUtil.exportandmail(diseaseAddedList,true,null,null,newlist);
	      
		 appResult.setResultCode(ResultCode.SUCCESS);
			return appResult;
	    }

	    
	    
}
