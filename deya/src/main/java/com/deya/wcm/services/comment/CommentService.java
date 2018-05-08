package com.deya.wcm.services.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.ip.Utils;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.comment.CommentBean;
import com.deya.wcm.bean.comment.CommentSet;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.comment.CommentDAO;
import com.deya.wcm.services.cms.info.InfoBaseManager;

public class CommentService {
	private static String delete_error = "该评论已经删除";
	/**
     * 取得评论信息最多的新闻
     * @param Map<String,String> m
     * @return CommentBean 
     * */
	public static List<CommentBean> getHotCommentInfo(Map<String,String> m)
	{
		return CommentDAO.getHotCommentInfo(m);
	}
	
	/**
     * 取得评论内容总数，前台
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentMainCountForBrowser(Map<String,String> m)
	{
		return CommentDAO.getCommentMainCountForBrowser(m);
	}
	
	/**
     * 取得评论内容列表，前台
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	public static Map<String,Object> getCommentMainListBrowserForInfo(Map<String,String> m)
	{
		//首先得到info对象
		InfoBean ib = InfoBaseManager.getInfoById(m.get("id"));
		if(ib == null || ib.getInfo_status() != 8 || ib.getFinal_status() == -1)
		{
			//信息为空，或未发布或已删除的信息直接返回null
			return null;
		}
		Map<String,Object> return_m = new HashMap<String,Object>();
		return_m.put("InfoObject", ib);
		return_m.put("CommentList", getCommentMainListForBrowserHandl(m));
		return_m.put("CommentConfig", CommentSetManager.getCommentSetBySiteIdAndAppID(m.get("site_id"),"cms"));
		return return_m;
	}
	
	/**
     * 处理评论盖楼，前台
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	public static List<CommentBean> getCommentMainListForBrowserHandl(Map<String,String> m)
	{
		List<CommentBean> l = CommentDAO.getCommentMainListForBrowser(m);
		if(l != null && l.size() > 0)
		{
			for(CommentBean cb : l)
			{
				//判断这条评论是否被回复，如果没有回复，再判断它是不是盖了别人的楼
				if(cb.getIs_replay() == 0)
				{
					if(cb.getParent_id() != 0)
					{
						//cb.setParent_list(CommentDAO.getCommentListForIDS(cb.getParent_str()));
						//获取盖楼的根级
						cb.setParent_comment(commentListToParentBean(commentListToMap(CommentDAO.getCommentListForIDS(cb.getParent_str())),cb.getParent_id()));
						String country = Utils.getCountry(cb.getIp());
						if(!"局域网".equals(country))
						{
							cb.setCountry(country);
						}
					}
				}
			}
		}
		return l;
	}
	
	/**
     * 处理盖楼，将list放入map,减少list的遍历查询
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	public static Map<Integer,CommentBean> commentListToMap(List<CommentBean> l){
		Map<Integer,CommentBean> m = new HashMap<Integer,CommentBean>();
		int i=1;
		for(CommentBean cb : l)
		{
			cb.setCom_sort(i);
			m.put(cb.getId(), cb);
			i++;
		}
		return m;
	}
	
	/**
     * 处理盖楼，将平级的评论按层级放入到父对象中,递归
     * @param Map<String,String> m
     * @return List<CommentBean> 
     * */
	public static CommentBean commentListToParentBean(Map<Integer,CommentBean> m,int parent_id)
	{
		if(m.containsKey(parent_id))
		{
			CommentBean cb = m.get(parent_id);
			cb.setParent_comment(commentListToParentBean(m,cb.getParent_id()));
			return cb;
		}
		return null;		
	}
	
	/**
     * 取得后台评论总数
     * @param Map<String,String> m
     * @return String 
     * */
	public static String getCommentMainCount(Map<String,String> m,String info_type)
	{
		if("info".equals(info_type))
		{
			return CommentDAO.getCommentMainCountForInfo(m);
		}
		return "";
	}
	
