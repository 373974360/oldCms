package com.deya.wcm.services.appeal.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.count.CategoryBean;


/**
 *  统计用到了调用其他模块的接口类 js调用类.
 * <p>Title: CicroDate</p>
 * <p>Description: 统计用到了调用其他模块的接口类 js调用类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class CountServicesUtilRPC {

	/**
	 * 得到业务列表
	 * @return List<Map>
	 */
	  public static List<Map> getBusinessList(){
		  return CountServicesUtil.getBusinessList();
	  }   
	  
	
}
