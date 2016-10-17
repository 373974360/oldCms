package com.deya.wcm.services.system.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateResourcesBean;
import com.deya.wcm.bean.system.template.TemplateBean;
import com.deya.wcm.bean.system.template.TemplateCategoryBean;
import com.deya.wcm.bean.system.template.TemplateClassBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateVerBean;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.system.template.TemplateEditManager;

/**
 * @author zhuliang
 * @version 1.0
 * @date   2011-3-24 上午10:23:11
 */
public class TemplateRPC {
	public static int getNewTemplate()
	{
		return TemplateEditManager.getNewTemplate();
	}
	
	//根据站点ＩＤ得到资源目录，包括images.styles.js　目录
	public static String getFolderJSONStr(String site_id)
	{
		return FileRmiFactory.getFolderJSONStr(site_id);
	}
	
	//Resources
	public static List<TemplateResourcesBean> getResourcesListBySiteID(String site_id)
	{
		return FileRmiFactory.getResourcesListBySiteID(site_id);
	}
	
	public static List<TemplateResourcesBean> getResImageListBySiteID(String site_id)
	{
		return FileRmiFactory.getResImageListBySiteID(site_id);
	}
	
	//添加模板资源目录
	public static boolean addTemplateResourcesFolder(String file_path)
	{
		return FileRmiFactory.addTemplateResourcesFolder(file_path);
	}
	
	public static boolean deleteTemplateResources(String file_path,String site_id)
	{
		return FileRmiFactory.deleteTemplateResources(file_path,site_id);
	}
	
	public static boolean updateResourcesFile(String file_path,String file_content,String site_id) throws IOException
	{
		return FileRmiFactory.updateResourcesFile(file_path,file_content,site_id);
	}
	
	public static String getResourcesFileContent(String file_path,String site_id) throws IOException
	{
		return FileRmiFactory.getResourcesFileContent(file_path,site_id);
	}
	
	//TemplateEdit
	public static List<TemplateEditBean> getAllTemplateEditList(){
		return TemplateEditManager.getAllTemplateEditList();
	}
	
	public static List<TemplateEditBean> getTemplateEditList(Map<String,String> con_map){
		return TemplateEditManager.getTemplateEditListForDB(con_map);
	}
	
	public static String getTemplateEditCount(Map<String,String> con_map){
		return TemplateEditManager.getTemplateEditCount(con_map);
	}
	
	public static TemplateEditBean getTemplateEditById(int template_id, String site_id, String app_id){
		return TemplateEditManager.getTemplateEditBean(template_id, site_id, app_id);
	}
	
	public static String getTemplateNameById(int template_id, String site_id, String app_id){
		return TemplateEditManager.getTemplateNameById(template_id, site_id, app_id);		
	}
	
