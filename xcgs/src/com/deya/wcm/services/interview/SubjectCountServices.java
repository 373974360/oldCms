package com.deya.wcm.services.interview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.interview.SubjectCategory;
import com.deya.wcm.bean.interview.SubjectCount;
import com.deya.wcm.dao.interview.SubjectCategoryDAO;
import com.deya.wcm.dao.interview.SubjectCountDAO;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

/**
 * 访谈统计逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈统计的逻辑处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectCountServices {
	/*********************** 按分类模型统计 开始 ******************************/
	/**
     * 得到分类统计数据
     * @param String start_time
     * @param String end_time
     * @return Map
     * */
	public static Map getSubjectCategoryCount(String start_time,String end_time,String time_type,String site_id)
	{ 
		String con = " and cs."+time_type+" > '"+start_time+" 00:00' and cs."+time_type+" < '"+end_time+" 23:59'";
		
		//集合，主键为分类ID，值为Count对象
		Map count_map = new HashMap<String,SubjectCount>();
		
		List<SubjectCount> category_list = SubjectCountDAO.getAllSubjectCategory(site_id);
		System.out.println(category_list);
		if(category_list != null && category_list.size() > 0)
		{
			//得到所有的访谈模型			
			setCategroyMap(count_map,category_list);
//			得到所有的主题数
			Map m = new HashMap();
			m.put("con", con);
			List<SubjectCount> main_list = SubjectCountDAO.getSubjectCategoryCount(m);
			setCountMap(count_map,main_list);
			//首先得到访谈的发言统计数据，应为它们的查询条件是一样的
			List<SubjectCount> chat_list = SubjectCountDAO.getSubjectCategoryCount_chat(m);
			setChatCountToMap(count_map,chat_list);
//			得到已发布的主题数
			con += " and cs.publish_status=1";
			m.put("con", con);
			List<SubjectCount> public_list = SubjectCountDAO.getSubjectCategoryCount(m);
			setPublishCountToMap(count_map,public_list);
//			按分类得到所有人员总和统计数据
			List<SubjectCount> user_list = SubjectCountDAO.getSubjectCategoryCount_user(m);
			setUserCountToMap(count_map,user_list);
//			得到推荐的主题数
			con += " and cs.recommend_flag=1";
			m.put("con", con);
			List<SubjectCount> recommend_list = SubjectCountDAO.getSubjectCategoryCount(m);
			setRecommendCountToMap(count_map,recommend_list);			
		}
		
		//生成excel文件 
		String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
		createExcel(file_name,getCategorySubjectData(start_time,end_time,count_map,time_type),"category","访谈模型统计列表");
		count_map.put("file_path", DateUtil.getCurrentDate()+"/"+file_name+".xls");
		
		return count_map;
	}
	
	public static String getTimeTypeText(String time_type)
	{
		String str = "按照创建时间统计";
		if("publish_time".equals(time_type))
			str = "按照发布时间统计";
		if("start_time".equals(time_type))
			str = "按照直播时间统计";
		return str;
	}
	
	/**
     * 得到excel中要显示的数据
     * @param String start_time
     * @param String end_time
     * @param Map m 统计数据 
     * @return String[][]
     * */
	public static String[][] getCategorySubjectData(String start_time,String end_time,Map m,String time_type)
	{
		String str = getTimeTypeText(time_type);
		
		String[][] data = new String[m.size()+4][6]; 
    	data[0][0] = "报表生成时间：";
    	data[0][1] = DateUtil.getCurrentDateTime();
    	data[1][0] = "统计条件：";
    	data[1][1] = "统计方式："+str;
    	data[2][1] = "日期范围："+start_time+" -- "+end_time;
    	data[3][0] = "访谈模型";
    	data[3][1] = "创建主题数";
    	data[3][2] = "发布主题数";
    	data[3][3] = "推荐主题数";
    	data[3][4] = "访问人数";
    	data[3][5] = "网友发言数";
    	
		//遍历，取出统计对象
		Iterator iter = m.entrySet().iterator(); 
		int i=4;
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 			    		    
		    SubjectCount sc = (SubjectCount)entry.getValue();		    
		    data[i][0] = sc.getCategory_name();
		    data[i][1] = ""+sc.getSub_count();
		    data[i][2] = ""+sc.getPublish_count();
		    data[i][3] = ""+sc.getRecommend_count();
		    data[i][4] = ""+sc.getUser_count();
		    data[i][5] = ""+sc.getChat_count();
		    i++;
		}	
		return data;
	}
	
	public static void createExcel(String  file_name,String[][] data,String type,String sheelName)
	{
		try{		
			 JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
		     String path = bc.getProperty("path", "", "manager_path").trim()+"/interview/count";
		     String tempPath = path+"/"+DateUtil.getCurrentDate();
		     
		     File file2 = new File(FormatUtil.formatPath(tempPath));
	    	if(!file2.exists()){	    		
	    		file2.mkdir();
	    	}
		     String xFile = FormatUtil.formatPath(tempPath+"/"+file_name+".xls"); 
		    		     
//		   删除今天以前的文件夹
		    OutExcel.deleteFile(path);
		    
			OutExcel oe=new OutExcel(sheelName);
			if("category".equals(type))
				oe.doOut(xFile,data,1,2); 
			else
				oe.doOut(xFile,data,1,4);  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
     * 将所有的数据放入MAP中，主键为分类ID，值为Count对象
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setCategroyMap(Map m,List<SubjectCount> category_list)
	{
		for(int i=0;i<category_list.size();i++)
		{
			m.put(category_list.get(i).getCategory_id(), category_list.get(i));			
		}
	}
	
	/**
     * 将所有的数据放入MAP中，主键为分类ID，值为Count对象
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setCountMap(Map m,List<SubjectCount> main_list)
	{
		for(int i=0;i<main_list.size();i++)
		{
			try{
				SubjectCount sc = (SubjectCount)m.get(main_list.get(i).getCategory_id());				
				sc.setSub_count(main_list.get(i).getSub_count());
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+main_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 将发言统计数据入到相应的分类统计对象中
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setChatCountToMap(Map m,List<SubjectCount> chat_list)
	{
		for(int i=0;i<chat_list.size();i++)
		{
			try{
				SubjectCount sc = (SubjectCount)m.get(chat_list.get(i).getCategory_id());				
				sc.setChat_count(chat_list.get(i).getChat_count());
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+chat_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 将已发布的主题数计入到相应的分类统计对象中
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setPublishCountToMap(Map m,List<SubjectCount> public_list)
	{
		for(int i=0;i<public_list.size();i++)
		{
			try{
				SubjectCount sc = (SubjectCount)m.get(public_list.get(i).getCategory_id());
				sc.setPublish_count(public_list.get(i).getSub_count());
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+public_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 将所有人员统计数计入到相应的分类统计对象中
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setUserCountToMap(Map m,List<SubjectCount> public_list)
	{
		for(int i=0;i<public_list.size();i++)
		{
			try{
				SubjectCount sc = (SubjectCount)m.get(public_list.get(i).getCategory_id());
				sc.setUser_count(public_list.get(i).getUser_count());
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+public_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 将所有推荐主题总数计入到相应的分类统计对象中
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setRecommendCountToMap(Map m,List<SubjectCount> recommend_list)
	{
		for(int i=0;i<recommend_list.size();i++)
		{
			try{
				SubjectCount sc = (SubjectCount)m.get(recommend_list.get(i).getCategory_id());
				sc.setRecommend_count(recommend_list.get(i).getSub_count());
				
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+recommend_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	/*********************** 按分类模型统计 结束 ******************************/
	/*********************** 按热度排行 开始 ******************************/
	/**
     * 得到分类统计数据
     * @param String start_time 开始时间
     * @param String end_time 结束时间
     * @param String category_ids 栏目ID
     * @param String order_type 排序类型
     * @param String count_num 排序条数
     * @return Map
     * */
	public static Map getHotCount(String start_time,String end_time,String category_ids,String order_type,int count_num,String time_type)
	{ 
		String con = " and cs."+time_type+" > '"+start_time+" 00:00' and cs."+time_type+" < '"+end_time+" 23:59'";
		Map m = new HashMap();
		m.put("con", con);
		m.put("category_ids", category_ids);
		List<SubjectCount> count_list = new ArrayList();
		Map count_map = new HashMap();
		setListToMap(count_list,order_type,m,count_num);
		count_map.put("count_list", count_list);
		
//		生成excel文件 
		String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
		createExcel(file_name,getHotCountData(start_time,end_time,category_ids,count_list,order_type,count_num,time_type),"hot","访谈热度排行");
		count_map.put("file_path", DateUtil.getCurrentDate()+"/"+file_name+".xls");
		return count_map;
	}
	
	/**
     * 得到excel中要显示的数据
     * @param String start_time
     * @param String end_time
     * @param Map m 统计数据 
     * @return String[][]
     * */
	public static String[][] getHotCountData(String start_time,String end_time,String category_ids,List<SubjectCount> count_list,String order_type,int count_num,String time_type)
	{
		String str = getTimeTypeText(time_type);
		String mesg = "按照访问人数";
		if("chat".equals(order_type))
			mesg = "按照网友发言条数";
		String co_num = "";
		if(count_num == 0)
				co_num = "所有的";
		else
			co_num = ""+count_num;
		String[][] data = new String[count_list.size()+6][4];
    	data[0][0] = "报表生成时间：";
    	data[0][1] = DateUtil.getCurrentDateTime();
    	data[1][0] = "统计条件：";
    	data[1][1] = "统计方式："+str;
    	data[2][1] = "日期范围："+start_time+" -- "+end_time;
    	data[3][1] = "排行依据："+mesg+"  显示条数："+co_num;
    	data[4][1] = "所属模型："+getCategoryNames(category_ids);    	    	
    	data[5][0] = "访谈主题名称";
    	data[5][1] = "访谈模型";
    	data[5][2] = "访问人数";
    	data[5][3] = "网友发言数";
    	
    	if(count_list != null && count_list.size() > 0)
    	{
    		for(int i=0;i<count_list.size();i++)
    		{
    			data[i+6][0] = count_list.get(i).getSub_name();
    		    data[i+6][1] = count_list.get(i).getCategory_name();
    		    data[i+6][2] = ""+count_list.get(i).getUser_count();
    		    data[i+6][3] = ""+count_list.get(i).getChat_count();
    		}
    	}		
		return data;
	}
	
	/**
     * 得到访谈模型名称
     * @param String category_ids
     * @return String category_Names
     * */
	public static String getCategoryNames(String category_ids)
	{
		String names = "";
		List<SubjectCategory> l = SubjectCategoryDAO.getSubCategoryAllName();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(category_ids.contains(l.get(i).getCategory_id()))
					names += ","+l.get(i).getCategory_name();
			}
			names = names.substring(1);
		}
		return names;
	}
	
	/**
     * 将所有的数据放入MAP中，主键为主题ID，值为Count对象
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setSubjectMap(Map m,List<SubjectCount> list)
	{
		for(int i=0;i<list.size();i++)
		{
			m.put(list.get(i).getSub_id(), list.get(i));			
		}
	}
	
	public static void setListToMap(List<SubjectCount> count_list,String order_type,Map m,int count_num)
	{
//		按用户数取得统计列表
		List<SubjectCount> user_list = SubjectCountDAO.getHotCount_user(m);
		//按发言数取得统计列表
		List<SubjectCount> chat_list = SubjectCountDAO.getHotCount_chat(m);
		
		Map list_map = new HashMap<String,SubjectCount>();
		//如果是按用户数排序
		if("user".equals(order_type))
		{
			if(user_list != null && user_list.size() > 0)
			{				
				setSubjectMap(list_map,chat_list);
				//count_num 为0的或大于List的总数，取List所有的数据，
				if(user_list.size() < count_num || count_num == 0)
					count_num = user_list.size();
				
				for(int i=0;i<count_num;i++)
				{
					if(list_map.containsKey(user_list.get(i).getSub_id()))
					{
						SubjectCount sc = (SubjectCount)list_map.get(user_list.get(i).getSub_id());						
						user_list.get(i).setChat_count(sc.getChat_count());
					}
					
					count_list.add(user_list.get(i));
				}
			}
		}
		//按访谈发言数排序
		if("chat".equals(order_type))
		{
			if(chat_list != null && chat_list.size() > 0)
			{
				
				setSubjectMap(list_map,user_list);
				if(chat_list.size() < count_num  || count_num == 0)
					count_num = chat_list.size();
				for(int i=0;i<count_num;i++)
				{
					if(list_map.containsKey(chat_list.get(i).getSub_id()))
					{
						SubjectCount sc = (SubjectCount)list_map.get(user_list.get(i).getSub_id());
						chat_list.get(i).setChat_count(sc.getUser_count());
					}
					count_list.add(chat_list.get(i));
				}
				//如果发言数列表比用户列表要少，补充上用户列表的数据
				if(count_list.size() < user_list.size())
				{
					for(int i=0;i<count_num;i++)
					{//如果chat_list没有这个ID的数据，写入到count_list
						if(!user_list.get(i).getSub_id().equals(chat_list.get(i).getSub_id()))
						{
							count_list.add(user_list.get(i));
						}
					}
				}				
			}
			else
			{
				if(user_list.size() < count_num || count_num == 0)
					count_num = user_list.size();
				
				for(int i=0;i<count_num;i++)
				{
					count_list.add(user_list.get(i));
				}
			}
				
		}
		
	}
	
	/*********************** 按热度排行 结束 ******************************/
	public static void main(String[] args)
	{
		System.out.println(getSubjectCategoryCount("2010-04-28","2010-07-28","start_time","ss"));
		//System.out.println(getHotCount("2010-06-01","2010-07-30","'05c209d4-48f0-406f-8045-ddeeda6b005e','62edc16e-889c-4440-a57a-384bb30fe6fd','8ffe0b67-e64c-48d1-a6a4-ef43a49dbc54','a6ae3d74-70af-4c73-afca-5bd105b1c779','117ac189-3818-4bb8-afdb-f5f3eeb81161','6f5668b3-b131-4062-83ba-5df285b66901','f0af2ac1-9837-453d-a9ca-05ddfdb3d863','bd5c629c-727c-4202-b052-8f13ee13334c','a6bf8cf8-5eee-4cef-ac59-fd5cad642c94','eefb91b0-968d-45cb-8dac-1297d085b2d1','07fabb64-8517-4a9e-99be-70d9a7796f3c'","user",0,"publish_time"));
		
	}
}
