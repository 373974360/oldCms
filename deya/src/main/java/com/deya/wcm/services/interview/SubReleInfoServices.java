package com.deya.wcm.services.interview;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.interview.*;
import com.deya.util.DateUtil;

/**
 * 访谈主题相关信息处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题相关信息处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author songyufeng
 * @version 1.0
 * * 
 */

public class SubReleInfoServices {

	
	
	public static String getReleInfoCountBySub_id(String sub_id)
	{	
		return SubReleInfoDAO.getReleInfoCountBySub_id(sub_id);
	}
	
	/**
     * 得到主题相关信息列表count
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	public static String getReleInfoListCountBySub_id(String con,String sub_id)
	{
		Map<String,String> m = new HashMap<String,String>();	
		m.put("con",con);
		m.put("sub_id",sub_id);
		return SubReleInfoDAO.getReleInfoListCountBySub_id(m);
	}
	
	/**
     * 得到主题相关信息列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getReleInfoListBySub_id(String con,int start_num,int page_size,String sub_id)
	{	
		Map<String, Comparable> m = new HashMap<String, Comparable>();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con",con);
		m.put("sub_id",sub_id);
		return SubReleInfoDAO.getReleInfoListBySub_id(m);
	}
	
	/**
     * 得到相关信息对象
     * @param int id 参与者对象id
     * @return SubReleInfo
     * */
	public static SubReleInfo getSubReleInfo(int id)
	{
		return SubReleInfoDAO.getSubReleInfo(id);
	}
	
	/**
     * 得到相关信息对象
     * @param String id 参与者对象uuid
     * @return SubReleInfo
     * */
	public static SubReleInfo getSubReleInfo(String id)
	{
		return SubReleInfoDAO.getSubReleInfo(id);
	}
	
	/**
     * 添加相关信息
     * @param SubReleInfo sri 相关信息对象
     * @return List　列表
     * */
	public static boolean insertReleInfo(SubReleInfo sri,SettingLogsBean stl)
	{
		String current_time = DateUtil.getCurrentDateTime();
		String info_id = UUID.randomUUID().toString();
		sri.setInfo_id(info_id);
		sri.setAdd_time(current_time);
		
		return SubReleInfoDAO.insertReleInfo(sri,stl);	
	}
	
	/**
     * 修改相关信息
     * @param SubReleInfo sri 相关信息对象
     * @return List　列表
     * */
	public static boolean updateReleInfo(SubReleInfo sri,SettingLogsBean stl)
	{
		sri.setUpdate_time(DateUtil.getCurrentDateTime());
		sri.setUpdate_user(sri.getAdd_user());
		return SubReleInfoDAO.updateReleInfo(sri,stl);
	}
	

	/**
     * 删除相关信息
     * @param Map m
     * @return List　列表
     * */
	public static boolean deleteReleInfo(String ids,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		return SubReleInfoDAO.deleteReleInfo(m,stl);
	}
	
	/**
     * 删除相关信息
     * @param Map m
     * @return List　列表
     * */
	public static boolean publishReleInfo(String ids,String user_name,int publish_flag,SettingLogsBean stl)
	{
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("ids", ids);
		m.put("publish_flag", publish_flag);
		m.put("oper_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		return SubReleInfoDAO.publishReleInfo(m,stl);
	}
	
	/**
     * 保存相关信息排序
     * @param Map m
     * @return List　列表
     * */
	public static boolean saveReleInfoSort(String ids,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", ids);
		return SubReleInfoDAO.saveReleInfoSort(m,stl);
	}
	
	public static void main(String[] args)
	{
		System.out.println(getSubReleInfo("24feb736-9168-47e3-a941-bc58a721e839"));
		
	}
}
