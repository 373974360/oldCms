package com.deya.wcm.services.interview;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.interview.SubReleInfo;
import com.deya.wcm.bean.interview.SubjectActor;
import com.deya.wcm.bean.interview.SubjectBean;
import com.deya.wcm.bean.interview.SubjectCategory;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * 访谈主题管理前台访问类.
 * <p>Title: CicroDate</p>
 * <p>Description: 为访谈主题管理前台访问提供接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectRPC {
	/**
     * 得到访谈列表(前台使用)
     * @param String con
     * @param int start_num
     * @param int page_size
     * @return List
     * 
	@SuppressWarnings("unchecked")
	public static List getSubjectBrowserList(String con,int start_num,int page_size)
	{
		return SubjectServices.getSubjectBrowserList(con,start_num,page_size);
	}*/
	
	/**
     * 得到推荐列表(前台使用)
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectRecommendList(String con,int start_num,int page_size)
	{
		return SubjectServices.getSubjectRecommendList(con,start_num,page_size);
	}
	
	/************************* 访谈主题管理　开始 *********************************************/
	/**
     * 设置推荐状态
     * @param String ids
     * @return boolean
     * */
	public static boolean updateSubjectRecommend(String ids,String recommend_flag)
	{
		return SubjectServices.updateSubjectRecommend(ids,recommend_flag);
	}
	
	/**
     * 得到主题总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubjectCount(String con,String login_user_name)
	{
		return SubjectServices.getSubjectCount(con,login_user_name);
	}
	
	/**
     * 得到管理主题列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubjectManagerCount(String con,String site_id)
	{
		return SubjectServices.getSubjectManagerCount(con,site_id);
	}
	
	/**
     * 得到主题对象信息
     * @param int　id 查询条件
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getSubjectObj(int id)
	{
		return SubjectServices.getSubjectObj(id);
	}
	
	/**
     * 得到主题对象信息
     * @param String sub_id 查询条件
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getSubjectObjBySubID(String sub_id)
	{
		return SubjectServices.getSubjectObjBySubID(sub_id);
	}
	
	/**
     * 插入主题
     * @param SubjectBean sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean insertSubject(SubjectBean sub,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectServices.insertSubject(sub,stl);
		}else
			return false;	
	}
	/**
     * 修改主题
     * @param SubjectBean sc　主题对象
     * @return boolean　true or false
     * */
	public static boolean updateSubject(SubjectBean sub,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);	
		if(stl != null)
		{
			return SubjectServices.updateSubject(sub,stl);
		}else
			return false;
	}
	
	/**
     * 删除主题
     * @param String ids 要删除的ID
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean deleteSubject(String ids,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		
		if(stl != null)
		{
			return SubjectServices.deleteSubject(ids, user_name,stl);
		}else
			return false;
	}
	
	/**
     * 得到主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectList(String con,int start_num,int page_size,String login_user_name)
	{		
		return SubjectServices.getSubjectList(con,start_num,page_size,login_user_name);
	}
	
	/**
     * 得到主题列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubjectManagerList(String con,int start_num,int page_size,String site_id)
	{
		return SubjectServices.getSubjectManagerList(con, start_num, page_size,site_id);
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
	public static boolean updateSubjectStatus(String ids,String filds,String status_flag,String oper_name,String oper_message,String user_name,String user_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectServices.updateSubjectStatus(ids, filds, status_flag, oper_name, oper_message, user_name, user_id,stl);
		}else
			return false;
	}
	
	/**
     * 提交主题
     * @param String ids　要发布的信息ID
     * @param int status_flag　要发布的状态　1为发布，0为撤消
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean subjectSubmit(String ids,int status_flag,String user_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectServices.subjectSubmit(ids, status_flag, user_id,stl);
		}else
			return false;
	}
	
	/**
     * 得到历史记录
     * @param String sub_id
     * @return SubjectBean　题分类对象
     * */
	public static SubjectBean getHistoryRecord(String sub_id)
	{
		return SubjectServices.getHistoryRecord(sub_id);
	}
	

	/**
     *　维护访谈历史记录   
     * @param SubjectBean sb
     * @return boolean
     * */
	public static boolean updateHistoryRecord(SubjectBean sb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectServices.updateHistoryRecord(sb,stl);
		}else
			return false;
	}
	
	/************************* 访谈主题管理　结束 *********************************************/
	
	/****************** 附件操作 开始************************/
	/**
     * 得到附件列表
     * @param Map m　组织好的数据
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseList(String sub_id,String affix_status,String affix_type)
	{
		return SubjectServices.getResouseList(sub_id, affix_status, affix_type);
	}
	/**
     * 得到附件列表 用于修改页面和查看页面取出预告图片，预告视频，直播视频，历史视频项
     * @param String sub_id
     * @return boolean　true or false
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseListByManager(String sub_id)
	{
		return SubjectServices.getResouseListByManager(sub_id);
	}
	/****************** 附件操作　结束************************/
	
	
	/************************* 访谈分类管理　开始 *********************************************/
	/**
     * 根据分类ID得到该分类下的主题个数,用于删除分类时判断下面是否有主题记录
     * @param int id
     * @return int　条数
     * */
	public static String getSubjectCountByCategoryID(int id)
	{
		return SubjectCategoryServices.getSubjectCountByCategoryID(id);
	}
	
	/**
     * 得到主题分类总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubCategoryCount(String con,String site_id){
		return SubjectCategoryServices.getSubCategoryCount(con,site_id);
	} 
	
	/**
     * 得到所有主题分类信息　id,category_id,name
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubCategoryAllName(String site_id)
	{				
		return SubjectCategoryServices.getSubCategoryAllName(site_id);
	}
	
	/**
     * 得到主题分类对象信息
     * @param int　id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBeanByID(int id)
	{
		return SubjectCategoryServices.getSubjectCategoryBean(id);
	}
	
	/**
     * 得到主题分类对象信息
     * @param String　category_id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBeanByCID(String category_id)
	{
		return SubjectCategoryServices.getSubjectCategoryBean(category_id);
	}
	
	/**
     * 得到主题分类对象信息
     * @param String id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBean(String category_id)
	{
		return SubjectCategoryServices.getSubjectCategoryBean(category_id);
	}
	
	/**
     * 得到主题分类列表
     * @param String　con 查询条件
     * @param int　start_num 起始条数
     * @param int　page_size 每页显示条数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public List getSubCategoryList(String con,int start_num,int page_size,String site_id){	
		return SubjectCategoryServices.getSubCategoryList(con,start_num,page_size,site_id);
	}
	/**
     * 插入主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean insertSubCategory(SubjectCategory sc,HttpServletRequest request)
	{				
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectCategoryServices.insertSubCategory(sc,stl);
		}else
			return false;
	}	
	
	/**
     * 修改主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean updateSubCategory(SubjectCategory sc,HttpServletRequest request)
	{				
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectCategoryServices.updateSubCategory(sc,stl);
		}else
			return false;
	}
	/**
     * 删除主题分类
     * @param String ids 要删除的ID
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean deleteSubCategory(String ids,String user_name,HttpServletRequest request)
	{				
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectCategoryServices.deleteSubCategory(ids,user_name,stl);
		}else
			return false;
	}
	/**
     * 修改主题分类发布状态
     * @param String ids　要发布的信息ID
     * @param int status_flag　要发布的状态　1为发布，0为撤消
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean updateSubCategoryState(String ids,int status_flag,String user_name,HttpServletRequest request)
	{	
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectCategoryServices.updateSubCategoryState(ids, status_flag,user_name,stl);
		}else
			return false;
	}
	
	/**
     * 保存分类排序
     * @param String ids　要排序的信息ID
     * @return boolean　true or false
     * */
	public static boolean saveSubCategorySort(String ids,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectCategoryServices.saveSubCategorySort(ids,stl);
		}else
			return false;	
	}
	/************************* 访谈分类管理　结束 *********************************************/
	
	
	/****************** 访谈参与者管理　开始************************/
	/**
     * 得到所有参与者的名称
     * @param String sub_id
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getAllActorName(String sub_id)
	{
		return SubjectActorServices.getAllActorName(sub_id);
	}
	
	/**
     * 得到参与者列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getActorList(String con,String sub_id)
	{
		return SubjectActorServices.getActorList(con, sub_id);
	}
	
	/**
     * 得到参与者对象
     * @param int id 参与者对象id
     * @return List　列表
     * */
	public static SubjectActor getSubActor(int id)
	{
		return SubjectActorServices.getSubActor(id);
	}
	
	/**
     * 添加参与者
     * @param SubjectActor sa 参与者对象
     * @return List　列表
     * */
	public static boolean insertSubActor(SubjectActor sa,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectActorServices.insertSubActor(sa,stl);		
		}else
			return false;
	}
	
	/**
     * 修改参与者
     * @param SubjectActor sa 参与者对象
     * @return List　列表
     * */
	public static boolean updateSubActor(SubjectActor sa,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectActorServices.updateSubActor(sa,stl);
		}else
			return false;
	}
	
	/**
     * 删除参与者
     * @param Map m
     * @return List　列表
     * */
	public static boolean deleteSubActor(String ids,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectActorServices.deleteSubActor(ids, user_name,stl);
		}else
			return false;
	}
	
	/**
     * 保存参与者排序
     * @param String ids
     * @return List　列表
     * */
	public static boolean saveSubActorSort(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubjectActorServices.saveSubActorSort(ids,stl);
		}else
			return false;
	}
	/************************* 访谈参与者管理　结束 *********************************************/
	
