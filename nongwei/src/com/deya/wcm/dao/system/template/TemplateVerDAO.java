package com.deya.wcm.dao.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateVerBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  模板主体数据备份处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 模板主题数据处理类,sql在template.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * * 
 */
public class TemplateVerDAO {

		/**
	     * 得到所有模板信息
	     * @param 
	     * @return List
	     * */	
		@SuppressWarnings("unchecked")
		public static List<TemplateVerBean> getAllTemplateVerList()
		{
			return DBManager.queryFList("getAllTemplateVerList", "");
		}
		
		/**
	     * 根据ID得到模板信息
	     * @param int t_id
	     * @return TemplateVerBean
	     * */
		public static TemplateVerBean getTemplateVerBean(int t_id,String site_id,String ver)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("t_id", t_id+"");
			map.put("site_id", site_id);
			map.put("t_ver", ver);
			return (TemplateVerBean)DBManager.queryFObj("getTemplateVerBean", map);
		}
		
		/**
	     * 根据模板ID及站点ID得到此模板的最新的发布状态模板
	     * @param int t_id
	     * @return TemplateVerBean
	     * */
		public static TemplateVerBean getTemplateVerBeanByState(int t_id,String site_id)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("t_id", t_id+"");
			map.put("site_id", site_id);
			return (TemplateVerBean)DBManager.queryFObj("getTemplateVerBeanByState", map);
		}
		
		/**
	     * 得到模板总数
	     * @param 
	     * @return String
	     * */
		public static String getTemplateVerCount(Map<String,String> con_map)
		{
			return DBManager.getString("getTemplateVerCount", con_map);
		}
		
		/**
	     * 根据条件查询模板信息
	     * @param Map<String,String> con_map
	     * @return List<TemplateVerBean>
	     * */
		@SuppressWarnings("unchecked")
		public static List<TemplateVerBean> getTemplateVerListForDB(Map<String,String> con_map)
		{
			return DBManager.queryFList("getTemplateVerListForDB", con_map);
		}
		
		/**
		 * 修改模板
		 * @param tb
		 * @param stl
		 * @return boolean
		 */
		public static boolean updateTemplateVer(TemplateVerBean tb, SettingLogsBean stl){
			if(DBManager.update("updateTemplateVer", tb))
			{
				PublicTableDAO.insertSettingLogs("修改","备份模板",tb.getT_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加模板
		 * @param hw
		 * @return boolean
		 */
		public static boolean addTemplateVer(TemplateVerBean tb, SettingLogsBean stl){
			int id = PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_VER_TABLE_NAME);
			tb.setId(id);
			if(DBManager.insert("insertTemplateVer", tb))
			{
				PublicTableDAO.insertSettingLogs("添加","备份模板",tb.getT_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 克隆模板
		 * @param TemplateVerBean tb
		 * @return boolean
		 */
		public static boolean cloneTemplateVer(TemplateVerBean tb){
			tb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_VER_TABLE_NAME));
			return DBManager.insert("insertTemplateVer", tb);
		}
		
		/**
		 * 删除模板
		 * @param t_ids
		 * @param stl
		 * @return boolean
		 */
		public static boolean delTemplateVer(String t_ids, String site_id, SettingLogsBean stl){
			Map<String,String> map = new HashMap<String,String>();
			map.put("t_ids", t_ids);
			map.put("site_id", site_id);
			if(DBManager.delete("deleteTemplateVer", map))
			{
				PublicTableDAO.insertSettingLogs("删除","备份模板",t_ids,stl);
				return true;
			}else
				return false;
		}
		
		public static String getTemplateVerNum(int t_id, String site_id){
			Map<String,String> map = new HashMap<String,String>();
			map.put("t_id", t_id+"");
			map.put("site_id", site_id);
			return DBManager.getString("getNewTemplateVerNum", map);
		}
		
		public static void main(String[] args) {
			TemplateVerBean teb = new TemplateVerBean();
			teb.setApp_id("aaaaaaaaaaaaaaa");
			teb.setCreat_dtime("aaaaaaaaaaaaaaaa");
			teb.setCreat_user(1);
			teb.setModify_dtime("aaaaaaaaaaaaaaaaa");
			teb.setModify_user(1);
			teb.setSite_id("gx");
			teb.setT_cname("cccccccccccccccccccccc");
			teb.setT_content("aaaaaaaaaaaaaaaaaa");
			teb.setT_ename("aaaaaaaaaaaaaaa");
			teb.setT_id(1);
			teb.setT_path("aaaaaaaaaaaaaaaaa");
			teb.setT_ver(1);
			teb.setTcat_id(1);
//			DBManager.insert("insertTemplateVer", teb);
//			teb.setModify_dtime("ccccccccccccc");
//			teb.setT_path("bbbbbbbbbbbbbbbbbbb");
//			DBManager.update("updateTemplateVer", teb);
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("t_ids", "1");
//			if(DBManager.delete("deleteTemplateVer", map))
//			System.out.println(getAllTemplateVerList());
			
			System.out.println(">>="+getTemplateVerNum(2,"0"));
		}
}
