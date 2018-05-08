package com.deya.wcm.services.appeal.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.count.CategoryBean;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.count.LetterCountBean;


/**
 *  诉求统计分析类 js调用类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求统计分析类 js调用类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class CountServicesRPC {

	/**
	 * 按诉求目的-办理情况 得到统计列表
	 * @param String s  开始时间 
	 * @param String e  结束时间
	 * @param String b_ids  业务id 以逗号分割 
	 * @return List<Map> 
	 */
     public static List<CountBean> getCountAimHandle(String s,String e,String b_ids){
    	 return CountServices.getCountAimHandle(s, e, b_ids);
     } 
     
      
	  /**
	   * 得到部门树 
	   * @param String s  开始时间
	   * @param String e  结束时间
	   * @param String b_ids 业务Id
	   * @return List<CategoryBean>
	   */
	  public static List<CategoryBean> getListAll(String s,String e,String b_ids){
		  return CountDeptTreeServices.getListNew(s,e,b_ids);
	  }  
	  
	  /**
	   * 得到部门树  -- 信件类型
	   * @param String s  开始时间
	   * @param String e  结束时间
	   * @param String b_ids 业务Id  
	   * @return List<CategoryBean> 
	   */ 
	  public static List<CategoryBean> getListAllType(String s,String e,String b_ids){
		  return CountDeptTreeServices.getListType(s,e,b_ids);
	  }
	  
	  /**
	   * 得到部门树  -- 警示类型
	   * @param String s  开始时间
	   * @param String e  结束时间
	   * @param String b_ids 业务Id  
	   * @return List<CategoryBean> 
	   */ 
	  public static List<CategoryBean> getListAllCaution(String s,String e,String b_ids){
		  return CountDeptTreeServices.getListCaution(s,e,b_ids);
	  }
 	  
	  /**
	   * 得到部门树  -- 诉求目的
	   * @param String s  开始时间
	   * @param String e  结束时间
	   * @param String b_ids 业务Id  
	   * @return List<CategoryBean> 
	   */ 
	  public static List<CategoryBean> getListAllPur(String s,String e,String b_ids){
		  return CountDeptTreeServices.getListPur(s,e,b_ids);
	  }
	  
	  /**
	   * 满意度统计--处理部门
	   * @param String s  开始时间
	   * @param String e  结束时间
	   * @param String b_ids 业务Id  
	   * @return List<CategoryBean> 
	   */ 
	  public static List<CategoryBean> getListAllSatisfactionDept(String s,String e,String b_ids){
		  return CountDeptTreeServices.getListAllSatisfactionDept(s,e,b_ids);
	  }
	  
   	/**
   	 * 通过  时间，业务ID ，部门ID  附加条件   得到诉求信件列表 数目
   	 * @param Map map
   	 * @return List<LetterCountBean>
   	 */
   	public static List<LetterCountBean> getListByModelIdAndDept(Map map){
   		return CountServices.getListByModelIdAndDept(map);
   	}
   	
	/**
	 * 通过  时间，业务ID ，部门ID  附加条件   得到诉求信件列表 数目 
	 * @param Map map
	 * @return String
	 */  
	public static String getListByModelIdAndDeptCount(Map map){
		return CountServices.getListByModelIdAndDeptCount(map);
	}
	
	
	/**
	 * 通过  时间，业务ID,用户   得到诉求信件列表 数目 
	 * @param String
	 * @return List
	 */  
	public static  List<CountBean> getCountUsersHandle(String s,String e,String b_ids){
		return CountServices.getCountUsersHandle(s,e,b_ids);
	}	
	
	/**
	 * 通过  时间，业务ID ，用户iD  附加条件   得到诉求信件列表 数目 
	 * @param Map map
	 * @return String
	 */  
	public static List<LetterCountBean>  getListByModelIdAndUserId(Map map){
		return CountServices.getListByModelIdAndUserId(map);
	}
	
	/**
	 * 通过  时间，业务ID ，用户iD  附加条件   得到诉求信件列表 数目 
	 * @param Map map
	 * @return String
	 */  
	public static String getListByModelIdAndUserCount(Map map){
		return CountServices.getListByModelIdAndUserCount(map);
	}
}
