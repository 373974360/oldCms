package com.deya.wcm.services.appeal.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.CalculateNumber;
import com.deya.wcm.bean.appeal.count.CategoryBean;
import com.deya.wcm.bean.appeal.count.HandleBean;
import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.dao.appeal.count.CountDAO;


/**
 *  诉求统计分析用到的部门树.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求统计分析用到的部门树</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class CountDeptTreeServices {
    
	//private static List<CpDeptBean> listAll = new ArrayList<CpDeptBean>();
	
	
//	static{
//		listAll = CountServicesUtil.getDeptmentList();
//	}  
	
	/**
	 * 解析得到的树结构为html
	 * @return String
	 */
	private static String getDeptList(String s,String e,String b_ids){
		String strResult = "";
		//System.out.println("-----getDeptList-----start--------");
		try{
			List<CategoryBean> beanList = getListNew(s,e,b_ids);
			strResult += setTreeNode(beanList.get(0));
			return strResult;
		}catch (Exception t) {
			t.printStackTrace();
			return strResult;
		}
	}
	
	/**
	 * 解析得到的树节点为html
	 * @return String
	 */
	private static String setTreeNode(CategoryBean bean){
		StringBuffer sb = new StringBuffer();
		try{
			
			if(bean.getCategory_type().equals("leaf"))
			{		
				String type_calss = "";
				if(bean.getP_id().equals("0")){  
				      type_calss = "class='child-of-node-"+bean.getP_id()+"'";
				}
				sb.append("<tr id='node-"+bean.getCategory_id()+"' "+type_calss+" > <td><span class='file'>"+bean.getCategory_name()+"</span></td>");
				sb.append(setHandlList(bean.getChild_list()));
				sb.append("</tr>");
			}else{
				String type_calss = "";
				if(bean.getP_id().equals("0")){  
				      type_calss = "class='child-of-node-"+bean.getP_id()+"'";
				} 
				sb.append("<tr id='node-"+bean.getCategory_id()+"' "+type_calss+" > <td><span class='folder'>"+bean.getCategory_name()+"</span></td>");
				sb.append(setHandlList(bean.getChild_list()));
				sb.append("</tr>");
				List child_list = bean.getChild_list();
				if(child_list.size() > 0){ 
					 for(int i=0;i<child_list.size();i++)
					 {						
						 sb.append(setTreeNode((CategoryBean)child_list.get(i)));
					 }
				  }  
			}
			
			return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			return sb.toString();
		}
	}
	
	
	private static String setHandlList(List<HandleBean> l)
	{
		StringBuffer sb = new StringBuffer("");
		if(l!=null)
		{			
			for(int i=0;i<l.size();i++)
			{					
				sb.append("<td>"+l.get(i).getHandle_name()+"</td>");
			} 
		}
		return sb.toString();
	}
	
	/**
	 * 得到列表
	 * @return List<CpDeptBean>
	 */
	private static List<CpDeptBean> getDeptList(){
		return CountServicesUtil.getDeptmentList();
	}
	
	
	/**
	 * 得到部门树--办理情况
	 * @param String s 开始时间
	 * @param String e 开始时间
	 * @param String b_ids 业务id
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getListNew(String s,String e,String b_ids){ 
		//System.out.println("---getListNew ---- --  start ---");
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids); 
			map.put("sq_flag","0");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			//System.out.println("listDept.size()====" + listDept.size());
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandl(bean.getDept_id(),map)); 
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
			//System.out.println("---getListNew ---- --  end 11111111111 ---");
		}catch (Exception t) {
			t.printStackTrace();
		}
		//System.out.println("---getListNew ---- --  end 2222222222222 ---");
		return l;
	}
	
	public static List<CategoryBean> getListtTest(String s,String e,String b_ids){ 
		//System.out.println("---getListtTest ---- --  start ---");
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids); 
			map.put("sq_flag","0");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			//System.out.println("listDept.size()====" + listDept.size());
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandl(bean.getDept_id(),map)); 
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
			//System.out.println("---getListtTest ---- --  end 11111111111 ---");
		}catch (Exception t) {
			t.printStackTrace();
		}
		//System.out.println("---getListtTest ---- --  end 2222222222222 ---");
		return l;
	}
	
	/**
	 * 得到部门树--信件类型
	 * @param String s 开始时间
	 * @param String e 开始时间
	 * @param String b_ids 业务id
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getListType(String s,String e,String b_ids){ 
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids);
			map.put("type", "2");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandlType(bean.getDept_id(),map));
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
		}catch (Exception t) {
			t.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 得到部门树--警示类型
	 * @param String s 开始时间
	 * @param String e 开始时间
	 * @param String b_ids 业务id
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getListCaution(String s,String e,String b_ids){ 
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids);
			map.put("type", "3");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandlCaution(bean.getDept_id(),map));
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
		}catch (Exception t) {
			t.printStackTrace();
		}
		return l;
	}
	
	
	/**
	 * 得到部门树--诉求目的
	 * @param String s 开始时间
	 * @param String e 开始时间
	 * @param String b_ids 业务id
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getListPur(String s,String e,String b_ids){ 
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids);
			map.put("type", "4");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandlPur(bean.getDept_id(),map));
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
		}catch (Exception t) {
			t.printStackTrace();
		}
		return l;
	}
	
	
	/**
	 * 满意度统计--处理部门
	 * @param String s 开始时间
	 * @param String e 开始时间
	 * @param String b_ids 业务id
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getListAllSatisfactionDept(String s,String e,String b_ids){ 
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		try{
			s = CountUtil.getTimeS(s);//开始时间
    		e = CountUtil.getTimeE(e);//结束时间
			Map map = new HashMap();
			map.put("s",s);
			map.put("e",e);  
			map.put("b_ids",b_ids);
			map.put("type", "5");
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(0);
			for(CpDeptBean bean : listDept){
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name()); 
				cb.setP_id(String.valueOf(bean.getParent_id()));
				cb.setHandle_list(getPublicHandlSatisfactionDept(bean.getDept_id(),map));
				cb.setChild_list(getchildList(bean.getDept_id(),map));		
				l.add(cb); 
			}
		}catch (Exception t) {
			t.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 通过父ID得到该节点的子节点列表
	 * @param int p_id 父ID 
	 * @param Map map
	 * @return List<CategoryBean>
	 */
	public static List<CategoryBean> getchildList(int p_id,Map map){
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		try{   
			List<CpDeptBean> listDept = getLsitCpDeptBeanByPaerentId(p_id);
			//System.out.println("getchildList ---- " + listDept.size() + "---" + p_id);
			for(int i=0;i<listDept.size();i++){ 
				CpDeptBean bean = listDept.get(i);
				CategoryBean cb = new CategoryBean();
				cb.setCategory_id(String.valueOf(bean.getDept_id()));
				cb.setCategory_name(bean.getDept_name());
				cb.setP_id(String.valueOf(bean.getParent_id()));
				
				String type = (String)map.get("type");
				if(type!=null && "2".equals(type)){ 
					cb.setHandle_list(getPublicHandlType(bean.getDept_id(),map));
				}else if(type!=null && "3".equals(type)){ 
					cb.setHandle_list(getPublicHandlCaution(bean.getDept_id(),map));
				}else if(type!=null && "4".equals(type)){ 
					cb.setHandle_list(getPublicHandlPur(bean.getDept_id(),map));
				}else if(type!=null && "5".equals(type)){ 
					cb.setHandle_list(getPublicHandlSatisfactionDept(bean.getDept_id(),map));
				}else{
					cb.setHandle_list(getPublicHandl(bean.getDept_id(),map));
				}
				cb.setChild_list(getchildList(bean.getDept_id(),map));	
				if((getLsitCpDeptBeanByPaerentId(bean.getDept_id())).size()==0){
					cb.setCategory_type("leaf");
				}    
				list.add(cb);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 通过父ID得到该节点的子节点
	 * @param int p_id 父ID
	 * @return List<CpDeptBean>
	 */
	public static List<CpDeptBean> getLsitCpDeptBeanByPaerentId(int p_id){
		List<CpDeptBean> list = new ArrayList<CpDeptBean>();
		try{
			List<CpDeptBean> list0 =  getDeptList();
			for(int i=0;i<list0.size();i++){
				CpDeptBean dept = list0.get(i);
				int id = dept.getParent_id();
				if(p_id==id){
					list.add(dept);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 设置各个部门的信件统计数
	 * @param int dpe_id 部门ID
	 * @param  Map map
	 * @return List<HandleBean>
	 */
	public static List getPublicHandl(int dep_id, Map map)
	{   
		List l = new ArrayList();
		try{
			String s = (String)map.get("s");
			String e = (String)map.get("e");
			String b_ids = (String)map.get("b_ids");
			
			s = CountUtil.getTimeS(s);//开始时间
		    e = CountUtil.getTimeE(e);//结束时间
		    if(b_ids.endsWith(",")){
			    b_ids = b_ids.substring(0,b_ids.length()-1);
		    }	
			
			//总件数
			map.put("model_id", b_ids);
			map.put("do_dept", dep_id);
			map.remove("timeout_flag"); 
			map.remove("sq_status");
			HandleBean hb = new HandleBean();
			hb.setHandle_id("h000"+dep_id+"1");
			String countAll = CountDAO.getCountByModelIdAndDept(map);
			hb.setHandle_name(countAll);
			l.add(hb);
			
			//待处理
			HandleBean hb2 = new HandleBean();
			hb2.setHandle_id("h000"+dep_id+"2");
			map.put("sq_status", "0");
			String countdai = CountDAO.getCountByModelIdAndDept(map);
			hb2.setHandle_name(countdai);
			l.add(hb2);
			
			//处理中
			HandleBean hb3 = new HandleBean();
			hb3.setHandle_id("h000"+dep_id+"3");  
			map.put("sq_status", "1");
			//System.out.println("map===" + map);
			String countchu = CountDAO.getCountByModelIdAndDept(map);
			hb3.setHandle_name(countchu);
			//System.out.println("countchu===" + countchu);
			l.add(hb3);
			
			//待审核
			HandleBean hb4 = new HandleBean();
			hb4.setHandle_id("h000"+dep_id+"3");
			map.put("sq_status", "2");
			String countshen = CountDAO.getCountByModelIdAndDept(map);
			hb4.setHandle_name(countshen);
			l.add(hb4);
			
			//已办结
			HandleBean hb5 = new HandleBean();
			hb5.setHandle_id("h000"+dep_id+"3");
			map.put("sq_status", "3");
			String countend = CountDAO.getCountByModelIdAndDept(map);
			hb5.setHandle_name(countend);
			l.add(hb5);
			
			//未办结
			HandleBean hb5_1 = new HandleBean();
			hb5_1.setHandle_id("h000"+dep_id+"3");
			int countwei = Integer.parseInt(countdai) + Integer.parseInt(countshen) + Integer.parseInt(countchu);
			hb5_1.setHandle_name(countwei + "");
			l.add(hb5_1); 
			
//			//李苏培新加的 统计结果列
//			//按时办结数
//			HandleBean hb5_2 = new HandleBean();
//			hb5_2.setHandle_id("h000"+dep_id+"3");
//			map.put("sq_status", "3");
//			map.put("timeout_flag", "1");
//			String countend_2 = CountDAO.getCountByModelIdAndDept(map);
//			hb5_2.setHandle_name(countend_2);
//			l.add(hb5_2);
//			
//			//超期办结数
//			HandleBean hb5_3 = new HandleBean();
//			hb5_3.setHandle_id("h000"+dep_id+"3");
//			int countend_3 = Integer.parseInt(countend) - Integer.parseInt(countend_2);
//			hb5_3.setHandle_name(countend_3 + "");
//			l.add(hb5_3); 
			
			
			//李苏培新加的 统计结果列
			//按时办结数
			HandleBean hb5_2 = new HandleBean();
			hb5_2.setHandle_id("h000"+dep_id+"3");
			map.put("sq_status", "3");
			map.put("timeout_flag", "0");
			String countend_2 = CountDAO.getCountByModelIdAndDept(map);
			hb5_2.setHandle_name(countend_2);
			l.add(hb5_2);
			
			//超期办结数
			HandleBean hb5_3 = new HandleBean();
			hb5_3.setHandle_id("h000"+dep_id+"3");
			int countend_3 = Integer.parseInt(countend) - Integer.parseInt(countend_2);
			hb5_3.setHandle_name(countend_3 + "");
			l.add(hb5_3); 
			
			
			
			//按时办结率
			HandleBean hb5_4 = new HandleBean();
			hb5_4.setHandle_id("h000"+dep_id+"3");
			String countEndRate_4 = "0%";
			if(countAll!=null && !countAll.equals("0")){
				countEndRate_4 = CalculateNumber.getRate(countend_2, countAll,0);
			}
			hb5_4.setHandle_name(countEndRate_4 + "");
			l.add(hb5_4);
			  
			//办结率  
			HandleBean hb6 = new HandleBean();
			hb6.setHandle_id("h000"+dep_id+"3");
			String countEndRate = "0%";
			if(countAll!=null && !countAll.equals("0")){
				countEndRate = CalculateNumber.getRate(countend, countAll,0);
			}
			hb6.setHandle_name(countEndRate);
			l.add(hb6);
			
			return l;
		}catch (Exception e) {
			e.printStackTrace();
			return l;
		}		
	}
	
	/**
	 * 设置各个部门的信件统计数--信件类型
	 * @param int dpe_id 部门ID
	 * @param  Map map
	 * @return List<HandleBean>
	 */
	public static List getPublicHandlType(int dep_id, Map map)
	{   
		List l = new ArrayList();
		try{
			String s = (String)map.get("s");
			String e = (String)map.get("e");
			String b_ids = (String)map.get("b_ids");
			
			s = CountUtil.getTimeS(s);//开始时间
		    e = CountUtil.getTimeE(e);//结束时间
		    if(b_ids.endsWith(",")){
			    b_ids = b_ids.substring(0,b_ids.length()-1);
		    }	
			
			//总件数
			map.put("model_id", b_ids);
			map.put("do_dept", dep_id);
			map.remove("sq_flag");
			HandleBean hb = new HandleBean();
			hb.setHandle_id("h000"+dep_id+"1");
			String countAll = CountDAO.getCountByModelIdAndDept(map);
			hb.setHandle_name(countAll);
			l.add(hb);
			
			//正常信件
			HandleBean hb2 = new HandleBean();
			hb2.setHandle_id("h000"+dep_id+"2");
  		    map.put("sq_flag", "0");
  		    String countNormal = CountDAO.getCountByModelIdAndDept(map);
			hb2.setHandle_name(countNormal);
			l.add(hb2);
			
			//无效信件
			HandleBean hb3 = new HandleBean();
			hb3.setHandle_id("h000"+dep_id+"3");
  		    map.put("sq_flag", "-1");
  		    String countInvalid = CountDAO.getCountByModelIdAndDept(map);
			hb3.setHandle_name(countInvalid); 
			l.add(hb3);

			//重复信件
			HandleBean hb4 = new HandleBean();
			hb4.setHandle_id("h000"+dep_id+"3");
  		    map.put("sq_flag", "1");
  		    String countRepeat = CountDAO.getCountByModelIdAndDept(map);
			hb4.setHandle_name(countRepeat);
			l.add(hb4);

			//不予受理信件
			HandleBean hb5 = new HandleBean();
			hb5.setHandle_id("h000"+dep_id+"3");
  		    map.put("sq_flag", "2");
  		    String countNohandle = CountDAO.getCountByModelIdAndDept(map);
  		    hb5.setHandle_name(countNohandle);
			l.add(hb5);
			
			return l;
		}catch (Exception e) {
			e.printStackTrace();
			return l;
		}		
	}
	
	/**
	 * 设置各个部门的信件统计数--警示类型
	 * @param int dpe_id 部门ID
	 * @param  Map map
	 * @return List<HandleBean>
	 */
	public static List getPublicHandlCaution(int dep_id, Map map)
	{   
		List l = new ArrayList();
		try{
			String s = (String)map.get("s");
			String e = (String)map.get("e");
			String b_ids = (String)map.get("b_ids");
			
			s = CountUtil.getTimeS(s);//开始时间
		    e = CountUtil.getTimeE(e);//结束时间
		    if(b_ids.endsWith(",")){
			    b_ids = b_ids.substring(0,b_ids.length()-1);
		    }	
			
			//总件数
			map.put("model_id", b_ids);
			map.put("do_dept", dep_id);
			HandleBean hb = new HandleBean();
			hb.setHandle_id("h000"+dep_id+"1");
			//全部信件数
    		String count = CountDAO.getCountByModelIdAndDept(map);
			hb.setHandle_name(count);
			l.add(hb);
			 
			//超期件
			HandleBean hb2 = new HandleBean();
			hb2.setHandle_id("h000"+dep_id+"2");
		    map.put("timeout_flag", "1");
		    String countOver = CountDAO.getCountByModelIdAndDept(map);
		    hb2.setHandle_name(countOver);
			l.add(hb2);
			
			//预警件
			HandleBean hb3 = new HandleBean();
			hb3.setHandle_id("h000"+dep_id+"3");
		    map.remove("timeout_flag");
		    map.put("alarm_flag", "1");
		    String countWarn = CountDAO.getCountByModelIdAndDept(map);
			hb3.setHandle_name(countWarn); 
			l.add(hb3);

			//黄牌件
			HandleBean hb4 = new HandleBean();
			hb4.setHandle_id("h000"+dep_id+"3");
    	    map.put("alarm_flag", "2");
    		String countYellow = CountDAO.getCountByModelIdAndDept(map);
			hb4.setHandle_name(countYellow);
			l.add(hb4);

			//红牌件
			HandleBean hb5 = new HandleBean();
			hb5.setHandle_id("h000"+dep_id+"3");
		    map.put("alarm_flag", "3"); 
		    String countRed = CountDAO.getCountByModelIdAndDept(map);
  		    hb5.setHandle_name(countRed);
			l.add(hb5);
			
			//督办件
			HandleBean hb6 = new HandleBean();
			hb6.setHandle_id("h000"+dep_id+"3");
		    map.remove("alarm_flag");
		    map.put("supervise_flag", "1");
		    String countSupervise = CountDAO.getCountByModelIdAndDept(map);
		    hb6.setHandle_name(countSupervise);
			l.add(hb6);
		    
		    //延期件  
			HandleBean hb7 = new HandleBean();
			hb7.setHandle_id("h000"+dep_id+"3");
		    map.remove("supervise_flag");
		    map.put("limit_flag", "1");  
 		    String countLimit = CountDAO.getCountByModelIdAndDept(map);
 		    hb7.setHandle_name(countLimit);
			l.add(hb7);
			
			return l;
		}catch (Exception e) {
			e.printStackTrace();
			return l;
		}		
	}
	
	
	
	/**
	 * 设置各个部门的信件统计数--诉求目的
	 * @param int dpe_id 部门ID
	 * @param  Map map
	 * @return List<HandleBean>
	 */
	public static List getPublicHandlPur(int dep_id, Map map)
	{   
		List l = new ArrayList();
		try{
			String s = (String)map.get("s");
			String e = (String)map.get("e");
			String b_ids = (String)map.get("b_ids");
			
			s = CountUtil.getTimeS(s);//开始时间
		    e = CountUtil.getTimeE(e);//结束时间
		    if(b_ids.endsWith(",")){
			    b_ids = b_ids.substring(0,b_ids.length()-1);
		    }	
			
			//总件数
			map.put("model_id", b_ids);
			map.put("do_dept", dep_id);
			
			//得到诉求目的列表
    		List<Map>  listPurpose = com.deya.wcm.services.appeal.count.CountServicesUtil.getPurposeList();
    		List<String> tempList = new ArrayList<String>();
    		int countAll = 0; 
    		for(int i=0;i<listPurpose.size();i++){   
    			   map.put("pur_id",(listPurpose.get(i)).get("id")); 
  		           String count = CountDAO.getCountByModelIdAndDept(map);
  		           //System.out.println("count=========="+count);
  		           countAll += Integer.valueOf(count);
  		           tempList.add(count);
    		} 
    		HandleBean hb = new HandleBean();
			hb.setHandle_id("h000"+dep_id+"1");
			hb.setHandle_name(String.valueOf(countAll));
			//System.out.println("String.valueOf(countAll)===" + String.valueOf(countAll));
			l.add(hb);
			//System.out.println("++++++++++++++++++++++++++");
    		for(String str : tempList){
    			HandleBean hb2 = new HandleBean();
    			hb2.setHandle_id("h000"+dep_id+"2"); 
    		    hb2.setHandle_name(str);
    		    //System.out.println("str===" + str);
    			l.add(hb2); 
    		}
    		//System.out.println("++++++++++++++++++++++++++");

			return l;
		}catch (Exception e) {
			e.printStackTrace();
			return l;
		}		
	}
	

	/**
	 * 设置各个部门的信件统计数--满意度作为条件
	 * @param int dpe_id 部门ID
	 * @param  Map map
	 * @return List<HandleBean>
	 */  
	public static List getPublicHandlSatisfactionDept(int dep_id, Map map){   
		List l = new ArrayList();
		try{
			String s = (String)map.get("s");
			String e = (String)map.get("e");
			String b_ids = (String)map.get("b_ids");
			
			s = CountUtil.getTimeS(s);//开始时间
		    e = CountUtil.getTimeE(e);//结束时间
		    if(b_ids.endsWith(",")){
			    b_ids = b_ids.substring(0,b_ids.length()-1);
		    }	
			  
			//总件数
			map.put("model_id", b_ids);
			map.put("do_dept", dep_id);
			
			//得到满意度列表
			List<Map>  listSatisfaction =com.deya.wcm.services.appeal.count.CountServicesUtil.getSatisfactionList();
			//已办结数
			map.put("sq_status", "2");  
 		    String countEnd = CountDAO.getCountByModelIdAndDept(map);
			HandleBean hb = new HandleBean();
			hb.setHandle_id("h000"+dep_id+"1");
			hb.setHandle_name(String.valueOf(countEnd));
			l.add(hb);
			
			//已评价信件数
 		    map.put("sat_score", "0");  
 		    String countRecord = CountDAO.getCountByModelIdAndDept(map);
 		    HandleBean hb1 = new HandleBean();
 		    hb1.setHandle_id("h000"+dep_id+"1");
 		    hb1.setHandle_name(String.valueOf(countRecord));
			l.add(hb1); 
			
    		int countAll = 0; 
    		for(int i=0;i<listSatisfaction.size();i++){   
    			   map.put("sat_id",(listSatisfaction.get(i)).get("id")); 
  		           String count = CountDAO.getCountByModelIdAndDeptSat(map);
  		           if(count==null || count.trim().equals("")){
    		    	  count="0";
    		       }
  		           HandleBean hbBean = new HandleBean();
  		           hbBean.setHandle_id("h000"+dep_id+"1");
  		           hbBean.setHandle_name(String.valueOf(count));
  				   l.add(hbBean);
  		           countAll += Integer.valueOf(count);
    		} 
    		
    		HandleBean hb4 = new HandleBean();
    		hb4.setHandle_id("h000"+dep_id+"1");
    		hb4.setHandle_name(String.valueOf(countAll));
    		l.add(hb4);
    		
			return l;
		}catch (Exception e) {
			e.printStackTrace();
			return l;
		}		
	}
}
