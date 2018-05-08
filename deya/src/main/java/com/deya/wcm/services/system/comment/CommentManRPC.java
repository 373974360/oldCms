package com.deya.wcm.services.system.comment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.comment.CommentBean;
import com.deya.wcm.services.Log.LogManager;

public class CommentManRPC {
	/**
	 * 根据会员ID得到评论列表总数
	 * @param mp
	 * @return
	 */
	public static String getCommontCountByMemberID(String me_id)
	{
		return CommentManager.getCommontCountByMemberID(me_id);
	}
	
	/**
	 * 根据会员ID得到评论列表
	 * @param mp
	 * @return
	 */
	public static List<CommentBean> getCommontListByMemberID(String me_id,String start_num,String page_size)
	{
		return CommentManager.getCommontListByMemberID(me_id,start_num,page_size);
	}
	
	/**
	 * 取得评论信息列表
	 * @param mp
	 * @return	评论信息列表
	 */
	public static List<CommentBean> getCommentList(Map<String, String> mp)
	{
		return CommentManager.getCommentList(mp);
	}
	
	/**
	 * 取得评论信息数目
	 * @param mp
	 * @return	评论信息数目
	 */
	public static String getCommentListCnt(Map<String, String> mp)
	{
		return CommentManager.getCommentListCnt(mp);
	}
	
	/**
	 * 插入评论信息
	 * @param cb	评论信息
	 */
	public static boolean insertComment(CommentBean cb,HttpServletRequest request)
	{
		cb.setCmt_ip(FormatUtil.getIpAddr(request));
		return CommentManager.insertComment(cb);
	}
	
	/**
	 * 修改评论信息
	 * @param cb 评论信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updateComment(CommentBean cb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommentManager.updateComment(cb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除评论信息
	 * @param mp
	 * @param request
	 * @return true 成功| false 失败
	 */
	public static boolean deleteComment(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommentManager.deleteComment(mp, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 取得IP对应的国家地区
	 * @param lt
	 * @return	map key=ip，value=国家地区
	 */
	public static Map<String, String> getIPAdress(List<CommentBean> lt)
	{
		return CommentManager.getIPAdress(lt);
	}
}
