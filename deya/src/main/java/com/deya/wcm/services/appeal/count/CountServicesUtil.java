package com.deya.wcm.services.appeal.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpUser.CPUserReleInfo;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.appeal.satisfaction.SatisfactionBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.appeal.cpUser.CpUserManager;
import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.appeal.purpose.PurposeManager;
import com.deya.wcm.services.appeal.satisfaction.SatisfactionManager;


/**
 *  统计用到了调用其他模块的接口类.
 * <p>Title: CicroDate</p>
 * <p>Description: 统计用到了调用其他模块的接口类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei  
 * @version 1.0
 * * 
 */
public class CountServicesUtil {
       
	/**
	 * 得到业务列表
	 * @return List<Map>
	 */
	  public static List<Map> getBusinessList(){
		  List<Map> list = new ArrayList<Map>();
		  try{
			  
			  //要调用得到  业务列表的接口   -- lisp
			  
			  List<ModelBean> modelBeans = ModelManager.getAllSQModelList();
			  for(ModelBean bean : modelBeans){
				  Map map = new HashMap();
				  map.put("id",String.valueOf(bean.getModel_id()));
				  map.put("name",bean.getModel_cname());
				  list.add(map);
			  } 
			  
			  return list; 
		  }catch (Exception e) {
			  e.printStackTrace();
			  return list;
		  }
	  }
	  
	  
	 /**
	 * 通过业务id得到业务名称 （暂时不用）
	 * @param String id 业务id
	 * @return String name 
	 */
	  public static String getBusinessName(String id){
		  String name = "";
		  try{
			  
			  //调用业务模块 通过业务id得到业务名称的接口   -- lisp
			  name = "领导信箱";
			  
			  return name; 
		  }catch (Exception e) {
			  e.printStackTrace();
			  return name;
		  }
	  }
	  
	  
		 /**
		 * 得到诉求目的列表
		 * @return List<Map> 
		 */
		  public static List<Map> getPurposeList(){
			  List<Map> list = new ArrayList<Map>();
			  try{
				  
				  //调用   得到诉求目的列表   的列表 -- lisp
				  
				  List<PurposeBean> listBean = PurposeManager.getAllPurposeList();
				  for(PurposeBean bean : listBean){
					  Map map = new HashMap();
					  map.put("id",String.valueOf(bean.getPur_id()));
					  map.put("name",bean.getPur_name());
					  list.add(map);
				  }
				  
				  return list;
			  }catch (Exception e) {
				  e.printStackTrace();
				  return list;
			  }
		  }
		  
		  
			 /**
			 * 得满意度列表
			 * @return List<Map> 
			 */
			  public static List<Map> getSatisfactionList(){
				  List<Map> list = new ArrayList<Map>();
				  try{
					    
					  //调用   得到满意度的列表   的列表 -- lisp
//					  Map map1 = new HashMap();
//					  map1.put("id","1");
//					  map1.put("name","是否按时回复");
//					  Map map2 = new HashMap();
//					  map2.put("id","2");  
//					  map2.put("name","是否满意办理结果");
//					  list.add(map1);
//					  list.add(map2);
					  
					  List<SatisfactionBean> listBean =  SatisfactionManager.getSatisfactionList();
					  for(SatisfactionBean bean : listBean){
						  Map map = new HashMap();
						  map.put("id",String.valueOf(bean.getSat_id()));
						  map.put("name",bean.getSat_item());
						  list.add(map);
					  }
					   
					  return list;
				  }catch (Exception e) {
					  e.printStackTrace();
					  return list;
				  }
			  }
		  
		  
		  /**
		 * 得到部门对象列表
		 * @return List<CpDeptBean> 
		 */
		  public static List<CpDeptBean> getDeptmentList(){
			  List<CpDeptBean> list = new ArrayList<CpDeptBean>();
			  try{	
					List<CpDeptBean> deptBeansList = CpDeptManager.getAllCpDeptBySort();
				    for(CpDeptBean bean : deptBeansList){
				    	list.add(bean); 
				    }
					
				  return list; 
			  }catch (Exception e) {
				  e.printStackTrace();
				  return list;
			}
		  }
		  
		  /**
		   * 得到所有已关联的用户
		   * @return List<CPUserReleInfo> 
		  */
		   public static List<CPUserReleInfo> getCPUserReleList(){
				  List<CPUserReleInfo> list = new ArrayList<CPUserReleInfo>();
				  try{
						Map<Integer,CPUserReleInfo> m = CpUserManager.getAllReleUserMap();
					    for(Map.Entry<Integer,CPUserReleInfo> entry : m.entrySet()){    
					    	CPUserReleInfo  CPUserRele = entry.getValue();  
					    	list.add(CPUserRele); 
			            }
					  return list; 
				  }catch (Exception e) {
					  e.printStackTrace();
					  return list;
				}
		   }
}