	/**
     * 取得后台评论列表
     * @param Map<String,String> m
     * @return String 
     * */
	public static List<CommentBean> getCommentMainList(Map<String,String> m,String info_type)
	{
		if("info".equals(info_type))
		{
			return CommentDAO.getCommentMainListForInfo(m);
		}
		return null;
	}

	/**
     * 插入评论
     * @param CommentBean
     * @return boolean 
     * */
	public static boolean insertCommentMain(CommentBean comB)
	{
		CommentSet cs = CommentSetManager.getCommentSetBySiteIdAndAppID(comB.getSite_id(), comB.getApp_id());
		//首先判断是否需要审核
		setCommentBeanStatus(comB,cs.getIs_need());
		//判断是否有敏感信息
		setCommentBeanQuest(comB,cs.getTact_word());
		//插入父节点的ID字符串
		setCommentParentStr(comB);
		comB.setAdd_time(DateUtil.getCurrentDateTime());
		comB.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.COMMENT_MAIN_TABLE_NAME));
		if(CommentDAO.insertCommentMain(comB))
		{
			if(comB.getIs_status() == 1 && comB.getParent_id() > 0)
			{
				//如果已发布，且是对评论进行回复的话，对目标评论进行回复状态修改
				Map<String,String> m = new HashMap<String,String>();
				m.put("is_replay", "1");
				m.put("id", comB.getParent_id()+"");
				CommentDAO.updateCommentMain(m, null);
			}
			return true;
		}else
			return false;
	}
	
	/**
     * 修改评论内容
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean updateCommentMain(Map<String,String> m,SettingLogsBean stl)
	{
		return CommentDAO.updateCommentMain(m, stl);
	}
	
	/**
     * 通过评论状态
     * @param String ids
     * @param List<CommentList> l 审核通过时需要判断父评论，如果有，需要对is_replay进行付值
     * @param String is_status
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean updateCommentStatus(String ids,List<CommentBean> l,String is_status,SettingLogsBean stl)
	{		
		if("-1".equals(is_status))
		{//撤消操作
			Map<String,String> m = new HashMap<String,String>();
			m.put("ids", ids);
			m.put("is_status", is_status);
			m.put("content", delete_error);
			return CommentDAO.cancelCommentMain(m, stl);
		}else
		{
			return CommentDAO.passCommentMain(ids,l,is_status,stl);
		}
		
	}
	
	/**
     * 删除评论
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean 
     * */
	public static boolean deleteCommentMain(Map<String,String> m,SettingLogsBean stl)
	{
		m.put("content", delete_error);
		return CommentDAO.deleteCommentMain(m, stl);
	}
	
	//判断是否需要审核
	public static void setCommentBeanStatus(CommentBean comB,String is_need)
	{
		if("0".equals(is_need))
		{//不需要审核的话，评论状态设置成已审核
			comB.setIs_status(1);
		}
	}
	
	/**
     * 插入评论敏感词状态
     * @param CommentBean
     * @param String tact_word
     * @return  
     * */
	public static void setCommentBeanQuest(CommentBean comB,String tact_word)
	{
		if(tact_word != null && !"".equals(tact_word))
		{
			tact_word = tact_word.trim();
			String[] tempA = tact_word.split(",");
			if(tempA != null && tempA.length > 0)
			{
				for(int i=0;i<tempA.length;i++)
				{
					if(comB.getContent().contains(tempA[i]))
					{
						comB.setIs_quest(1);
						break;
					}
				}
			}
		}
	}
	
	public static void setCommentParentStr(CommentBean comB)
	{
		if(comB.getParent_id() != 0)
		{
			String parent_str = CommentDAO.getParentStr(comB.getParent_id()+"");
			if(parent_str != null && !"".equals(parent_str) && !"0".equals(parent_str))
			{
				parent_str += ","+comB.getParent_id();
			}else
				parent_str = comB.getParent_id()+"";
			
			comB.setParent_str(parent_str);
		}
	}
}
