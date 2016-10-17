package com.deya.wcm.services.system.error;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.system.error.ErrorReportBean;
import com.deya.wcm.dao.system.error.ErrorReportDao;

public class ErrorReportRPC {

	
	/**
     * 得到报列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<ErrorReportBean> getErrorReportList(Map map) {
		return ErrorReportService.getErrorReportList(map);
	}
	
	/**
     * 得到报列表 数量
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static int getErrorReportListCount(Map map) {
		return ErrorReportService.getErrorReportListCount(map);
	} 
	  
	/**
     * 插入纠错信息
     * @param ErrorReportBean errorReportBean
     * @return boolean
     * */    
	public static boolean addErrorReport(ErrorReportBean errorReportBean,HttpServletRequest request) {
		return ErrorReportService.addErrorReport(errorReportBean, request);
	}
	
	
	/**
	 * 删除纠错信息
	 * @param String obtids
	 * @return boolean
	 */  
	public static boolean deleteErrorReports(String obtids)
	{
		return ErrorReportService.deleteErrorReports(obtids);
	}
	
	/**
     * 得到纠错信息
     * @param int id sql所需要的参数 
     * @return ErrorReportBean
     * */  
	public static ErrorReportBean getErrorReportById(int id) {
		return ErrorReportService.getErrorReportById(id);
	}
	
	/**
     * 处理纠错信息
     * @param ErrorReportBean errorReportBean sql所需要的参数 
     * @return boolean
     * */ 
	public static boolean updateErrorReportById(ErrorReportBean errorReportBean) {
		return ErrorReportService.updateErrorReportById(errorReportBean);
	}
	
}
