package com.deya.wcm.services.system.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.wcm.bean.system.template.TemplateCategoryBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.services.control.site.SiteManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-4-28 上午09:13:20
 */
public class TemplateUtils implements ISyncCatch{
	//保存所有的模板实体文件路径key=tid+siteid+appid  value=path+file+.vm
	private static Map<String, String> templatePathMap = new HashMap<String, String>();
	private static String template_default_path = JconfigFactory.getJconfigUtilInstance("velocityTemplate").getProperty("path", "", "template_default_path");
	
	static {
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl(){
		List<TemplateEditBean> tebs = TemplateEditManager.getAllTemplateEditList();
		templatePathMap.clear();
		if(tebs.size() > 0){
			for(TemplateEditBean teb : tebs){				
				TemplateCategoryBean tcb = TemplateCategoryManager.getTemplateCategoryBean(teb.getTcat_id(),teb.getSite_id(),"");				
				if(tcb == null){
					//System.out.println("ERROR:此模板没有所属分类。。。。。。tid="+teb.getTcat_id());
					templatePathMap.put(teb.getT_id()+"_"+teb.getSite_id()/*+"_"+teb.getApp_id()*/, FormatUtil.formatPath(template_default_path+"/"+teb.getSite_id(), true)+teb.getT_id()+"_"+teb.getT_ename()+".vm");
					continue;
				}//System.out.println(FormatUtil.formatPath(SiteManager.getSiteTempletPath(teb.getSite_id())+"/"+tcb.getTcat_position(), true)+teb.getT_id()+"_"+teb.getT_ename()+".vm");
				templatePathMap.put(teb.getT_id()+"_"+teb.getSite_id()/*+"_"+teb.getApp_id()*/, FormatUtil.formatPath(SiteManager.getSiteTempletPath(teb.getSite_id())+"/"+tcb.getTcat_position(), true)+teb.getT_id()+"_"+teb.getT_ename()+".vm");
			}
		}
	}
	
	public static void initTemplatePathMap(){
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.template.TemplateUtils");
	}
	
	public static String getTemplatePath(String key){
		if("7062_HIWCMzmzj".equals(key)){}
			//System.out.println(templatePathMap);
		return templatePathMap.get(key);
	}
	
	public static String getTemplatePth(String key){
		return getTemplatePath(key);
	}
	
	public static void setTemplatePath(String key, String value){
		templatePathMap.remove(key);
		templatePathMap.put(key, value);
	}
	 
	public static void setTemplatePath(TemplateEditBean teb)
	{
		TemplateCategoryBean tcb = TemplateCategoryManager.getTemplateCategoryBean(teb.getTcat_id(),teb.getSite_id(),"");
		templatePathMap.put(teb.getT_id()+"_"+teb.getSite_id()/*+"_"+teb.getApp_id()*/, FormatUtil.formatPath(SiteManager.getSiteTempletPath(teb.getSite_id())+"/"+tcb.getTcat_position(), true)+teb.getT_id()+"_"+teb.getT_ename()+".vm");
	}
	
	public static boolean delTemplatePath(String key){
		templatePathMap.remove(key);
		return true;
	}

	public static String formatSymbolString(String str, String symbol){
		if(str == null){
			return "";
		}
		if(str.trim().startsWith(symbol)){
			str = str.substring(1);
		}
		if(str.trim().endsWith(symbol)){
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	
	public static void mkDirectory(java.io.File f){
		if(!f.exists()){
			if(f.getPath().toLowerCase().endsWith(".vm")){
				f.getParentFile().mkdirs();
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				f.mkdirs();
			}
		}
	}
	
	public static void showMap(){
		System.out.println("templatePathMap start****************************************************************************");
		System.out.println(templatePathMap);
		System.out.println("templatePathMap end******************************************************************************");
	}
	public static void main(String[] args) throws Exception {
		System.out.println(templatePathMap);
		//java.io.File f = new java.io.File("E:\\tmp\\template\\wcm\\wcm1\\wcm2\\wcm3\\test.vm");
		//mkDirectory(f);
	}
}
