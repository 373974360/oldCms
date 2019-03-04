package com.deya.wcm.services.survey;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.*;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.survey.*;
import com.deya.util.DateUtil;
import java.util.*;

import org.apache.commons.collections.map.LRUMap;
/**
 * 问卷调查逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查的逻辑处理
 * </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyService implements ISyncCatch{	
	/**
	 * 已发布的问卷调查对象存入到缓存.survey_id做为键值
	 */
	private static LRUMap survey_Map = new LRUMap(100);
	/**
	 * 将已发布的问卷的题型对象存入到缓存.survey_id做为键值
	 */
	private static LRUMap survey_sub_Map = new LRUMap(100);
	/**
	 * 起动时加载所有已发布的问卷对象
	 */
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		try{
			survey_Map.clear();
			survey_sub_Map.clear();
			List<SurveyBean> survey_list = SurveyDAO.getAllSurveyObjBYPublish();
			if(survey_list != null && survey_list.size() > 0)
			{
				for(int i=0;i<survey_list.size();i++)
				{
					survey_Map.put(survey_list.get(i).getS_id(), survey_list.get(i));
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 加载所有已发布的问卷对象
	 */
	public static void reLoadSurveyBean()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.survey.SurveyService");
	}
	/**
	 * 在缓存中加载单个问卷
	 */
	public static void reLoadSurveyBeanBySID(String s_id)
	{
		survey_Map.remove(s_id);
		survey_Map.put(s_id,getSurveyBean(s_id));
	}
	
	/**
     * 得到推荐列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSurveyRecommendListCount(String con)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("con", con);
		return SurveyDAO.getSurveyRecommendListCount(m);
	}
	
	/**
     * 得到问卷调查列表总数(前台调用)
     * @param String con　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	public static String getSurveyListCountBrowser(String con)
	{
		Map<String,String> m = new HashMap<String,String>();
		con += " and cs.publish_status = 1 ";
		m.put("con", con);
		return SurveyDAO.getSurveyListCountBrowser(m);
	}
	
	
	/**
     * 得到问卷调查列表(前台调用)
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List　列表
     * */
	public static List getSurveyListBrowser(String con,int start_num,int page_size,String ordery_by)
	{
		 
		Map m = new HashMap();
		m.put("orderby", ordery_by);
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		con += " and cs.publish_status = 1 ";
		m.put("con", con);		
		return SurveyDAO.getSurveyList(m);		
	}
	
	/**
     * 得到推荐列表(前台使用)
     * @param 
     * @return List
     * */
	public static List getSurveyRecommendList(String con,int start_num,int page_size)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		return SurveyDAO.getSurveyRecommendList(m);
	}
	
	/**
     * 设置推荐状态
     * @param String ids
     * @param String recommend_flag
     * @return boolean
     * */
	public static boolean updateSurveyRecommend(String ids,String recommend_flag)
	{
		Map m = new HashMap();
		m.put("ids", ids);
		m.put("recommend_flag", recommend_flag);
		if("1".equals(recommend_flag))
			m.put("current_time", DateUtil.getCurrentDateTime());
		else
			m.put("current_time", "");
		return SurveyDAO.updateSurveyRecommend(m);
	}
	
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSurveyCount(String con,String site_id)
	{
		Map m = new HashMap();
		m.put("con", con);
		m.put("site_id", site_id);
		return SurveyDAO.getSurveyCount(m);
	}
	
	/**
     * 得到问卷调查列表
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List　列表
     * */
	public static List getSurveyList(String con,int start_num,int page_size,String site_id)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		m.put("site_id", site_id);
		m.put("orderby", "cs.add_time desc");
		return SurveyDAO.getSurveyList(m);
	}
	
	/**
     * 得到问卷调查主信息
     * @param String s_id
     * @return List　列表
     * */
	public static SurveyBean getSurveyBean(String s_id)
	{
		//首先从缓存中取
		if(survey_Map.containsKey(s_id))
		{
			return (SurveyBean)survey_Map.get(s_id);
		}
		else{//如果缓存中没有，从表中取，并存入缓存
			SurveyBean sb = SurveyDAO.getSurveyBean(s_id);
			survey_Map.put("s_id", sb);
			return sb;
		}
	}
	
	/**
     * 得到问卷调查题目及选项信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectBean(String s_id)
	{
		if(survey_sub_Map.containsKey(s_id))
		{
			return (List)survey_sub_Map.get(s_id);
		}
		else
		{
			List l = SurveyDAO.getSurveySubjectBean(s_id);
			survey_sub_Map.put(s_id, l);
			return l;
		}		
	}
	
	/**
     * 得到问卷调查题目信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectSingle(String s_id)
	{
		return SurveyDAO.getSurveySubjectSingle(s_id);
	}
	
	/**
     * 修改问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean updateSurvey(SurveyBean sb,List l,SettingLogsBean stl)
	{
		sb.setUpdate_time(DateUtil.getCurrentDateTime());
		try{
			if(SurveyDAO.updateSurvey(sb,stl))
			{
				//首先删除题目及选项
				SurveyDAO.deleteSubjectItem(sb.getS_id(),stl);
				insertSubjectItme(sb.getS_id(),l,stl);
			}
			//更新缓存中的这条数据
			reLoadSurveyBeanBySID(sb.getS_id());
			//删除题型缓存中的数据
			survey_sub_Map.remove(sb.getS_id());
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	/**
     * 问卷调查属性设置
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean setSurveyAttr(SurveyBean sb,SettingLogsBean stl)
	{
		sb.setUpdate_time(DateUtil.getCurrentDateTime());
		if(SurveyDAO.setSurveyAttr(sb,stl))
		{
			//此处sb里只包含的属性的内容，所以不能直接写入缓存，需要先删除缓存里的内容，再查询出对象来
			reLoadSurveyBeanBySID(sb.getS_id());
			return true;
		}else
			return false;
	}
	
	/**
     * 插入问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurvey(SurveyBean sb,List l,SettingLogsBean stl)
	{
		String survey_id = UUID.randomUUID().toString();
		sb.setAdd_time(DateUtil.getCurrentDateTime());
		sb.setS_id(survey_id);
		sb.setIs_delete(0);
		
		//首先插入问卷主表
		if(SurveyDAO.insertSurvey(sb,stl))
		{  
			insertSubjectItme(survey_id,l,stl);
		}
		return true;
	}
	
	public static void insertSubjectItme(String survey_id,List l,SettingLogsBean stl)
	{
//		得到问卷题目列表
		if(l != null && l.size() > 0)
		{
			List<SurveySub> subList = l;
			for(int i=0;i<subList.size();i++)
			{
				String sub_id = UUID.randomUUID().toString();
				SurveySub sub = subList.get(i);
				sub.setSurvey_id(survey_id);
				sub.setSubject_id(sub_id);
				//插入问卷题目
				SurveyDAO.insertSurveySub(sub,stl);
				//得到题目选项列表
				if(sub.getItemList() != null && sub.getItemList().size() > 0)
				{
					List<SurveySuvItem> itemList = sub.getItemList();
					for(int j=0;j<itemList.size();j++)
					{							
						SurveySuvItem item = itemList.get(j);	
						item.setSurvey_id(survey_id);
						item.setSubject_id(sub_id);							
						//插入选项
						SurveyDAO.insertSurveySubItem(item);
						
						//得到子选项列表
						List<SurveyChildItem> childList = item.getChildList();
						if(childList != null && childList.size()>0)
						{
							for(int k=0;k<childList.size();k++)
							{
								SurveyChildItem ci = childList.get(k);
								ci.setSubject_id(sub_id);
//								插入选项
								SurveyDAO.insertSurveyChildItem(ci);
							}
						}
					}
				}
				
			}
		}
	}
	
	/**
     * 插入问卷调查题目
     * @param List l
     * @return boolean　true or false
     * */
	public static boolean insertSurveySub(List l)
	{
		if(l != null && l.size() > 0)
		{
			List<SurveySub> subList = l;
			for(int i=0;i<subList.size();i++)
			{
				SurveySub sub = subList.get(i);
				if(sub.getItemList() != null && sub.getItemList().size() > 0)
				{
					List<SurveySuvItem> itemList = sub.getItemList();
					for(int j=0;j<itemList.size();j++)
					{
						SurveySuvItem item = itemList.get(j);						
					}
				}
				
			}
		}
		return true;
	}
	
	/**
     * 发布撤消问卷
     * @param String ids
     * @param String publish_status 1为发布,0为撤消
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean publishSurvey(String ids,String publish_status,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		m.put("publish_status", publish_status);
		if(SurveyDAO.publishSurvey(m,stl))
		{
			reLoadSurveyBean();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除问卷
     * @param String ids
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean deleteSurvey(String ids,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		if(SurveyDAO.deleteSurvey(m,stl))
		{
			reLoadSurveyBean();
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getSurveySubjectSingle("d25fb4f2-06dc-4be0-902e-c33ca1ee8ca5"));
	}
}
