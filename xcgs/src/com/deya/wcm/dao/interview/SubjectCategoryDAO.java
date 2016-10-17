package com.deya.wcm.dao.interview;

import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.logs.SettingLogsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 访谈主题分类数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题分类的数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectCategoryDAO {
	@SuppressWarnings("unchecked")
	public static List<SubjectCategory> getAllSubjectCategoryList()
	{
		return DBManager.queryFList("getAllSubjectCategoryList", "");
	}
	
	
	/**
     * 根据分类ID得到该分类下的主题个数,用于删除分类时判断下面是否有主题记录
     * @param int id
     * @return int　条数
     * */
	public static String getSubjectCountByCategoryID(int id)
	{
		return DBManager.getString("getSubjectCountByCategoryID", id);
	}
	
	/**
     * 得到主题分类总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubCategoryCount(String con,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();		
		m.put("con", con);
		m.put("site_id", site_id);
		return DBManager.getString("getSubCategoryCount", m);
	}
	
	/**
     * 得到主题分类对象信息
     * @param int　id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBean(int id)
	{
		return (SubjectCategory)DBManager.queryFObj("getSubjectCategoryBean", id);
	}
	
	/**
     * 得到主题分类对象信息
     * @param String sub_id　id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBean(String category_id)
	{
		return (SubjectCategory)DBManager.queryFObj("getSubjectCategoryBeanByCId", category_id);
	}
	
	/**
     * 得到所有主题分类信息　id,category_id,name
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubCategoryAllName()
	{		
		List l = DBManager.queryFList("getSubCategoryAllName","");
		return l;
	}
	
	/**
     * 得到主题分类列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubCategoryList(Map m)
	{		
		List l = DBManager.queryFList("getSubCategoryList", m);
		return l;
	}
	
	/**
     * 插入主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean insertSubCategory(SubjectCategory sc,SettingLogsBean stl)
	{				
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_SCATEGORY_TABLE_NAME);
		sc.setId(id);
		if(DBManager.insert("insertSubCategory",sc)){
			PublicTableDAO.insertSettingLogs("添加","访谈主题分类",id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean updateSubCategory(SubjectCategory sc,SettingLogsBean stl)
	{				
		if(DBManager.update("updateSubCategory",sc)){
			PublicTableDAO.insertSettingLogs("修改","访谈主题分类",sc.getId()+"",stl);
			return true;
		}
		else
			return false;		
	}
	
	/**
     * 删除主题分类
     * @param Map m　组织好的删除条件及用户名，时间等参数
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static boolean deleteSubCategory(Map m,SettingLogsBean stl)
	{	
		if(DBManager.update("deleteSubCategory",m)){
			PublicTableDAO.insertSettingLogs("删除","访谈主题分类",m.get("ids")+"",stl);
			return true;
		}
		else
			return false;		
	}
	
	/**
     * 修改主题分类发布状态
     * @param Map m　组织好的要发布的条件及用户名，时间等参数
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static boolean updateSubCategoryState(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("updateSubCategoryStatus",m)){
			PublicTableDAO.insertSettingLogs("修改","访谈主题分类发布状态",m.get("ids")+"",stl);
			return true;
		}
		else
			return false;		
	}	
	
	/**
     * 保存分类排序
     * @param String ids　要排序的信息ID
     * @return boolean　true or false
     * */
	public static boolean saveSubCategorySort(String ids,SettingLogsBean stl)
	{		
		if(ids != null && !"".equals(ids))
		{
			Map<String,String> new_m = new HashMap<String,String>();
			String[] tempA = ids.split(",");
			try{
				for(int i=0;i<tempA.length;i++)
				{
					new_m.put("sort", (i+1)+"");
					new_m.put("id", tempA[i]);
					DBManager.update("saveSubCategorySort", new_m);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;				
			}
			PublicTableDAO.insertSettingLogs("保存排序","访谈主题分类",ids,stl);
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		
		
	}
}
