package com.deya.wcm.services.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateVerBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.design.DesignDAO;
import com.deya.wcm.dao.system.template.TemplateEditDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.template.TemplateBase;

/**
 * @author zhuliang
 * @date   2011-3-24 上午10:19:00
 */
public class TemplateEditManager implements ISyncCatch{
	private static Map<String,String> temp_name_map = new HashMap<String,String>();//缓存所有的模板名称，已免取名称时再读库
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		temp_name_map.clear();
		List<TemplateEditBean> l = TemplateEditDAO.getAllTemplateEditList();
		try{
			if(l != null && l.size() > 0)
			{
				for(TemplateEditBean tm : l)
				{
					temp_name_map.put(tm.getT_id()+"_"+tm.getSite_id(), tm.getT_cname());
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void reloadTemplateEdit()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.template.TemplateEditManager");
	}
	
	public static int getNewTemplate()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_EDIT_TABLE_NAME);
	}
	
	/**
	 * 根据ID得到模板信息
	 * @param int t_id
	 * @return TemplateEditBean
	 * */
	public static TemplateEditBean getTemplateEditBean(int t_id, String site_id, String app_id)
	{
		return TemplateEditDAO.getTemplateEditBean(t_id,site_id,app_id);
	}
	
	public static String getTemplateNameById(int template_id, String site_id, String app_id){
		String k = template_id+"_"+site_id;
		if(temp_name_map.containsKey(k))
		{
			return temp_name_map.get(k);
		}else
		{
			TemplateEditBean t = TemplateEditManager.getTemplateEditBean(template_id, site_id, app_id);
			if(t != null)
			{
				return t.getT_cname();
			}else
				return "";
		}
	}

	/**
	 * 得到模板总数
	 * @param 
	 * @return String
	 * */
	public static String getTemplateEditCount(Map<String,String> con_map)
	{
		return TemplateEditDAO.getTemplateEditCount(con_map);
	}

