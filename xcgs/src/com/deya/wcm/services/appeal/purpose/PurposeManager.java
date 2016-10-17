package com.deya.wcm.services.appeal.purpose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.purpose.PurposeDao;

public class PurposeManager implements ISyncCatch{

private static Map<String ,PurposeBean> pur_map = new TreeMap<String, PurposeBean>(); 
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		pur_map.clear();
		List<PurposeBean> lt = PurposeDao.getAllPurposeList();
		if(lt != null && lt.size()>0)
		{
			for(int i=0; i<lt.size(); i++)
			{
				pur_map.put(lt.get(i).getPur_id()+"", lt.get(i));
			}
		}
	}
	/**
	 * 初始化加载诉求目的信息
	 */
	public static void reloadMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.purpose.PurposeManager");
	}
	/**
	 * 得到诉求目的列表
	 * @return	诉求目的列表
	 */
	public static List<PurposeBean> getAllPurposeList()
	{
		List<PurposeBean> ret = new ArrayList<PurposeBean>();
		Iterator<PurposeBean> it = pur_map.values().iterator();
			while(it.hasNext())
			{
				ret.add(it.next());
			}
			Collections.sort(ret, new CateComparator());
			return ret;
	}
	/**
	 * 根据诉求目的ID取得诉求目的信息
	 * @param id 诉求目的ID
	 * @return	诉求目的信息
	 */
	public static PurposeBean getPurposeByID(String id)
	{	
		PurposeBean mcb = pur_map.get(id);	
		return mcb;
	}
	/**
     * 得到ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getAppPurposeID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_PROCESS_TABLE_NAME);
	}
	
	/**
	 * 添加诉求目的
	 * @return	true 成功| false 失败
	 */
	public static boolean insertPurpose(PurposeBean mcb, SettingLogsBean stl)
	{
		if(PurposeDao.insertPurpose(mcb, stl))
		{
			reloadMap();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 诉求总数
	 *
	 */
	 public static String 	getPurposeCount() {
		return PurposeDao.getPurposeCount();
	}

	/**
	 * 修改诉求目的信息
	 * @param mcb	诉求目的对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updatePurpose(PurposeBean mcb, SettingLogsBean stl)
	{
		if(PurposeDao.updatePurpose(mcb, stl))
		{
			reloadMap();
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
	public static boolean savePurposeSort(String mcat_id,SettingLogsBean stl)
	{
		if(PurposeDao.savePurposeSort(mcat_id, stl))
		{
			reloadMap();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除诉求目的信息
	 * @param mp	key=mcat_ids,values=诉求目的IDS
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deletePurpose(Map<String, String> mp, SettingLogsBean stl)
	{
		if(PurposeDao.deletePurpose(mp, stl))
		{
			reloadMap();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 诉求目的比较器，比较诉求目的对象的sort_id
	 * @author Administrator
	 *
	 */
	static class CateComparator implements Comparator<PurposeBean>
	{
		
		public int compare(PurposeBean bean1, PurposeBean bean2) 
		{
			int ret = 0;
			if(bean1.getSort_id() > bean2.getSort_id())
			{
				ret = 1;
			}else if(bean1.getSort_id() == bean2.getSort_id())
			{
				ret = 0;
			}else
			{
				ret = -1;
			}
			return ret;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String ages[]){
		getPurposeByID("1");
		List lt = PurposeDao.getAllPurposeList();	
		System.out.println(lt.size());
	}
}