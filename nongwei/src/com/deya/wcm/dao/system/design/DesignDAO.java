package com.deya.wcm.dao.system.design;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.system.design.DesignCSSBean;
import com.deya.wcm.bean.system.design.DesignCaseBean;
import com.deya.wcm.bean.system.design.DesignCategoryBean;
import com.deya.wcm.bean.system.design.DesignFrameBean;
import com.deya.wcm.bean.system.design.DesignLayoutBean;
import com.deya.wcm.bean.system.design.DesignModuleBean;
import com.deya.wcm.bean.system.design.DesignStyleBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  可视化模板创建数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 可视化模板创建数据处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class DesignDAO {
	/************************** 设计工具整体风格 开始 *******************************/
	public static String getDesignCssCount()
	{
		return DBManager.getString("getDesignCssCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignCSSBean> getDesignCssList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignCssList", m);
	}
	
	public static DesignCSSBean getDesignCssBean(int css_id)
	{
		return (DesignCSSBean)DBManager.queryFObj("getDesignCssBean", css_id+"");
	}
	
	public static boolean insertDesignCss(DesignCSSBean cssbean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_CSS_TABLE_NAME);
		cssbean.setCss_id(id);
		cssbean.setId(id);
		return DBManager.insert("insert_desing_css", cssbean);
	}
	
	public static boolean updateDesignCss(DesignCSSBean cssbean)
	{		
		return DBManager.update("update_desing_css", cssbean);
	}
	
	public static boolean deleteDesignCss(String css_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("css_ids", css_ids);
		return DBManager.delete("delete_desing_css", m);
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具整体风格 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具布局 开始 *******************************/
	public static String getDesignLayoutCount()
	{
		return DBManager.getString("getDesignLayoutCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignLayoutBean> getDesignLayoutList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignLayoutList", m);
	}
	
	public static DesignLayoutBean getDesignLayoutBean(int layout_id)
	{
		return (DesignLayoutBean)DBManager.queryFObj("getDesignLayoutBean", layout_id+"");
	}
	
	public static boolean insertDesignLayout(DesignLayoutBean layoutbean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_LAYOUT_TABLE_NAME);
		layoutbean.setLayout_id(id);
		layoutbean.setId(id);
		return DBManager.insert("insert_desing_layout", layoutbean);
	}
	
	public static boolean updateDesignLayout(DesignLayoutBean layoutbean)
	{		
		return DBManager.update("update_desing_layout", layoutbean);
	}
	
	public static boolean deleteDesignLayout(String layout_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("layout_ids", layout_ids);
		return DBManager.delete("delete_desing_layout", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具布局 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具模块 开始 *******************************/
	public static String getDesignModuleCount()
	{
		return DBManager.getString("getDesignModuleCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignModuleBean> getDesignModuleList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignModuleList", m);
	}
	
	public static DesignModuleBean getDesignModuleBean(int module_id)
	{
		return (DesignModuleBean)DBManager.queryFObj("getDesignModuleBean", module_id+"");
	}
	
	public static boolean insertDesignModule(DesignModuleBean modulebean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_MODULE_TABLE_NAME);
		modulebean.setModule_id(id);
		modulebean.setId(id);
		return DBManager.insert("insert_desing_module", modulebean);
	}
	
	public static boolean updateDesignModule(DesignModuleBean modulebean)
	{		
		return DBManager.update("update_desing_module", modulebean);
	}
	
	public static boolean deleteDesignModule(String module_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("module_ids", module_ids);
		return DBManager.delete("delete_desing_module", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具模块 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具内容样式 开始 *******************************/
	public static String getDesignStyleCount()
	{
		return DBManager.getString("getDesignStyleCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignStyleBean> getDesignStyleList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignStyleList", m);
	}
	
	public static DesignStyleBean getDesignStyleBean(int style_id)
	{
		return (DesignStyleBean)DBManager.queryFObj("getDesignStyleBean", style_id+"");
	}
	
	public static boolean insertDesignStyle(DesignStyleBean stylebean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_STYLE_TABLE_NAME);
		stylebean.setStyle_id(id);
		stylebean.setId(id);
		return DBManager.insert("insert_desing_style", stylebean);
	}
	
	public static boolean updateDesignStyle(DesignStyleBean stylebean)
	{		
		return DBManager.update("update_desing_style", stylebean);
	}
	
	public static boolean deleteDesignStyle(String style_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("style_ids", style_ids);
		return DBManager.delete("delete_desing_style", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具内容样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具外框样式 开始 *******************************/
	public static String getDesignFrameCount()
	{
		return DBManager.getString("getDesignFrameCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignFrameBean> getDesignFrameList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignFrameList", m);
	}
	
	public static DesignFrameBean getDesignFrameBean(int frame_id)
	{
		return (DesignFrameBean)DBManager.queryFObj("getDesignFrameBean", frame_id+"");
	}
	
	public static boolean insertDesignFrame(DesignFrameBean framebean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_FRAME_TABLE_NAME);
		framebean.setFrame_id(id);
		framebean.setId(id);
		return DBManager.insert("insert_desing_frame", framebean);
	}
	
	public static boolean updateDesignFrame(DesignFrameBean framebean)
	{		
		return DBManager.update("update_desing_frame", framebean);
	}
	
	public static boolean deleteDesignFrame(String frame_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("frame_ids", frame_ids);
		return DBManager.delete("delete_desing_frame", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具外框样式 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计工具方案 开始 *******************************/
	public static String getDesignCaseCount()
	{
		return DBManager.getString("getDesignCaseCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignCaseBean> getDesignCaseList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignCaseList", m);
	}
	
	public static DesignCaseBean getDesignCaseBean(int case_id)
	{
		return (DesignCaseBean)DBManager.queryFObj("getDesignCaseBean", case_id+"");
	}
	
	public static boolean insertDesignCase(DesignCaseBean casebean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_CASE_TABLE_NAME);
		casebean.setCase_id(id);
		casebean.setId(id);
		return DBManager.insert("insert_desing_case", casebean);
	}
	
	public static boolean updateDesignCase(DesignCaseBean casebean)
	{		
		return DBManager.update("update_desing_case", casebean);
	}
	
	public static boolean deleteDesignCase(String case_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("case_ids", case_ids);
		return DBManager.delete("delete_desing_case", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计工具方案 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/************************** 设计页面与栏目关联表 开始 *******************************/
	public static String getDesignCategoryCount()
	{
		return DBManager.getString("getDesignCategoryCount", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<DesignCategoryBean> getDesignCategoryList(Map<String,String> m)
	{
		return DBManager.queryFList("getDesignCategoryList", m);
	}
	
	public static DesignCategoryBean getDesignCategoryBean(int category_id,String site_id,String page_type)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("category_id", category_id+"");
		m.put("site_id", site_id);
		m.put("page_type", page_type);
		return (DesignCategoryBean)DBManager.queryFObj("getDesignCategoryBean", m);
	}
	
	public static boolean insertDesignCategory(DesignCategoryBean categorybean)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DESIGN_CATEGORY_TABLE_NAME);		
		categorybean.setId(id);
		return DBManager.insert("insert_desing_category", categorybean);
	}
	
	public static boolean updateDesignCategory(DesignCategoryBean categorybean)
	{		
		return DBManager.update("update_desing_category", categorybean);
	}
	
	public static boolean deleteDesignCategory(String category_ids,String template_ids,String site_id)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		if(category_ids != null && !"".equals(category_ids))
			m.put("category_ids", category_ids);
		if(template_ids != null && !"".equals(template_ids))
			m.put("template_ids", template_ids);
		return DBManager.delete("delete_desing_category", m);
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~~ 设计页面与栏目关联表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
     * 根据表名,字段名和内容,判断此内容是否已存在
     * @param int model_id
     * @return List
     * */
	public static boolean nameIsExist(Map<String,String> m)
	{
		String count = DBManager.getString("getDesignItemValueCount", m);
		if("0".equals(count))
		{
			return false;
		}else
			return true;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getDesignCssCount());
	}
}