	/**
	 * 修改模板
	 * @param author
	 * @return boolean
	 */
	public static boolean updateTemplateEdit(TemplateEditBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}else{
			template.setT_ver(1 + TemplateVerManager.getTemplateVerNum(template.getT_id(),template.getSite_id()));
			template.setModify_dtime(DateUtil.getCurrentDateTime());
		}
		if(TemplateEditDAO.updateTemplateEdit(template,stl)){
			TemplateVerManager.addTemplateVer(TEBtoTVB(template), stl);
			//向缓存添加模板路径信息
			TemplateUtils.setTemplatePath(template.getT_id()+"_"+template.getSite_id(),FormatUtil.formatPath(SiteManager.getSiteTempletPath(template.getSite_id())+"/"+TemplateCategoryManager.getTemplateCategoryBean(template.getTcat_id(), template.getSite_id(), template.getApp_id()).getTcat_position(), true)+template.getT_id()+"_"+template.getT_ename()+".vm");
		}
		reloadTemplateEdit();
		return true;
	}
	
	/**
	 * 修改模板状态
	 * @param author
	 * @return boolean
	 */
	public static boolean updateTemplateEditStatus(TemplateEditBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}
		template.setT_status(1+"");
		TemplateEditDAO.updateTemplateEdit(template, stl);
		reloadTemplateEdit();
		return true;
	}	

	/**
	 * 添加模板
	 * @param template
	 * @return boolean
	 */
	public static boolean addTemplateEdit(TemplateEditBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}else{
			template.setT_ver(1 + TemplateVerManager.getTemplateVerNum(template.getT_id(),template.getSite_id()));
			template.setCreat_dtime(DateUtil.getCurrentDateTime());
		}
		int tbId = PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_EDIT_TABLE_NAME);
		template.setT_id(tbId);
		template.setId(tbId);
		if(TemplateEditDAO.addTemplateEdit(template,stl)){
			TemplateVerManager.addTemplateVer(TEBtoTVB(template), stl);
			//向缓存添加模板路径信息
			TemplateUtils.setTemplatePath(template.getT_id()+"_"+template.getSite_id(),FormatUtil.formatPath(SiteManager.getSiteTempletPath(template.getSite_id())+"/"+TemplateCategoryManager.getTemplateCategoryBean(template.getTcat_id(), template.getSite_id(), template.getApp_id()).getTcat_position(), true)+template.getT_id()+"_"+template.getT_ename()+".vm");
		}
		reloadTemplateEdit();
		return true;
	}
	
	/**
	 * 从模板设计工具中添加模板，直接发布
	 * @param TemplateEditBean template
	 * @param boolean is_update 添加还是和操作
	 * @return boolean
	 */
	public static boolean addTemplateEditForDesign(TemplateEditBean template,boolean is_update, SettingLogsBean stl){
		template.setT_ver(1 + TemplateVerManager.getTemplateVerNum(template.getT_id(),template.getSite_id()));
		template.setModify_dtime(DateUtil.getCurrentDateTime());
		if(is_update)
		{			
			if(TemplateEditDAO.updateTemplateEdit(template,stl)){
				TemplateVerManager.addTemplateVer(TEBtoTVB(template), stl);
				//向缓存添加模板路径信息			
				TemplateUtils.setTemplatePath(template.getT_id()+"_"+template.getSite_id(),FormatUtil.formatPath(SiteManager.getSiteTempletPath(template.getSite_id())+"/"+TemplateCategoryManager.getTemplateCategoryBean(template.getTcat_id(), template.getSite_id(), template.getApp_id()).getTcat_position(), true)+template.getT_id()+"_"+template.getT_ename()+".vm");
				publishTemplate(template.getT_id()+"",template.getSite_id(),template.getApp_id(),stl);
				return true;
			}else
				return false;
		}else
		{
			if(TemplateEditDAO.addTemplateEdit(template,stl)){
				TemplateVerManager.addTemplateVer(TEBtoTVB(template), stl);
				//向缓存添加模板路径信息			
				TemplateUtils.setTemplatePath(template.getT_id()+"_"+template.getSite_id(),FormatUtil.formatPath(SiteManager.getSiteTempletPath(template.getSite_id())+"/"+TemplateCategoryManager.getTemplateCategoryBean(template.getTcat_id(), template.getSite_id(), template.getApp_id()).getTcat_position(), true)+template.getT_id()+"_"+template.getT_ename()+".vm");
				publishTemplate(template.getT_id()+"",template.getSite_id(),template.getApp_id(),stl);
				return true;
			}else
				return false;
		}
	}

	/**
	 * 所有模板列表
	 * @return List<TemplateEditBean>
	 */
	public static List<TemplateEditBean> getAllTemplateEditList()
	{
		return TemplateEditDAO.getAllTemplateEditList(null, null, null);
	}
	
	/**
	 * 所有模板列表
	 * @return List<TemplateEditBean>
	 */
	public static List<TemplateEditBean> getAllTemplateEditList(String tcat_id, String site_id, String app_id)
	{
		return TemplateEditDAO.getAllTemplateEditList(tcat_id, site_id, app_id);
	}

	/**
	 * 得到模板列表
	 * @param con_map
	 * @return List<TemplateEditBean>
	 */
	public static List<TemplateEditBean> getTemplateEditListForDB(Map<String,String> con_map)
	{
		return TemplateEditDAO.getTemplateEditListForDB(con_map);
	}

	/**
	 * 删除模板,级联删除版本表中的与他对应的所有历史版本，并删除模板物理文件
	 * @param t_id
	 * @return boolean
	 */
	public static boolean delTemplateEditById(String t_ids, String site_id, SettingLogsBean stl){
		if(t_ids == null || t_ids.equals("")){
			return false;
		}
		if(TemplateEditDAO.delTemplateEdit(t_ids, site_id, stl)){
			TemplateVerManager.delTemplateVerByIds(t_ids, site_id, stl);
			//删除缓存中的模板路径信息
			if(t_ids.indexOf(",") != -1){
				for(String id : t_ids.split(",")){
					TemplateBase.delTemplateFile(id, site_id,"");//删除模板文件
					TemplateUtils.delTemplatePath(id+"_"+site_id);//清除模板缓存信息
				}
			}else{
				TemplateBase.delTemplateFile(t_ids, site_id,"");//删除模板文件
				TemplateUtils.delTemplatePath(t_ids+"_"+site_id);//清除模板缓存信息
			}
			//删除模板与设计页面的关联
			DesignDAO.deleteDesignCategory("", t_ids, site_id);
			//删除模板与栏目的关联
			CategoryManager.clearCategoryTemplate(t_ids, site_id);
		}else{
			return false;
		}
		reloadTemplateEdit();
		return true;
	}
	
	/**
	 * 发布模板，把版本表中对应 的模板设置为已发布状态，并生成对应的模板物理文件
	 * @param t_id
	 * @return boolean
	 */
	public static boolean publishTemplate(String t_ids, String site_id, String app_id, SettingLogsBean stl){
		try{
			if(t_ids == null){
				return false;
			}else{
				t_ids = TemplateUtils.formatSymbolString(t_ids, ",");
			}
			for(String id : t_ids.split(",")){
				int t_id = Integer.parseInt(id);
				
				TemplateEditBean teb = getTemplateEditBean(t_id, site_id, app_id);
				updateTemplateEditStatus(teb, stl);
				TemplateVerBean tvb = TEBtoTVB(teb);
				tvb.setT_status(1);//设置为发布状态
				TemplateVerManager.updateTemplateVer(tvb, stl);//更新版本表中 的发布状态
				//------
				TemplateManager.delTemplateById(t_id+"",site_id, stl);
				//在正式表中写入发布信息
				TemplateBean tb = new TemplateBean();
				tb.setT_id(t_id);
				tb.setT_ver(tvb.getT_ver());
				tb.setSite_id(site_id);
				tb.setApp_id(app_id);
				TemplateManager.addTemplate(tb, stl);
				//把模板保存成.vm文件
				TemplateUtils.initTemplatePathMap();
				TemplateBase.saveTemplateFile(teb);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 撤销模板，把版本表中对应 的模板设置为未发布状态，并删除对应的模板物理文件
	 * @param t_id
	 * @return boolean
	 */
	public static boolean cancelTemplate(String t_ids, String site_id, String app_id, SettingLogsBean stl){
		try{
			if(TemplateManager.delTemplateById(t_ids,site_id, stl)){
				for(String tid : t_ids.split(",")){
					if(tid != null && tid.length()>0){
						TemplateEditBean teb = getTemplateEditBean(Integer.parseInt(tid), site_id, app_id);
						TemplateVerBean tvb = TEBtoTVB(teb);
						tvb.setT_status(0);//设置为未发布状态
						TemplateVerManager.updateTemplateVer(tvb, stl);//更新版本表中 的发布状态
					}
					TemplateBase.delTemplateFile(tid, site_id, app_id);//删除模板物理文件
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 类型转变
	 * @param teb
	 * @return TemplateVerBean
	 */
	public static TemplateVerBean TEBtoTVB(TemplateEditBean teb){
		if(teb == null) return null;
		TemplateVerBean tvb = new TemplateVerBean();
		tvb.setT_id(teb.getT_id());
		tvb.setTcat_id(teb.getTcat_id());
		tvb.setT_ename(teb.getT_ename());
		tvb.setT_cname(teb.getT_cname());
		tvb.setT_path(teb.getT_path());
		tvb.setT_content(teb.getT_content());	
		tvb.setT_ver(teb.getT_ver());	
		tvb.setCreat_user(teb.getCreat_user());	
		tvb.setCreat_dtime(teb.getCreat_dtime());	
		tvb.setModify_user(teb.getModify_user());	
		tvb.setModify_dtime(teb.getModify_dtime());	
		tvb.setApp_id(teb.getApp_id());	
		tvb.setSite_id(teb.getSite_id());	
		tvb.setT_status(0);//0未发布，1已发布
		return tvb;
	}
	
	public static void main(String[] args) {
		publishTemplate("182","11111ddd","0",new SettingLogsBean());
	}
}
