package com.deya.wcm.dao.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 模板分类数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 模板分类数据处理类,sql在template.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * @date   2011-3-24 下午02:04:44
 */
public class TemplateCategoryDAO {
	
	/**
	    * 根据站点ID得到模板分类列表
	    * @param String site_id
	    * @return List
	    * */	
		@SuppressWarnings("unchecked")
		public static List<TemplateCategoryBean> getTemplateCategoryListBySiteID(String site_id)
		{
			return DBManager.queryFList("getTemplateCategoryListBySiteID", site_id);
		}
	
	/**
    * 得到所有模板分类信息
    * @param 
    * @return List
    * */	
	@SuppressWarnings("unchecked")
	public static List<TemplateCategoryBean> getAllTemplateCategoryList()
	{
		return DBManager.queryFList("getAllTemplateCategoryList", "");
	}
	
	/**
    * 根据ID得到模板信息
    * @param int t_id
    * @return TemplateCategoryBean
    * */
	public static TemplateCategoryBean getTemplateCategoryBean(int tcat_id, String site_id/*, String appid*/)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("tcat_id", tcat_id+"");
		map.put("site_id", site_id+"");
		/*map.put("app_id", appid+"");*/
		return (TemplateCategoryBean)DBManager.queryFObj("getTemplateCategoryBean", map);
	}
	
	/**
    * 得到模板总数
    * @param 
    * @return String
    * */
	public static String getTemplateCategoryCount(Map<String,String> con_map)
	{
		return DBManager.getString("getTemplateCategoryCount", con_map);
	}
	
	/**
    * 根据条件查询模板信息
    * @param Map<String,String> con_map
    * @return List<TemplateCategoryBean>
    * */
	@SuppressWarnings("unchecked")
	public static List<TemplateCategoryBean> getTemplateCategoryListForDB(Map<String,String> con_map)
	{
		return DBManager.queryFList("getTemplateCategoryListForDB", con_map);
	}
	
	/**
	 * 修改模板
	 * @param tcb
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateTemplateCategory(TemplateCategoryBean tcb, SettingLogsBean stl){
		if(DBManager.update("updateTemplateCategory", tcb))
		{
			PublicTableDAO.insertSettingLogs("修改","模板分类",tcb.getTcat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 添加模板
	 * @param hw
	 * @return boolean
	 */
	public static boolean addTemplateCategory(TemplateCategoryBean tcb, SettingLogsBean stl){		
		if(DBManager.insert("insertTemplateCategory", tcb))
		{
			PublicTableDAO.insertSettingLogs("添加","模板分类",tcb.getTcat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 克隆模板分类（用于站点克隆）
	 * @param hw
	 * @return boolean
	 */
	public static boolean cloneTemplateCategory(TemplateCategoryBean tcb){	
		tcb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_CATEGORY_TABLE_NAME));
		return DBManager.insert("insertTemplateCategory", tcb);
	}
	
	/**
	 * 删除模板
	 * @param t_ids
	 * @param stl
	 * @return boolean
	 */
	public static boolean delTemplateCategory(String tcat_ids,String site_id, SettingLogsBean stl){
		Map<String,String> map = new HashMap<String,String>();
		map.put("tcat_ids", tcat_ids);
		map.put("site_id", site_id);
		if(DBManager.delete("deleteTemplateCategory", map))
		{
			PublicTableDAO.insertSettingLogs("删除","模板分类",tcat_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortTemplateCategory(String ids,SettingLogsBean stl)
	{
		try{
			Map<String,Object> m =new HashMap<String,Object>();
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				m.put("sort_id", i+1);
				m.put("id", tempA[i]);
				DBManager.update("sort_template_category", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","模板分类",ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}
	
	public static void main(String[] args) {
		
		System.out.println(getAllTemplateCategoryList());
	}
}
