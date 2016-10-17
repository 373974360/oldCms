package com.deya.wcm.services.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.comment.CommentBean;
import com.deya.wcm.bean.comment.CommentSet;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;

public class CommentSetRPC {
	/**************************评论内容 开始************************************/
	/**
     * 取得评论内容总数，前台
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentCount(String id,String type)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("id", id);
		if("info".equals(type))
			m.put("info_type", "1");
		if("survey".equals(type))
		{
			m.put("survey", "2");
			m.put("info_type", "2");
		}
		if("appeal".equals(type))
			m.put("info_type", "3");
		return CommentService.getCommentMainCountForBrowser(m);
	}
	
	/**
     * 取得后台评论总数
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentMainCount(Map<String,String> m,String info_type)
	{
		return CommentService.getCommentMainCount(m, info_type); 
	}
	
	/**
     * 取得后台评论列表
     * @param Map<String,String> m
     * @return String 
     * */
	public static List<CommentBean> getCommentMainList(Map<String,String> m,String info_type)
	{
		return CommentService.getCommentMainList(m, info_type); 
	}
	
	public static String insertCommentMain(CommentBean comB,HttpServletRequest request)
	{
		String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
		String ip = FormatUtil.getIpAddr(request);
		CommentSet cs = getCommentSetBySiteIdAndAppIDD(comB.getSite_id(),"cms");
		if(!"0".equals(cs.getIp_time()))
		{
			Map<String,String> m = new HashMap<String,String>();
			m.put("site_id", site_id);
			m.put("ip", ip);
			m.put("page_size", "1");
			m.put("start_num", "0");
			m.put("sort_name", "com.id");
			m.put("sort_type", "desc");
			List<CommentBean> cl = CommentService.getCommentMainList(m, "info");
			if(cl != null && cl.size() > 0)
			{
				try{
					long pre_time = DateUtil.dateToTimestamp(cl.get(0).getAdd_time());
					long confine_time = pre_time + Integer.parseInt(cs.getIp_time())*60*1000;
					if(DateUtil.dateToTimestamp() < confine_time)
					{
						return "timeout";
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		comB.setSite_id(site_id);
		comB.setIp(ip);
		return CommentService.insertCommentMain(comB)+"";
	}
	
	//支持数，前台用
	public static boolean supportComment(Map<String,String> m)
	{
		return CommentService.updateCommentMain(m, null);
	}
	
	/**
     * 修改评论内容
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean updateCommentMain(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommentService.updateCommentMain(m, stl);
		}else
			return false;
	}
	
	/**
     * 修改评论状态
     * @param String ids
     * @param String is_status
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean updateCommentStatus(String ids,List<CommentBean> l,String is_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommentService.updateCommentStatus(ids,l, is_status, stl);
		}else
			return false;
	}
	
	/**
     * 删除评论
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean deleteCommentMain(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CommentService.deleteCommentMain(m, stl);
		}else
			return false;
	}
	/**************************评论内容 结束************************************/

	/**************************评论配置 开始************************************/
	
	//通过site_id和app_id得到评论配置  -- 外部调用 
	public static CommentSet getCommentSetBySiteIdAndAppID(String site_id,String app_id){
		 return CommentSetManager.getCommentSetBySiteIdAndAppID(site_id, app_id);
	}
	
	//通过site_id和app_id得到评论配置  -- 动态读取
	public static CommentSet getCommentSetBySiteIdAndAppIDD(String site_id,String app_id){
		 return CommentSetManager.getCommentSetBySiteIdAndAppIDD(site_id, app_id);
	}
	
	/**
     * 修改信息
     * @param SubScription subScription 
     * @return boolean
     * */
	public static boolean updateCommentSet(CommentSet commentSet) {
		return CommentSetManager.updateCommentSet(commentSet);
	}
	/**************************评论配置 结束************************************/
}
