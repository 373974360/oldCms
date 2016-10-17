package com.deya.wcm.dao.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  模板主体数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 模板主体数据处理类,sql在template.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class TemplateEditDAO {
	@SuppressWarnings("unchecked")
	public static List<TemplateEditBean> getAllTemplateEditList(){		
		return DBManager.queryFList("getAllTemplateEditList", "");
	}

		/**
	     * 得到所有模板主体信息
	     * @param 
	     * @return List
	     * */	
		@SuppressWarnings("unchecked")
		public static List<TemplateEditBean> getAllTemplateEditList(String tcat_id, String site_id, String app_id){
			Map<String, String> map = new HashMap<String, String>();
			map.put("site_id", site_id);
			//map.put("app_id", app_id);//
			map.put("tcat_id", tcat_id);
			return DBManager.queryFList("getAllTemplateEditList", map);
		}
		
		/**
	     * 根据ID得到模板主体信息
	     * @param int t_id
	     * @return TemplateEditBean
	     * */
		public static TemplateEditBean getTemplateEditBean(int t_id, String site_id, String app_id)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("t_id", t_id+"");
			map.put("site_id", site_id);
			//map.put("app_id", app_id);
			return (TemplateEditBean)DBManager.queryFObj("getTemplateEditBean", map);
		}
		
		/**
	     * 得到模板主体总数
	     * @param 
	     * @return String
	     * */
		public static String getTemplateEditCount(Map<String,String> con_map)
		{
			System.out.println("getTemplateEditCount*****************************************"+con_map);
			return DBManager.getString("getTemplateEditCount", con_map);
		}
		
		/**
	     * 根据条件查询模板主体信息
	     * @param Map<String,String> con_map
	     * @return List<TemplateEditBean>
	     * */
		@SuppressWarnings("unchecked")
		public static List<TemplateEditBean> getTemplateEditListForDB(Map<String,String> con_map)
		{
			System.out.println("getTemplateEditListForDB*****************************************"+con_map);
			return DBManager.queryFList("getTemplateEditListForDB", con_map);
		}
		
		/**
		 * 修改模板主体
		 * @param tb
		 * @param stl
		 * @return boolean
		 */
		public static boolean updateTemplateEdit(TemplateEditBean tb, SettingLogsBean stl){
			if(DBManager.update("updateTemplateEdit", tb))
			{
				PublicTableDAO.insertSettingLogs("修改","模板主体",tb.getT_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加模板主体
		 * @param hw
		 * @return boolean
		 */
		public static boolean addTemplateEdit(TemplateEditBean tb, SettingLogsBean stl){
			if(DBManager.insert("insertTemplateEdit", tb))
			{
				PublicTableDAO.insertSettingLogs("添加","模板主体",tb.getId()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加模板主体
		 * @param hw
		 * @return boolean
		 */
		public static boolean cloneTemplateEdit(TemplateEditBean tb){				
			tb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_EDIT_TABLE_NAME));
			return DBManager.insert("insertTemplateEdit", tb);
		}
		
		/**
		 * 删除模板主体
		 * @param t_ids
		 * @param stl
		 * @return boolean
		 */
		public static boolean delTemplateEdit(String t_ids, String site_id, SettingLogsBean stl){
			Map<String,String> map = new HashMap<String,String>();
			map.put("t_ids", t_ids);
			map.put("site_id", site_id);
			if(DBManager.delete("deleteTemplateEdit", map))
			{
				PublicTableDAO.insertSettingLogs("删除","模板主体",t_ids,stl);
				return true;
			}else
				return false;
		}
		
		public static void main(String[] args) {
			TemplateEditBean teb = new TemplateEditBean();
			teb.setApp_id("cms");
			teb.setCreat_dtime(DateUtil.getCurrentDateTime());
			teb.setCreat_user(1);
			teb.setModify_dtime(DateUtil.getCurrentDateTime());
			teb.setModify_user(1);
			teb.setSite_id("test");
			teb.setT_cname("aaaaaaaaaaaaaaaaaa");
			teb.setT_content("aaaaaaaaaaaaaaaaaa");
			teb.setT_ename("aaaaaaaaaaaaaaa");
			teb.setT_id(2);
			teb.setT_path("aaaaaaaaaaaaaaaaa");
			teb.setT_ver(0);
			teb.setTcat_id(7);
			
			teb.setId(9);
			DBManager.insert("insertTemplateEdit", teb);
////			teb.setModify_dtime("ccccccccccccc");
////			teb.setT_path("bbbbbbbbbbbbbbbbbbb");
////			DBManager.update("updateTemplateEdit", teb);
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("t_ids", "1");
//			if(DBManager.delete("deleteTemplateEdit", map))
			//System.out.println(getAllTemplateEditList("test",""));
		}
}
