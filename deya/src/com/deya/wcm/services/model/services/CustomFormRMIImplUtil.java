package com.deya.wcm.services.model.services;

import java.rmi.RemoteException;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.dao.cms.info.ModelUtilDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.rmi.IFileRmi;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.cms.info.InfoPublishManager;
import com.deya.wcm.services.cms.info.ModelConfig;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.control.server.SiteServerManager;

public class CustomFormRMIImplUtil {

	//private static final String httpstr = "http://xxzyk.xjbt.gov.cn/sys/file/";
	private static String httpstr = JconfigUtilContainer.bashConfig().getProperty("urlFile", "", "wcm_zyk");
	//private static final String resStr = "/sys/file/";
	private static String resStr = JconfigUtilContainer.bashConfig().getProperty("resUrlFile", "", "wcm_zyk");
	static{
		if(httpstr==null || "".equals(httpstr)){
			httpstr = "http://xxzyk.xjbt.gov.cn/sys/file/";
		}
		if(resStr==null || "".equals(resStr)){
			resStr = "/sys/file/";  
		}
	}
	
	public static String replaceAllStr(String content){
		return content.replaceAll(resStr, httpstr);
	}
	
	/**
	 * 修改相关模型，模型可以是文章模型，通用图组新闻，通用视频新闻，用户自定义字段
	 * 中的任何一个
	 * @param o	具体模型的Bean对象
	 * @param sqlName	模型对应的MyBatis配置文件中的ID
	 * @param String model_name
	 * @param stl
	 * @return
	 */
	public static boolean updateModel(Object o, String sqlName,String model_name, SettingLogsBean stl)
	{
		if(updateInfo(o, stl))
		{
			if(model_name.toLowerCase().contains("gk"))
			{//如果英文名含有gk,表示是公开的内容模型，需要添加内容模型的公用表
				GKInfoDAO.updateGKInfo(o);
			}
			if(!"".equals(sqlName))
			{ 
				if(sqlName.equals("update_info_pic"))
				{
					return ModelUtilDAO.insertPicInfo(o,model_name);
				}
				else
					return DBManager.update(sqlName, o);
			}
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	/**
	 * update BaseInfomation Operate
	 * @param info
	 * @param stl
	 * @return boolean
	 */  
	public static boolean updateInfo(Object ob, SettingLogsBean stl){
		InfoBean info = (InfoBean)ob; 
		if(info.getInput_dtime() != null && !"".equals(info.getInput_dtime())){
			info.setModify_dtime(info.getInput_dtime());
			info.setOpt_dtime(info.getInput_dtime());
			info.setReleased_dtime(info.getInput_dtime());
		} 
		CustomFormRMIImpl.changeInfoStatus(info);
		return InfoDAO.updateInfo(info, stl);
	}
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean updateInfo(String rmi_site_id,Object ob, String model_name, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			//如果不是多台服务器，直接执行了
			return update(ob, model_name, stl);			
		}else
		{
			IFileRmi ifr = FileRmiFactory.getFileRmiObj(rmi_site_id);
			try {
				return ifr.updateInfo(ob, model_name, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 内容模型更改处理
	 * @param ob	具体模型的Bean对象
	 * @param model_name	具体模型的名称
	 * @param stl
	 * @return
	 */
	public static boolean update(Object ob, String model_name, SettingLogsBean stl)
	{
		try{
			Map<String, String> mp = ModelConfig.getModelMap(model_name);
			ModelUtil.setPageCountInObject(ob,model_name);
			
			String UpdateSQL = "";
			if(mp!=null){ 
				UpdateSQL = mp.get("UpdateSQL");
			} 
			if(CustomFormRMIImplUtil.updateModel(ob, UpdateSQL,model_name, stl))
			{
				InfoBean info = (InfoBean)ob;
				
				if(info.getInput_dtime().equals("")){
					info.setInput_dtime(DateUtil.getCurrentDateTime());
				} 
				info.setReleased_dtime(info.getInput_dtime());
			
				CustomFormRMIImpl.changeInfoStatus(info);
				if(info.getInfo_status() == 8){//发布后要处理的事情				
					InfoPublishManager.publishAfterEvent(info);	
				} 
				return true;
			}else  
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
