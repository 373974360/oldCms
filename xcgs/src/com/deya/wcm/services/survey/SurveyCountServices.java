package com.deya.wcm.services.survey;

import java.io.File;
import java.util.*;

import com.deya.wcm.bean.survey.SurveyCategory;
import com.deya.wcm.bean.survey.SurveyCount;
import com.deya.wcm.dao.survey.SurveyCountDAO;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

public class SurveyCountServices {
	/*********************** 按分类统计 开始 ******************************/
	/**
     * 得到分类统计数据
     * @param String start_time
     * @param String end_time
     * @return Map
     * */
	public static Map getSurveyCategoryCount(String start_time,String end_time,String time_type,String site_id)
	{ 
		String con = " and cs."+time_type+" > '"+start_time+"' and cs."+time_type+" < '"+end_time+"'";
		
		//集合，主键为分类ID，值为Count对象
		Map count_map = new HashMap<String,SurveyCount>();
		
		List<SurveyCount> category_list = SurveyCountDAO.getAllSurveyCategory(site_id);
		if(category_list != null && category_list.size() > 0)
		{
			//得到所有的问卷分类			
			setCategroyMap(count_map,category_list);
//			按分类下的问卷数统计
			Map m = new HashMap();
			m.put("con", con);
			m.put("site_id", site_id);
			List<SurveyCount> main_list = SurveyCountDAO.getSurveyCategoryCount(m);
			setCountMap(count_map,main_list);
			//按分类下的问卷所有主题数统计，应为它们的查询条件是一样的
			List<SurveyCount> subject_list = SurveyCountDAO.getSurveySubjectCount(m);
			setSubjectCountToMap(count_map,subject_list);
//			按分类下的问卷所有答卷数统计，应为它们的查询条件是一样的
			List<SurveyCount> answer_list = SurveyCountDAO.getSurveyAnswerCount(m);
			setAnswerCountToMap(count_map,answer_list);			
//			得到已发布的主题数
			con += " and cs.publish_status=1";
			m.put("con", con);
			List<SurveyCount> public_list = SurveyCountDAO.getSurveyCategoryCount(m);
			setPublishCountToMap(count_map,public_list);
			
		}
		
		//生成excel文件 
		String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
		createExcel(file_name,getCategorySubjectData(start_time,end_time,count_map,time_type),"category","问卷类型统计列表");
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
		
		String[][] data = new String[m.size()+4][5]; 
    	data[0][0] = "报表生成时间：";
    	data[0][1] = DateUtil.getCurrentDateTime();
    	data[1][0] = "统计条件：";
    	data[1][1] = "统计方式："+str;
    	data[2][1] = "日期范围："+start_time+" -- "+end_time;
    	data[3][0] = "问卷分类";
    	data[3][1] = "问卷数";
    	data[3][2] = "发布问卷数";
    	data[3][3] = "问题总数";
    	data[3][4] = "答案数";
    	
		//遍历，取出统计对象
		Iterator iter = m.entrySet().iterator(); 
		int i=4;
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 			    		    
		    SurveyCount sc = (SurveyCount)entry.getValue();		    
		    data[i][0] = sc.getC_name();
		    data[i][1] = ""+sc.getSur_count();
		    data[i][2] = ""+sc.getPublish_count();
		    data[i][3] = ""+sc.getSubject_count();
		    data[i][4] = ""+sc.getAnswer_count();		    
		    i++;
		}	
		return data;
	}
	
	public static void createExcel(String  file_name,String[][] data,String type,String sheelName)
	{
		try{		
			 JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
			 String path = bc.getProperty("path", "", "manager_path").trim()+"/survey/count";
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
	public static void setCategroyMap(Map m,List<SurveyCount> category_list)
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
	public static void setCountMap(Map m,List<SurveyCount> main_list)
	{
		for(int i=0;i<main_list.size();i++)
		{
			try{
				SurveyCount sc = (SurveyCount)m.get(main_list.get(i).getCategory_id());				
				sc.setSur_count(main_list.get(i).getSur_count());
			}catch(Exception e)
			{
				System.out.println("setChatCountToMap category is null "+main_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 得到问卷下的问题总数
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setSubjectCountToMap(Map m,List<SurveyCount> subject_list)
	{
		for(int i=0;i<subject_list.size();i++)
		{
			try{
				SurveyCount sc = (SurveyCount)m.get(subject_list.get(i).getCategory_id());				
				sc.setSubject_count(subject_list.get(i).getSubject_count());
			}catch(Exception e)
			{
				System.out.println("setSubjectCountToMap category is null "+subject_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 得到答卷总数
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setAnswerCountToMap(Map m,List<SurveyCount> answer_list)
	{
		for(int i=0;i<answer_list.size();i++)
		{
			try{
				SurveyCount sc = (SurveyCount)m.get(answer_list.get(i).getCategory_id());
				sc.setAnswer_count(answer_list.get(i).getAnswer_count());
			}catch(Exception e)
			{
				System.out.println("setAnswerCountToMap category is null "+answer_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}
	
	/**
     * 得到问卷发部数
     * @param Map
     * @param List
     * @return List　列表
     * */
	public static void setPublishCountToMap(Map m,List<SurveyCount> public_list)
	{
		for(int i=0;i<public_list.size();i++)
		{
			try{
				SurveyCount sc = (SurveyCount)m.get(public_list.get(i).getCategory_id());
				sc.setPublish_count(public_list.get(i).getPublish_count());
			}catch(Exception e)
			{
				System.out.println("setPublishCountToMap category is null "+public_list.get(i).getCategory_id());
				e.printStackTrace();			
			}
		}
	}	
	/*********************** 按分类统计 结束 ******************************/
	
	/*********************** 按热度排行 开始 ******************************/
	/**
     * 得到分类统计数据
     * @param String start_time 开始时间
     * @param String end_time 结束时间
     * @param String category_ids 栏目ID
     * @param String count_num 排序条数
     * @return Map
     * */
	public static Map getHotCount(String start_time,String end_time,String category_ids,int count_num,String time_type,String site_id)
	{ 
		String con = " and cs.start_time > '"+start_time+"' and cs.start_time < '"+end_time+"'";
		Map m = new HashMap();
		m.put("con", con);
		m.put("category_ids", category_ids);
		m.put("site_id", site_id);
		List<SurveyCount> count_list = new ArrayList();
		Map count_map = new HashMap();
		setListToMap(count_list,m,count_num);
		count_map.put("count_list", count_list);
		
//		生成excel文件 
		String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
		createExcel(file_name,getHotCountData(start_time,end_time,category_ids,count_list,count_num,time_type),"hot","问卷热度排行");
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
	public static String[][] getHotCountData(String start_time,String end_time,String category_ids,List<SurveyCount> count_list,int count_num,String time_type)
	{
		String str = getTimeTypeText(time_type);
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
    	data[3][1] = "显示条数："+co_num;
    	data[4][1] = "所属分类："+getCategoryNames(category_ids);    	    	
    	data[5][0] = "问卷名称";
    	data[5][1] = "问卷分类";    	
    	data[5][3] = "问题总数";
    	data[5][2] = "答卷数";
    	
    	if(count_list != null && count_list.size() > 0)
    	{
    		for(int i=0;i<count_list.size();i++)
    		{
    			data[i+6][0] = count_list.get(i).getS_name();
    		    data[i+6][1] = count_list.get(i).getC_name();
    		    data[i+6][2] = ""+count_list.get(i).getSubject_count();
    		    data[i+6][3] = ""+count_list.get(i).getAnswer_count();
    		}
    	}		
		return data;
	}
	
	/**
     * 得到问卷分类名称
     * @param String category_ids
     * @return String category_Names
     * */
	public static String getCategoryNames(String category_ids)
	{
		String names = "";
		/*
		List<SurveyCount> l = SurveyCountDAO.getAllSurveyCategory();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(category_ids.contains(l.get(i).getCategory_id()))
					names += ","+l.get(i).getC_name();
			}
			names = names.substring(1);
		}*/
		category_ids = category_ids.replaceAll("'", "");
		String[] tempA = category_ids.split(",");
		if(tempA != null && tempA.length > 0)
		{
			for(int i=0;i<tempA.length;i++)
			{
				SurveyCategory sc = SurveyCategoryService.getSurveyCategoryBean(tempA[i]);
				if(sc != null)
					names += ","+sc.getC_name();
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
	public static void setSubjectMap(Map m,List<SurveyCount> list)
	{
		for(int i=0;i<list.size();i++)
		{
			m.put(list.get(i).getS_id(), list.get(i));			
		}
	}
	
	public static void setListToMap(List<SurveyCount> count_list,Map m,int count_num)
	{
//		按答卷数取得统计列表
		List<SurveyCount> answer_list = SurveyCountDAO.getHotCount_answer(m);
		//分类下的问卷所有主题数统计
		List<SurveyCount> subject_list = SurveyCountDAO.getSurveySubjectCountBySub(m);
		
		Map list_map = new HashMap<String,SurveyCount>();	
		
		if(answer_list != null && answer_list.size() > 0)
		{				
			setSubjectMap(list_map,subject_list);
			//count_num 为0的或大于List的总数，取List所有的数据，
			if(answer_list.size() < count_num || count_num == 0)
				count_num = answer_list.size();
			
			for(int i=0;i<count_num;i++)
			{
				if(list_map.containsKey(answer_list.get(i).getS_id()))
				{
					SurveyCount sc = (SurveyCount)list_map.get(answer_list.get(i).getS_id());					
					answer_list.get(i).setSubject_count(sc.getSubject_count());
				}
				
				count_list.add(answer_list.get(i));
			}
		}
			
	}
	
	/*********************** 按热度排行 结束 ******************************/
	public static void main(String[] args)
	{
		System.out.println(getSurveyCategoryCount("2011-08-01", "2011-08-15", "add_time","11111ddd"));
		
	}
}