	public static boolean updateTemplateEditById(TemplateEditBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.updateTemplateEdit(template,stl);
		}else
			return false;	
	}
	
	public static boolean addTemplateEdit(TemplateEditBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.addTemplateEdit(template,stl);
		}else
			return false;	
	}
	
	/**
	 * 从模板设计工具中添加模板，不要版本记录，直接发布
	 * @param template
	 * @param boolean is_update 添加还是和操作
	 * @return boolean
	 */
	public static boolean addTemplateEditForDesign(TemplateEditBean template,boolean is_update,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.addTemplateEditForDesign(template,is_update,stl);
		}else
			return false;	
	}
	
	public static boolean delTemplateEditById(String template_id,String site_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.delTemplateEditById(template_id,site_id,stl);
		}else
			return false;	
	}
	
	public static boolean publish(String t_ids, String site_id, String app_id, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.publishTemplate(t_ids, site_id, app_id, stl);
		}else
			return false;	
	}
	
	public static boolean cancel(String t_ids, String site_id, String app_id, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateEditManager.cancelTemplate(t_ids, site_id, app_id, stl);
		}else
			return false;	
	}
	
	///TemplateVer
	public static List<TemplateVerBean> getAllTemplateVerList(){
		return TemplateVerManager.getAllTemplateVerList();
	}
	
	public static List<TemplateVerBean> getTemplateVerList(Map<String,String> con_map){
		return TemplateVerManager.getTemplateVerListForDB(con_map);
	}
	
	public static String getTemplateVerCount(Map<String,String> con_map){
		return TemplateVerManager.getTemplateVerCount(con_map);
	}
	
	public static TemplateVerBean getTemplateVerById(int template_id, String version, String site_id){
		return TemplateVerManager.getTemplateVerBean(template_id, version, site_id);
	}
	
	/**
	 * 根据ID得到简版的模板信息，用于列表显示，不包括content的内容
	 * @param int t_id
	 * @return TemplateVerBean
	 * */
	public static TemplateVerBean getSimpleTemplateVerBean(int template_id, String version, String site_id){
		return TemplateVerManager.getSimpleTemplateVerBean(template_id, version, site_id);
	}
	
	public static boolean recoveryTemplateVer(int template_id, String site_id, String version, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateVerManager.recoveryTemplateVer(template_id, site_id, version, stl);
		}else
			return false;
	}
	
	
	//Template
	public static List<TemplateBean> getAllTemplateList(){
		return TemplateManager.getAllTemplateList();
	}
	
	public static List<TemplateBean> getTemplateList(Map<String,String> con_map){
		return TemplateManager.getTemplateListForDB(con_map);
	}
	
	public static String getTemplateCount(Map<String,String> con_map){
		return TemplateManager.getTemplateCount(con_map);
	}
	
	public static TemplateBean getTemplateById(int template_id){
		return TemplateManager.getTemplateBean(template_id);
	}
	
	//TemplateClass
	public static List<TemplateClassBean> getAllTemplateClassList(){
		return TemplateClassManager.getAllTemplateClassList();
	}
	
	public static List<TemplateClassBean> getTemplateClassList(Map<String,String> con_map){
		return TemplateClassManager.getTemplateClassListForDB(con_map);
	}
	
	public static String getTemplateClassCount(Map<String,String> con_map){
		return TemplateClassManager.getTemplateClassCount(con_map);
	}
	
	public static TemplateClassBean getTemplateClassById(int template_id){
		return TemplateClassManager.getTemplateClassBean(template_id);
	}
	
	public static boolean updateTemplateClassById(TemplateClassBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateClassManager.updateTemplateClass(template,stl);
		}else
			return false;	
	}
	
	public static boolean addTemplateClass(TemplateClassBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateClassManager.addTemplateClass(template,stl);
		}else
			return false;	
	}
	
	public static boolean delTemplateClassById(String template_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateClassManager.delTemplateClassById(template_id,stl);
		}else
			return false;	
	}
	
	//TemplateCategory
	public static List<TemplateCategoryBean> getAllTemplateCategoryList(){
		return TemplateCategoryManager.getAllTemplateCategoryList();
	}
	
	public static List<TemplateCategoryBean> getTemplateCategoryList(Map<String,String> con_map){
		return TemplateCategoryManager.getTemplateCategoryListForDB(con_map);
	}
	
	public static String getTemplateCategoryCount(Map<String,String> con_map){
		return TemplateCategoryManager.getTemplateCategoryCount(con_map);
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortTemplateCategory(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateCategoryManager.sortTemplateCategory(ids,stl);
		}else
			return false;
	}
	
	public static TemplateCategoryBean getTemplateCategoryById(int template_id, String site_id, String app_id){
		return TemplateCategoryManager.getTemplateCategoryBean(template_id,site_id,app_id);
	}
	
	public static boolean updateTemplateCategoryById(TemplateCategoryBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateCategoryManager.updateTemplateCategory(template,stl);
		}else
			return false;	
	}
	
	public static int getNewID()
	{
		return TemplateCategoryManager.getNewID();
	}
	
	public static boolean addTemplateCategory(TemplateCategoryBean template,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateCategoryManager.addTemplateCategory(template,stl);
		}else
			return false;	
	}
	
	public static boolean delTemplateCategoryById(String template_id, String site_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TemplateCategoryManager.delTemplateCategoryById(template_id,site_id,stl);
		}else
			return false;	
	}
	
	public static String templateCategoryListToJsonStr(String id, String site_id, String appid){
		return TemplateCategoryManager.templateCategoryListToJsonStr(id,site_id,appid);
	}
	/**
	 * 读取上传的html文件修改模板
	 * @param file_path 文件路径
	 * @param t_id   
	 * @param site_id
	 * @param t_cname
	 * @param modify_dtime
	 * @param t_ename
	 * @param creat_dtime
	 * @param id
	 * @param modify_user
	 * @param tcat_id
	 * @param t_path
	 * @param app_id
	 * @param t_ver
	 * @param creat_user
	 * @param request
	 * @return  boolean
	 */
	public static boolean updateUploadTemplateFile(String file_path,String t_id,String site_id,String t_cname,String modify_dtime,String t_ename,String creat_dtime,String id,String modify_user,String tcat_id,String t_path,String app_id,String t_ver,String creat_user,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		// 1、读取文件
		String tContent = TemplateManager.readFileToString(site_id,file_path);
		 
		TemplateEditBean template = null;
		// 2、修改模板
		if(stl != null)
		{
		    template=new TemplateEditBean();
		    template.setT_content(tContent);
		    template.setT_id(Integer.parseInt(t_id));
		    template.setSite_id(site_id);
		    template.setT_cname(t_cname);
		    template.setModify_dtime(modify_dtime);
		    template.setT_ename(t_ename);
		    template.setCreat_dtime(creat_dtime);
		    template.setId(Integer.parseInt(id));
		    template.setModify_user(Integer.parseInt(modify_user));
		    template.setTcat_id(Integer.parseInt(tcat_id));
		    template.setT_path(t_path);
		    template.setApp_id(app_id);
		    template.setT_ver(Integer.parseInt(t_ver));
		    template.setCreat_user(Integer.parseInt(creat_user));
		    TemplateManager.delFile(site_id, file_path);
		    return TemplateEditManager.updateTemplateEdit(template,stl);
		}else
			return false;	
	}
	
	public static void main(String[] args) {
//		TemplateEditBean teb = new TemplateEditBean();
//		teb.setApp_id("aaaaaaaaaaaaaaa");
//		teb.setCreat_dtime("aaaaaaaaaaaaaaaa");
//		teb.setCreat_user(1);
//		teb.setModify_dtime("aaaaaaaaaaaaaaaaa");
//		teb.setModify_user(1);
//		teb.setSite_id("gx");
//		teb.setT_cname("aaaaaaaaaaaaaaaaaa");
//		teb.setT_content("aaaaaaaaaaaaaaaaaa");
//		teb.setT_ename("aaaaaaaaaaaaaaa");
//		teb.setT_id(1);
//		teb.setT_path("aaaaaaaaaaaaaaaaa");
//		teb.setT_ver(0);
//		teb.setTcat_id(1);
//		addTemplateEdit(teb, null);
		
		System.out.println(getTemplateEditById(997,"HIWCMdemo","zwgk"));
	}
}
