package com.deya.wcm.dao.interview;
import java.util.*;

import com.deya.wcm.db.DBManager;
/**
 * 访谈统计数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈统计的数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectCountDAO {
	/**
     * 得到所有的访谈模型
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getAllSubjectCategory(String site_id)
	{		
		return DBManager.queryFList("getAllSubjectCategory", site_id);
	}
	
	/**
     * 得到所有的主题总数
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectCategoryCount(Map m)
	{		
		return DBManager.queryFList("getSubjectCategoryCount", m);
	}
	
	/**
     * 按分类得到所有人员总和统计数据
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectCategoryCount_user(Map m)
	{
		return DBManager.queryFList("getSubjectCategoryCount_user", m);
	}
	
	/**
     * 按分类得到访谈发言统计数据
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectCategoryCount_chat(Map m)
	{		
		return DBManager.queryFList("getSubjectCategoryCount_chat", m);
	}
	
	
	/**
     * 按热度排行人员统计
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getHotCount_user(Map m)
	{	
		return DBManager.queryFList("getHotCount_user", m);
	}
	
	/**
     * 按热度排行发言统计
     * @param String con
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getHotCount_chat(Map m)
	{	
		return DBManager.queryFList("getHotCount_chat", m);
	}
}
