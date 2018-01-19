package com.yhyt.health.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yhyt.health.model.SysConfig;

@Component    
public class DownLoadUtil {

    private static DownLoadUtil downLoadUtil;
    @Autowired
    private com.yhyt.health.mapper.SysConfigMapper sysConfigMapper;
    
    @PostConstruct
    public void init() {
    	downLoadUtil = this;
    	downLoadUtil.sysConfigMapper=this.sysConfigMapper;
    }

    
    
	
	public static String myEmailAccount = "";
    public static String myEmailPassword = "";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
//    public static String myEmailSMTPHost = "smtp.163.com";
    
    public static String myEmailSMTPHost = "";
    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "";
    
    public static String custser = "";
	
	public static Workbook  downloadFIle(HttpServletRequest request, HttpServletResponse response,List t1,List t2,List t3,String sheetname,String filename, List newlist) {
		
	 	
	   	 Workbook workbook = exportExcel(t1,t2,t3,sheetname, ExcelType.HSSF);
	   	 
	   	 
		 
	   	 //设置成下拉列表
	      
//		     String[] strs = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii"};
		    
		   String[] strs = new String[newlist.size()];
		   for(int ne=0;ne<newlist.size();ne++) {
			   strs[ne]=newlist.get(ne).toString();
		   }
         //设置第一列的1-10行为下拉列表

         CellRangeAddressList regions = new CellRangeAddressList(0, t3.size(), 16, 18);

         //创建下拉列表数据

         DVConstraint constraint = DVConstraint.createExplicitListConstraint(strs);

         //绑定

         HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);

         workbook.getSheetAt(0).addValidationData(dataValidation);
//         sheet.addValidationData(dataValidation);
	   	 
	   	 
	   	 if(null!=request&&null!=response) {
	   	 try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/x-download");
	        String filedisplay = filename;
	        try {
				filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);

	        try {
	            OutputStream out = response.getOutputStream();
	            workbook.write(out);
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   	 }
	        return workbook;
		}
	
	 
    public static  Workbook exportExcel(List t1,List t2,List t3,String sheetname, ExcelType type) {
    	
    	
    	
    	HSSFWorkbook workbook;
        if (ExcelType.HSSF.equals(type)) {
            workbook = new HSSFWorkbook();
        } else {
    //        workbook = new XSSFWorkbook();
            workbook = new HSSFWorkbook();
        }
        workbook=createExcel(workbook,t1,t2,t3,sheetname);
        return workbook;
    }
    
    
    
