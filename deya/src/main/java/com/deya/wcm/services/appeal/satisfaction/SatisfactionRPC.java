package com.deya.wcm.services.appeal.satisfaction;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.deya.wcm.bean.appeal.satisfaction.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

public class SatisfactionRPC {

	/**
     * 获取诉求满意度指标配置列表信息
     * @param 
     * @return List
     * */	
	public static List<SatisfactionBean> getSatisfactionList()
	{
		return SatisfactionManager.getSatisfactionList();
	}
	/**
     * 根据ID返回诉求满意度指标配置对象
     * @param int wf_id
     * @return SatisfactionBean
     * */
	public static SatisfactionBean getSatisfactionBean(int wf_id)
	{
		return SatisfactionManager.getSatisfactionBean(wf_id);
	}
	/**
     * 插入诉求满意度指标配置信息
     * @param SatisfactionBean wfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSatisfaction(List<SatisfactionBean> sf_list,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SatisfactionManager.insertSatisfaction(sf_list,stl);
		}else
			return false;		
	}
}
