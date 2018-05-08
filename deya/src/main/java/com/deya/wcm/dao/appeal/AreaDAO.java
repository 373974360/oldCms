package com.deya.wcm.dao.appeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.AreaBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  诉求地区分类管理处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求地区分类管理处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class AreaDAO {
	/**
     * 得到所有地区列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<AreaBean> getAllAreaList()
	{
		return DBManager.queryFList("getAllAreaList", "");
	}
    /**
     * 根据地区ID得到对象
     * @param area_id
     * @return
     */
	public static AreaBean getAreaBean(int area_id)
	{
		return (AreaBean)DBManager.queryFObj("getAreaBean", area_id);
	}
	/**
	 * 新增记录 
	 * @param areabean
	 * @return
	 */
	public static boolean insertArea(AreaBean areabean,SettingLogsBean stl)
	{
		 areabean.setArea_position(areabean.getArea_position()+ areabean.getArea_id() + "$"); 
		
		if(DBManager.insert("insert_Area", areabean)){
			PublicTableDAO.insertSettingLogs("添加","地区分类",areabean.getArea_id()+"",stl);
			return true;
		}else{
			return false;
		}
		 
	}
	/**
     * 修改地区信息
     * @param AreaBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateArea(AreaBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_area", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","修改",ob.getArea_id()+"",stl);
			return true;
		}else
			return false;
	}
	/**
     * 删除地区信息
     * @param String area_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteArea(String area_id,SettingLogsBean stl)
	{
		boolean isCorrect = false;
		Map<String, String> m = new HashMap<String, String>();
		m.put("area_id", area_id);
		if(DBManager.delete("delete_Area_ids", m))
		{
			if(DBManager.delete("delete_parent_ids", m))
			{
				isCorrect = true;
			}
		}
		return isCorrect;
	}
	public static void main(String[] args)
	{
		System.out.println(DBManager.getString("insertArea", ""));
	}
}
