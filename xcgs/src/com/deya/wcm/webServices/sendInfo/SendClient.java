package com.deya.wcm.webServices.sendInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import sun.misc.BASE64Encoder;

public class SendClient {
	
	/**@
	 * 编译文件 FileToBase64
	 * @param path 编译的目标地址 【例：D://test/1.jpg】
	 * @return
	 */
	public static String encodeBase64File(String path) 
	{  
		String str = "";
	  try {
		          File  file = new File(path);  
		          FileInputStream inputFile;
				  inputFile = new FileInputStream(file);
		          byte[] buffer = new byte[(int)file.length()];  
	              inputFile.read(buffer);  
		          inputFile.close();  
		          str = new BASE64Encoder().encode(buffer); 
		   } catch (FileNotFoundException e) {
					e.printStackTrace();
		   } catch (IOException e) {
					e.printStackTrace();
		   } 
		  // System.out.println(str);
		   return str; 
	 }
	
	
	
	public static ISendInfo getServicesObj(String site_domain) {
		Service srvcModel = new ObjectServiceFactory().create(ISendInfo.class);
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory
				.newInstance().getXFire());
 
		String servicesURL = "http://" + site_domain + "/services/SendInfo";
		try {
			return (ISendInfo) factory.create(srvcModel, servicesURL);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getXML()
	{
		String username = "system";         // 用户名
		String password = "=#=88OWKd587QY=";         // 密    码
		String t_site_id = "CMSWCM16";        // 站点ID 
		String s_site_domain = "www.16.com";    // 报送网站域名 
		String s_site_name = "政务门户演示网站";      // 报送网站名称 
		String s_user_name = "系统管理员";      // 系统管理员 
		String s_send_dtime = "2013-03-27 09:00:08";     // 添加时间，格式为2008-10-11 11:22:33
		
		String r_cat_id = "10001";         // 报送栏目ID
		String r_cat_name = "测试目录三ydc";       // 报送栏目名称
		String send_record_id = "87";   // 信息ID
		String info_content = "<p><br />&nbsp;&nbsp;&nbsp; 在伟大的中国共产党成立90周年之际，我州各地的广大党员干部和普通百姓，都在以感恩的心情，学习和了解我们国家的执政党&mdash;&mdash;中国共产党的奋斗史、创业史和改革开放史。这是一件非常有意义的事。<br />&nbsp;&nbsp;&nbsp; 一位学者说，&ldquo;不懂历史的人没有根，淡忘历史的民族没有魂&rdquo;。我们应该记住这句名言。我们每个人都有了解党史、读懂党史、运用党史的责任。只有用党的&ldquo;三史&rdquo;来洗礼自己的思想，才能为&ldquo;做懂根的人，做有魂的民族&rdquo;打下思想基础。<br />&nbsp;&nbsp;&nbsp; &ldquo;以铜为镜可以正衣冠，以人为镜可以知得失，以史为镜可以知古今&rdquo;。学过党史的人知道，我们的党在已经走过的90年光辉历程中，真正做到了从无到有、由弱变强的转变，它带领全国各族人民战胜重重艰难险阻，成功地领导了两次革命，干了三件大事，实现了两次飞跃&hellip;&hellip;中国共产党的诞生和发展，扭转了近现代中国历史的走向，改变了中国人民的命运，对整个世界的格局和面貌也产生了广泛而深远的影响。<br />&nbsp;&nbsp;&nbsp; 了解了党史、学习了党史，可以得出这样的结沦：共产党没有自身的特殊利益，共产党的职能就是为人民服务。过去，没有共产党就没有新中国；今天，没有共产党就没有和谐发展的中国；未来，我们应该更加坚定一个信念：没有共产党就不会有未来屹立于世界强国之列的中国和中华民族。</p>";     // 内容
		String info_keywords = "关键字,测试";    // 关键字
		String info_description = "简介，描述"; // 简介，描述
		String title = "第三方报送信息测试20130516";            // 标题
		String model_id = "11";       // 模型默认为：11
		String author = "作者";           // 作者
		String source = "西安日报";           // 来源
		
		
		String file_name = "local.gif"; // 附件名称1
		String file_url = "/browser/images/local.gif";  // 附件地址(上传到服务器的地址/browser/images/local.gif)
		String file_code = ""; // 生成的附件加密编码1
		file_code = encodeBase64File("D://ydc/local.gif");
		
		System.out.println(file_code);
		StringBuffer sbf = new StringBuffer();
		sbf.append("<cicrowcm>");
		sbf.append("<username><![CDATA["+username+"]]></username>");
		sbf.append("<password><![CDATA["+password+"]]></password>");
		sbf.append("<t_site_id><![CDATA["+t_site_id+"]]></t_site_id>");
		sbf.append("<s_site_domain><![CDATA["+s_site_domain+"]]></s_site_domain>");
		sbf.append("<s_site_name><![CDATA["+s_site_name+"]]></s_site_name>");
		sbf.append("<s_user_name><![CDATA["+s_user_name+"]]></s_user_name>");
		sbf.append("<s_send_dtime><![CDATA["+s_send_dtime+"]]></s_send_dtime>");
		sbf.append("<infoList>");
		sbf.append("<info>");
		sbf.append("<r_cat_id>"+r_cat_id+"</r_cat_id>");
		sbf.append("<r_cat_name>"+r_cat_name+"</r_cat_name>");
		sbf.append("<send_record_id>"+send_record_id+"</send_record_id>");
		sbf.append("<r_content>");
		sbf.append("<info_content><![CDATA["+info_content+"]]></info_content>");
		sbf.append("<info_keywords><![CDATA["+info_keywords+"]]></info_keywords>");
		sbf.append("<info_description><![CDATA["+info_description+"]]></info_description>");
		sbf.append("<title><![CDATA["+title+"]]></title>");
		sbf.append("<model_id><![CDATA["+model_id+"]]></model_id>");
		sbf.append("<author><![CDATA["+author+"]]></author>");
		sbf.append("<source><![CDATA["+source+"]]></source>");
		sbf.append("</r_content>");
		
			//附件加载开始
			sbf.append("<fileList>");
			  //附件循环处开始
				sbf.append("<files>");
				sbf.append("<f_content>");
				sbf.append("<file_name><![CDATA["+file_name+"]]></file_name>");
				sbf.append("<file_url><![CDATA["+file_url+"]]></file_url>");
				sbf.append("<file_code><![CDATA["+file_code+"]]></file_code>");
				sbf.append("</f_content>");
				sbf.append("</files>");
			  //附件循环处结束
			sbf.append("</fileList>");
			//附件结束
			
		sbf.append("</info>");
		sbf.append("</infoList>");
		sbf.append("</cicrowcm>");
		System.out.println("");
		//System.out.println(sbf.toString());
	  return sbf.toString();	
	}
	/**
	 * 主方法
	 * @param filename
	 * @param imgstr
	 */
	public static void init(){
		 
		   String xml = getXML(); //得到xml数据
		   
		     //services链接地址
		   String servicesURL = "http://www.hbly.gov.cn/services/SendInfo";
		   
		    
			Service srvcModel = new ObjectServiceFactory().create(ISendInfo.class);
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
			
			ISendInfo service = null;
			try {
				service = (ISendInfo) factory.create(srvcModel,servicesURL);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (service != null) {
			    
				boolean isCorrect = service.sendInfoFormThirdParty(xml,"","");
			    
			    if(!isCorrect){
			    	System.out.println("webService失败！！！");
			    	return;
			    }
			    System.out.println("成功！！！"); 
			}
	}
	 
	 
	public static void main(String[] args)
	{
		 init();
//		 ISendInfo s = getServicesObj("www.hbly.gov.cn");
//		 String a = s.getReceiveConfigForXML();
//		 System.out.println(a);
	}
}
