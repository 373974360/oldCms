package com.deya.wcm.services.appeal.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.lang.CommonLangBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

public class CommonLangRPC {

	/**
	 * 取得常用语信息列表
	 * @param mp
	 * @return	常用语信息列表
	 */
	public static List<CommonLangBean> getCommonLangList(Map<String, String> mp)
	{
		return CommonLangManager.getCommonLangList(mp);
	}
	
	/**
	 * 取得常用语信息条数
	 * @param mp
	 * @return	常用语信息条数
	 */
	public static String getCommonLangCnt(Map<String, String> mp)
	{
		return CommonLangManager.getCommonLangCnt(mp);
	}
	
	/**
	 * 通过常用语ID取得常用语信息
	 * @param id 常用语ID
	 * @return	常用语信息
	 */
	public static CommonLangBean getCommonLangByID(String id)
	{
		return CommonLangManager.getCommonLangByID(id);
	}
	
	/**
	 * 根据常用语类型取得常用语列表
	 * @param type	常用语类型
	 * @return	常用语列表
	 */
	public static List<CommonLangBean> getCommonLangListByType(int type)
	{
		return CommonLangManager.getCommonLangListByType(type);
	}
	
	/**
	 * 插入常用语信息
	 * @param clb	常用语信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean insertCommonLang(CommonLangBean clb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommonLangManager.insertCommonLang(clb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改常用语信息
	 * @param clb	常用语信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCommonLang(CommonLangBean clb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommonLangManager.updateCommonLang(clb, stl);
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
	public static boolean saveSort(String ids, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommonLangManager.saveSort(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除常用语信息
	 * @param mp
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteCommonLang(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommonLangManager.deleteCommonLang(mp, stl);
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
		List<CommonLangBean> lt = getCommonLangList(mp);
		
		//boolean flg = updateCommonLang(mp, new SettingLogsBean());
		System.out.println(getCommonLangCnt(mp));
	}
}
