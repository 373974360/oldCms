package com.deya.wcm.services.system.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateBean;
import com.deya.wcm.dao.system.template.TemplateDAO;
import com.deya.wcm.services.control.site.SiteManager;

/**
 * @author 符江波
 * @date   2011-3-24 上午10:19:00
 */
public class TemplateManager {

	/**
	 * 根据ID得到模板信息
	 * @param int t_id
	 * @return TemplateBean
	 * */
	public static TemplateBean getTemplateBean(int t_id)
	{
		return TemplateDAO.getTemplateBean(t_id);
	}

	/**
	 * 得到模板总数
	 * @param 
	 * @return String
	 * */
	public static String getTemplateCount(Map<String,String> con_map)
	{
		return TemplateDAO.getTemplateCount(con_map);
	}

	/**
	 * 修改模板
	 * @param author
	 * @return boolean
	 */
	public static boolean updateTemplate(TemplateBean template, SettingLogsBean stl){
		return TemplateDAO.updateTemplate(template, stl);
	}

	/**
	 * 添加模板
	 * @param template
	 * @return boolean
	 */
	public static boolean addTemplate(TemplateBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}else{
			template.setT_ver(1);
		}
		
		return TemplateDAO.addTemplate(template,stl);
	}

	/**
	 * 所有模板列表
	 * @return List<TemplateBean>
	 */
	public static List<TemplateBean> getAllTemplateList()
	{
		return TemplateDAO.getAllTemplateList();
	}

	/**
	 * 得到模板列表
	 * @param con_map
	 * @return List<TemplateBean>
	 */
	public static List<TemplateBean> getTemplateListForDB(Map<String,String> con_map)
	{
		return TemplateDAO.getTemplateListForDB(con_map);
	}

	/**
	 * 删除模板
	 * @param t_id
	 * @return boolean
	 */
	public static boolean delTemplateById(String t_ids, String site_id, SettingLogsBean stl){
		if(t_ids == null || t_ids.equals("")){
			return false;
		}
		return TemplateDAO.delTemplate(t_ids,site_id, stl);
	}
	/**
	 * 读取上传的模板文件内容
	 * @param file_path
	 * @return String
	 */
	public static String readFileToString(String site_id,String file_path) {
		String str = "";
		try {
			com.deya.wcm.bean.control.SiteBean siteBean = com.deya.wcm.services.control.site.SiteManager.getSiteBeanBySiteID(site_id);
	        String uploadpath = siteBean.getSite_path();
			str = FileOperation.readFileToString(uploadpath+file_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 删除上传的模板文件
	 * @param site_id
	 * @param file_path
	 * @return
	 */
	public static boolean delFile(String site_id,String file_path)
	{
			com.deya.wcm.bean.control.SiteBean siteBean = com.deya.wcm.services.control.site.SiteManager.getSiteBeanBySiteID(site_id);
	        String uploadpath = siteBean.getSite_path();
	        return FileOperation.deleteFile(uploadpath+file_path);
	}
}
