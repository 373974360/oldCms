package com.deya.wcm.dao.system.assist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.HotWordBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  热词管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 热词管理数据处理类,sql在 assist.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class HotWordDAO {
	/**
     * 得到所有热词信息
     * @param 
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public static List<HotWordBean> getAllHotWordList()
	{
		return DBManager.queryFList("getAllHotWordList", "");
	}
	
	/**
     * 根据ID得到热词信息
     * @param int hot_id
     * @return HotWordBean
     * */
	public static HotWordBean getHotWordBean(int hot_id)
	{
		return (HotWordBean)DBManager.queryFObj("getHotWordBean", hot_id);
	}
	
	/**
     * 得到热词总数
     * @param 
     * @return String
     * */
	public static String getHotWordCount(Map<String,String> con_map)
	{
		return DBManager.getString("getHotWordCount", con_map);
	}
	
	/**
     * 根据条件查询热词信息
     * @param Map<String,String> con_map
     * @return List<HotWordBean>
     * */
	@SuppressWarnings("unchecked")
	public static List<HotWordBean> getHotWordListForDB(Map<String,String> con_map)
	{
		return DBManager.queryFList("getHotWordListForDB", con_map);
	}
	
	/**
	 * 修改热词
	 * @param hw
	 * @return boolean
	 */
	public static boolean updateHotWord(HotWordBean hw, SettingLogsBean stl){
		if(DBManager.update("update_hotWord", hw))
		{
			PublicTableDAO.insertSettingLogs("修改","热词",hw.getHot_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 添加热词
	 * @param hw
	 * @return boolean
	 */
	public static boolean addHotWord(HotWordBean hw, SettingLogsBean stl){
		int hot_id = PublicTableDAO.getIDByTableName(PublicTableDAO.HOTWORD_TABLE_NAME);
		hw.setHot_id(hot_id);
		if(DBManager.insert("insert_hotWord", hw))
		{
			PublicTableDAO.insertSettingLogs("添加","热词",hot_id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除热词
	 * @param hot_id
	 * @param stl
	 * @return boolean
	 */
	public static boolean delHotWord(String hot_ids, SettingLogsBean stl){
		Map<String,String> map = new HashMap<String,String>();
		map.put("hot_ids", hot_ids);
		if(DBManager.delete("deleteHotWord", map))
		{
			PublicTableDAO.insertSettingLogs("删除","热词",hot_ids,stl);
			return true;
		}else
			return false;
	}
	
	public static void main(String[] args) {
		HotWordBean hw = new HotWordBean();
		hw.setHot_id(1);
		hw.setHot_name("hot01");
		hw.setHot_url("www.baidu.com");
		hw.setApp_id("appid");
		hw.setSite_id("wcm");
		DBManager.insert("insert_hotWord", hw);
//		DBManager.update("deleteHotWord", "1");
//		System.out.println(DBManager.queryFList("getAllHotWordList", ""));
	}
}


