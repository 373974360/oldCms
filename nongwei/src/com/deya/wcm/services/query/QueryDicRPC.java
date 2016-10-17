package com.deya.wcm.services.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.deya.wcm.bean.query.QueryDicBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
 

public class QueryDicRPC {
	

	public static List<QueryDicBean> getDicListByConf_id(int conf_id)
	{
		return QueryDicManager.getDicListByConf_id(conf_id);
	}
	
	public static boolean insertQueryDicBean(int conf_id,List<QueryDicBean> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return QueryDicManager.insertQueryDic(conf_id,l,stl);
		}else{
		    return false;
		}
	}
	
	public static QueryDicBean getDicByConf_Dic_Id(int dic_id,int conf_id)
	{
		System.out.println("dic_id====="+dic_id+"==conf_id   ===="+conf_id);
		return QueryDicManager.getDicByConf_Dic_Id(dic_id,conf_id);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	}
}