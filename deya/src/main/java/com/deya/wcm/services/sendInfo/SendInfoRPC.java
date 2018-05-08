package com.deya.wcm.services.sendInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.ReceiveCatConf;
import com.deya.wcm.bean.sendInfo.ReceiveConfigBean;
import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.bean.sendInfo.SendConfigBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.bean.sendInfo.SendRecordCount;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.webServices.sendInfo.SendClient;

public class SendInfoRPC {
	/***************接收站点配置　开始****************************/
	/**
     * 得到该服务器下的接收站树
     * @param 
     * @return String
     * */
	public static String getRecieveSiteJSONTree(String type)
	{
		return ReceiveConfigManager.getRecieveSiteJSONTree(type);
	}
	
	/**
     * 得到接收站点设置列表
     * @param 
     * @return List
     * */
	public static List<ReceiveConfigBean> getReceiveConfigList()
	{
		return ReceiveConfigManager.getReceiveConfigList();
	}
	
	/**
     * 根据站点ＩＤ得到配置对象,抱括所选栏目
     * @param String site_id
     * @return ReceiveConfigBean
     * */
	public static Map<String,Object> getReceiveConfigForSiteID(String site_id)
	{
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("rcfBean", ReceiveConfigManager.getReceiveConfigForSiteID(site_id));
		m.put("catList", ReceiveConfigManager.getReceiveCatConfList(site_id));
		return m;
	}
	
	/**
     * 插入接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertReceiveConfig(ReceiveConfigBean rcf,List<ReceiveCatConf> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
			return ReceiveConfigManager.insertReceiveConfig(rcf, l, stl);
		else
			return false;
	}
	
	/**
     * 修改接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateReceiveConfig(ReceiveConfigBean rcf,List<ReceiveCatConf> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
			return ReceiveConfigManager.updateReceiveConfig(rcf, l, stl);
		else
			return false;
	}
	
	/**
     * 修改接收站点设置状态，停用，启用
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateReceiveConfigStatus(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
			return ReceiveConfigManager.updateReceiveConfigStatus(m, stl);
		else
			return false;
	}
	
	/**
     * 删除接收站点设置
     * @param String site_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteReceiveConfig(String site_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
			return ReceiveConfigManager.deleteReceiveConfig(site_ids, stl);
		else
			return false;
	}
	/***************接收站点配置　结束****************************/
	
	/***************报送站点配置　开始****************************/
	/**
     * 根据url获取到该站群服务器上允许报送的站点列表
     * @param String site_domain
     * @return String json
     * */
	public static String getReceiveSiteList(String site_domain)
	{
		try{
			return SendClient.getServicesObj(site_domain).getReceiveConfigForJSON();
		}catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public static List<SendConfigBean> getSendConfigList()
	{
		return SendConfigManager.getSendConfigList();
	}
	
	public static SendConfigBean getSendConfigBean(String site_id)
	{
		return SendConfigManager.getSendConfigBean(site_id); 
	}
	
	public static boolean insertSendConfig(List<SendConfigBean> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SendConfigManager.insertSendConfig(l, stl);
		}else
			return false;
	}
	
	/**
     * 删除接收站点设置
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSendConfig(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SendConfigManager.deleteSendConfig(m, stl);
		}else
			return false;
	}
	
	/**
     * 得到该服务器下的报送过信息的站点树
     * @param 
     * @return String
     * */
	public static String getSendSiteJSONTree(String type)
	{
		return SendConfigManager.getSendSiteJSONTree(type);
	}
	/***************报送站点配置　结束****************************/
	/***************信息报送　开始****************************/
	/**
     * 根据url获取到该站点允许报送的栏目
     * @param String site_domain
     * @return String json
     * */
	public static String getReceiveCategoryList(String site_domain)
	{
		return SendInfoManager.getReceiveCategoryList(site_domain);
	}
	
	/**
     * 报送信息处理
     * @param String List<SendRecordBean>
     * @return String
     * */
	public static String insertSendInfo(List<SendRecordBean> sendRecordList,List<InfoBean> infoList,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SendInfoManager.insertSendInfo(sendRecordList,infoList,stl);
		}else
			return "false";
	}
	/***************信息报送　结束****************************/
	
	/***************接收信息 开始****************************/
	/**
     * 得到报送到该站点的信息总数
     * @param 
     * @return String
     * */
	public static String getReceiveInfoCount(Map<String,String> m)
	{
		return ReceiveInfoManager.getReceiveInfoCount(m);
	}
	
	/**
     * 得到报送到该站点的信息列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ReceiveInfoBean> getReceiveInfoList(Map<String,String> m)
	{
		return ReceiveInfoManager.getReceiveInfoList(m);
	}
	
	/**
     * 采用报送信息操作
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean adoptReceiveInfo(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ReceiveInfoManager.adoptReceiveInfo(m, stl);
		}else
			return false;
	}
	
	/**
     * 删除报送信息
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean deleteReceiveInfo(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ReceiveInfoManager.deleteReceiveInfo(m, stl);
		}else
			return false;
	}
	
	/**
     * 得到接收的栏目列表
     * @param String site_id
     * @return List
     * */
	public static List<ReceiveInfoBean> getReceiveCateInfoList(String site_id)
	{
		return ReceiveInfoManager.getReceiveCateInfoList(site_id);
	}
	/***************接收信息 结束****************************/
	
	/***************报送记录 开始****************************/
	/**
     * 得到报送记录总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSendRecordCount(Map<String,String> m)
	{
		return SendRecordManager.getSendRecordCount(m);
	}
	
	/**
     * 得到报送记录列表
     * @param Map<String,String> m
     * @return String
     * */
	public static List<SendRecordBean> getSendRecordList(Map<String,String> m)
	{
		return SendRecordManager.getSendRecordList(m);
	}
	
	/**
     * 删除报送记录
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static boolean deleteSendRecord(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SendRecordManager.deleteSendRecord(m, stl);
		}else
			return false;
	}
	/***************报送记录 结束****************************/
	
	/***************报送记录统计 开始****************************/
	/**
     * 按站点得到人员的报送信息工作量统计
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static List<SendRecordCount> getSendRecordUserCount(Map<String,String> m)
	{
		return SendCountManager.getSendRecordUserCount(m);
	}
	
	/**
     * 按站点得到目录的报送信息量统计
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static List<SendRecordCount> getSendCateListForRecord(Map<String,String> m)
	{
		return SendCountManager.getSendCateListForRecord(m);
	}
	
	/**
     * 根据条件得到报送过的站点列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordBean> getReceiveSiteListForRecord(String site_id)
	{
		return SendCountManager.getReceiveSiteListForRecord(site_id);
	}
	/***************报送记录统计 结束****************************/
	
	/***************接收信息统计 开始****************************/
	/**
     * 根站点得到所有报送到此站点的列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ReceiveInfoBean> getSendSiteList(String site_id)
	{
		return ReceiveCountManager.getSendSiteList(site_id);
	}
	
	/**
     * 得到报送站点信息量统计列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordCount> getSendSiteCountForReceive(Map<String,String> m)
	{
		return ReceiveCountManager.getSendSiteCountForReceive(m);
	}
	
	/**
     * 根据条件得到栏目接收统计
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordCount> getReceiveCateListForRecord(Map<String,String> m)
	{
		return ReceiveCountManager.getReceiveCateListForRecord(m);
	}
	/***************接收信息统计 开始****************************/
}
