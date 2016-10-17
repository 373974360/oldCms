package com.deya.wcm.services.system.config;

import java.io.File;

import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.services.control.site.SiteManager;

/**
 *  前台页面颜色管理.
 * <p>Title: WebPageColorService</p>
 * <p>Description: 前台页面颜色管理.</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class WebPageColorService {

	private static final String GROUPCSS = "group.css";
	private static final String SITESS = "site.css";
	private static final String GROUPCSSHTML = "html{filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);}";
	private static final String GROUPCSSHTMLANNOTATION = "/*html{filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);}*/";
	private static final String GROUPCSSHTML_PREFIX = "html{filter:progid";
	private static final String GROUPCSSHTML_SUFFIX = "BasicImage(grayscale=1);}";
	private static final String GROUPCSSHTMLANNOTATION_PREFIX = "/*html{filter:progid";
	private static final String GROUPCSSHTMLANNOTATION_SUFFIX = "BasicImage(grayscale=1);}*/";
	/**
	 * 得到group.css的全路径
	 * @return	String
	 */
	public static String getGroupCssFile(){
		  String cms_files_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "cms_files");
		  String group_css = cms_files_path + File.separator + "styles" + File.separator + GROUPCSS;
		  return group_css;
	}
	
	/**
	 * 设置整个站群页面为灰色
	 * @return	boolean
	 */
	public static boolean setGroupWebPageGrey(){
		  try{
			  String group_css = getGroupCssFile(); 
			  String content = FileOperation.readFileToString(group_css,"utf-8");
			  if(content.contains(GROUPCSSHTMLANNOTATION)){
				  content = getGreyCode(content);
				  FileOperation.writeStringToFile(group_css, content, false,"utf-8");
			  }else{
				  content = GROUPCSSHTML + content;
				  FileOperation.writeStringToFile(group_css, content, false,"utf-8");
			  }
			  return true;
		  }catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}
	
	/**
	 * 设置整个站群页面不为灰色
	 * @return	boolean
	 */
	public static boolean setGroupWebPageNoGrey(){
		  try{
			  String group_css = getGroupCssFile(); 
			  String content = FileOperation.readFileToString(group_css,"utf-8");
			  if(!content.contains(GROUPCSSHTMLANNOTATION)){  
				  content = getNoGreyCode(content);  
				  FileOperation.writeStringToFile(group_css, content, false,"utf-8");
			  }
			  return true;
		  }catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}  
	
	//注释掉灰色代码
	public static String getNoGreyCode(String content){
		int start  = content.indexOf(GROUPCSSHTML_PREFIX);
		 int end  = content.indexOf(GROUPCSSHTML_SUFFIX)+GROUPCSSHTML_SUFFIX.length();
		 if(start>=0 && end>0){
			 System.out.println(start);
			 System.out.println(end);
			 String temp1 = content.substring(0,start);
			 String temp2 = content.substring(end,content.length());
			 content = temp1 + GROUPCSSHTMLANNOTATION + temp2;
		 }
		 return content;
	}
	
	//添加灰色代码
	public static String getGreyCode(String content){
		 int start  = content.indexOf(GROUPCSSHTMLANNOTATION_PREFIX);
		 int end  = content.indexOf(GROUPCSSHTMLANNOTATION_SUFFIX)+GROUPCSSHTMLANNOTATION_SUFFIX.length();
		 if(start>=0 && end>0){
			 System.out.println(start);
			 System.out.println(end);
			 String temp1 = content.substring(0,start);
			 String temp2 = content.substring(end,content.length());
			 content = temp1 + GROUPCSSHTML + temp2;
		 }
		 return content;
	}
	
	
	//通过站点ID得到站点路径
	public static String getSiteCssFile(String site_id){
		  String site_root_path = SiteManager.getSitePath(site_id);
		  String site_css = site_root_path + File.separator + "styles" + File.separator + SITESS;
		  return site_css;
	}
	
	
	//整个站群用
	//如果页面中包含的是 已经置灰的代码  就设置为不 置灰
	//如果页面中包含的是 不置灰的代码  就设置为 置灰
	public static boolean setGroupWebPageGreyNoGrey(){
		try{
			  String group_css = getGroupCssFile(); 
			  String content = FileOperation.readFileToString(group_css,"utf-8");
			  if(content.contains(GROUPCSSHTML)){
				  if(content.contains(GROUPCSSHTMLANNOTATION)){//没有置灰
					  setGroupWebPageGrey();
				  }else{//已经置灰
					  setGroupWebPageNoGrey();
				  }
			  }else{//没有置灰
				  setGroupWebPageGrey();
			  }
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//单个站点用
	//如果页面中包含的是 已经置灰的代码  就设置为不 置灰
	//如果页面中包含的是 不置灰的代码  就设置为 置灰
	public static boolean setSiteWebPageGreyORNoGrey(String site_id){
		try{
			  String site_css = getSiteCssFile(site_id); 
			  String content = FileOperation.readFileToString(site_css,"utf-8");
			  if(content.contains(GROUPCSSHTML)){
				  if(content.contains(GROUPCSSHTMLANNOTATION)){//没有置灰
					  setSiteWebPageGrey(site_id);
				  }else{//已经置灰
					  setSiteWebPageNoGrey(site_id);
				  }
			  }else{//没有置灰
				  setSiteWebPageGrey(site_id);
			  }
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 通过站点id设置该站点页面为灰色
	 * @param String site_id
	 * @return	boolean
	 */
	public static boolean setSiteWebPageGrey(String site_id){
		  try{
			  String site_css = getSiteCssFile(site_id); 
			  String content = FileOperation.readFileToString(site_css,"utf-8");
			  if(content.contains(GROUPCSSHTMLANNOTATION)){
				  content = getGreyCode(content);
				  FileOperation.writeStringToFile(site_css, content, false,"utf-8");
			  }else{
				  content = GROUPCSSHTML + content;
				  FileOperation.writeStringToFile(site_css, content, false,"utf-8");
			  }
			  return true;
		  }catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}
	
	/**
	 * 通过站点id设置该站点页面不为灰色
	 * @return	boolean
	 */
	public static boolean setSiteWebPageNoGrey(String site_id){
		  try{
			  String site_css = getSiteCssFile(site_id);
			  String content = FileOperation.readFileToString(site_css,"utf-8");
			  if(!content.contains(GROUPCSSHTMLANNOTATION)){  
				  content = getNoGreyCode(content);  
				  FileOperation.writeStringToFile(site_css, content, false,"utf-8");
			  }
			  return true;
		  }catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	} 
	
	
	public static void main (String arr[]){

	}
	
	
	
}
