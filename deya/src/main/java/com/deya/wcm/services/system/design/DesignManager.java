package com.deya.wcm.services.system.design;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.system.design.DesignCSSBean;
import com.deya.wcm.bean.system.design.DesignCaseBean;
import com.deya.wcm.bean.system.design.DesignCategoryBean;
import com.deya.wcm.bean.system.design.DesignFrameBean;
import com.deya.wcm.bean.system.design.DesignLayoutBean;
import com.deya.wcm.bean.system.design.DesignModuleBean;
import com.deya.wcm.bean.system.design.DesignStyleBean;
import com.deya.wcm.dao.system.design.DesignDAO;

public class DesignManager {
	/************************** 设计工具整体风格 开始 *******************************/
	public static String getDesignCssCount()
	{
		return DesignDAO.getDesignCssCount();
	}
	
	public static List<DesignCSSBean> getDesignCssList(Map<String,String> m)
	{
		return DesignDAO.getDesignCssList(m);
	}
	
	public static DesignCSSBean getDesignCssBean(int css_id)
	{
		return DesignDAO.getDesignCssBean(css_id);
	}
	
	public static boolean insertDesignCss(DesignCSSBean cssbean)
	{		
		if(DesignDAO.insertDesignCss(cssbean))
		{
			String filepath = FormatUtil.formatPath(getCssSavePath()+cssbean.getCss_ename());
			File f = new File(filepath);
			f.mkdirs();
			return true;
		}else
			return false;
	}
	
	public static boolean updateDesignCss(DesignCSSBean cssbean)
	{		
		return DesignDAO.updateDesignCss(cssbean);
	}
	
