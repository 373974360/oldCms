package com.deya.wcm.dao.appeal.count;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.count.LetterCountBean;
import com.deya.wcm.db.DBManager;

/**
 *  诉求统计分析数据库操作类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求统计分析数据库操作类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * *  
 */
public class CountDAO {

	
	/**
	 * 通过 诉求目的 ， 时间，业务ID 得到诉求信件总数
	 * @param Map map
	 * @return String
	 */
	public static String getCountByModelIdAndPur(Map map){
		String count = "0";
		try{
			count = DBManager.getString("count.getCountByModelIdAndPur",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace(); 
			return count;
		}
	}
	
	/**
	 * 通过 诉求目的 ， 时间，业务ID，处理状态  得到诉求信件数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndPurStatus(Map map){
		String count = "0";
		try{
			count = DBManager.getString("count.getCountByModelIdAndPurStatus",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  诉求目的 ， 时间，业务ID ，信件类型 得到诉求信件数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndFlag(Map map){
		String count = "0";
		try{
			count = DBManager.getString("count.getCountByModelIdAndFlag",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  诉求目的 ， 时间，业务ID ，警示状态    得到诉求信件数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndAlarm(Map map){
		String count = "0";
		try{
			count = DBManager.getString("count.getCountByModelIdAndAlarm",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  时间，业务ID，部门ID 得到诉求信件数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndDept(Map map){
		String count = "0";
		try{  
			count = DBManager.getString("count.getCountByModelIdAndDept",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  时间，业务ID,用户ID 得到诉求信件数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndUserID(Map map){
		String count = "0";
		try{  
			count = DBManager.getString("count.getCountByModelIdAndUserID",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  时间，业务ID ，部门ID，满意度指标ID   得到满意度分数
	 * @param Map map
	 * @return String 
	 */
	public static String getCountByModelIdAndDeptSat(Map map){
		String count = "0";
		try{  
			count = DBManager.getString("count.getCountByModelIdAndDeptSat",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	/**
	 * 通过  时间，业务ID ，部门ID  附加条件   得到诉求信件列表 数目
	 * @param Map map
	 * @return List<LetterCountBean>
	 */
	public static List<LetterCountBean> getListByModelIdAndDept(Map map){
		List<LetterCountBean> list = new ArrayList<LetterCountBean>();
		try{  
			list = DBManager.queryFList("count.getListByModelIdAndDept",map);
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	/**
	 * 通过  时间，业务ID ，部门ID  附加条件   得到诉求信件列表 数目 
	 * @param Map map
	 * @return String 
	 */
	public static String getListByModelIdAndDeptCount(Map map){
		String count = "0";
		try{  
			count = DBManager.getString("count.getListByModelIdAndDeptCount",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	
	/**
	 * 通过  时间，业务ID ，用户iD 得到诉求信件列表 数目
	 * @param Map map
	 * @return List<LetterCountBean>
	 */
	public static List<LetterCountBean> getListByModelIdAndUserID(Map map){
		List<LetterCountBean> list = new ArrayList<LetterCountBean>();
		try{  
			list = DBManager.queryFList("count.getListByModelIdAndUserID",map);
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	/**
	 * 通过  时间，业务ID，用户ID 得到诉求信件列表 数目 
	 * @param Map map
	 * @return String 
	 */
	public static String getListByModelIdAndUserIDCount(Map map){
		String count = "0";
		try{  
			count = DBManager.getString("count.getListByModelIdAndUserIDCount",map);
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
}