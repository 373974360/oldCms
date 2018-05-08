package com.deya.wcm.dao.system.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.system.error.ErrorReportBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;



/**
 *  纠错管理.
 * <p>Title: ErrorReportDao</p>
 * <p>Description: 纠错管理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class ErrorReportDao {

	  private static String ERROR_TABLE_NAME = "cs_err_report";
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<ErrorReportBean> getErrorReportList(Map map) {
		return (List<ErrorReportBean>)DBManager.queryFList("error_report.getErrorReportList", map);
	} 
	
	  
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static int getErrorReportListCount(Map map) {
		return Integer.valueOf((Integer)DBManager.queryFObj("error_report.getErrorReportListCount", map));
	}
	
	/**
     * 插入纠错信息
     * @param ErrorReportBean errorReportBean
     * @return boolean
     * */  
	public static boolean addErrorReport(ErrorReportBean errorReportBean) {
		//String ip = FormatUtil.getIpAddr(request);
		errorReportBean.setId(PublicTableDAO.getIDByTableName(ERROR_TABLE_NAME));
		//JSONObject jo = JSONObject.fromObject(errorReportBean);
        //System.out.println("jo===" + jo); 
		return DBManager.insert("error_report.addErrorReport",errorReportBean);
	}
	
	/**
	 * 删除业纠错信息
	 * @param String ids
	 * @return OnlinebookType
	 */
	public static boolean deleteErrorReports(String ids)
	{   
		Map map = new HashMap();
		map.put("ids", ids.trim());
		return DBManager.delete("error_report.deleteErrorReports",map);
	}
	
	
	/**
     * 得到纠错信息
     * @param int id sql所需要的参数 
     * @return ErrorReportBean
     * */
	public static ErrorReportBean getErrorReportById(int id) {
		Map map = new HashMap();
		map.put("id",id);
		return (ErrorReportBean)DBManager.queryFObj("error_report.getErrorReportById", map);
	}
	
	/**
     * 处理纠错信息
     * @param ErrorReportBean errorReportBean sql所需要的参数 
     * @return boolean
     * */ 
	public static boolean updateErrorReportById(ErrorReportBean errorReportBean) {
		errorReportBean.setO_time(DateUtil.getCurrentDateTime());
		return DBManager.update("error_report.updateErrorReportById", errorReportBean);
	}
	
}
