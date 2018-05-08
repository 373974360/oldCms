package com.deya.wcm.dao.interview;

import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 访谈主题数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题的数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectDAO {
	/**
     * 得到推荐列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSubjectRecommendListCount(Map<String,String> m)
	{
		return DBManager.getString("getSubjectRecommendListCount",m);
	}
	
	/**
     * 得到访谈列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSubjectBrowserListHandlCount(Map<String,String> m)
	{
		return DBManager.getString("getSubjectBrowserListHandlCount",m);
	}
	
	/**
     * 得到推荐列表(前台使用)
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectRecommendList(Map m)
	{
		return DBManager.queryFList("getSubjectRecommendList",m);
	}
	
	/**
     * 得到访谈列表(前台使用)
     * @param Map m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectBrowserList(Map m)
	{
		return DBManager.queryFList("getSubjectBrowserList",m);
	}
	
	/**
     * 设置推荐状态
     * @param Map m
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean updateSubjectRecommend(Map m)
	{
		return DBManager.update("updateSubjectRecommend", m);
	}
	
	/**
     * 得到创建主题列表总条数
     * @param Map m
     * @return String　条数
     * */
	@SuppressWarnings("unchecked")
	public static String getSubjectCount(Map m)
	{
		return DBManager.getString("getSubjectCount", m);
	}
	
	/**
     * 得到管理主题列表总条数
     * @param Map m
     * @return String　条数
     * */
	@SuppressWarnings("unchecked")
	public static String getSubjectManagerCount(Map m)
	{
		return DBManager.getString("getSubjectManagerCount", m);
	}
	
	/**
     * 得到主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectList(Map m)
	{		
		List l = DBManager.queryFList("getSubjectList", m);
		return l;
	}
	
	/**
     * 得到管理主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectManagerList(Map m)
	{		
		List l = DBManager.queryFList("getSubjectManagerList", m);
		return l;
	}
	
	/**
     * 得到历史记录
     * @param String sub_id
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getHistoryRecord(String sub_id)
	{
		return (SubjectBean)DBManager.queryFObj("getHistoryRecord", sub_id);
	}
	
	/**
     *　维护访谈历史记录   
     * @param SubjectBean sb
     * @return boolean
     * */
	public static boolean updateHistoryRecord(SubjectBean sb,SettingLogsBean stl)
	{
		if(DBManager.update("updateHistoryRecord", sb))
		{
			PublicTableDAO.insertSettingLogs("修改","访谈历史记录",sb.getSub_id(),stl);
			return true;
		}else
			return false;			
	}
	
	/**
     * 得到主题对象信息
     * @param int　id 查询条件
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getSubjectObj(int id)
	{
		return (SubjectBean)DBManager.queryFObj("getSubjectObj", id);
	}
	
	/**
     * 得到主题对象信息
     * @param String sub_id 查询条件
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getSubjectObjBySubID(String sub_id)
	{
		return (SubjectBean)DBManager.queryFObj("getSubjectObjBySubID", sub_id);
	}
	
	/**
     * 插入主题
     * @param SubjectCategory sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean insertSubject(SubjectBean sub,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_SUBJECT_TABLE_NAME);
		sub.setId(id);
		if(DBManager.insert("insertSubject", sub))
		{
			PublicTableDAO.insertSettingLogs("添加","访谈主题",id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改主题
     * @param SubjectCategory sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean updateSubject(SubjectBean sub,SettingLogsBean stl)
	{
		if(DBManager.update("updateSubject", sub))
		{
			PublicTableDAO.insertSettingLogs("修改","访谈主题",sub.getId()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除主题
     * @param SubjectCategory sc　主题对象
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static boolean deleteSubject(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("delete_interview_subject", m))
		{
			PublicTableDAO.insertSettingLogs("删除","访谈主题",m.get("ids")+"",stl);
			return true;
		}else
			return false;
	}

	/**
     * 修改主题状态，发布状态，访谈状态，审核状态
     * @param Map m
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static boolean updateSubjectStatus(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("updateSubjectStatus", m))
		{
			PublicTableDAO.insertSettingLogs("修改","主题"+m.get("oper_name")+"状态",m.get("ids")+"",stl);
			return true;
		}else
			return false;		
	}
	
	/**
     * 提交主题
     * @param Map m　组织好的数据
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static boolean subjectSubmit(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("subjectSubmit", m))
		{
			PublicTableDAO.insertSettingLogs("提交","访谈主题",m.get("ids")+"",stl);
			return true;
		}else
			return false;
	}
	
	/****************** 附件操作 开始************************/
	
	/**
     * 插入主题附件信息
     * @param SubjectResouse sr
     * @return boolean　true or false
     * */
	public static boolean insertResouse(SubjectResouse sr,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_RESOUSE_TABLE_NAME);
		sr.setId(id);		
		if(DBManager.insert("insertResouse", sr))
		{
			PublicTableDAO.insertSettingLogs("添加","访谈主题附件",id+"",stl);
			return true;
		}
		else
			return false;		
	}
	
	/**
     * 删除主题附件信息
     * @param String sub_id
     * @return boolean　true or false
     * */
	public static boolean deleteResouseBySub(String sub_id)
	{
		return DBManager.delete("deleteResouseBySub", sub_id);
	}
	
	/**
     * 得到附件列表
     * @param Map m　组织好的数据
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseList(Map m)
	{
		return DBManager.queryFList("getResouseList", m);
	}
	
	/**
     * 得到附件列表 用于修改页面和查看页面取出预告图片，预告视频，直播视频，历史视频项
     * @param String sub_id
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseListByManager(String sub_id)
	{
		return DBManager.queryFList("getResouseListByManager", sub_id);
	}
	
	/****************** 附件操作 结束************************/
}
