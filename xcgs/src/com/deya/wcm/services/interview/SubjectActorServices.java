package com.deya.wcm.services.interview;
import java.util.*;

import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.interview.*;
import com.deya.util.DateUtil;

/**
 * 访谈主题参与者逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题参与者的逻辑处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectActorServices {
	/**
     * 得到参与者列表总数
     * @param Map m　组织好的查询，翻页条数等参数
     * @return String　列表
     * */
	public static String getActorListALLCount(String sub_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		return SubjectActorDAO.getActorListALLCount(m);
	}
	
	/**
     * 得到所有参与者的名称
     * @param String sub_id
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static String getAllActorNames(String sub_id)
	{
		String names = "";
		List<SubjectActor> l = new ArrayList<SubjectActor>();
		l = SubjectActorDAO.getAllActorName(sub_id);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				names += ","+l.get(i).getActor_name();
			}
		}
		if(names != null && !"".equals(names))
			names = names.substring(1);
		
		return names;
	}
	
	/**
     * 得到所有参与者的名称
     * @param String sub_id
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List<SubjectActor> getAllActorName(String sub_id)
	{
		return SubjectActorDAO.getAllActorName(sub_id);
	}
	
	/**
     * 得到参与者列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getActorList(String con,String sub_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		m.put("con", con);
		return SubjectActorDAO.getActorList(m);
	}
	
	/**
     * 得到参与者列表所有内容
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getActorListALL(String sub_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		return SubjectActorDAO.getActorListALL(m);
	}
	
	/**
     * 得到参与者对象
     * @param int id 参与者对象id
     * @return List　列表
     * */
	public static SubjectActor getSubActor(int id)
	{
		return SubjectActorDAO.getSubActor(id);
	}
	
	/**
     * 得到参与者对象
     * @param String id 参与者对象uuid
     * @return SubjectActor
     * */
	public static SubjectActor getSubActor(String id)
	{
		return SubjectActorDAO.getSubActor(id);
	}
	
	/**
     * 添加参与者
     * @param SubjectActor sa 参与者对象
     * @return List　列表
     * */
	public static boolean insertSubActor(SubjectActor sa,SettingLogsBean stl)
	{
		sa.setActor_id(UUID.randomUUID().toString());
		sa.setAdd_time(DateUtil.getCurrentDateTime());
		return SubjectActorDAO.insertSubActor(sa,stl);
	}
	
	/**
     * 修改参与者
     * @param SubjectActor sa 参与者对象
     * @return List　列表
     * */
	public static boolean updateSubActor(SubjectActor sa,SettingLogsBean stl)
	{
		sa.setUpdate_time(DateUtil.getCurrentDateTime());
		return SubjectActorDAO.updateSubActor(sa,stl);
	}
	
	/**
     * 删除参与者
     * @param Map m
     * @return List　列表
     * */
	public static boolean deleteSubActor(String ids,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());	
		return SubjectActorDAO.deleteSubActor(m,stl);
	}
	
	/**
     * 保存参与者排序
     * @param String ids
     * @return List　列表
     * */
	public static boolean saveSubActorSort(String ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		return SubjectActorDAO.saveSubActorSort(m,stl);
	}
	
	public static void main(String args[])
	{
		System.out.println(getSubActor("91c2b307-cd11-46e4-91d7-e7c3c7c8b4e4"));
	}
}
