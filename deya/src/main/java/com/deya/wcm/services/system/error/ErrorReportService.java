package com.deya.wcm.services.system.error;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.system.error.ErrorReportBean;
import com.deya.wcm.dao.system.error.ErrorReportDao;
import com.deya.wcm.services.cms.info.InfoBaseManager;

public class ErrorReportService {

	
	/**
     * 得到报列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<ErrorReportBean> getErrorReportList(Map map) {
		return ErrorReportDao.getErrorReportList(map);
	}
	
	/**
     * 得到报列表 数量
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static int getErrorReportListCount(Map map) {
		return ErrorReportDao.getErrorReportListCount(map);
	}
	
	/**
     * 插入纠错信息
     * @param ErrorReportBean errorReportBean
     * @return boolean
     * */  
	public static boolean addErrorReport(ErrorReportBean errorReportBean,HttpServletRequest request) {
			try{
				String ip = FormatUtil.getIpAddr(request);
				String site_id = errorReportBean.getSite_id();
				if(site_id==null || "".equals(site_id)){
					site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
				}
				errorReportBean.setErr_name_ip(ip);
				errorReportBean.setErr_time(DateUtil.getCurrentDateTime());
				errorReportBean.setSite_id(site_id);
				return ErrorReportDao.addErrorReport(errorReportBean);
			}catch (Exception e) { 
				e.printStackTrace();
				return false;
			}
	}
	
	
	/**
     * 填充提交前的页面信息
     * @param int info_id 
     * @return ErrorReportBean
     * */
	public static ErrorReportBean getBeforeAddErrorReportBean(String info_id) {
		ErrorReportBean errorReportBean = new ErrorReportBean();
		InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
		errorReportBean.setInfo_id(Integer.valueOf(info_id));
		
		return errorReportBean;
	}
	
	
	/**
	 * 删除纠错信息
	 * @param String obtids
	 * @return boolean
	 */
	public static boolean deleteErrorReports(String obtids)
	{   
		try{
			String eacuseid[]= obtids.split(",");
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<eacuseid.length;i++){
	        	String id = eacuseid[i];
	        	if(id!=null && !"".equals(id)){
	        		if(i!=eacuseid.length-1){
	        			sb.append(""+id+""+",");
	        		}else{
	        			sb.append(""+id+"");
	        		}
	        		
	        	}
	        }       
	        ErrorReportDao.deleteErrorReports(sb.toString());
	        return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
     * 得到纠错信息
     * @param int id sql所需要的参数 
     * @return ErrorReportBean
     * */
	public static ErrorReportBean getErrorReportById(int id) {
		return ErrorReportDao.getErrorReportById(id);
	}
	
	/**
     * 处理纠错信息
     * @param ErrorReportBean errorReportBean sql所需要的参数 
     * @return boolean
     * */ 
	public static boolean updateErrorReportById(ErrorReportBean errorReportBean) {
		return ErrorReportDao.updateErrorReportById(errorReportBean);
	}
	
	
}
