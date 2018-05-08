package com.deya.wcm.services.material;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.UploadManager;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateInfoBean;
import com.deya.wcm.services.Log.LogManager;

public class MateInfoRPC {
		
	/**
     * 在上传之前获取密钥，用于做上传安全处理
     * @param 
     * @return String
     * */
	public static String getUploadSecretKey()
	{
		return UploadManager.getUploadSecretKey();
	}
	
	/**
     * 
     * @return String
     * */
	public static String getImgDomain(String site_id)
	{
		return UploadManager.getImgBrowserUrl(site_id);
	}
	
	/**
     * 
     * @return String
     * */
	public static String getArrIdFromTable()
	{
		return MateInfoManager.getArrIdFromTable();
	}
	
	/**
     * 得到素材列表
     * @param
     * @return 
     * */
	public static List<MateInfoBean> getMateInfoList(Map<String,String> con_m)
	{
		return MateInfoManager.getMateInfoList(con_m);
	}
	
	/**
     * 得到素材对象
     * @param 
     * @return List
     * */
	public static MateInfoBean getMateInfoBean(String arr_id)
	{
		return MateInfoManager.getMateInfoBean(arr_id);
	}
	
	/**
     * 得到素材总数
     * @param 
     * @return List
     * */
	public static String  getMateInfoListCounts(Map<String,String> con_m)
	{
		return MateInfoManager.getMateInfoListCounts(con_m);
	}
	
	/**
     * 添加素材对象
     * @param 
     * @return List
     * */
	public boolean insertMateInfoBean(MateInfoBean mfb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		
		if(stl != null)
		{
			return MateInfoManager.insertMateInfo(mfb,stl);
		}else
			return false;
	}
	
	/**
     * 修改素材对象
     * @param 
     * @return List
     * */
	public boolean updateMateInfoBean(MateInfoBean mfb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateInfoManager.updateMateInfo(mfb,stl);
		}else
			return false;
	}
	
	/**
     * 移动附件
     * @param String f_id
     * @param String att_ids
     * @return boolean
     * */
	public static boolean moveMateInfo(String f_id,String att_ids)
	{
		return MateInfoManager.moveMateInfo(f_id, att_ids);
	}
	
	/**
     * 删除素材信息
     * @param String opt_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMateInfo(String opt_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateInfoManager.deleteMateInfo(opt_id,stl);
		}else
			return false;
	}
}