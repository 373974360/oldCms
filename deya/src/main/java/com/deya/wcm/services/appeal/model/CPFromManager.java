package com.deya.wcm.services.appeal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.model.CPFrom;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.appeal.model.ModelDAO;

/**
 *  诉求业务自定义字段逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求业务自定义字段逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */

public class CPFromManager implements ISyncCatch{
	private static List<CPFrom> from_list = new ArrayList<CPFrom>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		from_list.clear();
		from_list = ModelDAO.getAllCPFormList();	
	}
	
	public static void reloadCPFrom()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.model.CPFromManager");	
	}
	
	public static List<CPFrom> getCPFormListByModel(int model_id)
	{
		List<CPFrom> l = new ArrayList<CPFrom>();
		if(from_list != null && from_list.size() > 0)
		{
			for(int i=0;i<from_list.size();i++)
			{
				if(from_list.get(i).getModel_id() == model_id)
				{
					l.add(from_list.get(i));
				}
			}
		}
		return l;
	}
	
	public static boolean insertCPFrom(int model_id,List<CPFrom> l,SettingLogsBean stl)
	{
		if(ModelDAO.insertCPFrom(model_id, l, stl))
		{
			reloadCPFrom();
			return true;
		}else
			return false;
	}
	
	public static boolean deleteCPFrom(String model_ids)
	{
		if(ModelDAO.deleteCPFrom(model_ids))
		{
			reloadCPFrom();
			return true;
		}else
			return false;	
	}
	
	public static void main(String args[])
	{
		
	}
}
