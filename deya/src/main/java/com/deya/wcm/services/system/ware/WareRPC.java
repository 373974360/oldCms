package com.deya.wcm.services.system.ware;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.bean.system.ware.WareInfoBean;
import com.deya.wcm.bean.system.ware.WareInfos;
import com.deya.wcm.bean.system.ware.WareReleUser;
import com.deya.wcm.services.Log.LogManager;

public class WareRPC {
	
	/**************************信息标签分类处理*********************************/
	/**
	 * 根据登录用户得到它能管理的标签分类树节点
	 * @param int user_id
	 * @param String site_id
	 * @return String
	 */
	public static String getJSONTreeBySiteUser(int user_id,String site_id)
	{
		return WareCategoryManager.getJSONTreeBySiteUser(user_id,site_id);
	}
	
	/**
	 * 取得JsonTree字符串
	 * @return
	 */
	public static String getJSONTreeStr(Map<String, String> mp)
	{
		return WareCategoryManager.getJSONTreeStr(mp);
	}
	
	/**
	 * 通过ID，site_id，app_id取得信息标签分类列表
	 * @param id	信息标签ID
	 * @param mp	site_id，app_id
	 * @return	信息标签列表
	 */
	public static List<WareCategoryBean> getWareCateList(String id, Map<String, String> mp)
	{
		return WareCategoryManager.getWareCateList(id, mp);
	}
	
	/**
	 * 根据ID取得信息标签分类
	 * @param id 信息标签ID
	 * @return	信息标签分类对象
	 */
	public static WareCategoryBean getWareCategoryByID(String id)
	{
		return WareCategoryManager.getWareCategoryByID(id);
	}
	
	/**
	 * 插入信息标签分类信息
	 * @param wcb	信息标签分类信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean insertWareCate(WareCategoryBean wcb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareCategoryManager.insertWareCate(wcb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签分类信息
	 * @param wcb	信息标签分类信息
	 * @param request	
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareCate(WareCategoryBean wcb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareCategoryManager.updateWareCategory(wcb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存信息标签分类排序
	 * @param ids	信息标签分类IDS，多个直接用“,”分隔
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean saveSort(String ids,  HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareCategoryManager.saveSort(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除信息标签分类信息
	 * @param mp	删除条件
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWareCategory(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareCategoryManager.deleteWareCategory(mp, stl);
		}
		else
		{
			return false;
		}
	}
	/**************************信息标签分类处理结束*********************************/

	/*****************************信息标签处理**************************************/
	/**
	 * 根据站点ID到所有的手动标签列表
	 * @param site_id	信息标签ID
	 * @param ware_type	信息标签ID
	 * @return	List
	 */
	public static List<WareBean> getHandWareList(String site_id)
	{
		return WareManager.getWareListByType(site_id, 2);
	}
	
	/**
	 * 根据站点ID和分类ID得到所有的手动标签列表
	 * @param mp
	 * @return	List
	 */
	public static List<WareBean> getHandWareListByCateID(Map<String, String> mp)
	{
		return WareManager.getWareListByTypeAndCateID(mp, 2);
	}	
	
	/**
     * 批量生成静态页面　
     * @param List
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean createHtmlPageMutil(String ware_ids,String site_id)
	{
		return WareManager.createHtmlPageMutil(ware_ids,site_id);
	}
	
	/**
     * 生面成态页面　
     * @param String ware_id
     * @param String site_id
     * @return boolean
     * */
	public static boolean createHtmlPage(String ware_id,String site_id)
	{
		return WareManager.createWarePage(ware_id, site_id);
	}
	/**
	 * 根据ware_id，site_id 得到组件对象
	 * @param String ware_id
	 * @param String site_id
	 * @return	信息标签
	 */
	public static WareBean getWareBeanByWareID(String ware_id,String site_id)
	{
		return WareManager.getWareBeanByWareID(ware_id, site_id);
	}
	
	/**
	 * 通过ID取得标签信息
	 * @param id	标签信息ID
	 * @return	标签信息
	 */
	public static WareBean getWareByID(String id)
	{
		return WareManager.getWareByID(id);
	}
	
	/**
	 * 根据标签信息分类ID取得标签信息列表
	 * @param wcat_id	标签信息分类ID
	 * @param mp
	 * @return	标签信息列表
	 */
	public static List<WareBean> getWareList(String wcat_id, Map<String, String> mp)
	{
		return WareManager.getWareList(wcat_id, mp);
	}
	
	/**
	 * 根据标签信息分类ID取得标签信息列表
	 * @param wcat_id	标签信息分类ID
	 * @param mp
	 * @return	标签信息列表
	 */
	public static List<WareBean> getAllWareList(Map<String, String> mp)
	{
		return WareManager.getWareListOfSite(mp);
	}
	
