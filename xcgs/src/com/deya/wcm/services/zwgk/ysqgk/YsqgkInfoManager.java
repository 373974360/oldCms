package com.deya.wcm.services.zwgk.ysqgk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkInfoDAO;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class YsqgkInfoManager {
	
	private static String defalut_randon_str = "0-9";//默认随机码显示范围
		
	/**
     * 插入依申请公开信息对象
     * @param 
     * @return boolean
     * */
	public static Map<String,String> insertYsqgkInfo(YsqgkBean ysqgk,SettingLogsBean stl)
	{
		YsqgkConfigBean ycf = new YsqgkConfigBean();
		
		String ysqCode = ycf.getCode_pre()+DateUtil.getCurrentDateTime(ycf.getCode_rule())+RandomStrg.getRandomStr(defalut_randon_str,ycf.getCode_num()+"");
		String query_code = "";
		//设置查询码
		YsqgkConfigBean ycb = YsqgkConfigManager.getYsqgkConfigBean();
		query_code = RandomStrg.getRandomStr(defalut_randon_str, ycb.getQuery_num()+"");
		
		ysqgk.setYsq_code(ysqCode);
		ysqgk.setQuery_code(query_code);
		ysqgk.setYsq_id(PublicTableDAO.getIDByTableName(PublicTableDAO.YSQGK_INFO_TABLE_NAME));
        
		if(ysqgk.getPut_dtime() == null || "".equals(ysqgk.getPut_dtime())){
				ysqgk.setPut_dtime(DateUtil.getCurrentDateTime().substring(0,10));
		}
		ysqgk.setNode_name(GKNodeManager.getNodeNameByNodeID(ysqgk.getNode_id()));
		if(YsqgkInfoDAO.insertYsqgkInfo(ysqgk,stl))
		{
			Map<String,String> m = new HashMap<String,String>();
			m.put("ysq_code", ysqCode);
			m.put("query_code", query_code);
			return m;
		}else
			return null;
	}
	
	/**
     * 得到依申请公开信息对象
     * @param 
     * @return boolean
     * */
	public static YsqgkBean getYsqgkBean(String ysq_id)
	{
		return YsqgkInfoDAO.getYsqgkBean(ysq_id);
	}
	
	/**
	 * 得到依申请公开信息对象
	 * @param String  ysq_code
	 * @param String  query_code
	 * @return YsqgkBean
	 */
	public static YsqgkBean getYsqgkBeanForQuery(String  ysq_code,String  query_code)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ysq_code", ysq_code);
		m.put("query_code", query_code);
		return YsqgkInfoDAO.getYsqgkBeanForQuery(m);
	}

	/**
	 * 得到依申请公开信息列表
	 * @param Map m 
	 * @return List
	 */
	public static List<YsqgkListBean> getYsqgkLists(Map<String,String> m)
	{
		setTimeCon(m);
		return YsqgkInfoDAO.getYsqgkLists(m);
	}
	
	/**

	 * 设置时间查询条件

	 * @param Map m

	 * @return void

	 */

	public static void setTimeCon(Map<String,String> m)
	{
		if(m.containsKey("put_dtime"))
		{
			if("day".equals(m.get("put_dtime")))
			{
				m.put("put_dtime", DateUtil.getCurrentDate()+" 00:00:00");
			}else if("yestetoday".equals(m.get("put_dtime")))
			{
				m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),1)+" 00:00:00");
			}else if("week".equals(m.get("put_dtime")))
			{
				m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),7)+" 00:00:00");
			}else if("month".equals(m.get("put_dtime")))
			{
				m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),30)+" 00:00:00");
			}else{
				m.remove("put_dtime");
			}
		}
	}
	/**
	 * 得到依申请公开信息总数
	 * @param map  
	 * @return int
	 */
	public static int getYsqgkListsCount(Map<String,String> m)
	{
		setTimeCon(m);
		return YsqgkInfoDAO.getYsqgkListsCount(m);
	}
	
	/**
     *  修改依申请公开信息
     * @param String ysq_id
     * @return boolean
     * */
	public static boolean updateYsqgkInfo(YsqgkBean ysqgk,SettingLogsBean stl)
	{
		if(YsqgkInfoDAO.updateYsqgkInfo(ysqgk, stl))
		{
			return true;
		}else
			return false;
	}
	
	/**
     *   处理依申请公开信息
     * @param String ysq_id
     * @return boolean
     * */
	public static boolean updateStatus(Map<String, String> map,SettingLogsBean stl)
	{
		if(map.size() > 0)
		{
			if("0".equals(map.get("dealtype"))){
				 map.put("accept_dtime",DateUtil.getCurrentDateTime()); 
				 map.put("reply_dtime",""); 
			}else if("1".equals(map.get("dealtype"))){
				 map.put("reply_dtime",DateUtil.getCurrentDateTime()); 
			}else if("2".equals("dealtype")){
				map.put("accept_dtime",DateUtil.getCurrentDateTime());
			}
		}
		if(YsqgkInfoDAO.updateStatus(map, stl))
		{
			return true;
		}else
			return false;	
	}
	
	
	/**
     *  修改依申请公开信息为删除状态
     * @param map
     * @return boolean
     * */
	public static boolean setDeleteState(Map<String,String> m,SettingLogsBean stl)
	{
		if(YsqgkInfoDAO.setDeleteState(m, stl))
		{
			return true;
		}else
			return false;
	}
	/**
     *  还原依申请公开信息
     * @param map
     * @return boolean
     * */
	public static boolean reBackInfos(Map<String,String> m,SettingLogsBean stl)
	{
		if(YsqgkInfoDAO.reBackInfos(m, stl))
		{
			return true;
		}else
			return false;
	}
	/**
     *  删除依申请公开信息
     * @param map
     * @return boolean
     * */
	public static boolean deleteYsqgkInfo(Map<String,String> m,SettingLogsBean stl)
	{
		if(YsqgkInfoDAO.deleteYsqgkInfo(m, stl))
		{
			return true;
		}else
			return false;
	}
	
	/**
     *  清除回收站信息
     * @param map
     * @return boolean
     * */
	public static boolean clearDeleteYsqgkInfos(SettingLogsBean stl)
	{
		if(YsqgkInfoDAO.clearDeleteYsqgkInfos(stl))
		{
			return true;
		}else
			return false;
	}
	
	/**
	 * 得到依申请总数，用于前台统计显示
	 * @param Map<String,String> m
	 * @return String
	 */
	public static String getYsqStatistics(Map<String,String> m)
	{
		return  YsqgkInfoDAO.getYsqStatistics(m);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    String d ="2011-09-01 00:11:38";
    System.out.println(getYsqgkBeanForQuery("YSQ201202108488","577200"));
	}
}