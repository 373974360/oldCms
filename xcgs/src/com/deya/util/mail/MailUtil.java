package com.deya.util.mail;

import com.deya.util.CryptoTools;
import com.deya.util.jconfig.JconfigUtilContainer;

public class MailUtil {

    private static String mailserverhost = "smtp.163.com";
    private static String fromemail = "like19880828@163.com";
    private static String password = "ywshuiteiLKb.";

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

	public static void main(String arr[]){
		MailUtil.mailutil("373974360@qq.com", "中国人", "我是众人倒萨<a href=\"http://hao123.com\" target=\"_blank\" > 好123</a>");
	}
	
}
