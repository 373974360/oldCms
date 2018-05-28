package com.deya.wcm.services.appeal.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.CalculateNumber;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.count.HandleBean;
import com.deya.wcm.bean.appeal.count.LetterCountBean;
import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpUser.CPUserReleInfo;
import com.deya.wcm.dao.appeal.count.CountDAO;


/**
 *  诉求统计分析类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求统计分析类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class CountServices {
       
	/**
	 * 按诉求目的-办理情况 得到统计列表
	 * @param String s  开始时间
	 * @param String e  结束时间
	 * @param String b_ids  业务id 以逗号分割
	 * @return List<Map>
	 */
     public static List<CountBean> getCountAimHandle(String s,String e,String b_ids){
    	 List<CountBean> list = new ArrayList<CountBean>();
    	 try{
    		   s = CountUtil.getTimeS(s);//开始时间
    		   e = CountUtil.getTimeE(e);//结束时间
    		   if(b_ids.endsWith(",")){
    			   b_ids = b_ids.substring(0,b_ids.length()-1);
    		   }
    		   
    		   //得到诉求目的列表
    		   List<Map> listPurpose = CountServicesUtil.getPurposeList();
    		   for(Map mapPurpose : listPurpose){
    			   String purposeId = (String)mapPurpose.get("id");
    			   String purposeName = (String)mapPurpose.get("name");
    			   
    			   CountBean countBean1 = new CountBean();
    			   //诉求目的名称
        		   countBean1.setPurpose_name(purposeName);
        		   
        		   
        		   
        		   Map map = new HashMap();
        		   map.put("model_id", b_ids); 
        		   map.put("pur_id", purposeId);
        		   map.put("s", s);
        		   map.put("e", e);
        		   //总件数
        		   String countAll = CountDAO.getCountByModelIdAndPur(map);
        		   //待处理信件
        		   map.put("sq_status", "0"); 
        		   String countDai = CountDAO.getCountByModelIdAndPurStatus(map);
        		   //处理中信件
        		   map.put("sq_status", "1");
        		   String countChu = CountDAO.getCountByModelIdAndPurStatus(map);
        		   //待审核信件
        		   map.put("sq_status", "2");
        		   String countShen = CountDAO.getCountByModelIdAndPurStatus(map);
        		   //已办结信件 
        		   map.put("sq_status", "3");  
        		   String countEnd = CountDAO.getCountByModelIdAndPurStatus(map);
        		   //办结率 
        		   String countEndRate = "0%";
        		   if(!"0".equals(countAll)){ 
        			   countEndRate = CalculateNumber.getRate(countEnd, countAll,0);
				    }  
        		     
        		   countBean1.setCountall(countAll);
        		   countBean1.setCountdai(countDai);
        		   countBean1.setCountchu(countChu);
        		   countBean1.setCountshen(countShen);
        		   countBean1.setCountend(countEnd);
        		   countBean1.setCountendp(countEndRate);
        		   list.add(countBean1);
    		   }
    		   
    		   return list;
    	 }catch (Exception t) {
			t.printStackTrace();
			return list;
		}
     }
     
     
     /**
 	 * 按诉求目的-信件类型 得到统计列表
 	 * @param String s  开始时间
 	 * @param String e  结束时间
 	 * @param String b_ids  业务id 以逗号分割
 	 * @return List<Map>
 	 */
      public static List<CountBean> getCountAimType(String s,String e,String b_ids){
     	 List<CountBean> list = new ArrayList<CountBean>();
     	 try{
     		   s = CountUtil.getTimeS(s);//开始时间
     		   e = CountUtil.getTimeE(e);//结束时间
     		   if(b_ids.endsWith(",")){
     			   b_ids = b_ids.substring(0,b_ids.length()-1);
     		   }
     		     
     		   //得到诉求目的列表
     		   List<Map> listPurpose = CountServicesUtil.getPurposeList();
     		   for(Map mapPurpose : listPurpose){
     			   String purposeId = (String)mapPurpose.get("id");
     			   String purposeName = (String)mapPurpose.get("name");
     			   
     			   CountBean countBean1 = new CountBean();
     			   //诉求目的名称
         		   countBean1.setPurpose_name(purposeName);
         		   
         		   Map map = new HashMap();
         		   map.put("model_id", b_ids); 
         		   map.put("pur_id", purposeId);
         		   map.put("s", s);
       		       map.put("e", e);
         		   //全部信件数
         		   String count = CountDAO.getCountByModelIdAndFlag(map);
         		   //正常信件
         		   map.put("sq_flag", "0");
         		   String countNormal = CountDAO.getCountByModelIdAndFlag(map);
         		   //无效信件
         		   map.put("sq_flag", "-1");
         		   String countInvalid = CountDAO.getCountByModelIdAndFlag(map);
         		   //重复信件
         		   map.put("sq_flag", "1");
         		   String countRepeat = CountDAO.getCountByModelIdAndFlag(map);
         		   //不予受理信件
         		   map.put("sq_flag", "2");
         		   String countNohandle = CountDAO.getCountByModelIdAndFlag(map);
         		  
         		   countBean1.setCount(count);
         		   countBean1.setCount_normal(countNormal);
         		   countBean1.setCount_invalid(countInvalid);
         		   countBean1.setCount_repeat(countRepeat);
         		   countBean1.setCount_nohandle(countNohandle);
         		   list.add(countBean1);
     		   }
     		   
     		   return list;
     	 }catch (Exception t) {
 			t.printStackTrace();
 			return list;
 		}
      }
      
      
      
      /**
   	 * 按诉求目的-警示类型 得到统计列表
   	 * @param String s  开始时间
   	 * @param String e  结束时间
   	 * @param String b_ids  业务id 以逗号分割
   	 * @return List<Map>
   	 */
        public static List<CountBean> getCountAimWarn(String s,String e,String b_ids){
       	 List<CountBean> list = new ArrayList<CountBean>();
       	 try{  
       		   s = CountUtil.getTimeS(s);//开始时间
       		   e = CountUtil.getTimeE(e);//结束时间
       		   if(b_ids.endsWith(",")){
       			   b_ids = b_ids.substring(0,b_ids.length()-1);
       		   }
       		     
       		   //得到诉求目的列表
       		   List<Map> listPurpose = CountServicesUtil.getPurposeList();
       		   for(Map mapPurpose : listPurpose){
       			   String purposeId = (String)mapPurpose.get("id");
       			   String purposeName = (String)mapPurpose.get("name");
       			   
       			   CountBean countBean1 = new CountBean();
       			   //诉求目的名称
           		   countBean1.setPurpose_name(purposeName);
           		   
           		   Map map = new HashMap();
           		   map.put("model_id", b_ids); 
           		   map.put("pur_id", purposeId);
           		   map.put("s", s);
         		   map.put("e", e);
           		   //全部信件数 
           		   String count = CountDAO.getCountByModelIdAndAlarm(map);
           		   //超期件
           		   map.put("timeout_flag", "1");
           		   String countOver = CountDAO.getCountByModelIdAndAlarm(map);
           		   //预警件
           		   map.remove("timeout_flag");
           		   map.put("alarm_flag", "1");
           		   String countWarn = CountDAO.getCountByModelIdAndAlarm(map);
           		   //黄牌件
           		   map.put("alarm_flag", "2");
           		   String countYellow = CountDAO.getCountByModelIdAndAlarm(map);
           		   //红牌件
           		   map.put("alarm_flag", "3"); 
           		   String countRed = CountDAO.getCountByModelIdAndAlarm(map);
           		   //督办件
           		   map.remove("alarm_flag");
           		   map.put("supervise_flag", "1");
           		   String countSupervise = CountDAO.getCountByModelIdAndAlarm(map);
           		   //延期件
           		   map.remove("supervise_flag");
           		   map.put("limit_flag", "1");
        		   String countLimit = CountDAO.getCountByModelIdAndAlarm(map);
           		   
           		   countBean1.setCount(count);
           		   countBean1.setCount_over(countOver);
           		   countBean1.setCount_warn(countWarn);
           		   countBean1.setCount_yellow(countYellow);
           		   countBean1.setCount_red(countRed);
           		   countBean1.setCount_supervise(countSupervise);
           		   countBean1.setCount_limit(countLimit); 
           		   
           		   list.add(countBean1);
       		   }
       		   
       		   return list;
       	 }catch (Exception t) {
   			t.printStackTrace();
   			return list;
   		}
        }
	
        
    	/**
    	 * 按处理单位-办理情况 得到统计列表
    	 * @param String s  开始时间
    	 * @param String e  结束时间
    	 * @param String b_ids  业务id 以逗号分割
    	 * @return List<Map>
    	 */
         public static List<CountBean> getCountDeptHandle(String s,String e,String b_ids){
        	 List<CountBean> list = new ArrayList<CountBean>();
        	 try{ 
        		   s = CountUtil.getTimeS(s);//开始时间
        		   e = CountUtil.getTimeE(e);//结束时间
        		   if(b_ids.endsWith(",")){
        			   b_ids = b_ids.substring(0,b_ids.length()-1);
        		   }
        		   
        		   
        		   //得到部门列表
        		   List<CpDeptBean> listDept = CountServicesUtil.getDeptmentList();
        		   for(CpDeptBean deptBean : listDept){
        			   String dep_id = String.valueOf(deptBean.getDept_id());
        			   CountBean countBean1 = new CountBean();
        			   
        			   //处理单位名称
            		   countBean1.setDept_name(deptBean.getDept_name());
            		   
            		   //节点深度
            		   countBean1.setNode_level(deptBean.getDept_level());
            		   
            		   Map map = new HashMap();
            		   map.put("model_id", b_ids); 
            		   map.put("s", s);
            		   map.put("e", e);
            		   map.put("do_dept", dep_id);
            		   map.put("sq_flag","0");
            		   //总件数
            		   String countAll = CountDAO.getCountByModelIdAndDept(map);
            		   
            		   //待处理信件
            		   map.put("sq_status", "0");
            		   String countDai = CountDAO.getCountByModelIdAndDept(map);
            		   //处理中信件
            		   map.put("sq_status", "1");
            		   String countChu = CountDAO.getCountByModelIdAndDept(map);
            		   //待审核信件
            		   map.put("sq_status", "2");
            		   String countShen = CountDAO.getCountByModelIdAndDept(map);
            		   //已办结信件   
            		   map.put("sq_status", "3");  
            		   String countEnd = CountDAO.getCountByModelIdAndDept(map);
            		   
            		   //按时办结数
            		   //private String count_over;//超期件
            		   //private String count_warn;//预警件
            		   //private String count_yellow;//黄牌件
            		   map.put("sq_status", "3");
           			   map.put("timeout_flag", "0");
           			   String countend_2 = CountDAO.getCountByModelIdAndDept(map);
           			   countBean1.setCount_over(countend_2);//按时办结数
           			   //超期办结数
           			   countBean1.setCount_warn((Integer.parseInt(countEnd) - Integer.parseInt(countend_2))+"");
           			   //按时办结率
	           			String countEndRate_4 = "0%";
	        			if(countAll!=null && !countAll.equals("0")){
	        				countEndRate_4 = CalculateNumber.getRate(countend_2, countAll,0);
	        			}
           			   countBean1.setCount_yellow(countEndRate_4+"");
           			   
            		   //办结率 
            		   String countEndRate = "0%";
            		   if(!"0".equals(countAll)){ 
            			   countEndRate = CalculateNumber.getRate(countEnd, countAll,0);
    				    }  
            		       
            		   countBean1.setCountall(countAll);
            		   countBean1.setCountdai(countDai);
            		   countBean1.setCountchu(countChu);
            		   countBean1.setCountshen(countShen);
            		   countBean1.setCountend(countEnd);
            		   int countwei = Integer.parseInt(countDai) + Integer.parseInt(countShen)+ Integer.parseInt(countChu);
            		   countBean1.setCountwei(countwei + "");
            		   countBean1.setCountendp(countEndRate);
            		   list.add(countBean1);
        		   }
        		   
        		   return list;
        	 }catch (Exception t) {
    			t.printStackTrace();
    			return list;
    		}
         }
         
         /**
     	 * 按处理人-办理情况 得到统计列表
     	 * @param String s  开始时间
     	 * @param String e  结束时间
     	 * @param String b_ids  业务id 以逗号分割
     	 * @return List<Map>
     	 */
          public static List<CountBean> getCountUsersHandle(String s,String e,String b_ids){
         	 List<CountBean> list = new ArrayList<CountBean>();
         	 try{ 
         		   s = CountUtil.getTimeS(s);//开始时间
         		   e = CountUtil.getTimeE(e);//结束时间
         		   if(b_ids.endsWith(",")){
         			   b_ids = b_ids.substring(0,b_ids.length()-1);
         		   } 
         		   //得到用户列表
         		   List<CPUserReleInfo> listUser = CountServicesUtil.getCPUserReleList();
         		   for(CPUserReleInfo CPUser : listUser){
         			   String user_id = String.valueOf(CPUser.getUser_id());
         			   CountBean countBean1 = new CountBean();
         			   //处理人
         			   countBean1.setUser_id(user_id);
             		   countBean1.setUser_realname(CPUser.getUser_realname());
             		    
             		   Map map = new HashMap();
             		   map.put("model_id",b_ids); 
             		   map.put("s", s);
             		   map.put("e", e);
             		   map.put("user_id",user_id);
             		   map.put("sq_flag","0");
             		   //总件数
             		   String countAll = CountDAO.getCountByModelIdAndUserID(map); 
             		   
             		   //待处理信件
            		   map.put("sq_status", "0");
            		   String countDai = CountDAO.getCountByModelIdAndUserID(map);
            		   //处理中信件
            		   map.put("sq_status", "1");
            		   String countChu = CountDAO.getCountByModelIdAndUserID(map);
            		   //待审核信件
            		   map.put("sq_status", "2");
            		   String countShen = CountDAO.getCountByModelIdAndUserID(map);
            		   //已办结信件   
            		   map.put("sq_status", "3");  
            		   String countEnd = CountDAO.getCountByModelIdAndUserID(map);
             		               		   
             		   //办结率 
             		   String countEndRate = "0%";
             		   if(!"0".equals(countAll)){ 
             		  	 countEndRate = CalculateNumber.getRate(countEnd,countAll,0);
     				  }  
             		       
             		   countBean1.setCountall(countAll);  
             		   countBean1.setCountdai(countDai);
             		   countBean1.setCountchu(countChu);
             		   countBean1.setCountshen(countShen);
             		   countBean1.setCountend(countEnd);
             		   int countwei = Integer.parseInt(countDai) + Integer.parseInt(countShen)+ Integer.parseInt(countChu);
             		   countBean1.setCountwei(countwei + "");
             		   countBean1.setCountendp(countEndRate);
             		   list.add(countBean1);
         		   }
         		   
         		   return list;
         	 }catch (Exception t) {
     			t.printStackTrace();
     			return list;
     		}
          }
         
       	/**
     	 * 按处理单位-信件类型   得到统计列表
     	 * @param String s  开始时间
     	 * @param String e  结束时间
     	 * @param String b_ids  业务id 以逗号分割
     	 * @return List<Map>
     	 */  
          public static List<CountBean> getCountDeptType(String s,String e,String b_ids){
         	 List<CountBean> list = new ArrayList<CountBean>();
         	 try{ 
         		   s = CountUtil.getTimeS(s);//开始时间
         		   e = CountUtil.getTimeE(e);//结束时间
         		   if(b_ids.endsWith(",")){
         			   b_ids = b_ids.substring(0,b_ids.length()-1);
         		   }
         		   
         		   
         		   //得到部门列表
         		   List<CpDeptBean> listDept = CountServicesUtil.getDeptmentList();
         		   for(CpDeptBean deptBean : listDept){
         			   String dep_id = String.valueOf(deptBean.getDept_id());
         			   CountBean countBean1 = new CountBean();
         			   //处理单位名称
             		   countBean1.setDept_name(deptBean.getDept_name());
             		   
             		   Map map = new HashMap();
             		   map.put("model_id", b_ids); 
             		   map.put("s", s);
             		   map.put("e", e);
             		   map.put("do_dept", dep_id);
             		   //全部信件数
             		   String count = CountDAO.getCountByModelIdAndDept(map);
             		   
             		   //正常信件
             		   map.put("sq_flag", "0");
             		   String countNormal = CountDAO.getCountByModelIdAndDept(map);
             		   //无效信件
             		   map.put("sq_flag", "-1");
             		   String countInvalid = CountDAO.getCountByModelIdAndDept(map);
             		   //重复信件
             		   map.put("sq_flag", "1");
             		   String countRepeat = CountDAO.getCountByModelIdAndDept(map);
             		   //不予受理信件
             		   map.put("sq_flag", "2");
             		   String countNohandle = CountDAO.getCountByModelIdAndDept(map); 
             		       
             		   countBean1.setCount(count);
            		   countBean1.setCount_normal(countNormal);
            		   countBean1.setCount_invalid(countInvalid);
            		   countBean1.setCount_repeat(countRepeat);
            		   countBean1.setCount_nohandle(countNohandle);
            		   list.add(countBean1);
         		   }
         		   
         		   return list;
         	 }catch (Exception t) {
     			t.printStackTrace();
     			return list;
     		}
          }
          
          
      	/**
       	 * 按处理单位-信件类型   得到统计列表
       	 * @param String s  开始时间
       	 * @param String e  结束时间
       	 * @param String b_ids  业务id 以逗号分割
       	 * @return List<Map>
       	 */  
            public static List<CountBean> getCountDeptCaution(String s,String e,String b_ids){
           	 List<CountBean> list = new ArrayList<CountBean>();
           	 try{ 
           		   s = CountUtil.getTimeS(s);//开始时间
           		   e = CountUtil.getTimeE(e);//结束时间 
           		   if(b_ids.endsWith(",")){
           			   b_ids = b_ids.substring(0,b_ids.length()-1);
           		   }
           		   
           		   
           		   //得到部门列表
           		   List<CpDeptBean> listDept = CountServicesUtil.getDeptmentList();
           		   for(CpDeptBean deptBean : listDept){
           			   String dep_id = String.valueOf(deptBean.getDept_id());
           			   CountBean countBean1 = new CountBean();
           			   //处理单位名称
               		   countBean1.setDept_name(deptBean.getDept_name());
               		   
               		   Map map = new HashMap();
               		   map.put("model_id", b_ids); 
               		   map.put("s", s);
               		   map.put("e", e);
               		   map.put("do_dept", dep_id);
               		   //全部信件数
               		   String count = CountDAO.getCountByModelIdAndDept(map);
               		   
               		   //超期件
               		   map.put("timeout_flag", "1");
               		   String countOver = CountDAO.getCountByModelIdAndDept(map);
               		   //预警件
               		   map.remove("timeout_flag");
               		   map.put("alarm_flag", "1");
               		   String countWarn = CountDAO.getCountByModelIdAndDept(map);
               		   //黄牌件
               		   map.put("alarm_flag", "2");
               		   String countYellow = CountDAO.getCountByModelIdAndDept(map);
               		   //红牌件
               		   map.put("alarm_flag", "3"); 
               		   String countRed = CountDAO.getCountByModelIdAndDept(map);
               		   //督办件
               		   map.remove("alarm_flag");
               		   map.put("supervise_flag", "1");
               		   String countSupervise = CountDAO.getCountByModelIdAndDept(map);
               		   //延期件  
               		   map.remove("supervise_flag");
               		   map.put("limit_flag", "1");
            		   String countLimit = CountDAO.getCountByModelIdAndDept(map);
               		   
               		   countBean1.setCount(count);
               		   countBean1.setCount_over(countOver);
               		   countBean1.setCount_warn(countWarn);
               		   countBean1.setCount_yellow(countYellow);
               		   countBean1.setCount_red(countRed);
               		   countBean1.setCount_supervise(countSupervise);
               		   countBean1.setCount_limit(countLimit);
              		   list.add(countBean1);
           		   }
           		   
           		   return list;
           	 }catch (Exception t) {
       			t.printStackTrace();
       			return list;
       		}
      }
            
            
            /**
           	 * 按处理单位-诉求目的  得到统计列表
           	 * @param String s  开始时间
           	 * @param String e  结束时间
           	 * @param String b_ids  业务id 以逗号分割
           	 * @return List<Map>
           	 */  
            public static List<Object[]> getCountDeptPur(String s,String e,String b_ids){
           	 List<Object[]> list = new ArrayList<Object[]>();
           	 try{ 
           		   s = CountUtil.getTimeS(s);//开始时间
           		   e = CountUtil.getTimeE(e);//结束时间 
           		   if(b_ids.endsWith(",")){
           			   b_ids = b_ids.substring(0,b_ids.length()-1);
           		   }
           		   
           		   //得到诉求目的列表
           		   List<Map>  listPurpose =com.deya.wcm.services.appeal.count.CountServicesUtil.getPurposeList();
           		   
           		   //得到部门列表
           		   List<CpDeptBean> listDept = CountServicesUtil.getDeptmentList();
           		   for(CpDeptBean deptBean : listDept){
           			   String dep_id = String.valueOf(deptBean.getDept_id());
           			   Object[] objects = new Object[listPurpose.size()+2];
           			   objects[0] = deptBean.getDept_name();
               		     
               		   Map map = new HashMap();
               		   map.put("model_id", b_ids); 
               		   map.put("s", s);
               		   map.put("e", e);
               		   map.put("do_dept", dep_id);
               		   
               		   int countAll = 0;
               		   
               		   for(int i=0;i<listPurpose.size();i++){   
               			   map.put("pur_id",(listPurpose.get(i)).get("id")); 
             		       String count = CountDAO.getCountByModelIdAndDept(map);  
             		       objects[i+2] = count;
             		       countAll += Integer.valueOf(count);
               		   }
               		   objects[1] = String.valueOf(countAll);
               		   list.add(objects);
           		   }
               		   
               		   return list;
               	 }catch (Exception t) {
					t.printStackTrace();
					return list;
				}
           }
            
            
            /**
           	 * 满意度统计-处理部门  得到统计列表
           	 * @param String s  开始时间
           	 * @param String e  结束时间
           	 * @param String b_ids  业务id 以逗号分割
           	 * @return List<Map>
           	 */   
            public static List<Object[]> getCountRecordDept(String s,String e,String b_ids){
           	 List<Object[]> list = new ArrayList<Object[]>();
           	 try{  
           		   s = CountUtil.getTimeS(s);//开始时间
           		   e = CountUtil.getTimeE(e);//结束时间 
           		   if(b_ids.endsWith(",")){
           			   b_ids = b_ids.substring(0,b_ids.length()-1);
           		   }
           		   
           		   //得到满意度列表
           		   List<Map>  listSatisfaction =com.deya.wcm.services.appeal.count.CountServicesUtil.getSatisfactionList();
           		   
           		   //得到部门列表 
           		   List<CpDeptBean> listDept = CountServicesUtil.getDeptmentList();
           		   for(CpDeptBean deptBean : listDept){
           			   String dep_id = String.valueOf(deptBean.getDept_id());
           			   Object[] objects = new Object[listSatisfaction.size()+4];
           			   objects[0] = deptBean.getDept_name();
               		        
               		   Map map = new HashMap();
               		   map.put("model_id", b_ids); 
               		   map.put("s", s);
               		   map.put("e", e);
               		   map.put("do_dept", dep_id);
               		   
               		   //已办结信件 
            		   map.put("sq_status", "2");  
            		   String countEnd = CountDAO.getCountByModelIdAndDept(map);
            		   objects[1] = countEnd;
            		   //已评价信件数
            		   map.put("sat_score", "0");  
            		   String countRecord = CountDAO.getCountByModelIdAndDept(map);
            		   objects[2] = countRecord;
            		   
            		   int countAll = 0;
               		   for(int i=0;i<listSatisfaction.size();i++){   
               			   map.put("sat_id",(listSatisfaction.get(i)).get("id")); 
             		       String count = CountDAO.getCountByModelIdAndDeptSat(map);  
             		       if(count==null || count.trim().equals("")){
             		    	  count="0"; 
             		       }
             		       objects[i+3] = count;
             		       countAll += Integer.valueOf(count);
               		   } 
               		   objects[listSatisfaction.size()+3] = String.valueOf(countAll);
               		   list.add(objects);
           		   } 
               		   
               		   return list;
               	 }catch (Exception t) {
					t.printStackTrace();
					return list;
				}
           }
            
            
            /**
        	 * 按满意度统计-诉求目的  得到统计列表
        	 * @param String s  开始时间
        	 * @param String e  结束时间
        	 * @param String b_ids  业务id 以逗号分割
        	 * @return List<Map>
        	 */
             public static List<Object[]> getCountRecordPur(String s,String e,String b_ids){
            	 List<Object[]> list = new ArrayList<Object[]>();
            	 try{
            		   s = CountUtil.getTimeS(s);//开始时间
            		   e = CountUtil.getTimeE(e);//结束时间
            		   if(b_ids.endsWith(",")){
            			   b_ids = b_ids.substring(0,b_ids.length()-1);
            		   }
            		   
            		   //得到满意度列表
               		   List<Map>  listSatisfaction =com.deya.wcm.services.appeal.count.CountServicesUtil.getSatisfactionList();
            		   
            		   //得到诉求目的列表
            		   List<Map> listPurpose = CountServicesUtil.getPurposeList();
            		   for(Map mapPurpose : listPurpose){
            			   String purposeId = (String)mapPurpose.get("id");
            			   String purposeName = (String)mapPurpose.get("name");
            			   
            			   Object[] objects = new Object[listSatisfaction.size()+4];
            			   //诉求目的名称
            			   objects[0] = purposeName;
                		   
                		   Map map = new HashMap();
                		   map.put("model_id", b_ids); 
                		   map.put("pur_id", purposeId);
                		   map.put("s", s);
                		   map.put("e", e);
                		   //已办结总件数
                		   map.put("sq_status", "3");
                		   String countEnd = CountDAO.getCountByModelIdAndDept(map);
                		   objects[1] = countEnd;
                		   //已评价信件数
                		   map.put("sat_score", "0");  
                		   String countRecord = CountDAO.getCountByModelIdAndDept(map);
                		   objects[2] = countRecord;
                		  
                		   int countAll = 0;
                   		   for(int i=0;i<listSatisfaction.size();i++){   
                   			   map.put("sat_id",(listSatisfaction.get(i)).get("id")); 
                 		       String count = CountDAO.getCountByModelIdAndDeptSat(map);  
                 		       if(count==null || count.trim().equals("")){
                 		    	  count="0"; 
                 		       }
                 		       objects[i+3] = count;
                 		       countAll += Integer.valueOf(count);
                   		    } 
                   		    objects[listSatisfaction.size()+3] = String.valueOf(countAll);
                   		    list.add(objects);
                   		   
            		   }  
            		   return list;
            	 }catch (Exception t) {
        			t.printStackTrace();
        			return list;
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
         			list = CountDAO.getListByModelIdAndDept(map);
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
        			count = CountDAO.getListByModelIdAndDeptCount(map);
         			return count;
         		}catch (Exception e) {
					e.printStackTrace();
					return count;
				}
        	}
        	
        	
        	/**
         	 * 通过  时间，业务ID,用户ID， 得到信件列表 数目
         	 * @param Map map
         	 * @return List<LetterCountBean>
         	 */
         	public static List<LetterCountBean> getListByModelIdAndUserId(Map map){
         		List<LetterCountBean> list = new ArrayList<LetterCountBean>();
         		try{
         			list = CountDAO.getListByModelIdAndUserID(map);
         			return list;
         		}catch (Exception e) {
					e.printStackTrace();
					return list;
				}
         	}
         	
        	/**
        	 * 通过  时间，业务ID，用户ID ，得到信件列表 数目 
        	 * @param Map map
        	 * @return String 
        	 */
        	public static String getListByModelIdAndUserCount(Map map){
        		String count = "0";
        		try{
        			count = CountDAO.getListByModelIdAndUserIDCount(map);
         			return count;
         		}catch (Exception e) {
					e.printStackTrace();
					return count;
				}
        	}
}
