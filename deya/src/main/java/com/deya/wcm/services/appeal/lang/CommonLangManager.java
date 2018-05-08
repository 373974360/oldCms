package com.deya.wcm.services.appeal.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.lang.CommonLangBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.appeal.lang.CommonLangDAO;

public class CommonLangManager {
	
	private static Map<String, CommonLangBean> lang_mp = new HashMap<String, CommonLangBean>();
	
	static 
	{
		reload();
	}
	
	/**
	 * 初始化缓冲信息
	 */
	private static void reload()
	{
		lang_mp.clear();
		List<CommonLangBean> lt = CommonLangDAO.getAllCommonLangList();
		if(lt != null && lt.size() > 0)
		{
			for(int i=0; i<lt.size(); i++)
			{
				lang_mp.put(lt.get(i).getPh_id(), lt.get(i));
			}
		}
	}
	
	/**
	 * 取得常用语信息列表
	 * @param mp
	 * @return	常用语信息列表
	 */
	public static List<CommonLangBean> getCommonLangList(Map<String, String> mp)
	{
		if("all".equals(mp.get("type")))
		{
			return CommonLangDAO.getAllCommonLangListByDB(mp);
		}
		else
		{
			return CommonLangDAO.getCommonLangList(mp);
		}
	}
	
	/**
	 * 取得常用语信息条数
	 * @param mp
	 * @return	常用语信息条数
	 */
	public static String getCommonLangCnt(Map<String, String> mp)
	{
		return CommonLangDAO.getCommonLangCnt(mp);
	}
	
	/**
	 * 通过常用语ID取得常用语信息
	 * @param id 常用语ID
	 * @return	常用语信息
	 */
	public static CommonLangBean getCommonLangByID(String id)
	{
		CommonLangBean ret = lang_mp.get(id);
		if(ret == null)
		{
			ret = CommonLangDAO.getCommonLangByID(id);
			lang_mp.put(id, ret);
		}
		return ret;
	}
	
	/**
	 * 根据常用语类型取得常用语列表
	 * @param type	常用语类型
	 * @return	常用语列表
	 */
	public static List<CommonLangBean> getCommonLangListByType(int type)
	{
		List<CommonLangBean> ret = new ArrayList<CommonLangBean>();
		Iterator<CommonLangBean> it = lang_mp.values().iterator();
		while(it.hasNext())
		{
			CommonLangBean clb = it.next();
			if(clb.getPh_type() == type)
			{
				ret.add(clb);
			}
		}
		Collections.sort(ret, new CommonLangComparator());
		return ret;
	}
	
	/**
	 * 插入常用语信息
	 * @param clb	常用语信息
	 * @param stl
	 * @return true 成功| false 失败
	 */
	public static boolean insertCommonLang(CommonLangBean clb, SettingLogsBean stl)
	{
		if(CommonLangDAO.insertCommonLang(clb, stl))
		{
			reload();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改常用语信息
	 * @param clb	常用语信息
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCommonLang(CommonLangBean clb, SettingLogsBean stl)
	{
		if(CommonLangDAO.updateCommonLang(clb, stl))
		{
			reload();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存排序
	 * @param ids
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean saveSort(String ids, SettingLogsBean stl)
	{
		CommonLangBean clb = new CommonLangBean();
		if(ids != null && ids.trim() != "")
		{
			String[] arrIDS = ids.trim().split(","); 
			for(int i=0; i<arrIDS.length; i++)
			{
				clb.setSort_id(i+1);
				clb.setPh_id(arrIDS[i]);
				if(!CommonLangDAO.updateCommonLangSort(clb, stl))
				{
					reload();
					return false;
				}
			}	
		}
		reload();
		return true;
	}
	
	/**
	 * 删除常用语信息
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteCommonLang(Map<String, String> mp, SettingLogsBean stl)
	{
		if(CommonLangDAO.deleteCommonLang(mp, stl))
		{
			reload();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String arg[])
	{
		Map mp = new HashMap<String, String>();
		mp.put("ph_type", "2");
		List<CommonLangBean> lt = getCommonLangListByType(1);
		
		//boolean flg = updateCommonLang(mp, new SettingLogsBean());
		System.out.println(lt.size());
	}

	/**
	 * 常用语sort_id比较器
	 * @author Administrator
	 *
	 */
	static class CommonLangComparator implements Comparator<CommonLangBean>
	{ 
	
		public int compare(CommonLangBean bean1, CommonLangBean bean2) 
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

}
