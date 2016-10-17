package com.deya.wcm.services.appeal.purpose;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.purpose.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.appeal.category.CategoryManager;

public class PurposeRPC {

	
	/**
	 * 得到诉求目的信息列表
	 * @return	会员信息列表
	 */
	public static List<PurposeBean> getPurposeList()
	{
		return PurposeManager.getAllPurposeList();
	}
	
	/**
	 * 根据诉求目的ID取得诉求目的信息
	 * @param id 诉求目的ID
	 * @return	诉求目的信息
	 */
	public static PurposeBean getPurposeByID(String id)
	{
		return PurposeManager.getPurposeByID(id);
	}
	
	/**
     * 得到菜单ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getAppPurposeID()
	{
		return PurposeManager.getAppPurposeID();
	}
	
	/**
     * 得到诉求目的总数
     * @param
     * @return String
     * */
	public static String getPurposeCount()
	{
		return PurposeManager.getPurposeCount();
	}
	
	/**
	 * 插入诉求目的信息
	 * @param mcb	诉求目的信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean insertPurpose(PurposeBean mcb, HttpServletRequest request)
	{
		System.out.println("		RPC insertPurpose============");
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PurposeManager.insertPurpose(mcb, stl);
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
	public static boolean savePurposeSort(String mcat_id, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PurposeManager.savePurposeSort(mcat_id, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改诉求目的信息
	 * @param mcb	诉求目的信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updatePurpose(PurposeBean mcb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PurposeManager.updatePurpose(mcb, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除诉求目的信息
	 * @param mp	删除条件
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deletePurpose(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PurposeManager.deletePurpose(mp, stl);
		}
		else
		{
			return false;
		}
	}
	public static void main(String arg[])
	{
		
	}
}
