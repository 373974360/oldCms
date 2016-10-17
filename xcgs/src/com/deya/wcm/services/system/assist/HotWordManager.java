package com.deya.wcm.services.system.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.HotWordBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.assist.HotWordDAO;

/**
 * 
 * @author 符江波
 * @date   2011-3-21
 */
public class HotWordManager implements ISyncCatch{
	private static Map<Integer,HotWordBean> hw_map = new HashMap<Integer, HotWordBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<HotWordBean> hw_list = HotWordDAO.getAllHotWordList();
		hw_map.clear();
		if(hw_list != null && hw_list.size() > 0)
		{
			for(int i=0;i<hw_list.size();i++){
				hw_map.put(hw_list.get(i).getHot_id(), hw_list.get(i));
			}
		}
	}
	
	public static void reloadHotWord()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.assist.HotWordManager");
	}
	
	/**
     * 得到所有热词列表
     * @param int hot_id
     * @return HotWordBean
     * */
	public static List<HotWordBean> getHotWordList()
	{
		List<HotWordBean> hwl = new ArrayList<HotWordBean>();
		Set<Integer> s = hw_map.keySet();
		for(Integer i : s)
		{
			hwl.add(hw_map.get(i));
		}
		return hwl;
	}
	
	/**
     * 替换内容中的热词字符
     * @param int hot_id
     * @return HotWordBean
     * */
	public static String replaceContentHotWord(String content)
	{
		if(content == null || "".equals(content.trim()))
			return content;
		Set<Integer> s = hw_map.keySet();
		for(Integer i : s)
		{
			content = content.replace(hw_map.get(i).getHot_name(), "<a target=\"_blank\" href=\""+hw_map.get(i).getHot_url()+"\">"+hw_map.get(i).getHot_name()+"</a>");
		}
		return content;
	}
	
	/**
     * 根据ID得到热词信息
     * @param int hot_id
     * @return HotWordBean
     * */
	public static HotWordBean getHotWordBean(int hot_id)
	{
		if(hw_map.containsKey(hot_id))
		{
			return hw_map.get(hot_id);
		}else
		{
			HotWordBean hwb = HotWordDAO.getHotWordBean(hot_id);
			if(hwb != null)
			{
				hw_map.put(hot_id, hwb);
				return hwb;
			}else
				return null;
		}
	}
	
	/**
     * 得到热词总数
     * @param 
     * @return String
     * */
	public static String getHotWordCount(Map<String,String> con_map)
	{
		return HotWordDAO.getHotWordCount(con_map);
	}
	
	/**
	 * 修改热词
	 * @param hw
	 * @return boolean
	 */
	public static boolean updateHotWord(HotWordBean hw, SettingLogsBean stl){
		if(HotWordDAO.updateHotWord(hw, stl)){
			hw_map.remove(hw.getHot_id());
			hw_map.put(hw.getHot_id(), hw);
			reloadHotWord();
			return true;
		}
		return false;
	}
	
	/**
	 * 添加热词
	 * @param hw
	 * @return boolean
	 */
	public static boolean addHotWord(HotWordBean hw, SettingLogsBean stl){
		if(HotWordDAO.addHotWord(hw,stl)){
			hw_map.put(hw.getHot_id(), hw);
			reloadHotWord();
			return true;
		}
		return false;
	}
	
	/**
	 * 根据条件查询热词信息
	 * @return List<HotWordBean>
	 */
	public static List<HotWordBean> getAllHotWordList()
	{
		Set<Integer> set = hw_map.keySet();
		List<HotWordBean> list = new ArrayList<HotWordBean>();
		for(int i : set){
			list.add(hw_map.get(i));
		}
		return list;
	}
	
	/**
	 * 得到热词列表
	 * @param con_map
	 * @return List<HotWordBean>
	 */
	public static List<HotWordBean> getHotWordListForDB(Map<String,String> con_map)
	{
		return HotWordDAO.getHotWordListForDB(con_map);
	}
	
	/**
	 * 删除热词
	 * @param hot_id
	 * @return boolean
	 */
	public static boolean delHotWordById(String hot_ids, SettingLogsBean stl){
		if(HotWordDAO.delHotWord(hot_ids, stl)){
			if(hot_ids != null){
				if(hot_ids.indexOf(",") != -1){
					for(String id : hot_ids.split(",")){
						hw_map.remove(Integer.parseInt(id));
					}
				}else{
					hw_map.remove(Integer.parseInt(hot_ids));
				}
			}
			reloadHotWord();
			return true;
		}
		return false;
	}
}
