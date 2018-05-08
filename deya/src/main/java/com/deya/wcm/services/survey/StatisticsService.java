package com.deya.wcm.services.survey;
import com.deya.wcm.dao.survey.*;
import com.deya.wcm.bean.survey.*;
import com.deya.util.CalculateNumber;
import java.util.*;
/**
 * 问卷调查统计类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查统计的逻辑处理
 * </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class StatisticsService {
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getStatisticsCount(String con)
	{
		return StatisticsDAO.getStatisticsCount(con);
	}
	
	/**
     * 得到问卷调查答卷列表
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getStatisticsList(String con,int start_num,int page_size)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		return StatisticsDAO.getStatisticsList(m);
	}
	
	/**
     * 得到所有答卷的统计数据
     * @param String s_id
     * @return List
     * */
//	public static Map getStatisticsDataBySurvey(String s_id,String con)//
//	{//
//		HashMap m = new HashMap();//
//		String answer_count = StatisticsDAO.getStatisticsCountBySurvey(s_id);//
//		m.put("answer_count", answer_count);//
//		try//
//		{//
//			HashMap con_m = new HashMap();//
//			con_m.put("s_id", s_id);//
//			if(con != null && !"".equals(con))//
//				con_m.put("con", con);//
//			List<StatisticsBean> sL = StatisticsDAO.getStatisticsDataBySurvey(con_m);//
//			if(sL != null && sL.size() > 0)//
//			{//
//				for(int i=0;i<sL.size();i++)//
//				{//计算百分比//
//					sL.get(i).setProportion(CalculateNumber.getRate(""+sL.get(i).getCounts(), answer_count, 0));//
//					//将list对象取出来，放入MAP中，使用item_id和item_num做为key键，值为StatisticsBean对象//
//					StatisticsBean sb = sL.get(i);//
//					m.put(sb.getItem_id()+"_"+sb.getItem_num(), sb);		//
//				}//
//			}//
//				//
//		}//
//		catch(Exception e)//
//		{//
//			e.printStackTrace();			//
//		}//
//		return m;//
//	}
	public static Map getStatisticsDataBySurvey(String s_id,String con)			{				HashMap m = new HashMap();				String answer_count = StatisticsDAO.getStatisticsCountBySurvey(s_id);				m.put("answer_count", answer_count);				try				{					HashMap con_m = new HashMap();					con_m.put("s_id", s_id);					if(con != null && !"".equals(con))						con_m.put("con", con);					List<StatisticsBean> sL = StatisticsDAO.getStatisticsDataBySurvey(con_m);					if(sL != null && sL.size() > 0)					{										//////start------					Map<String,Integer> map = new HashMap<String,Integer>();					for(int i=0;i<sL.size();i++){						StatisticsBean sb = sL.get(i);						String it_id = sb.getItem_id();						int count = sb.getCounts();						if(map.containsKey(it_id)){							map.put(it_id, count+map.get(it_id));						}else{							map.put(it_id, count);						}							}					//////end------										for(int i=0;i<sL.size();i++)						{//计算百分比							//sL.get(i).setProportion(CalculateNumber.getRate(""+sL.get(i).getCounts(), answer_count, 0));						String it_id = sL.get(i).getItem_id();						sL.get(i).setProportion(CalculateNumber.getRate(""+sL.get(i).getCounts(), ""+map.get(it_id), 2));						//将list对象取出来，放入MAP中，使用item_id和item_num做为key键，值为StatisticsBean对象							StatisticsBean sb = sL.get(i);							m.put(sb.getItem_id()+"_"+sb.getItem_num(), sb);								}					}										}				catch(Exception e)				{					e.printStackTrace();							}				return m;			}
	/**
     * 得到某个投票选项的答卷总数
     * @param String s_id  问卷ID
     * @param String item_id 选项ID
     * @return String count
     * */
	public static String getVoteCountBySurveyItem(String s_id,String item_id)
	{
		HashMap m = new HashMap();
		m.put("s_id", s_id);
		m.put("item_id", item_id);
		return StatisticsDAO.getVoteCountBySurveyItem(m);
	}
	
	/**
     * 得到某个投票选项的答卷计数汇总
     * @param String s_id  问卷ID
     * @param String item_id 选项ID
     * @return String count
     * */
//	public static Map getVoteTotalBySurveyItem(String s_id,String item_id)//
//	{		//
//		HashMap returnM = new HashMap();//
//		try//
//		{//
//			HashMap m = new HashMap();//
//			m.put("s_id", s_id);//
//			m.put("item_id", item_id);//
//			String answer_count = StatisticsDAO.getVoteCountBySurveyItem(m);//
//			List<StatisticsBean> sL = StatisticsDAO.getVoteTotalBySurveyItem(m);//
//			if(sL != null && sL.size() > 0)//
//			{
//				for(int i=0;i<sL.size();i++)
//				{//计算百分比
//					sL.get(i).setProportion(CalculateNumber.getRate(""+sL.get(i).getCounts(), answer_count, 2));
//					//将list对象取出来，放入MAP中，使用item_id和item_num做为key键，值为StatisticsBean对象
//					StatisticsBean sb = sL.get(i);
//					returnM.put(sb.getItem_id()+"_"+sb.getItem_num(), sb);		
//				}//
//			}//
//				//
//		}//
//		catch(Exception e)//
//		{//
//			e.printStackTrace();			//
//		}//
//		return returnM;//
//	}		//李苏培 修改	public static Map getVoteTotalBySurveyItem(String s_id,String item_id)		{					HashMap returnM = new HashMap();			try			{				HashMap m = new HashMap();				m.put("s_id", s_id);				m.put("item_id", item_id);				//String answer_count = StatisticsDAO.getVoteCountBySurveyItem(m);				List<StatisticsBean> sL = StatisticsDAO.getVoteTotalBySurveyItem(m);				if(sL != null && sL.size() > 0)					{										Map<String,Integer> map = new HashMap<String,Integer>();					for(int i=0;i<sL.size();i++){						StatisticsBean sb = sL.get(i);						String it_id = sb.getItem_id();						int count = sb.getCounts();						if(map.containsKey(it_id)){							map.put(it_id, count+map.get(it_id));						}else{							map.put(it_id, count);						}							}										for(int i=0;i<sL.size();i++)					{//计算百分比						String it_id = sL.get(i).getItem_id();						sL.get(i).setProportion(CalculateNumber.getRate(""+sL.get(i).getCounts(), ""+map.get(it_id), 2));						//将list对象取出来，放入MAP中，使用item_id和item_num做为key键，值为StatisticsBean对象						StatisticsBean sb = sL.get(i);						returnM.put(sb.getItem_id()+"_"+sb.getItem_num(), sb);							}					}									}				catch(Exception e)				{					e.printStackTrace();							}				return returnM;			}
	
	/**
     * 得到某个选项的答卷文本总数
     * @param Map
     * @return String count
     * */
	public static String getItemTextCount(String s_id,String item_id,String item_value,String is_text,String search_con)
	{
		String con = "";
		Map m = new HashMap();
		m.put("s_id", s_id);
		m.put("item_id", item_id);
		m.put("item_value", item_value);
		if("true".equals(is_text))
			con = "and item_text is not null";		
		m.put("con", con);
		if(search_con != null && !"".equals(search_con))
		{
			m.put("search_con", search_con);			
		}
		return StatisticsDAO.getItemTextCount(m);
	}
	
	/**
     * 得到某个选项的答卷文本总数
     * @param Map
     * @return String count
     * */
	public static List getItemTextList(String s_id,String item_id,String item_value,String is_text,int start_num,int page_size,String search_con)
	{
		String con = "";
		Map m = new HashMap();
		m.put("s_id", s_id);
		m.put("item_id", item_id);
		m.put("item_value", item_value);
		if("true".equals(is_text))
			con = "and item_text is not null";
		
		m.put("con", con);
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		if(search_con != null && !"".equals(search_con))
		{
			m.put("search_con", search_con);
			return StatisticsDAO.getItemTextList2(m);
		}
		else
			return StatisticsDAO.getItemTextList(m);
	}
	
	/**
     * 得到某个答卷详细信息
     * @param String answer_id
     * @return List
     * */
	public static List getAnswerItemDetail(String answer_id)
	{
		return StatisticsDAO.getAnswerItemDetail(answer_id);
	}	
	
	/**
     * 得到某个主题的答卷列表总数
     * @param String s_id 主题ID
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getAnswerListCount(String s_id,String search_con)
	{
		Map m = new HashMap();		
		m.put("s_id", s_id);
		if(search_con != null && !"".equals(search_con))
		{
			m.put("search_con", search_con);
		}
		return StatisticsDAO.getAnswerListCount(m);
	}
	
	/**
     * 得到某个主题的答卷列表
     * @param String s_id 主题ID
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getAnswerList(String s_id,String search_con,int start_num,int page_size)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数		
		m.put("s_id", s_id);
		if(search_con != null && !"".equals(search_con))
		{
			m.put("search_con", search_con);
			return StatisticsDAO.getAnswerList2(m);
		}
		else
			return StatisticsDAO.getAnswerList(m);
	}
	
	public static void main(String[] args)
	{
		HashMap con_m = new HashMap();
		con_m.put("s_id", "36938365-9eb2-4b74-bcf1-09469873f4ee");
		con_m.put("con", "and  (item_id = 'item_2'  and item_value = '1'  and item_value = '2' )  and  (item_id = 'item_9' and  item_value like '%11%')");
		//System.out.println(StatisticsDAO.getStatisticsDataBySurvey(con_m));
		Map m = new HashMap();
		m = getStatisticsDataBySurvey("36938365-9eb2-4b74-bcf1-09469873f4ee","");
		
		System.out.println(m.get(""));
	}
}
