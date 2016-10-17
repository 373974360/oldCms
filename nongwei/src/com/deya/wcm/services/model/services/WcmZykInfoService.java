package com.deya.wcm.services.model.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.model.WcmZykFile;

/**
 *  资源库同步信息时操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 资源库同步信息时的数据库操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class WcmZykInfoService {
	
	private static String filepath = JconfigUtilContainer.bashConfig().getProperty("filepath", "", "wcm_zyk");
	
	//通过信息info_id和字段ename 得到附件列表
	public static List<WcmZykFile> getZykinfoFileListByInfoId(String info_id,String fieldName) {
		List<WcmZykFile> list = new ArrayList<WcmZykFile>();
		try{
			list = WcmZykInfoDao.getZykinfoFileListByInfoId(info_id, fieldName);
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	//通过id得到附件详细信息
	public static WcmZykFile getZykinfoFileByInfoId(String id) {
		try{
			WcmZykFile wcmZykFile = WcmZykInfoDao.getZykinfoFileByInfoId(id);
			if(wcmZykFile!=null){
				wcmZykFile.setFile_id(filepath+wcmZykFile.getFile_id());
			}
			return wcmZykFile;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过id 得到info_id
     * @param String id
     * @return String
     * */
	public static String getWcminfo_zykinfoById(String id,String site_id) {
		return WcmZykInfoDao.getWcminfo_zykinfoById(id,site_id);
	}
	
	/**
     * 通过id 得到info_ids
     * @param String id
     * @return String
     * */
	public static List<Map> getWcminfo_zykinfosById(String id) {
		return WcmZykInfoDao.getWcminfo_zykinfosById(id);
	}
	
}
