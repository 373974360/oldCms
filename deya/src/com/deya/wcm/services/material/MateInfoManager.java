package com.deya.wcm.services.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateInfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.material.MateInfoDao;

public class MateInfoManager {
		
	
	/**
     * 根据素材库文件夹ID得到素材列表
     * @param String f_id
     * @return List
     * */	
	public static List<MateInfoBean> getMateInfoList(Map<String,String> con_m)
	{
		String time = con_m.get("time");
		if(time != null && !"".equals(time))
		{
			String beignTime = time.substring(0,4) + "-" + time.substring(4,6) + "-01 00:00:00";
			String endTime = time.substring(0,4) + "-" + time.substring(4,6) + "-31 23:59:59";
			con_m.put("beignTime", beignTime);
			con_m.put("endTime", endTime);
		}
		List<MateInfoBean> mfa = new ArrayList<MateInfoBean>();
			mfa = MateInfoDao.getMateInfoList(con_m);
		return mfa;
	}
	/**
     * 得到素材对象
     * @param 
     * @return List
     * */	
	public static MateInfoBean getMateInfoBean(String arr_id)
	{
		MateInfoBean  mfa = new  MateInfoBean();
		if(arr_id != ""){
			mfa = MateInfoDao.getMateInfoByArr_id(arr_id);
		}
		return mfa;
	}
	/**
     * 得到素材id
     * @param 
     * @return List
     * */
	public static String getArrIdFromTable()
	{
		return  PublicTableDAO.getIDByTableName(PublicTableDAO.MateInfo_TABLE_NAME)+"";
	}
	
	/**
     * 得到素材总数
     * @param 
     * @return List
     * */
	public static String getMateInfoListCounts(Map<String,String> con_m)
	{
		String counts="";
		if(con_m != null){
			String time = con_m.get("time");
			if(time != null && !"".equals(time))
			{
				String beignTime = time.substring(0,4) + "-" + time.substring(4,6) + "-01 00:00:00";
				String endTime = time.substring(0,4) + "-" + time.substring(4,6) + "-31 23:59:59";
				con_m.put("beignTime", beignTime);
				con_m.put("endTime", endTime);
			}
			counts = MateInfoDao.getMateInfoListCounts(con_m);
			return counts;
		}else{
			return "0";
		}	
	}	
	/**
     * 插入素材信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMateInfo(MateInfoBean mfb,SettingLogsBean stl)
	{
		int type=0; 
		String extName="";			
		String upload_dtime = com.deya.util.DateUtil.getCurrentDateTime();
	
		if(mfb.getAlias_name().lastIndexOf(".") >= 0) {
		    extName = mfb.getAlias_name().substring(mfb.getAlias_name().lastIndexOf(".")).toLowerCase();
			if(extName.equals(".gif") || extName.equals(".jpg")|| extName.equals(".jpeg")
					|| extName.equals(".png") || extName.equals(".bmp")){
				type = 0;//图片
			}else if(extName.equals(".swf")){
				type = 1;//flash
			}else if(extName.equals(".avi") || extName.equals(".flv")|| extName.equals(".wmv") || extName.equals(".mp4") || extName.equals(".mkv") || extName.equals(".mpg") || extName.equals(".wma") || extName.equals(".rmvb") || extName.equals(".wav")){
				type = 2;//视频
			}else if(extName.equals(".txt") || extName.equals(".doc")|| extName.equals(".docx") || extName.equals(".xls") || extName.equals(".xlsx")|| extName.equals(".ppt") || extName.equals(".rar") || extName.equals(".zip") || extName.equals(".pdf") || extName.equals(".html") || extName.equals(".htm")){
				type = 3;//文件
			}else{
				type = 4;//其它 
			}
		}
		
		mfb.setApp_id("cms");
		mfb.setFileext(extName);
		mfb.setUpload_dtime(upload_dtime);
		mfb.setAtt_type(type);

		if(mfb.getHd_url() == null || "null".equals(mfb.getHd_url())){
			mfb.setHd_url("");
		}
		if(mfb.getThumb_url() == null || "null".equals(mfb.getThumb_url())){
			mfb.setThumb_url("");
		}
		if(mfb.getAtt_cname() == null || "null".equals(mfb.getAtt_cname())){
			mfb.setAtt_cname(mfb.getAlias_name());
		}
		if(mfb.getAtt_description() == null || "null".equals(mfb.getAtt_description())){
			mfb.setAtt_description("");
		}
		
		
		if(MateInfoDao.insertMateInfo(mfb, stl))
		{
			return true;
		}else{
			return false;
		}
	}
	/**
     * 修改素材信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMateInfo(MateInfoBean mfb,SettingLogsBean stl)
	{
		if(MateInfoDao.updateMateInfo(mfb, stl))
		{
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 移动附件
     * @param String f_id
     * @param String att_ids
     * @return boolean
     * */
	public static boolean moveMateInfo(String f_id,String att_ids)
	{
		return  MateInfoDao.moveMateInfo(f_id, att_ids);
	}
	
	/**
     * 删除文件夹信息
     * @param String f_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMateInfo(String f_id,SettingLogsBean stl)
	{
		if(MateInfoDao.deleteMateInfo(f_id, stl))
		{
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String args[])
	{
		String a = "201506";
		System.out.println(a.substring(0,4));
	}
}