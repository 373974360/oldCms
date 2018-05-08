package com.deya.wcm.dao.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.comment.CommentBean;
import com.deya.wcm.bean.comment.CommentSet;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;


public class CommentDAO {
       
	/**
     * 插入信息
     * @param SubScription subScription 
     * @return boolean
     * */
	public static boolean addCommentSet(CommentSet commentSet) {
		commentSet.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.COMSET_TABLE_NAME));
		return DBManager.insert("comment_set.addCommentSet", commentSet);
	} 
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List 
     * */
	@SuppressWarnings("unchecked")
	public static List<CommentSet> getCommentSetList() { 
		Map<String,String> map = new HashMap<String,String>();
		return DBManager.queryFList("comment_set.getCommentSetList", map);
	} 
	
	/**
     * 得到列表数量
     * @param Map map sql所需要的参数 
     * @return List 
     * */
	public static int getCommentSetCount(Map<String,String> map) {
		return Integer.valueOf((Integer)DBManager.queryFObj("comment_set.getCommentSetCount", map));
	} 
	
	/**
     * 修改信息
     * @param SubScription subScription 
     * @return boolean
     * */
	public static boolean updateCommentSet(CommentSet commentSet) {
		return DBManager.update("comment_set.updateCommentSet", commentSet);
	} 
	
	
	/**
     * 得到信息
     * @param Map map sql所需要的参数 
     * @return List 
     * */
	public static CommentSet getCommentSetByAppIdAndSiteId(String site_id,String app_id) { 
		Map<String,String> map = new HashMap<String,String>();
		map.put("app_id", app_id);
		if(site_id!=null && !"".equals(site_id)){
			map.put("site_id", site_id);
		}
		return (CommentSet)DBManager.queryFObj("comment_set.getCommentSetByAppIdAndSiteId", map);
	} 
	
	/***************************评论信息 开始******************************************/
	/**
     * 取得评论信息最多的新闻
     * @param Map<String,String> m
     * @return CommentBean 
     * */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getHotCommentInfo(Map<String,String> m)
	{
		return DBManager.queryFList("getHotCommentInfo", m);	
	}
	
	/**
     * 取得评论内容总数，前台
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentMainCountForBrowser(Map<String,String> m)
	{
		return 	DBManager.getString("getCommentMainCountForBrowser", m);	
	}
	
	/**
     * 取得评论内容列表，前台
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getCommentMainListForBrowser(Map<String,String> m)
	{
		return 	DBManager.queryFList("getCommentMainListForBrowser", m);	
	}
	
	/**
     * 根据参数ID查询到所有评论信息，不区删除和审核状态，用于查盖楼
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getCommentListForIDS(String ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		return 	DBManager.queryFList("getCommentListForIDS", m);	
	}
	
	/**
     * 取得信息类型的后台评论总数
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentMainCountForInfo(Map<String,String> m)
	{
		return 	DBManager.getString("getCommentMainCountForInfo", m);	
	}
	
	/**
     * 取得信息类型的后台评论列表
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getCommentMainListForInfo(Map<String,String> m)
	{
		return 	DBManager.queryFList("getCommentMainListForInfo", m);	
	}
	
	
	/**
     * 插入评论
     * @param CommentBean
     * @return boolean 
     * */
	public static boolean insertCommentMain(CommentBean comB)
	{
		return DBManager.insert("insert_comment_main", comB);
	}
	
	/**
     * 审核通过评论
     * @param ids
     * @param List<CommentBean> l
     * @param String is_status
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean passCommentMain(String ids,List<CommentBean> l,String is_status,SettingLogsBean stl)
	{//没办法，通过的时候需要把content2里的值付到content中，只能一条一条循环弄了
		
		try{
			String parent_ids = "";
			Map<String,String> m = new HashMap<String,String>();
			m.put("is_status", is_status);			
			for(CommentBean cb : l)
			{
				m.put("id", cb.getId()+"");	
				DBManager.update("pass_comment_main", m);
				if(cb.getParent_id() != 0)
					parent_ids += ","+cb.getParent_id();
			}
			if(parent_ids != null && !"".equals(parent_ids))
			{
				m.put("parent_ids", parent_ids.substring(1));
				DBManager.update("update_comment_replay", m);
			}
			PublicTableDAO.insertSettingLogs(LogUtil.AUDIT,"评论",ids,stl);
			return true;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改评论内容
     * @param String ids
     * @param String is_status
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean updateCommentMain(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("update_comment_main", m))
		{
			if(stl != null)
				PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"评论",m.get("id"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 撤消评论
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean cancelCommentMain(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("cancel_comment_main", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.CANCEL,"评论",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除评论
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean deleteCommentMain(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("delete_comment_main", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"评论",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
		
	/**
     * 得到父节点ID字符串
     * @param String id
     * @return String 
     * */
	public static String getParentStr(String id)
	{
		return DBManager.getString("getCommentMainParentStr", id);
	}
}
