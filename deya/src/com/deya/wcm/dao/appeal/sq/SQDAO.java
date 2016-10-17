package com.deya.wcm.dao.appeal.sq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.sq.ProcessBean;
import com.deya.wcm.bean.appeal.sq.SQAttachment;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.appeal.sq.SQCustom;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  诉求信件数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求信件数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */

public class SQDAO {
	/**
	 * 按业务信件量进行排行
	 * @param Map<String,String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<CountBean> getSQStatisticsForModel(Map<String,String> m)
	{
		return DBManager.queryFList("getSQStatisticsForModel", m);
	}
	
	/**
	 * 根据条件统计信件总数
	 * @param Map<String,String> m
	 * @return String
	 */
	public static String getSQStatistics(Map<String,String> m)
	{
		return DBManager.getString("getSQStatistics", m);
	}
	
	/**
	 * 根据部门和业务得到不满意的信件总数
	 * @param Map<String,String> m
	 * @return List
	 */
	public static String getSQSatisfactionCount(Map<String,String> m)
	{
		return DBManager.getString("getSQSatisfactionCount", m);
	}
	
	/**
	 * 根据条件统计满意度排行并排序
	 * @param Map<String,String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<CountBean> getSQSatisfaction(Map<String,String> m)
	{
		return DBManager.queryFList("getSQSatisfaction", m);
	}
	
	/**
	 * 按照办结状态统计部门的办结量,并排序
	 * @param Map<String,String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<CountBean> getSqFinishCountForDept(Map<String,String> m)
	{
		return DBManager.queryFList("getSqFinishCountForDept", m);
	}
	
	/**
	 * 得到信件是在当年的第几封
	 * @return Map<String,String> m
	 * @return String
	 */
	public static synchronized int getSQYearNum()
	{
		Map<String,String> m = new HashMap<String,String>();		
		m.put("current_year", DateUtil.getCurrentDateTime("yyyy"));
		String num = DBManager.getString("getSQYearNum", m);
		if(num != null && !"".equals(num))
			return Integer.parseInt(num);
		else
			return 1;
	}
	