	public static boolean deleteDesignCss(String css_ids)
	{				
		return DesignDAO.deleteDesignCss(css_ids);
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具整体风格 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具布局 开始 *******************************/
	public static String getDesignLayoutCount()
	{
		return DesignDAO.getDesignLayoutCount();
	}
	
	
	public static List<DesignLayoutBean> getDesignLayoutList(Map<String,String> m)
	{
		return DesignDAO.getDesignLayoutList(m);
	}
	
	public static DesignLayoutBean getDesignLayoutBean(int layout_id)
	{
		return DesignDAO.getDesignLayoutBean(layout_id);
	}
	
	public static boolean insertDesignLayout(DesignLayoutBean layoutbean)
	{		
		return DesignDAO.insertDesignLayout(layoutbean);
	}
	
	public static boolean updateDesignLayout(DesignLayoutBean layoutbean)
	{		
		return DesignDAO.updateDesignLayout(layoutbean);
	}
	
	public static boolean deleteDesignLayout(String layout_ids)
	{				
		return DesignDAO.deleteDesignLayout(layout_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具布局 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具模块 开始 *******************************/
	public static String getDesignModuleCount()
	{
		return DesignDAO.getDesignModuleCount();
	}
	
	
	public static List<DesignModuleBean> getDesignModuleList(Map<String,String> m)
	{
		return DesignDAO.getDesignModuleList(m);
	}
	
	public static DesignModuleBean getDesignModuleBean(int module_id)
	{
		return DesignDAO.getDesignModuleBean(module_id);
	}
	
	public static boolean insertDesignModule(DesignModuleBean modulebean)
	{		
		return DesignDAO.insertDesignModule(modulebean);
	}
	
	public static boolean updateDesignModule(DesignModuleBean modulebean)
	{		
		return DesignDAO.updateDesignModule(modulebean);
	}
	
	public static boolean deleteDesignModule(String module_ids)
	{				
		return DesignDAO.deleteDesignModule(module_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具模块 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具内容样式 开始 *******************************/
	public static String getDesignStyleCount()
	{
		return DesignDAO.getDesignStyleCount();
	}
	
	
	public static List<DesignStyleBean> getDesignStyleList(Map<String,String> m)
	{
		return DesignDAO.getDesignStyleList(m);
	}
	
	public static DesignStyleBean getDesignStyleBean(int style_id)
	{
		return DesignDAO.getDesignStyleBean(style_id);
	}
	
	public static boolean insertDesignStyle(DesignStyleBean stylebean)
	{		
		return DesignDAO.insertDesignStyle(stylebean);
	}
	
	public static boolean updateDesignStyle(DesignStyleBean stylebean)
	{		
		return DesignDAO.updateDesignStyle(stylebean);
	}
	
	public static boolean deleteDesignStyle(String style_ids)
	{				
		return DesignDAO.deleteDesignStyle(style_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具内容样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具外框样式 开始 *******************************/
	public static String getDesignFrameCount()
	{
		return DesignDAO.getDesignFrameCount();
	}
	
	
	public static List<DesignFrameBean> getDesignFrameList(Map<String,String> m)
	{
		return DesignDAO.getDesignFrameList(m);
	}
	
	public static DesignFrameBean getDesignFrameBean(int frame_id)
	{
		return DesignDAO.getDesignFrameBean(frame_id);
	}
	
	public static boolean insertDesignFrame(DesignFrameBean framebean)
	{		
		return DesignDAO.insertDesignFrame(framebean);
	}
	
	public static boolean updateDesignFrame(DesignFrameBean framebean)
	{		
		return DesignDAO.updateDesignFrame(framebean);
	}
	
	public static boolean deleteDesignFrame(String frame_ids)
	{				
		return DesignDAO.deleteDesignFrame(frame_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具外框样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具方案 开始 *******************************/
	public static String getDesignCaseCount()
	{
		return DesignDAO.getDesignCaseCount();
	}
	
	
	public static List<DesignCaseBean> getDesignCaseList(Map<String,String> m)
	{
		return DesignDAO.getDesignCaseList(m);
	}
	
	public static DesignCaseBean getDesignCaseBean(int case_id)
	{
		return DesignDAO.getDesignCaseBean(case_id);
	}
	
	public static boolean insertDesignCase(DesignCaseBean casebean)
	{		
		return DesignDAO.insertDesignCase(casebean);
	}
	
	public static boolean updateDesignCase(DesignCaseBean casebean)
	{		
		return DesignDAO.updateDesignCase(casebean);
	}
	
	public static boolean deleteDesignCase(String case_ids)
	{			
		return DesignDAO.deleteDesignCase(case_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具方案 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计页面与栏目关联表 开始 *******************************/
	public static String getDesignCategoryCount()
	{
		return DesignDAO.getDesignCategoryCount();
	}
	
	
	public static List<DesignCategoryBean> getDesignCategoryList(Map<String,String> m)
	{
		return DesignDAO.getDesignCategoryList(m);
	}
	
	public static DesignCategoryBean getDesignCategoryBean(int category_id,String site_id,String page_type)
	{
		return DesignDAO.getDesignCategoryBean(category_id,site_id,page_type);
	}
	
	public static boolean insertDesignCategory(DesignCategoryBean categorybean)
	{		
		return DesignDAO.insertDesignCategory(categorybean);
	}
	
	public static boolean updateDesignCategory(DesignCategoryBean categorybean)
	{		
		return DesignDAO.updateDesignCategory(categorybean);
	}
	
	public static boolean deleteDesignCategory(String category_ids,String template_ids,String site_id)
	{				
		return DesignDAO.deleteDesignCategory(category_ids,template_ids,site_id);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计页面与栏目关联表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
     * 根据表名,字段名和内容,判断此内容是否已存在
     * @param int model_id
     * @return List
     * */
	public static boolean nameIsExist(String table_name,String item_name,String item_value)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("table_name", table_name);
		m.put("item_name", item_name);
		m.put("item_value", item_value);
		return DesignDAO.nameIsExist(m);
	}
	
	/**
     * 得到风格的保存路径
     * @param 
     * @return String
     * */
	public static String getCssSavePath()
	{
		return JconfigUtilContainer.bashConfig().getProperty("path", "", "cms_files") + "/design/theme/";
	}
	
	public static void main(String[] args)
	{
		System.out.println(nameIsExist("cs_design_css","css_ename","bbbb"));
	}
}
