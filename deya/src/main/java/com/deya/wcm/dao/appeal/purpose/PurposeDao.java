package com.deya.wcm.dao.appeal.purpose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class PurposeDao {

	/**
	 * 得到所有诉求目的信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<PurposeBean> getAllPurposeList()
	{
		return DBManager.queryFList("getAllPurposeList", "");
	}
	
	/**
	 * 得到所有诉求目的信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String  getPurposeCount()
	{
		return DBManager.getString("getPurposeCount","");
	}
	
	/**
	 * 添加诉求目的信息
	 * @param pb	诉求目的信息对象
	 * @param stl
	 * @return	true 成功| false	 失败
	 */
	public static boolean insertPurpose(PurposeBean pb, SettingLogsBean stl)
	{
		int pur_id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_PROCESS_TABLE_NAME);
		//pb.setPur_id(pur_id);
		if(DBManager.insert("insertPurpose", pb))
		{
			PublicTableDAO.insertSettingLogs("添加", "诉求目的", pb.getPur_id()+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据诉求目的ID取得诉求目的信息
	 * @param id	会员ID
	 * @return	会员信息对象
	 */
	public static PurposeBean getPurposeBeanByID(String id)
	{
		return (PurposeBean)DBManager.queryFObj("getPurposeBeanByID", id);
	}
	
	
	/**
	 * 修改诉求目的信息
	 * @param pb	诉求目的信息对象
	 * @param stl
	 * @return	true 成功| fasle 失败
	 */
	public static boolean updatePurpose(PurposeBean pb, SettingLogsBean stl)
	{
		if(DBManager.update("updatePurpose", pb))
		{
			PublicTableDAO.insertSettingLogs("修改", "诉求目的", pb.getPur_id()+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
     * 保存诉求目的排序
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean savePurposeSort(String pur_id,SettingLogsBean stl)
	{
		if(pur_id != null && !"".equals(pur_id))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = pur_id.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1));
					m.put("pur_id", tempA[i]);
					DBManager.update("update_Purpose_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","诉求目的",pur_id,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	/**
	 * 删除诉求目的信息
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deletePurpose(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deletePurpose", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "诉求目的", mp.get("pur_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
}