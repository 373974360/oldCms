package com.deya.wcm.services.interview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.interview.SubjectBean;
import com.deya.wcm.bean.interview.SubjectResouse;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.interview.SubjectDAO;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.util.DateUtil;
/**
 * 访谈主题逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题的逻辑处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectServices implements ISyncCatch{
	private static Map<String,SubjectBean> sub_map = new HashMap<String,SubjectBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		sub_map.clear();
	}
	
	public static void clearMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.interview.SubjectServices");
	}
	
	/**
     * 得到推荐列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSubjectRecommendListCount(String con,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("con", con);
		m.put("site_id", site_id);
		return SubjectDAO.getSubjectRecommendListCount(m);
	}
	
	/**
     * 得到访谈列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSubjectBrowserListHandlCount(String con)
	{
		Map<String,String> m = new HashMap<String,String>();				
		m.put("con", con);		
		return SubjectDAO.getSubjectBrowserListHandlCount(m);
	}
		
	/**
     * 得到调查列表(前台使用)
     * @param String con
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SubjectBean> getSubjectBrowserList(String con,int start_num,int page_size,String order_by)
	{
		List<SubjectBean> sl = new ArrayList<SubjectBean>();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数			
		m.put("con", con);
		m.put("order_by", order_by);		
		sl = SubjectDAO.getSubjectBrowserList(m);
		if(sl != null && sl.size() > 0)
		{
			for(int i=0;i<sl.size();i++)
			{
				sl.get(i).setActor_name(SubjectActorServices.getAllActorNames(sl.get(i).getSub_id()).replaceAll(",", " "));
				List<SubjectResouse> r = getResouseList(sl.get(i).getSub_id(),"forecast","pic");
				if(r != null && r.size() > 0)
					sl.get(i).setS_for_pic(r.get(0).getAffix_path());
			}
		}
		return sl;
	}
	
	/**
     * 得到推荐列表(前台使用)
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectRecommendList(String con,int start_num,int page_size)
	{
		List<SubjectBean> sl = new ArrayList<SubjectBean>();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);	
		sl = SubjectDAO.getSubjectRecommendList(m);
		if(sl != null && sl.size() > 0)
		{
			for(int i=0;i<sl.size();i++)
			{
				sl.get(i).setActor_name(SubjectActorServices.getAllActorNames(sl.get(i).getSub_id()).replaceAll(",", " "));
				List<SubjectResouse> r = getResouseList(sl.get(i).getSub_id(),"forecast","pic");
				if(r != null && r.size() > 0)
					sl.get(i).setS_for_pic(r.get(0).getAffix_path());
			}
		}
		return sl;
	}
	
	/**
     * 设置推荐状态
     * @param String ids
     * @param String recommend_flag
     * @return boolean
     * */
	public static boolean updateSubjectRecommend(String ids,String recommend_flag)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("recommend_flag", recommend_flag);
		if("1".equals(recommend_flag))
			m.put("current_time", DateUtil.getCurrentDateTime());
		else
			m.put("current_time", "");
		return SubjectDAO.updateSubjectRecommend(m);
	}
	
	/**
     * 得到主题总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubjectCount(String con,String login_user_name)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("login_user_name", login_user_name);
		m.put("con", con);
		return SubjectDAO.getSubjectCount(m);
	}
	/**
     * 得到管理主题列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubjectManagerCount(String con,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("con", con);
		m.put("site_id", site_id);
		return SubjectDAO.getSubjectManagerCount(m);
	}
	/**
     * 得到主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectList(String con,int start_num,int page_size,String login_user_name)
	{
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("login_user_name", login_user_name);
		m.put("con", con);
		return SubjectDAO.getSubjectList(m);
	}
	
	/**
     * 得到主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectManagerList(String con,int start_num,int page_size,String site_id)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		m.put("site_id", site_id);
		List<SubjectBean> l = SubjectDAO.getSubjectManagerList(m);
		if(l != null && l.size() >0)
		{
			for(int i=0;i<l.size();i++)
			{
				String apply_dept = l.get(i).getApply_dept();
				if(apply_dept != null && !"".equals(apply_dept))
				{
					DeptBean db = DeptManager.getDeptBeanByID(apply_dept);
					if(db != null)
						l.get(i).setApply_dept(db.getDept_fullname());
				}
				String apply_user = l.get(i).getApply_user();
				if(apply_user != null && !"".equals(apply_user))
				{
					UserBean ub = UserManager.getUserBeanByID(apply_user);
					if(ub != null)
						l.get(i).setApply_user(ub.getUser_realname());
				}
			}
		}
		return l;
	}
	
	/**
     * 得到历史记录
     * @param String sub_id
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getHistoryRecord(String sub_id)
	{
		return SubjectDAO.getHistoryRecord(sub_id);
	}
	
	/**
     *　维护访谈历史记录   
     * @param SubjectBean sb
     * @return boolean
     * */
	public static boolean updateHistoryRecord(SubjectBean sb,SettingLogsBean stl)
	{
		sb.setUpdate_time(DateUtil.getCurrentDateTime());
		if(SubjectDAO.updateHistoryRecord(sb,stl))
		{
			clearMap();
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
		return SubjectDAO.getSubjectObj(id);
	}
	
	/**
     * 得到主题对象信息
     * @param String sub_id 查询条件
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getSubjectObjBySubID(String sub_id)
	{
		if(sub_map.containsKey(sub_id))
			return sub_map.get(sub_id);
		else
		{
			SubjectBean sb = SubjectDAO.getSubjectObjBySubID(sub_id);
			if(sb != null)
			{
				sub_map.put(sub_id, sb);
				return sb;
			}else
				return null;
		} 
	}
	
	/**
     * 插入主题
     * @param SubjectBean sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean insertSubject(SubjectBean sub,SettingLogsBean stl)
	{
		String current_time = DateUtil.getCurrentDateTime();
		String sub_id = UUID.randomUUID().toString();
		sub.setSub_id(sub_id);
		sub.setApply_time(current_time);
		
		if(sub.getPublish_status() == 1)
			sub.setPublish_time(current_time);
		
		if(sub.getAudit_status() == 1)
		{
			sub.setAudit_user(sub.getApply_user());
			sub.setAudit_time(current_time);
		}
		sub_map.clear();
		if(SubjectDAO.insertSubject(sub,stl))
		{
			insertResouseBySubject(sub,current_time,stl);
			return true;
		}else{
			return false;
		}
	}
	
	public static void insertResouseBySubject(SubjectBean sub,String current_time,SettingLogsBean stl)
	{
		if(sub.getS_for_video() != null && !"".equals(sub.getS_for_video().trim()))
		{
			SubjectResouse sr = new SubjectResouse();
			sr.setSub_id(sub.getSub_id());
			sr.setAffix_type("video");
			sr.setAffix_path(sub.getS_for_video());
			sr.setAffix_name("");
			sr.setDescription("");
			sr.setAffix_status("forecast");
			sr.setAdd_time(current_time);
			sr.setAdd_user(sub.getApply_user());
			SubjectDAO.insertResouse(sr,stl);
		}
		if(sub.getS_for_pic() != null && !"".equals(sub.getS_for_pic().trim()))
		{
			SubjectResouse sr = new SubjectResouse();
			sr.setSub_id(sub.getSub_id());
			sr.setAffix_type("pic");
			sr.setAffix_path(sub.getS_for_pic());
			sr.setAffix_name("");
			sr.setDescription("");
			sr.setAffix_status("forecast");
			sr.setAdd_time(current_time);
			sr.setAdd_user(sub.getApply_user());
			SubjectDAO.insertResouse(sr,stl);
		}
		if(sub.getS_live_video() != null && !"".equals(sub.getS_live_video().trim()))
		{
			SubjectResouse sr = new SubjectResouse();
			sr.setSub_id(sub.getSub_id());
			sr.setAffix_type("video");
			sr.setAffix_path(sub.getS_live_video());
			sr.setAffix_name("");
			sr.setDescription("");
			sr.setAffix_status("live");
			sr.setAdd_time(current_time);
			sr.setAdd_user(sub.getApply_user());
			SubjectDAO.insertResouse(sr,stl);
		}
		if(sub.getS_history_video() != null && !"".equals(sub.getS_history_video().trim()))
		{
			SubjectResouse sr = new SubjectResouse();
			sr.setSub_id(sub.getSub_id());
			sr.setAffix_type("video");
			sr.setAffix_path(sub.getS_history_video());
			sr.setAffix_name("");
			sr.setDescription("");
			sr.setAffix_status("history");
			sr.setAdd_time(current_time);
			sr.setAdd_user(sub.getApply_user());
			SubjectDAO.insertResouse(sr,stl);
		}
	}
	
	/**
     * 修改主题
     * @param SubjectBean sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean updateSubject(SubjectBean sub,SettingLogsBean stl)
	{		
		sub_map.clear();
		sub.setUpdate_time(DateUtil.getCurrentDateTime());
		if(SubjectDAO.updateSubject(sub,stl))
		{
			SubjectDAO.deleteResouseBySub(sub.getSub_id());
			insertResouseBySubject(sub,DateUtil.getCurrentDateTime(),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除主题
     * @param String ids 要删除的ID
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean deleteSubject(String ids,String user_name,SettingLogsBean stl)
	{
		sub_map.clear();
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		return SubjectDAO.deleteSubject(m,stl);
	}
	
	/**
     * 修改主题状态，发布状态，访谈状态，审核状态
     * @param String ids 　要修改的ID
     * @param String filds　要修改的字段名称
     * @param String status_flag　要修改的状态值
     * @param String oper_name　要修改的操作名称
     * @param String oper_message　要修改的操作描述
     * @param String user_name　修改人用户名
     * @param String user_id　修改人用户ID
     * @return boolean　true or false
     * */
	public static boolean updateSubjectStatus(String ids,String filds,String status_flag,String oper_name,String oper_message,String user_name,String user_id,SettingLogsBean stl)
	{
		sub_map.clear();
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("filds", filds);
		m.put("status_flag", status_flag);
		m.put("oper_name", oper_name);
		m.put("oper_message", oper_message);
		m.put("user_name", user_name);
		m.put("user_id", user_id);
		m.put("current_time", DateUtil.getCurrentDateTime());
		if(SubjectDAO.updateSubjectStatus(m,stl))
		{
			//如果将访谈状态设置成　直播状态，从库中加载出此主题的相关信息
			if("status".equals(filds) && "1".equals(status_flag))
			{
				ChatRoomServices.reloadChat(ids);
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
     * 提交主题
     * @param String ids　要发布的信息ID
     * @param int status_flag　要发布的状态　1为发布，0为撤消
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean subjectSubmit(String ids,int status_flag,String user_id,SettingLogsBean stl)
	{
		sub_map.clear();
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("status", ""+status_flag);
		m.put("user_name", user_id);		
		m.put("current_time", DateUtil.getCurrentDateTime());
		return SubjectDAO.subjectSubmit(m,stl);
	}
	
	/****************** 附件操作 开始************************/
	/**
     * 得到附件列表
     * @param Map m　组织好的数据
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseList(String sub_id,String affix_status,String affix_type)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		m.put("affix_status", ""+affix_status);
		m.put("affix_type", affix_type);
		return SubjectDAO.getResouseList(m);
	}
	
	/**
     * 得到附件列表 用于修改页面和查看页面取出预告图片，预告视频，直播视频，历史视频项
     * @param String sub_id
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseListByManager(String sub_id)
	{
		return SubjectDAO.getResouseListByManager(sub_id);
	}
	/****************** 附件操作 结束************************/
}
