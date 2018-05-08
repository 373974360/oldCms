package com.deya.wcm.services.member;

import java.util.ArrayList;
import java.util.List;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.MemberConfigBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.member.MemberDAO;

public class MemberConfigManager implements ISyncCatch{
	
	private static MemberConfigBean config_bean = null;

	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		config_bean = null;
		List<MemberConfigBean> lt = MemberDAO.getAllMemberConfigList();
		if(lt != null && lt.size() > 0)
		{
			config_bean = lt.get(0);
		}
	}
	
	/**
	 * 初始化加载会员配置信息
	 */
	public static void reload()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.member.MemberConfigManager");
	}
	
	/**
	 * 得到会员配置信息
	 * @return	会员配置对象
	 */
	public static MemberConfigBean getMemberConfigBean()
	{
		MemberConfigBean ret = config_bean;
		return ret;
	}
	
	/**
	 * 取得禁用名称列表
	 * @return	禁用名称列表
	 */
	public static List<String> getForbiddenName()
	{
		List<String> ret = new ArrayList<String>();
		if(config_bean != null)
		{
			String[] names = config_bean.getForbidden_name().trim().split(" ");	
			for(int i=0; i<names.length; i++)
			{
				ret.add(names[i]);
			}
		}
		return ret;
	}
	
	/**
	 * 修改会员配置信息
	 * @param mcb	会员配置对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateMemberConfig(MemberConfigBean mcb, SettingLogsBean stl)
	{
		if(MemberDAO.updateMemberConfig(mcb, stl))
		{
			reload();
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