    public static  HSSFWorkbook createExcel(HSSFWorkbook book,
            List<String> heads, List<String> fieldList, List<Object> dataList,String sheettName) {
        HSSFWorkbook  workbook = book;
        if(workbook ==null)
            workbook= new HSSFWorkbook();
        
        if(sheettName==null||"".equals(sheettName))
            sheettName="sheet"+Math.random();
        
        HSSFSheet sheet = workbook.createSheet(sheettName); //
        sheet.setDefaultColumnWidth(20);  
        sheet.setDefaultRowHeightInPoints(20);
        // 在索引0的位置创建行（最顶端的行）
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell=null;
       
       //创建表格第一行的标题
        for (int i = 0; i < heads.size(); i++) {
            // 在索引0的位置创建单元格（左上端）
            cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            // 在单元格中输入一些内容
            cell.setCellValue(heads.get(i));
        }

        for (int n = 0; n < dataList.size(); n++) {
            // 在索引1的位置创建行（最顶端的行）
            HSSFRow row_value = sheet.createRow(n + 1);
            Map<String, String> dataMap =(Map<String, String>)dataList.get(n);
            HSSFCell cell_v=null;
            for (int i = 0; i < fieldList.size(); i++) {
                // 在索引0的位置创建单元格（左上端）
                cell_v = row_value.createCell(i);
                // 定义单元格为字符串类型
                cell_v.setCellType(HSSFCell.CELL_TYPE_STRING);
                // 在单元格中输入一些内容
                cell_v.setCellValue(String.valueOf(dataMap.get(fieldList.get(i))));
            }
        }
        return  workbook ;
    } 
    
    
    public static void exportandmail(List diseaseAddedList, boolean b,HttpServletRequest request, HttpServletResponse response, List newlist) {
 		// TODO Auto-generated method stub
 	    	  List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
 		      List<String> t1=new ArrayList<String>();
 		      t1.add("报告单位");
 		      t1.add("填表日期");
 		      t1.add("编号");
 		      t1.add("孕妇名称");
 		      t1.add("孕妇身份证号码");
 		      t1.add("年龄");
 		      t1.add("孕次");
 		      t1.add("产次");
 		      t1.add("丈夫姓名");
 		      t1.add("户口北京（区）");
 		      t1.add("户口外地（省）");
 		      t1.add("现住址");
 		      t1.add("手机");
 		      t1.add("手机号码");
 		      t1.add("孕周");
 		      t1.add("预产期");
 		      t1.add("高危因素1");
 		      t1.add("高危因素2");
 		      t1.add("高危因素3");
 		      t1.add("备注");
 		      t1.add("三联单回执");
 		      t1.add("转出单位");
 		      List<String> t2=new ArrayList<String>();
 		      t2.add("hospital");
 		      t2.add("createTime");
 		      t2.add("index");
 		      t2.add("realname");
 		      t2.add("idno");
 		      t2.add("age");
 		      t2.add("pregnantTime");
 		      t2.add("produceTime");
 		      t2.add("husbandName");
 		      t2.add("birthPlace1");
 		      t2.add("birthPlace2");
 		      t2.add("resideAdress");
 		      t2.add("husbandMobile");
 		      t2.add("username");
 		      t2.add("pregnantWeek");
 		      t2.add("expectBirthDate");
 		      t2.add("dangerous1");
 		      t2.add("dangerous2");
 		      t2.add("dangerous3");
 		      t2.add("remark");
 		      t2.add("threetime");
 		      t2.add("outhospital");
 		      List t3=new ArrayList();
 		      
 		      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		      for(int li=0;li<diseaseAddedList.size();li++) {
 		    	  Map temp=(Map) diseaseAddedList.get(li);
 		    	   Map m1=new HashMap();
 		    	      m1.put("hospital", temp.get("hospital"));
 		    	      if(null==temp.get("createTime")||"".equals(temp.get("createTime"))) {
 		    	    	  m1.put("createTime", "");
 		    	      }
 		    	      else {
 		    	    	  m1.put("createTime", simpleDateFormat.format(new Date(Long.parseLong( temp.get("createTime").toString()))));
 		    	      }
// 		    	      m1.put("createTime", simpleDateFormat.format(new Date(Long.parseLong( temp.get("createTime").toString()))));
 		    	      m1.put("index", li+1);
 		    	      m1.put("realname", temp.get("realname"));
 		    	      m1.put("idno", temp.get("idno"));
 		    	      m1.put("age", temp.get("age"));
 		    	      m1.put("pregnantTime", temp.get("pregnantTime"));
 		    	      m1.put("produceTime", temp.get("produceTime"));
 		    	      m1.put("husbandName", temp.get("husbandName"));
 		    	      if(null!=temp.get("birthPlace")&&!temp.get("birthPlace").equals("")) {
 		    	    	  if(temp.get("birthPlace").toString().contains("北京")) {
 		    	    		 m1.put("birthPlace1", temp.get("birthPlace"));
 	 	 		    	      m1.put("birthPlace2", "");
 		    	    	  }
 		    	    	  else {
 		    	    		 m1.put("birthPlace1", "");
 	 	 		    	      m1.put("birthPlace2", temp.get("birthPlace"));
 		    	    	  }
 		    	    	
 		    	      }
 		    	      else {
 		    	    	 m1.put("birthPlace1", "");
 	 		    	      m1.put("birthPlace2", "");
 		    	      }
 		    	     
 		    	      m1.put("resideAdress", temp.get("resideAdress"));
 		    	      m1.put("husbandMobile", temp.get("husbandMobile"));
 		    	      m1.put("username", temp.get("username"));
 		    	      m1.put("pregnantWeek", temp.get("pregnantWeek"));
 		    	      if(null==temp.get("expectBirthDate")||"".equals(temp.get("expectBirthDate"))) {
 		    	    	  m1.put("expectBirthDate", "");
 		    	      }
 		    	      else {
 		    	    	  m1.put("expectBirthDate", simpleDateFormat.format(new Date(Long.parseLong( temp.get("expectBirthDate").toString()))));
 		    	      }
 		    	      String dang=temp.get("dangerous").toString();
 		    	      if(null!=dang&&!"".equals(dang)) {
 		    	    	  String[] agr=dang.split(",");
 		    	    	  if(agr.length>=1&&null!=agr[0]) {
 		    	    		 m1.put("dangerous1", agr[0]);
 		    	    	  }
 		    	    	  
 		    	    	 if(agr.length>=2&&null!=agr[1]) {
 		    	    		 m1.put("dangerous2", agr[1]);
 		    	    	  }else {
 		    	    		 m1.put("dangerous2", "");
 	 	 		    	      m1.put("dangerous3", "");
 		    	    	  }
 		    	    	 if(agr.length>=3&&null!=agr[2]) {
 		    	    		 m1.put("dangerous3", agr[2]);
 		    	    	  }
 		    	    	 else {
 		    	    		 m1.put("dangerous3", "");
 		    	    	 }
 		    	      }
 		    	      else {
 		    	    	 m1.put("dangerous1", "");
 	 		    	      m1.put("dangerous2", "");
 	 		    	      m1.put("dangerous3", "");
 		    	      }
 		    	      
 		    	      m1.put("remark", temp.get("remark"));
 		    	      m1.put("threetime", "");
 		    	      m1.put("outhospital", "");
 		    	      t3.add(m1);
 		    	  
 		      }
 		   
 		      
 		      String filename="高危孕产妇信息上报录入表.xls";
 		      
 		      Workbook workbook=downloadFIle(request,response, t1, t2, t3, "表单1",filename,newlist);
 		      
 		      
 		      
 		      //设置成下拉列表
 		      
// 		     String[] strs = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii"};
 		    
 		   String[] strs = new String[newlist.size()];
 		   for(int ne=0;ne<newlist.size();ne++) {
 			   strs[ne]=newlist.get(ne).toString();
 		   }
             //设置第一列的1-10行为下拉列表

             CellRangeAddressList regions = new CellRangeAddressList(0, diseaseAddedList.size(), 16, 18);

             //创建下拉列表数据

             DVConstraint constraint = DVConstraint.createExplicitListConstraint(strs);

             //绑定

             HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);

