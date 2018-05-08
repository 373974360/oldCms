package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CateClassBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  分类方式数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 分类方式数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class CateClassDao {
	
	private static final String INSERT_OPERATE = "添加";
	private static final String UPDATE_OPERATE = "修改";
	private static final String DELETE_OPERATE = "删除";
	
	private static final String TABLE_NAME = "分类方式";
		
	/**
	 * 得到所有的"分类方式"信息列表
	 * @return 
	 * 		"分类方式"信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<CateClassBean> getAllCateClassList()
	{
		return DBManager.queryFList("getAllCateClassList", "");
	}
	
	/**
	 * 根据不用应用取得"分类方式"信息列表
	 * @param app_id
	 * @return
	 * 		"分类方式"信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<CateClassBean> getCateClassListByApp(String app_id)
	{
		return DBManager.queryFList("getCateClassListByApp", app_id);
	}
	
	/**
	 * 根据"分类方式ID"取得
	 * @param id 分类方式ID
	 * @return
	 * 		分类方式对象
	 */
	public static CateClassBean getCateClassBeanByID(String id)
	{
		return (CateClassBean)DBManager.queryFObj("getCateClassBeanByID", id);
	}
	
	/**
	 * 添加"分类方式"信息
	 * @param ccb "分类方式"对象
	 * @param stl
	 * @return
	 * 		true  添加成功|false  添加失败
	 */
	public static boolean insertCateClass(CateClassBean ccb, SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CLASS_TABLE_NAME);
		ccb.setClass_id(id);
		if(DBManager.insert("insertCateClass", ccb))
		{
			PublicTableDAO.insertSettingLogs(INSERT_OPERATE, TABLE_NAME, id+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改"分类方式"信息
	 * @param ccb "分类方式"对象
	 * @param stl
	 * @return
	 * 		true  修改成功|false  修改失败
	 */
	public static boolean updateCateClass(CateClassBean ccb, SettingLogsBean stl)
	{
		if(DBManager.update("updateCateClass", ccb))
		{
			PublicTableDAO.insertSettingLogs(UPDATE_OPERATE, TABLE_NAME, ccb.getClass_id()+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据"分类方式ID"删除"分类方式"
	 * @param ids
	 * 		分类方式ID,多个之间使用","隔开
	 * @param stl
	 * @return
	 * 		true  删除成功|false  删除失败
	 */
	public static boolean deleteCateClass(String ids, SettingLogsBean stl)
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("class_ids", ids);
		if(DBManager.update("deleteCateClass", mp))
		{
			PublicTableDAO.insertSettingLogs(DELETE_OPERATE, TABLE_NAME, ids+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}

}
