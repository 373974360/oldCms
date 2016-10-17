package com.deya.util.mail;

import com.deya.util.CryptoTools;
import com.deya.util.jconfig.JconfigUtilContainer;

public class MailUtil {
	
	private static String mailserverhost = JconfigUtilContainer.bashConfig().getProperty("mailserverhost","", "email");
	private static String fromemail = JconfigUtilContainer.bashConfig().getProperty("fromemail","", "email");
	private static String password = JconfigUtilContainer.bashConfig().getProperty("password","", "email");
	
	private static String titleP = JconfigUtilContainer.bashConfig().getProperty("title","", "email");
	private static String htmlP = JconfigUtilContainer.bashConfig().getProperty("htmlinfo","", "email");
	
	public static void mailutil(String mail, String title,String content) {
		  MailSenderInfo mailInfo = new MailSenderInfo();    
	
		  //qq邮箱
//	      mailInfo.setMailServerHost("smtp.qq.com");   //设置 发送邮件的服务器
//	      //mailInfo.setMailServerPort("25");            //设置 发送邮件的服务器端口
//	      mailInfo.setValidate(true);    
//	      mailInfo.setUserName("410959417@qq.com");    //您邮箱用户名   
//	      mailInfo.setPassword("qazwsxedcrfvs");    //您的邮箱密码    
//	      mailInfo.setFromAddress("410959417@qq.com"); //发送邮件地址 
	      
	      //163邮箱
//	      mailInfo.setMailServerHost("smtp.163.com");   //设置 发送邮件的服务器
//	      //mailInfo.setMailServerPort("25");            //设置 发送邮件的服务器端口
//	      mailInfo.setValidate(true);    
//	      mailInfo.setUserName("jimmylsp@163.com");    //您邮箱用户名   
//	      mailInfo.setPassword("19861208");    //您的邮箱密码    
//	      mailInfo.setFromAddress("jimmylsp@163.com"); //发送邮件地址 
		  
	      //163邮箱
	      mailInfo.setMailServerHost(mailserverhost);   //设置 发送邮件的服务器
	      //mailInfo.setMailServerPort("25");            //设置 发送邮件的服务器端口
	      mailInfo.setValidate(true);     
	      mailInfo.setUserName(fromemail);    //您邮箱用户名   
	      mailInfo.setPassword(password);    //您的邮箱密码    
	      mailInfo.setFromAddress(fromemail); //发送邮件地址 
		  
	      //DIY邮箱
//	      mailInfo.setMailServerHost("jimmy.com");   //设置 发送邮件的服务器
//	      //mailInfo.setMailServerPort("25");            //设置 发送邮件的服务器端口
//	      mailInfo.setValidate(true);     
//	      mailInfo.setUserName("fengyun");    //您邮箱用户名   
//	      mailInfo.setPassword("123456");    //您的邮箱密码    
//	      mailInfo.setFromAddress("fengyun@jimmy.com"); //发送邮件地址 
	      
	      
	      mailInfo.setToAddress(mail);   //接收邮件地址
	      mailInfo.setSubject(title);   //邮件标题
	      mailInfo.setContent(content);    //邮件内容
	      //这个类主要来发送邮件
	      MailSender sms = new MailSender();   
	      //sms.sendTextMail(mailInfo);//发送文体格式    
	      sms.sendHtmlMail(mailInfo);//发送html格式   
	}
	   
	
	public static boolean sendInfo(String email,String name){
		try{
			CryptoTools ct = new CryptoTools();
			name =  ct.encode(name).replaceAll("=", "DENGHAO").replaceAll("&","ANDYU").replaceAll("#","JINGHAO").replaceAll("\\+","JIAHAO");
			String title = "激活邮件";
			title = titleP;
			String href = "http://www.xadongman.gov.cn/active/active.jsp?name="+name;
			String html = "点击下面链接激活账号  <a href=\""+href+"\" target=\"_blank\" >"+href+"</a>";
			html = htmlP.replaceAll("nameStr", name);
			MailUtil.mailutil(email,title,html); 
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	public static void main(String arr[]){
		MailUtil.mailutil("526591164@qq.com", "中国人", "我是众人倒萨<a href=\"http://hao123.com\" target=\"_blank\" > 好123</a>");
	}
	
}