             workbook.getSheetAt(0).addValidationData(dataValidation);
//             sheet.addValidationData(dataValidation);
             
             
             
             
             
             
 		      
 		      if(b) {
 			ByteArrayOutputStream baos= new ByteArrayOutputStream();
 			try {
 				workbook.write(baos);  
 				  baos.flush();  
 				  byte[] bt = ((ByteArrayOutputStream) baos).toByteArray();  
 				  InputStream is = new ByteArrayInputStream(bt, 0, bt.length);  
 				  baos.close();  
 				  sendMail(is);
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} 
 		      }
 	}

    
    public static void sendMail(InputStream  dh) {
    	
    	   List<SysConfig> sysConfigurl = downLoadUtil.sysConfigMapper.selectSysConfig("mailurl");
    	   List<SysConfig> sysConfigpassword = downLoadUtil.sysConfigMapper.selectSysConfig("mailpassword");
    	   List<SysConfig> sysConfigHost = downLoadUtil.sysConfigMapper.selectSysConfig("mailhost");
    	   List<SysConfig> sysConfigcustser = downLoadUtil.sysConfigMapper.selectSysConfig("custser");
    	  
    	   if(null!=sysConfigurl&&null!=sysConfigpassword&&null!=sysConfigHost&&null!=sysConfigcustser) {
    		   myEmailAccount=sysConfigurl.get(0).getSalt1();
    		   receiveMailAccount=sysConfigurl.get(0).getSalt2();
    		   myEmailSMTPHost=sysConfigHost.get(0).getSalt1();
    		   myEmailPassword=sysConfigpassword.get(0).getSalt1();
    		   custser=sysConfigcustser.get(0).getSalt1();
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        try {
			// 3. 创建一封邮件
			MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,dh);

			// 4. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();

			// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			// 
			//    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
			//           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
			//           类型到对应邮件服务器的帮助网站上查看具体失败原因。
			//
			//    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
			//           (1) 邮箱没有开启 SMTP 服务;
			//           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
			//           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
			//           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
			//           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
			//
			//    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
			transport.connect(myEmailAccount, myEmailPassword);

			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			// 7. 关闭连接
			transport.close();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	   }
   }
   
   public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,InputStream  dh) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "海虹控股", "UTF-8"));
        List<SysConfig> sysConfigobstetrics = downLoadUtil.sysConfigMapper.selectSysConfig("obstetrics");
        String receivename="";
        try {
			receivename=sysConfigobstetrics.get(0).getSalt1()+sysConfigobstetrics.get(0).getSalt2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receivename, "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        Date da=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String dat=sd.format(da);
		System.out.println(dat);
        message.setSubject(dat+"垂杨柳医院产科高危孕产妇信息上报表", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent("您好，请查收邮件，附件内容为上周垂杨柳医院产科高危孕产妇信息上报表：如有问题请及时上报，客服电话："+custser, "text/html;charset=UTF-8");
        
        MimeBodyPart attachment = new MimeBodyPart();
//        DataHandler dh2 = new DataHandler(new FileDataSource("product.xls"));  // 读取本地文件
        DataSource source = new ByteArrayDataSource(dh, "application/msexcel"); 
        DataHandler dh2 = new DataHandler(source);  // 读取本地文件
        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        attachment.setFileName("高危孕产妇信息上报录入表.xls");
        
        
        
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系
        
        BodyPart contentPart = new MimeBodyPart();  
        contentPart.setText("您好，请查收邮件，附件内容为上周垂杨柳医院产科高危孕产妇信息上报表：如有问题请及时上报，客服电话："+custser);  
        mm.addBodyPart(contentPart);

        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
	
}
