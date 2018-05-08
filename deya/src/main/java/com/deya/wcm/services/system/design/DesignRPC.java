package com.deya.wcm.services.system.design;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.system.design.DesignCSSBean;
import com.deya.wcm.bean.system.design.DesignCaseBean;
import com.deya.wcm.bean.system.design.DesignCategoryBean;
import com.deya.wcm.bean.system.design.DesignFrameBean;
import com.deya.wcm.bean.system.design.DesignLayoutBean;
import com.deya.wcm.bean.system.design.DesignModuleBean;
import com.deya.wcm.bean.system.design.DesignStyleBean;

public class DesignRPC {
	/************************** 设计工具整体风格 开始 *******************************/
	public static String getDesignCssCount()
	{
		return DesignManager.getDesignCssCount();
	}
	
	public static List<DesignCSSBean> getDesignCssList(Map<String,String> m)
	{
		return DesignManager.getDesignCssList(m);
	}
	
	public static DesignCSSBean getDesignCssBean(int css_id)
	{
		return DesignManager.getDesignCssBean(css_id);
	}
	
	public static boolean insertDesignCss(DesignCSSBean cssbean)
	{		
		return DesignManager.insertDesignCss(cssbean);
	}
	
	public static boolean updateDesignCss(DesignCSSBean cssbean)
	{		
		return DesignManager.updateDesignCss(cssbean);
	}
	
	public static boolean deleteDesignCss(String css_ids)
	{				
		return DesignManager.deleteDesignCss(css_ids);
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具整体风格 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具布局 开始 *******************************/
	public static String getDesignLayoutCount()
	{
		return DesignManager.getDesignLayoutCount();
	}
	
	
	public static List<DesignLayoutBean> getDesignLayoutList(Map<String,String> m)
	{
		return DesignManager.getDesignLayoutList(m);
	}
	
	public static DesignLayoutBean getDesignLayoutBean(int layout_id)
	{
		return DesignManager.getDesignLayoutBean(layout_id);
	}
	
	public static boolean insertDesignLayout(DesignLayoutBean layoutbean)
	{		
		return DesignManager.insertDesignLayout(layoutbean);
	}
	
	public static boolean updateDesignLayout(DesignLayoutBean layoutbean)
	{		
		return DesignManager.updateDesignLayout(layoutbean);
	}
	
	public static boolean deleteDesignLayout(String layout_ids)
	{				
		return DesignManager.deleteDesignLayout(layout_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具布局 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具模块 开始 *******************************/
	public static String getDesignModuleCount()
	{
		return DesignManager.getDesignModuleCount();
	}
	
	
	public static List<DesignModuleBean> getDesignModuleList(Map<String,String> m)
	{
		return DesignManager.getDesignModuleList(m);
	}
	
	public static DesignModuleBean getDesignModuleBean(int module_id)
	{
		return DesignManager.getDesignModuleBean(module_id);
	}
	
	public static boolean insertDesignModule(DesignModuleBean modulebean)
	{		
		return DesignManager.insertDesignModule(modulebean);
	}
	
	public static boolean updateDesignModule(DesignModuleBean modulebean)
	{		
		return DesignManager.updateDesignModule(modulebean);
	}
	
	public static boolean deleteDesignModule(String module_ids)
	{				
		return DesignManager.deleteDesignModule(module_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具模块 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具内容样式 开始 *******************************/
	public static String getDesignStyleCount()
	{
		return DesignManager.getDesignStyleCount();
	}
	
	
	public static List<DesignStyleBean> getDesignStyleList(Map<String,String> m)
	{
		return DesignManager.getDesignStyleList(m);
	}
	
	public static DesignStyleBean getDesignStyleBean(int style_id)
	{
		return DesignManager.getDesignStyleBean(style_id);
	}
	
	public static boolean insertDesignStyle(DesignStyleBean stylebean)
	{		
		return DesignManager.insertDesignStyle(stylebean);
	}
	
	public static boolean updateDesignStyle(DesignStyleBean stylebean)
	{		
		return DesignManager.updateDesignStyle(stylebean);
	}
	
	public static boolean deleteDesignStyle(String style_ids)
	{				
		return DesignManager.deleteDesignStyle(style_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具内容样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具外框样式 开始 *******************************/
	public static String getDesignFrameCount()
	{
		return DesignManager.getDesignFrameCount();
	}
	
	
	public static List<DesignFrameBean> getDesignFrameList(Map<String,String> m)
	{
		return DesignManager.getDesignFrameList(m);
	}
	
	public static DesignFrameBean getDesignFrameBean(int frame_id)
	{
		return DesignManager.getDesignFrameBean(frame_id);
	}
	
	public static boolean insertDesignFrame(DesignFrameBean framebean)
	{		
		return DesignManager.insertDesignFrame(framebean);
	}
	
	public static boolean updateDesignFrame(DesignFrameBean framebean)
	{		
		return DesignManager.updateDesignFrame(framebean);
	}
	
	public static boolean deleteDesignFrame(String frame_ids)
	{				
		return DesignManager.deleteDesignFrame(frame_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具外框样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具方案 开始 *******************************/
	public static String getDesignCaseCount()
	{
		return DesignManager.getDesignCaseCount();
	}
	
	
	public static List<DesignCaseBean> getDesignCaseList(Map<String,String> m)
	{
		return DesignManager.getDesignCaseList(m);
	}
	
	public static DesignCaseBean getDesignCaseBean(int case_id)
	{
		return DesignManager.getDesignCaseBean(case_id);
	}
	
	public static boolean insertDesignCase(DesignCaseBean casebean)
	{		
		return DesignManager.insertDesignCase(casebean);
	}
	
	public static boolean updateDesignCase(DesignCaseBean casebean)
	{		
		return DesignManager.updateDesignCase(casebean);
	}
	
	public static boolean deleteDesignCase(String case_ids)
	{			
		return DesignManager.deleteDesignCase(case_ids);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具方案 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计页面与栏目关联表 开始 *******************************/
	public static String getDesignCategoryCount()
	{
		return DesignManager.getDesignCategoryCount();
	}
	
	
	public static List<DesignCategoryBean> getDesignCategoryList(Map<String,String> m)
	{
		return DesignManager.getDesignCategoryList(m);
	}
	
	public static DesignCategoryBean getDesignCategoryBean(int category_id,String site_id,String page_type)
	{
		return DesignManager.getDesignCategoryBean(category_id,site_id,page_type);
	}
	
	public static boolean insertDesignCategory(DesignCategoryBean categorybean)
	{		
		return DesignManager.insertDesignCategory(categorybean);
	}
	
	public static boolean updateDesignCategory(DesignCategoryBean categorybean)
	{		
		return DesignManager.updateDesignCategory(categorybean);
	}
	
	public static boolean deleteDesignCategory(String category_ids,String template_ids,String site_id)
	{				
		return DesignManager.deleteDesignCategory(category_ids,template_ids,site_id);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计页面与栏目关联表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
     * 根据表名,字段名和内容,判断此内容是否已存在
     * @param int model_id
     * @return List
     * */
	public static boolean nameIsExist(String table_name,String item_name,String item_value)
	{
		return DesignManager.nameIsExist(table_name, item_name, item_value);
	}
}
