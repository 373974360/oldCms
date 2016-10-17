package com.deya.wcm.dao.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateInfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class MateInfoDao {
	
	/**
     * 得到素材列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<MateInfoBean> getMateInfoList(Map<String,String> con_m)
	{
		return DBManager.queryFList("getMateInfoList",con_m);
	}
	/**
     * 得到素材对象
     * @param 
     * @return List
     * */
	public static MateInfoBean getMateInfoByArr_id(String arr_id)
	{
		return (MateInfoBean)DBManager.queryFObj("getMateInfoBean",arr_id);
	}
	/**
     * 得到素材总数
     * @param 
     * @return List
     * */
	public static String  getMateInfoListCounts(Map<String,String> con_m)
	{
		return DBManager.getString("getMateInfoListCounts",con_m);
	}
	
	/**
     * 添加素材对象
     * @param 
     * @return List
     * */
	public static boolean insertMateInfo(MateInfoBean mfb,SettingLogsBean stl)
	{	
		if(DBManager.insert("insertMateInfo",mfb))
		{
			PublicTableDAO.insertSettingLogs("添加","素材",mfb.getAtt_id()+"",stl);
			return true;
		}else
			return false;
	}
	/**
     * 修改素材信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMateInfo(MateInfoBean mfb,SettingLogsBean stl)
	{
		if(DBManager.update("updateMateInfo", mfb))
		{
			PublicTableDAO.insertSettingLogs("修改","素材",mfb.getAtt_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除素材信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMateInfo(String att_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("att_id", att_id);
		if(DBManager.update("deleteMateInfos",m))
		{
			PublicTableDAO.insertSettingLogs("删除","素材",att_id,stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 移动附件
     * @param String f_id
     * @param String att_ids
     * @return boolean
     * */
	public static boolean moveMateInfo(String f_id,String att_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("f_id", f_id);
		m.put("att_ids", att_ids);
		return DBManager.update("move_mate_info", m);
	}
}