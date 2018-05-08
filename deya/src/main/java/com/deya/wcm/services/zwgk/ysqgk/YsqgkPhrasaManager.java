package com.deya.wcm.services.zwgk.ysqgk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.lang.CommonLangBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkPhrasalBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.purpose.PurposeDao;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkPhrasalDAO;
import com.deya.wcm.db.DBManager;

public class YsqgkPhrasaManager implements ISyncCatch{
	
private static Map<String,YsqgkPhrasalBean> ysqpb_map = new TreeMap<String, YsqgkPhrasalBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		ysqpb_map.clear();
		List<YsqgkPhrasalBean> lt = YsqgkPhrasalDAO.getYsqgkPhrasaList();
		
		if(lt != null && lt.size()>0)
		{
			for(int i=0; i<lt.size(); i++)
			{
				ysqpb_map.put(lt.get(i).getGph_id()+"", lt.get(i));
			}
		}
	}
	/**
	 * 初始化加载依申请公开常用语
	 */
	private static void reloadMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.ysqgk.YsqgkPhrasaManager");
	}
	
	/**
	 * 得到依申请公开常用语列表
	 * @return	依申请公开常用语列表
	 */
	public static List<YsqgkPhrasalBean> getYsqgkPhrasaList()
	{
		List<YsqgkPhrasalBean> ret = new ArrayList<YsqgkPhrasalBean>();
		Iterator<YsqgkPhrasalBean> it = ysqpb_map.values().iterator();
			while(it.hasNext())
			{
				ret.add(it.next());
			}
			Collections.sort(ret, new CateComparator());
			return ret;
	}
	
	/**
	 * 得到依申请公开常用语列表
	 * @return	依申请公开常用语列表
	 */
	public static List<YsqgkPhrasalBean> getYsqgkPhrasaListByType(int gph_type)
	{
		List<YsqgkPhrasalBean> ret = new ArrayList<YsqgkPhrasalBean>();
		Iterator<YsqgkPhrasalBean> it = ysqpb_map.values().iterator();
			while(it.hasNext())
			{
				YsqgkPhrasalBean ypb = it.next();
				if(ypb.getGph_type() == gph_type)
				{
					ret.add(ypb);
				}
			}
		Collections.sort(ret, new CateComparator());
		return ret;
	}
	
	/**
	 * 得到依申请公开常用语id
	 * @return	String
	 */
	public static String getGph_id()
	{
		return  PublicTableDAO.getIDByTableName(PublicTableDAO.YSQGK_PHRASAL_TABLE_NAME)+"";
	}
	
	/**
	 * 添加常用语
	 * @return	true 成功| false 失败
	 */
	public static boolean insertYsqgkPhrasal(YsqgkPhrasalBean ypb, SettingLogsBean stl)
	{
		if(YsqgkPhrasalDAO.insertYPhrasal(ypb, stl))
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
	 * 修改常用语
	 * @param ypb	常用语对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateYsqgkPhrasal(YsqgkPhrasalBean ypb, SettingLogsBean stl)
	{
		if(YsqgkPhrasalDAO.updateYPhrasal(ypb, stl))
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
	 * 根据常用语ID取得常用语
	 * @param gph_id 常用语
	 * @return	常用语
	 */
	public static YsqgkPhrasalBean getYsqgkPhrasalById(String gph_id)
	{	
		YsqgkPhrasalBean ypb = ysqpb_map.get(gph_id);	
		return ypb;
	}
	
	/**
     * 保存常用语排序
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean savePhrasalSort(String gph_id,SettingLogsBean stl)
	{
		if(YsqgkPhrasalDAO.saveYPhrasalSort(gph_id, stl))
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
	 * 删除常用语
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deletePhrasalBeans(Map<String, String> mp, SettingLogsBean stl)
	{
		if(YsqgkPhrasalDAO.deleteYPhrasal(mp, stl))
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
	 * 申请公开常用语，比较诉求目的对象的sort_id
	 * @author Administrator
	 *
	 */
	static class CateComparator implements Comparator<YsqgkPhrasalBean>
	{
		
		public int compare(YsqgkPhrasalBean bean1, YsqgkPhrasalBean bean2) 
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
		List lt = YsqgkPhrasalDAO.getYsqgkPhrasaList();	
		System.out.println(lt.size());
	}
}