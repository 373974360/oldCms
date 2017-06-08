package com.deya.wcm.dao.appeal.sq;

import com.deya.util.DateUtil;
import com.deya.util.jspFilterHandl;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.sq.ProcessBean;
import com.deya.wcm.bean.appeal.sq.SQAttachment;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.appeal.sq.SQCustom;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQDAO
{
	public static List<CountBean> getSQStatisticsForModel(Map<String, String> m)
	{
		return DBManager.queryFList("getSQStatisticsForModel", m);
	}

	public static String getSQStatistics(Map<String, String> m)
	{
		return DBManager.getString("getSQStatistics", m);
	}

	public static String getSQSatisfactionCount(Map<String, String> m)
	{
		return DBManager.getString("getSQSatisfactionCount", m);
	}

	public static List<CountBean> getSQSatisfaction(Map<String, String> m)
	{
		return DBManager.queryFList("getSQSatisfaction", m);
	}

	public static List<CountBean> getSqFinishCountForDept(Map<String, String> m)
	{
		return DBManager.queryFList("getSqFinishCountForDept", m);
	}

	public static synchronized int getSQYearNum()
	{
		Map m = new HashMap();
		m.put("current_year", DateUtil.getCurrentDateTime("yyyy"));
		String num = DBManager.getString("getSQYearNum", m);
		if ((num != null) && (!("".equals(num)))) {
			return Integer.parseInt(num);
		}
		return 1;
	}

	public static synchronized int getSQNumber(int model_id, String year)
	{
		Map m = new HashMap();
		m.put("model_id", model_id + "");
		m.put("current_year", year);
		String num = DBManager.getString("getSQNumber", m);
		if ((num != null) && (!("".equals(num)))) {
			return Integer.parseInt(num);
		}
		return 1;
	}

	public static List<SQBean> getBroSQListByMemberID(String me_id)
	{
		return DBManager.queryFList("getBroSQListByMemberID", me_id);
	}

	public static String getBrowserSQCount(Map<String, String> m)
	{
		return DBManager.getString("getBrowserSQCount", m);
	}

	public static List<SQBean> getBrowserSQList(Map<String, String> m)
	{
		return DBManager.queryFList("getBrowserSQList", m);
	}

	public static SQBean getBrowserSQBean(int sq_id, String me_id)
	{
		Map m = new HashMap();
		m.put("sq_id", sq_id + "");
		if (!("".equals(me_id)))
			m.put("me_id", me_id);
		return ((SQBean)DBManager.queryFObj("getBrowserSQBean", m));
	}

	public static SQBean getSQSimpleBean(int sq_id)
	{
		return ((SQBean)DBManager.queryFObj("getSQSimpleBean", Integer.valueOf(sq_id)));
	}

	public static SQBean searchBrowserSQBean(Map<String, String> m)
	{
		return ((SQBean)DBManager.queryFObj("searchBrowserSQBean", m));
	}

	public static List<SQBean> getNotEndSQList()
	{
		return DBManager.queryFList("getNotEndSQList", "");
	}

	public static String getSqCount(Map<String, String> m)
	{
		if(m.containsKey("prove_type")&& jspFilterHandl.isTureKey(m.get("prove_type").toString())) {
			m.put("prove_type",null);
		}
		return DBManager.getString("getSqCount", m);
	}

	public static String getTransactSQCount(Map<String, String> m)
	{
		return DBManager.getString("getTransactSQCount", m);
	}

	public static List<SQBean> getTransactSQList(Map<String, String> m)
	{
		return DBManager.queryFList("getTransactSQList", m);
	}

	public static List<SQBean> getSqList(Map<String, String> m)
	{
		return DBManager.queryFList("getSqList", m);
	}

	public static SQBean getSqBean(int sq_id)
	{
		return ((SQBean)DBManager.queryFObj("getSqBean", Integer.valueOf(sq_id)));
	}

	public static List<SQCustom> getSQCustomList(int sq_id)
	{
		return DBManager.queryFList("getSQCustomList", Integer.valueOf(sq_id));
	}

	public static String getSQCustomValue(int sq_id, String cu_key)
	{
		Map m = new HashMap();
		m.put("sq_id", sq_id + "");
		m.put("cu_key", cu_key);
		return DBManager.getString("getSQCustomValue", m);
	}

	public static boolean insertSQ(SQBean sb)
	{
		return DBManager.insert("insert_sq", sb);
	}

	public static boolean insertSQCursom(List<SQCustom> l)
	{
		if ((l != null) && (l.size() > 0))
		{
			for (int i = 0; i < l.size(); ++i)
			{
				((SQCustom)l.get(i)).setCu_id(PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SQCUSTOM_TABLE_NAME));
				DBManager.insert("insert_sq_custom", l.get(i));
			}
		}
		return true;
	}

	public static boolean setSQClickCount(Map<String, Object> m)
	{
		return DBManager.update("setSQClickCount", m);
	}

	public static boolean updateSQ(SQBean sb, SettingLogsBean stl)
	{
		if (DBManager.update("update_sq", sb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信件", sb.getSq_id() + "", stl);
			return true;
		}
		return false;
	}

	public static boolean updateProcessNote(Map<String, String> m, SettingLogsBean stl)
	{
		if (DBManager.update("update_sq_process", m))
		{
			PublicTableDAO.insertSettingLogs("修改", "信件审核内容", (String)m.get("pro_id"), stl);
			return true;
		}
		return false;
	}

	public static boolean updateSQCustom(List<SQCustom> l)
	{
		if ((l != null) && (l.size() > 0))
		{
			for (int i = 0; i < l.size(); ++i)
			{
				DBManager.update("update_sq_custom", l.get(i));
			}
		}
		return true;
	}

	public static boolean updateStatus(Map<String, String> m)
	{
		return DBManager.update("update_sq_status", m);
	}

	public static boolean deleteSQ(String sq_ids, SettingLogsBean stl)
	{
		Map m = new HashMap();
		m.put("sq_ids", sq_ids);

		if (DBManager.delete("delete_sq_byID", m))
		{
			DBManager.delete("delete_sq_tag", m);
			DBManager.delete("delete_sq_process", m);
			DBManager.delete("delete_sq_atta", m);
			PublicTableDAO.insertSettingLogs("删除", "信件", sq_ids, stl);
			return true;
		}
		return false;
	}

	public static List<SQBean> getReduplicateSQList(Map<String, String> m)
	{
		return DBManager.queryFList("getReduplicateSQList", m);
	}

	public static boolean deleteSQByModel(String model_ids)
	{
		Map m = new HashMap();
		m.put("model_ids", model_ids);
		try {
			DBManager.delete("delete_tag_byModelID", m);
			DBManager.delete("delete_atta_byModelID", m);
			DBManager.delete("delete_proce_byModelID", m);
			DBManager.delete("delete_sq_byModelID", m);
			DBManager.delete("delete_sq_custom", m);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace(); }
		return false;
	}

	public static List<ProcessBean> getPro()
	{
		return DBManager.queryFList("getPro", "");
	}

	public static List<ProcessBean> getProcessListBySqID(int sq_id)
	{
		return DBManager.queryFList("getProcessListBySqID", Integer.valueOf(sq_id));
	}

	public static ProcessBean getLastProcessBySqID(String sq_id)
	{
		return ((ProcessBean)DBManager.queryFObj("getLastProcessBySqID", sq_id));
	}

	public static boolean insertProcess(ProcessBean pb)
	{
		return DBManager.insert("insert_sq_process", pb);
	}

	public static boolean deleteProcessByProID(int pro_id)
	{
		return DBManager.delete("delete_sq_process_byProid", Integer.valueOf(pro_id));
	}

	public static List<TagsBean> getSQTagList(int sq_id)
	{
		return DBManager.queryFList("getSQTagList", Integer.valueOf(sq_id));
	}

	public static boolean insertSQTag(String sq_id, String tag_ids)
	{
		if ((tag_ids != null) && (!("".equals(tag_ids)))) {
			try
			{
				Map m = new HashMap();
				m.put("sq_id", sq_id);
				String[] tempA = tag_ids.split(",");
				for (int i = 0; i < tempA.length; ++i)
				{
					m.put("tag_id", tempA[i]);
					DBManager.insert("insert_sq_tag", m);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean deleteSQTag(String sq_ids)
	{
		Map m = new HashMap();
		m.put("sq_ids", sq_ids);
		return DBManager.delete("delete_sq_tag", m);
	}

	public static List<SQAttachment> getSQAttachmentList(String sq_ids, String relevance_type)
	{
		Map m = new HashMap();
		m.put("sq_ids", sq_ids);
		m.put("relevance_type", relevance_type);
		return DBManager.queryFList("getSQAttachmentList", m);
	}

	public static boolean insertSQAtta(SQAttachment sqa)
	{
		return DBManager.insert("insert_sq_atta", sqa);
	}

	public static boolean deleteSQAtta(String sq_id, String relevance_type)
	{
		Map m = new HashMap();
		m.put("sq_ids", sq_id);
		m.put("relevance_type", relevance_type);
		return DBManager.delete("delete_sq_atta", m);
	}

	public static List<SQBean> getAllSQListByYear(String year)
	{
		Map m = new HashMap();
		m.put("c_year", year);
		return DBManager.queryFList("getAllSQListByYear", m);
	}

	public static boolean updateSQNumber(Map<String, String> m)
	{
		return DBManager.update("update_sq_number", m);
	}

	public static String getModelIdByDept_id(String dept_id)
	{
		return DBManager.getString("getModelIdByDept_id", dept_id);
	}

	public static void main(String[] args)
	{
		System.out.println(getSQCustomList(10));
	}
}