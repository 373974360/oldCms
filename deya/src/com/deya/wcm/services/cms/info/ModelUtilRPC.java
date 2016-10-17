package com.deya.wcm.services.cms.info;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.Log.LogManager;

public class ModelUtilRPC {

	/**
	 * 取得模型信息对象
	 * @param infoId	信息ID
	 * @param siteId	站点ID
	 * @param model_name	模型信息名称
	 * @return
	 */
	public static Object select(String infoId, String siteId, String model_name)
	{
		return ModelUtil.select(infoId, siteId, model_name);
	}
	/**
	 * 添加模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean insert(Object ob, String model_name, HttpServletRequest request)
	{
		//System.out.println("ob---cat_id = " + ((Map)ob).get("cat_id"));
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		try{ 
			if(stl != null){ 
				String info_status = "0";
				String site_id = "";
				String app_id = "";
				try {
					info_status = BeanUtils.getProperty(ob, "info_status");
					site_id = BeanUtils.getProperty(ob, "site_id");
					app_id = BeanUtils.getProperty(ob, "app_id");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("*************************************" + ob.toString());
				
				if("8".equals(info_status))
				{
					//如果需要发布，使用rmi调用
					return FileRmiFactory.insertInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id), ob, model_name, stl);
				}else				
					return ModelUtil.insert(ob, model_name, stl);
			}else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean update(Object ob, String model_name, HttpServletRequest request)
	{
		try{
			//System.out.println("ModelUtilRPC :: update :: start");
			SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
			if(stl != null){
				String info_status = "0";
				String site_id = "";
				String app_id = "";
				try {
					info_status = BeanUtils.getProperty(ob, "info_status");
					site_id = BeanUtils.getProperty(ob, "site_id");
					app_id = BeanUtils.getProperty(ob, "app_id");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if("8".equals(info_status))
				{
					//如果需要发布，使用rmi调用
					return FileRmiFactory.updateInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id), ob, model_name, stl);
				}else
					return ModelUtil.update(ob, model_name, stl);
			}else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}	
}