/************************* 访谈相关信息管理　开始 *********************************************/
	
	
	public static String getReleInfoCountBySub_id(String sub_id)
	{	
		return SubReleInfoServices.getReleInfoCountBySub_id(sub_id);
	}
	
	/**
     * 得到主题相关信息列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getReleInfoListBySub_id(String con,int start_num,int page_size,String sub_id)
	{	
		return SubReleInfoServices.getReleInfoListBySub_id(con, start_num, page_size,sub_id);
	}
	
	public static SubReleInfo getSubReleInfo(int id)
	{
		return SubReleInfoServices.getSubReleInfo(id);
	}
	
	public static  boolean insertReleInfo(SubReleInfo sri,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubReleInfoServices.insertReleInfo(sri,stl);
		}else
			return false;
	}
	
	public static  boolean updateReleInfo(SubReleInfo sri,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubReleInfoServices.updateReleInfo(sri,stl);
		}else
			return false;
	}
	
	public static boolean deleteReleInfo(String ids,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubReleInfoServices.deleteReleInfo(ids, user_name,stl);
		}else
			return false;
	}
	
	public static boolean publishReleInfo(String ids,String user_name,int publish_flag,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubReleInfoServices.publishReleInfo(ids, user_name, publish_flag,stl);
		}else
			return false;
	}
	
	/**
     * 保存相关信息排序
     * @param String ids
     * @return List　列表
     * */
	public static boolean saveReleInfoSort(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SubReleInfoServices.saveReleInfoSort(ids,stl);
		}else
			return false;
	}
	
	
	/************************* 访谈相关信息管理　结束 *********************************************/
	
	
	/************************* 访谈统计　结束 *********************************************/
	@SuppressWarnings("unchecked")
	public static Map getSubjectCategoryCount(String start_time,String end_time,String time_type,String site_id)
	{
		return SubjectCountServices.getSubjectCategoryCount(start_time,end_time,time_type,site_id);
	}
	/**
     * 得到分类统计数据
     * @param String start_time 开始时间
     * @param String end_time 结束时间
     * @param String category_ids 栏目ID
     * @param String order_type 排序类型
     * @param String count_num 显示条数
     * @return Map
     * */
	@SuppressWarnings("unchecked")
	public static Map getHotCount(String start_time,String end_time,String category_ids,String order_type,int count_num,String time_type)
	{
		return SubjectCountServices.getHotCount(start_time, end_time, category_ids, order_type, count_num,time_type);
	}
	/************************* 访谈统计　结束 *********************************************/
	
	public static void main(String args[])
	{
		
		//System.out.println("互动界面2_2.gif".replaceAll(".*\\.(.*?)","$1"));
	}
}
