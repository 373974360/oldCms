package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  专题分类管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 专题分类管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class ZTCategoryDAO {
	/**
     * 得到所有专题分类列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ZTCategoryBean> getALlZTCategoryList()
	{
		return DBManager.queryFList("getALlZTCategoryList", "");
	}
	
	/**
     * 得到专题分类总数
     * @param 
     * @return String
     * */
	public static String getZTCategoryCount(String con)
	{
		return DBManager.getString("getZTCategoryCount", con);
	}
	
	/**
     * 得到专题分类对象
     * @param int id
     * @return ZTCategoryBean
     * */
	public static ZTCategoryBean getZRCategoryBean(int id)
	{
		return (ZTCategoryBean)DBManager.queryFObj("getZRCategoryBean", id);
	}
	
	/**
     * 添加专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertZTCategory(ZTCategoryBean zb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_ZT_CATEGORY_TABLE_NAME);
		zb.setId(id);
		zb.setZt_cat_id(id);
		if(DBManager.insert("insert_zt_category", zb))
		{
			PublicTableDAO.insertSettingLogs("添加","专题分类",id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateZTCategory(ZTCategoryBean zb,SettingLogsBean stl)
	{		
		if(DBManager.update("update_zt_category", zb))
		{
			PublicTableDAO.insertSettingLogs("修改","专题分类",zb.getId()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 保存专题分类排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortZTCategory(String ids,SettingLogsBean stl)
	{
		String[] temp = ids.split(",");
		Map<String,String> m = new HashMap<String,String>();
		try{
			for(int i=0;i<temp.length;i++)
			{			
				m.put("sort_id", (i+1)+"");
				m.put("id", temp[i]);
				DBManager.update("sort_zt_category", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","专题分类",ids,stl);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 删除专题分类
     * @param int id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteZTCategory(int id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_zt_category", id))
		{
			PublicTableDAO.insertSettingLogs("删除","专题分类",id+"",stl);
			return true;
		}
		else
			return false;
	}
}
