package com.deya.wcm.dao.zwgk.ysqgk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkPhrasalBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;


/**
 *  依申请公开常用语
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请公开常用语类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class YsqgkPhrasalDAO {
	
	/**
	 * 得到依申请公开常用语列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<YsqgkPhrasalBean> getYsqgkPhrasaList()
	{
		return DBManager.queryFList("getYsqgkPhrasalLists", "");
	}
	
	
	/**
	 * 得到依申请公开常用语对象
	 * @return	依申请公开常用语对象
	 */
	public static YsqgkPhrasalBean getYsqgkPhrasaBean(String gph_id)
	{
		return (YsqgkPhrasalBean)DBManager.queryFObj("getYsqgkPhrasalBean", gph_id);
	}
	
	
	/**
	 * 插入常用语信息
	 * @param pb	常用语信息对象
	 * @param stl
	 * @return	true 成功| false	 失败
	 */
	public static boolean insertYPhrasal(YsqgkPhrasalBean ypb, SettingLogsBean stl)
	{
		if(DBManager.insert("insert_ysqgk_phrasal", ypb))
		{
			PublicTableDAO.insertSettingLogs("添加", "常用语",ypb.getGph_id()+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改常用语信息
	 * @param pb	常用语信息对象
	 * @param stl
	 * @return	true 成功| fasle 失败
	 */
	public static boolean updateYPhrasal(YsqgkPhrasalBean ypb, SettingLogsBean stl)
	{
		if(DBManager.update("update_ysqgk_phrasal", ypb))
		{
			PublicTableDAO.insertSettingLogs("修改", "常用语",ypb.getGph_id()+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除常用语信息
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteYPhrasal(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("delete_ysqgk_phrasal", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "常用语", mp.get("yph_id")+"", stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 保存排序
     * @param String gph_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveYPhrasalSort(String gph_id,SettingLogsBean stl)
	{
		if(gph_id != null && !"".equals(gph_id))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = gph_id.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1));
					m.put("gph_id", tempA[i]);
					DBManager.update("update_ysqgk_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","常用语",gph_id,stl);
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
}