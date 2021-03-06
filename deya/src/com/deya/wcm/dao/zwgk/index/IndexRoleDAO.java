package com.deya.wcm.dao.zwgk.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.index.IndexRoleBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 索引生成规则DAO类
 * 负责维护索引生成规则表，这个表只能有查询和修改操作
 * @author liqi
 *
 */
public class IndexRoleDAO {
	
	/**
	 * 取得全部的索引生成规则
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<IndexRoleBean> getAllIndexRole()
	{
		return DBManager.queryFList("getIndexRole", "");
	}

	/**
	 * 取得全部的索引生成规则
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<IndexRoleBean> getAllIndexRoleBySiteId(String siteId)
	{
		Map<String,String> map = new HashMap<>();
		map.put("siteId",siteId);
		return DBManager.queryFList("getIndexRoleBySiteId", map);
	}

	/**
	 * 修改索引生成规则
	 * @param irb	索引生成规则对象
	 * @return
	 */
	public static boolean updateIndexRole(IndexRoleBean irb, SettingLogsBean stl)
	{
		if(DBManager.update("updateIndexRole", irb)){
			PublicTableDAO.insertSettingLogs("修改","索引规则",irb.getId()+"",stl);
		    return true;
		}else{
			return false;
		}
	}

	/**
	 * 修改索引生成规则
	 * @param irb	索引生成规则对象
	 * @return
	 */
	public static boolean insertIndexRole(IndexRoleBean irb, SettingLogsBean stl)
	{
		if(DBManager.update("insertIndexRole", irb)){
			PublicTableDAO.insertSettingLogs("新增","索引规则",irb.getId()+"",stl);
			return true;
		}else{
			return false;
		}
	}

}
