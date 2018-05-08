package com.deya.wcm.dao.system.comment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.comment.CommentBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class CommentDAO {
	
	/**
	 * 根据会员ID得到评论列表总数
	 * @param mp
	 * @return
	 */
	public static String getCommontCountByMemberID(String me_id)
	{
		return DBManager.getString("getCommontCountByMemberID", me_id);
	}
	
	/**
	 * 根据会员ID得到评论列表
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getCommontListByMemberID(Map<String, String> mp)
	{
		return DBManager.queryFList("getCommontListByMemberID", mp);
	}
	
	/**
	 * 取得评论列表
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<CommentBean> getCommentList(Map<String, String> mp)
	{
		return DBManager.queryFList("getCommontListByDB", mp);
	}
	
	/**
	 * 取得评论条数
	 * @param mp 条件包括(cmt_ip,cmt_content,item_id)
	 * @return	评论条数
	 */
	public static String getCommentCnt(Map<String, String> mp)
	{
		return (String)DBManager.queryFObj("getCommentCnt", mp);
	}
	
	/**
	 * 检索评论
	 * @param mp
	 * @return
	 */
	public static List<CommentBean> searchCommentList(Map<String, String> mp)
	{
		return DBManager.queryFList("searchCommentList", mp);
	}
	
	/**
	 * 取得检索到的评论条数
	 * @param mp
	 * @return	评论条数
	 */
	public static String searchCommentCnt(Map<String, String> mp)
	{
		return (String)DBManager.queryFObj("", mp);
	}
	
	/** 
	 * 插入评论信息
	 * @param cb 评论信息
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean insertComment(CommentBean cb)
	{
		String id = PublicTableDAO.getIDByTableName(PublicTableDAO.COMMENT_TABLE_NAME)+"";		
		cb.setAdd_dtime(DateUtil.getCurrentDateTime());
		cb.setCmt_id(id);
		return DBManager.insert("insertComment", cb);
	}
	
	/**
	 * 修改评论
	 * @param cb
	 * @param stl
	 * @return
	 */
	public static boolean updateComment(CommentBean cb, SettingLogsBean stl)
	{
		if(DBManager.update("updateComment", cb))
		{
			PublicTableDAO.insertSettingLogs("修改", "评论", cb.getCmt_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除评论信息
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteComment(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteComment", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "评论", mp.get("con_value"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String arg[])
	{		
		System.out.println(getCommontCountByMemberID("13"));
	}
}