	/**
	 * 得到信件是在当年的第几封
	 * @return Map<String,String> m
	 * @return String
	 */
	public static synchronized int getSQNumber(int model_id,String year)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_id", model_id+"");
		m.put("current_year", year);
		String num =  DBManager.getString("getSQNumber", m);
		if(num != null && !"".equals(num))
			return Integer.parseInt(num);
		else
			return 1;
	}
	
	
	
	/**
	 * 根据会员ID得到该会员所提交的信件列表（前台使用）
	 * @return 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getBroSQListByMemberID(String me_id)
	{
		return DBManager.queryFList("getBroSQListByMemberID", me_id);
	}
	
	/**
	 * 得到信件列表总数（前台使用）
	 * @return 
	 * @return String
	 */
	public static String getBrowserSQCount(Map<String,String> m)
	{
		return DBManager.getString("getBrowserSQCount", m);
	}
	
	/**
	 * 得到信件列表（前台使用）
	 * @return 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getBrowserSQList(Map<String,String> m)
	{
		return DBManager.queryFList("getBrowserSQList", m);
	}
	
	/**
	 * 根据条件得到信件信息（前台使用）
	 * @return int sq_id
	 * @return String me_id 会员ID
	 * @return SQBean
	 */
	public static SQBean getBrowserSQBean(int sq_id,String me_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_id", sq_id+"");
		if(!"".equals(me_id))
			m.put("me_id", me_id);
		return (SQBean)DBManager.queryFObj("getBrowserSQBean", m);
	}
	
	public static SQBean getSQSimpleBean(int sq_id)
	{
		return (SQBean)DBManager.queryFObj("getSQSimpleBean", sq_id);
	}
	
	/**
	 * 根据信件编码的查询码得到信件ID和modelid（前台使用）
	 * @return Map
	 * @return SQBean
	 */
	public static SQBean searchBrowserSQBean(Map<String,String> m)
	{
		return (SQBean)DBManager.queryFObj("searchBrowserSQBean", m);
	}
	
	/**
	 * 得到所有未办结的信件
	 * @return 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getNotEndSQList()
	{
		return DBManager.queryFList("getNotEndSQList", "");
	}
	
	/**
	 * 根据条件得到信件总数
	 * @return Map m
	 * @return String
	 */
	public static String getSqCount(Map<String,String> m)
	{
		return DBManager.getString("getSqCount", m);
	}
	
	/**
	 * 得到已办理的信件总数
	 * @return Map m
	 * @return String
	 */
	public static String getTransactSQCount(Map<String,String> m)
	{
		return DBManager.getString("getTransactSQCount", m);
	}
	
	/**
	 * 得到已办理的信件列表
	 * @return Map m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getTransactSQList(Map<String,String> m)
	{
		return DBManager.queryFList("getTransactSQList", m);
	}
	
	/**
	 * 根据条件得到信件列表
	 * @return Map m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getSqList(Map<String,String> m)
	{
		return DBManager.queryFList("getSqList", m);
	}
	
	/**
	 * 根据条件得到信件信息
	 * @return int sq_id
	 * @return SQBean
	 */
	public static SQBean getSqBean(int sq_id)
	{
		return (SQBean)DBManager.queryFObj("getSqBean", sq_id);
	}
	
	/**
	 * 根据条件得到信件扩展字段列表
	 * @return int sq_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQCustom> getSQCustomList(int sq_id)
	{
		return DBManager.queryFList("getSQCustomList", sq_id);
	}
	
	/**
	 * 根据信件ID和扩展字段名称得到内容
	 * @return int sq_id
	 * @return String cu_key
	 * @return String
	 */
	public static String getSQCustomValue(int sq_id,String cu_key)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_id", sq_id+"");
		m.put("cu_key", cu_key);
		return DBManager.getString("getSQCustomValue", m);
	}
	
	/**
	 * 插入信件内容 用于前台
	 * @return SQBean sb
	 * @return boolean
	 */
	public static boolean insertSQ(SQBean sb)
	{		
		return DBManager.insert("insert_sq", sb);
	}
	
	/**
	 * 插入信件扩展字段 用于前台
	 * @return SQCustom
	 * @return boolean
	 */
	public static boolean insertSQCursom(List<SQCustom> l)
	{
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				l.get(i).setCu_id(PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SQCUSTOM_TABLE_NAME));
				DBManager.insert("insert_sq_custom", l.get(i));
			}			
		}
		return true;
	}
	
	/**
	 * 修改信件点击次数 
	 * @param int sq_id
	 * @return boolean
	 */
	public static boolean setSQClickCount(Map<String,Object> m)
	{
		return DBManager.update("setSQClickCount", m);
	}
	
	/**
	 * 修改信件内容 
	 * @return SQBean sb
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSQ(SQBean sb,SettingLogsBean stl)
	{
		if(DBManager.update("update_sq", sb))
		{
			PublicTableDAO.insertSettingLogs("修改","信件",sb.getSq_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改审核过程内容 
	 * @return Map m
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateProcessNote(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("update_sq_process", m))
		{
			PublicTableDAO.insertSettingLogs("修改","信件审核内容",m.get("pro_id"),stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改信件扩展字段 
	 * @return SQCustom
	 * @return boolean
	 */
	public static boolean updateSQCustom(List<SQCustom> l)
	{
		if(l != null && l.size() > 0)
		{			
			for(int i=0;i<l.size();i++)
			{
				DBManager.update("update_sq_custom", l.get(i));
			}
		}		
		return true;
	}
	
	/**
	 * 修改信件状态
	 * @return Map<String,String> m
	 * @return boolean
	 */
	public static boolean updateStatus(Map<String,String> m)
	{
		return DBManager.update("update_sq_status", m);
	}	
	
	/**
	 * 删除信件
	 * @return String sq_ids
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteSQ(String sq_ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_ids", sq_ids);
		
		if(DBManager.delete("delete_sq_byID", m))
		{			
			DBManager.delete("delete_sq_tag", m);
			DBManager.delete("delete_sq_process", m);
			DBManager.delete("delete_sq_atta", m);
			PublicTableDAO.insertSettingLogs("删除","信件",sq_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 得到重复件列表
	 * @return Map m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getReduplicateSQList(Map<String,String> m)
	{
		return DBManager.queryFList("getReduplicateSQList", m);
	}
	
	/**
	 * 根据业务ID删除信件ID
	 * @return String model_ids
	 * @return boolean
	 */	
	public static boolean deleteSQByModel(String model_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		try{
			DBManager.delete("delete_tag_byModelID", m);
			DBManager.delete("delete_atta_byModelID", m);
			DBManager.delete("delete_proce_byModelID", m);
			DBManager.delete("delete_sq_byModelID", m);
			DBManager.delete("delete_sq_custom", m);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/******************** 流程处理 开始 ******************************/
	public static List<ProcessBean> getPro()
	{
		return DBManager.queryFList("getPro", "");
	}
	
	
	/**
	 * 根据条件得到信件流程列表
	 * @return int sq_id 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<ProcessBean> getProcessListBySqID(int sq_id)
	{
		return DBManager.queryFList("getProcessListBySqID", sq_id);
	}
	
	/**
	 * 根据信件ID得到最后一步流程信息
	 * @return int sq_id 
	 * @return List
	 */
	public static ProcessBean getLastProcessBySqID(String sq_id)
	{
		return (ProcessBean)DBManager.queryFObj("getLastProcessBySqID", sq_id);
	}
	
	/**
	 * 插入流程信息
	 * @return ProcessBean pb
	 * @return boolean
	 */
	public static boolean insertProcess(ProcessBean pb)
	{			
		return DBManager.insert("insert_sq_process", pb);
	}
	
	/**
	 * 插入流程ID删除流程信息
	 * @return String pro_id
	 * @return boolean
	 */
	public static boolean deleteProcessByProID(int pro_id)
	{
		return DBManager.delete("delete_sq_process_byProid", pro_id);
	}
	/******************** 流程处理 结束 ******************************/
	
	
	/******************** 特征标识处理 开始 ******************************/
	/**
	 * 根据条件得到特征列表
	 * @return int 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<TagsBean> getSQTagList(int sq_id)
	{
		return DBManager.queryFList("getSQTagList", sq_id);
	}
	
	/**
	 * 插入特征词关联信息
	 * @return String sq_id 
	 * @return String tag_ids 
	 * @return List
	 */
	public static boolean insertSQTag(String sq_id,String tag_ids)
	{
		if(tag_ids != null && !"".equals(tag_ids))
		{
			try{
				Map<String,String> m = new HashMap<String,String>();
				m.put("sq_id", sq_id);
				String[] tempA = tag_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("tag_id", tempA[i]);
					DBManager.insert("insert_sq_tag", m);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}		
		return true;
	}
	
	/**
	 * 删除特征词关联信息
	 * @return String sq_ids 
	 * @return List
	 */
	public static boolean deleteSQTag(String sq_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_ids", sq_ids);
		return DBManager.delete("delete_sq_tag", m);
	}
	/******************** 特征标识处理 结束 ******************************/
	
	/******************** 附件处理 开始 ******************************/
	/**
	 * 得到附件信息	 
	 * @return String sq_ids
	 * @return String relevance_type
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQAttachment> getSQAttachmentList(String sq_ids,String relevance_type)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_ids", sq_ids);
		m.put("relevance_type", relevance_type);
		return DBManager.queryFList("getSQAttachmentList", m);
	}
	
	/**
	 * 插入附件	 
	 * @return SQAttachment sqa
	 * @return bolean
	 */
	public static boolean insertSQAtta(SQAttachment sqa)
	{		
		return DBManager.insert("insert_sq_atta", sqa);		 
	}
	
	/**
	 * 删除附件关联
	 * @return String sq_id 
	 * @return String relevance_type 
	 * @return bolean
	 */
	public static boolean deleteSQAtta(String sq_id,String relevance_type)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_ids", sq_id);
		m.put("relevance_type", relevance_type);
		return DBManager.delete("delete_sq_atta", m);
	}
	
	/******************** 附件处理 结束 ******************************/
	
	/**
	 * 根据年份得到信件列表（用于导数据后批量修改流水号）
	 * @return 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SQBean> getAllSQListByYear(String year)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("c_year", year);
		return DBManager.queryFList("getAllSQListByYear", m);
	}
	
	public static boolean updateSQNumber(Map<String,String> m)
	{
		return DBManager.update("update_sq_number", m);
	}
	
	//根据部门id得到所关联的业务，限制一个部门关联一个业务
	public static String  getModelIdByDept_id(String  dept_id)
	{
		return DBManager.getString("getModelIdByDept_id", dept_id);
	}

	public static void main(String[] args)
	{		
		System.out.println(getSQCustomList(10));
	}
	
	
}
