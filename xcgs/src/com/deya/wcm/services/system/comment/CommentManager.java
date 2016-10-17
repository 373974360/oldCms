package com.deya.wcm.services.system.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.ip.Utils;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.comment.CommentBean;
import com.deya.wcm.dao.system.comment.CommentDAO;
import com.deya.wcm.services.appeal.sq.SQManager;

public class CommentManager {
	/**
	 * 根据会员ID得到评论列表总数
	 * @param mp
	 * @return
	 */
	public static String getCommontCountByMemberID(String me_id)
	{
		return CommentDAO.getCommontCountByMemberID(me_id);
	}
	
	/**
	 * 根据会员ID得到评论列表
	 * @param mp
	 * @return
	 */
	public static List<CommentBean> getCommontListByMemberID(String me_id,String start_num,String page_size)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("me_id", me_id);
		m.put("start_num", start_num);
		m.put("page_size", page_size);
		return CommentDAO.getCommontListByMemberID(m);
	}
	
	/**
	 * 取得评论列表
	 * @param mp
	 * @return	评论列表
	 */
	public static List<CommentBean> getCommentList(Map<String, String> mp)
	{
		List<CommentBean> l = new ArrayList<CommentBean>();
		if(mp.get("search") != null)
		{
			l = CommentDAO.searchCommentList(mp);
		}else
		{
			l = CommentDAO.getCommentList(mp);
		}
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				SQBean sb = SQManager.getSQSimpleBean(Integer.parseInt(l.get(i).getItem_id()));
				if(sb != null)
				{
					l.get(i).setRele_title(sb.getSq_title2());
					l.get(i).setModel_id(sb.getModel_id());
					l.get(i).setDept_id(sb.getDo_dept());
					l.get(i).setIp_addr(Utils.getCountry(l.get(i).getCmt_ip()));
				}
			}
		}
		return l;
	}
	
	/**
	 * 取得评论关联的列表标题
	 * @param mp
	 * @return	评论列表
	 */
	public static String getCommentReleTitle(String app_id,String id)
	{
		String title = "";
		if("appeal".endsWith(app_id))
		{
			SQBean sb = SQManager.getSqBean(Integer.parseInt(id));
			if(sb != null)
				title = sb.getSq_title2();
		}
		return title;
	}
	
	/**
	 * 取得评论列表条数
	 * @param mp
	 * @return	评论列表条数
	 */
	public static String getCommentListCnt(Map<String, String> mp)
	{
		mp.put("is_deleted", "0");
		if(mp.get("search") == "getCnt")
		//if(mp.get("search") != null)
		{
			return CommentDAO.searchCommentCnt(mp);
		}else
		{
			return CommentDAO.getCommentCnt(mp);
		}
	}
	
	
	/**
	 * 取得所有评论条数，包括未审核的，和已删除的记录
	 * @param mp 条件包括(cmt_ip,cmt_content,item_id)
	 * @return	所有评论条数
	 */
	public static String getAllCommentCnt(Map<String, String> mp)
	{
		mp.remove("cmt_status");
		mp.remove("is_deleted");
		return CommentDAO.getCommentCnt(mp);
	}
	
	/**
	 * 取得审核通过的记录条数（已审核，未删除）
	 * @return
	 */
	public static String getCheckedCommentCnt(Map<String, String> mp)
	{
		mp.put("cmt_status", "1");
		mp.put("is_deleted", "0");
		return CommentDAO.getCommentCnt(mp);
	}
	
	/**
	 * 插入评论信息
	 * @param cb	评论信息
	 */
	public static boolean insertComment(CommentBean cb)
	{
		return CommentDAO.insertComment(cb);
	}
	
	/**
	 * 修改评论信息
	 * @param cb	评论信息
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateComment(CommentBean cb, SettingLogsBean stl)
	{
		return CommentDAO.updateComment(cb, stl);
	}
	
	/**
	 * 删除评论信息
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteComment(Map<String, String> mp, SettingLogsBean stl)
	{
		return CommentDAO.deleteComment(mp, stl);
	}
	
	/**
	 * 取得IP对应的国家地区
	 * @param lt
	 * @return	map key=ip，value=国家地区
	 */
	public static Map<String, String> getIPAdress(List<CommentBean> lt)
	{
		Map<String, String> mp = new HashMap<String, String>();
		for(int i=0; i< lt.size(); i++)
		{
			String ip = lt.get(i).getCmt_ip();
			String addr = Utils.getCountry(ip);
			mp.put(ip, addr);
		}
		return mp;
	}
	
	public static void main(String[] arg)
	{
		System.out.println(getCommontListByMemberID("13","0","10"));
		
	}
}