	/**
	 * 插入信息标签信息
	 * @param wb	信息标签对象
	 * @param request
	 * @return	true 成功| false 失败
	 * @throws IOException 
	 */
	public static boolean insertWare(WareBean wb, HttpServletRequest request) throws IOException
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.insertWare(wb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签
	 * @param wb	信息标签对象
	 * @param request
	 * @return	true 成功| false 失败
	 * @throws IOException 
	 */
	public static boolean updateWare(WareBean wb, HttpServletRequest request) throws IOException
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.updateWare(wb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签内容
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareContent(WareBean wb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.updateWareContent(wb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存信息标签排序
	 * @param ids	信息标签IDS，多个之间使用","分隔
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean savaWareSort(String ids, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.savaWareSort(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除标签信息
	 * @param mp
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWare(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.deleteWare(mp, stl);
		}
		else
		{
			return false;
		}
	}
	
	public static boolean moveWareToOtherCategory(Map<String, String> mp,  HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareManager.moveWareToOtherCategory(mp, stl);
		}
		else
		{
			return false;
		}
	}
	
	/******************* 分类与人员关联 开始 ********************************/
	/**
	 * 根据标签分类ID得到关联的用户和用户组列表
	 * 
	 * @param int wcat_id 
	 * @return list
	 */	
	public static List<WareReleUser> getWareReleUserListByCat(int wcat_id,String site_id)
	{
		return WareReleUserManager.getWareReleUserListByCat(wcat_id,site_id);
	}
	
	/**
	 * 插入标签分类与人员的关联(以分类为主)
	 * 
	 * @param int wcat_id
	 * @param String usre_ids
	 * @param String group_ids
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */	
	public static boolean insertWareReleUserByCat(int wcat_id,String usre_ids,String group_ids,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareReleUserManager.insertWareReleUserByCat(wcat_id,usre_ids,group_ids,app_id,site_id,stl);
		}
		else
		{
			return false;
		}
	}
	
	/******************* 分类与人员关联 结束 ********************************/
	
	/******************* 信息推荐处理　开始 ********************************/
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息总数
	 * @param Map m
	 * @return	List
	 */
	public static String getWareInfoRefCount(Map<String,String> m)
	{
		return WareInfoManager.getWareInfoRefCount(m);
	}
	
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息
	 * @param Map m
	 * @return	List
	 */	
	public static List<InfoBean> getWareInfoRefList(Map<String,String> m)
	{
		return WareInfoManager.getWareInfoRefList(m);
	}
	
	/**
	 * 根据信息ID，站点，应用ID得到标签列表
	 * @param String info_id
	 * @param String app_id
	 * @param String site_id
	 * @return	List
	 */	
	public static List<WareBean> getWareListByRefInfo(String info_id,String app_id,String site_id)
	{
		return WareInfoManager.getWareListByRefInfo(info_id, app_id, site_id);
	}
	
	/**
	 * 添加推荐信息
	 * @param String info_ids
	 * @param int ware_id
	 * @param int user_id
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfoRef(String info_ids,int ware_id,int user_id,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.insertWareInfoRef(info_ids, ware_id, user_id, app_id, site_id, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除推荐信息
	 * @param Map m
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoRef(String info_ids,String ware_ids,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.deleteWareInfoRef(info_ids, ware_ids, app_id, site_id, stl);
		}
		else
		{
			return false;
		}
	}		
	/******************* 信息推荐处理　结束 ********************************/
	
	/*********************** 手动标签信息处理　 开始 ********************************/
	/**
	 * 添加手动标签行数(用于在维护标签内容时添加)
	 * @param int ware_id
	 * @param int sort_id
	 * @param String app_id
	 * @param String site_id
	 * @return	int
	 */
	public static int insertWareInfo(int ware_id,int sort_id,int info_num,String app_id,String site_id)
	{
		return WareInfoManager.insertWareInfo(ware_id, sort_id,info_num, app_id, site_id);
	}
	
	/**
	 * 保存标签行排序
	 * @param String winfo_ids
	 * @return	boolean
	 */
	public static boolean sortWareInfo(String winfo_ids)
	{
		return WareInfoManager.sortWareInfo(winfo_ids);
	}
	
	/**
	 * 根据行号ID删除标签关联
	 *  @param int ware_id
	 * @param String winfo_id
	 * @param int info_num 目前总行数
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoByWInfoID(int ware_id,String winfo_id,int info_num,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.deleteWareInfoByWInfoID(ware_id,winfo_id,info_num,app_id,site_id, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据标签ID得到手动标签列表
	 * @param Map<String,String> m
	 * @return	boolean
	 */	
	public static List<WareInfoBean> getWareInfoList(Map<String,String> m)
	{
		return WareInfoManager.getWareInfoList(m);
	}
	
	/**
	 * 根据信息ID得到标签关联的信息内容
	 * @param int id
	 * @return	WareInfos
	 */
	public static WareInfos getWareInfosBean(int id)
	{
		return WareInfoManager.getWareInfosBean(id);
	}
	
	/**
	 * 得到手动标签行ID	
	 * @return	int
	 */
	public static int getNewWareInfosID()
	{
		return WareInfoManager.getNewWareInfosID();
	}
	
	/**
	 * 插入标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfos(WareInfos wif,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.insertWareInfos(wif, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean updateWareInfos(WareInfos wif,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.updateWareInfos(wif, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存标签信息排序
	 * @param String ids
	 * @return	boolean
	 */
	public static boolean sortWInfos(String ids)
	{
		return WareInfoManager.sortWInfos(ids);
	}
	
	/**
	 * 根据ID删除标签关联信息
	 * @param int id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfosByID(int id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WareInfoManager.deleteWareInfosByID(id, stl);
		}
		else
		{
			return false;
		}
	}
	/*********************** 手动标签信息处理　 结束 ********************************/
	
	public static void main(String []arg)
	{
		Map<String,String> m = new HashMap<String,String>();		
		m.put("app_id", "cms");
		m.put("site_id", "CMScattt");		
		System.out.println(getWareList("1",m));
	}
	
}
