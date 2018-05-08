package com.deya.wcm.template;

import java.io.File;
import java.io.IOException;

import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.system.template.TemplateUtils;

/**
 * 模板io操作类
 * @author 符江波
 * @version 1.0
 * @date   2011-4-16 下午06:29:46
 */
public class TemplateBase {

	/**
	 * 保存模板
	 * @param name
	 * @param site_id
	 * @param content
	 * @return boolean
	 */
	public static boolean saveTemplateFile(TemplateEditBean teb){
		return FileRmiFactory.saveTemplateFile(teb.getSite_id(), teb);
	}
	
	public static boolean saveTemplateFileHandl(TemplateEditBean teb){
		TemplateUtils.showMap();
		//System.out.println("key >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+teb.getT_id()+"_"+teb.getSite_id()/*+"_"+teb.getApp_id()*/);
		String path = TemplateUtils.getTemplatePath(teb.getT_id()+"_"+teb.getSite_id()/*+"_"+teb.getApp_id()*/);
		//System.out.println("save file at >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
		TemplateUtils.mkDirectory(new File(path));
		try {
			return FileOperation.writeStringToFile(path, teb.getT_content(), false, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean delTemplateFile(String t_id, String site_id, String app_id){
		String path = TemplateUtils.getTemplatePath(t_id+"_"+site_id/*+"_"+app_id*/);
		//System.out.println("delete file at >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
		try {			
			return FileRmiFactory.delFile(site_id, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 读取模板数据
	 * @param path
	 * @return String
	 
	public static String getTemplateFile(String t_id, String site_id, String app_id){
		System.out.println("key >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+t_id+"_"+site_id);
		String path = TemplateUtils.getTemplatePath(t_id+"_"+site_id);
		System.out.println("get file at >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
		try {
			return FileOperation.readFileToString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
}
