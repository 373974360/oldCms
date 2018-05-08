package com.deya.wcm.services.model.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.cms.info.InfoBaseRPC;
import com.deya.wcm.services.cms.info.ModelUtilRPC;
import com.deya.wcm.services.model.Fields;
import com.deya.wcm.services.system.formodel.ModelManager;


/**
 * 自定义数据操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 自定义数据操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class InfoCustomService {

	//添加自定义表单的数据
	public static boolean addInfoCuston(String model_id,Map map){
		try{
			//得到内容模型bean
			ModelBean modelBean = ModelManager.getModelBean(Integer.valueOf(model_id));
			String table_name = modelBean.getTable_name();
			insterInfoCustonByMapTableName(model_id,table_name,map);
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	//添加自定义表单的数据
	//sql inster into tablename (id,aa,bb,cc) values(id,'aa','bb','cc')
	public static boolean insterInfoCustonByMapTableName(String model_id,String tableName,Map map){
		try{
			//得到表单字段列表
			Map<String,String> mapFileType = new HashMap<String,String>();
			Map mapModel = new HashMap();
			mapModel.put("model_id", model_id);
			List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(mapModel);
			for(Fields f : fieldsList){
				mapFileType.put(f.getField_enname().toLowerCase(), f.getField_type());
			}
			
			StringBuffer sb_key = new StringBuffer("");
			StringBuffer sb_val = new StringBuffer("");
			Map map3 = MapManager.mapKeyValueToLow(map);
			//System.out.println("----insterInfoCustonByMapTableName----"+map3);
			Iterator it = map3.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				Object valO = (Object)map3.get(key);
				if(mapFileType.containsKey(key)){
					sb_key.append(key+",");
					if(mapFileType.get(key).equals("4")){//判断是不是字段类型是不是int
						sb_val.append((String)valO+",");
					}else{
						sb_val.append("'"+(String)valO+"',");
					}
				}else{
					if(key.equals("id")){
						    sb_key.append(key+",");
							sb_val.append((Integer)valO+",");
					}
				}
			 }
			String insertSql = "insert into "+tableName+" ("+sb_key.substring(0,sb_key.length()-1)+") values ("+sb_val.substring(0,sb_val.length()-1)+")";
			System.out.println("sql : " + insertSql);
			//String insertSql = "insert into "+tableName+" ("+sb_key.substring(0,sb_key.length()-1)+") values ("+sb_val.substring(0,sb_val.length()-1)+")";
			
			Map sqlMap = new HashMap();
			sqlMap.put("sql", insertSql);
			PublicTableDAO.createSql(sqlMap);
		    
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	*/
	
	//添加自定义表单的数据  -- 李苏培改
	//sql inster into tablename (id,aa,bb,cc) values(id,'aa','bb','cc')
	public static boolean insterInfoCustonByMapTableName(String model_id,String tableName,Map map){
		try{
			//得到表单字段列表
			Map<String,String> mapFileType = new HashMap<String,String>();
			Map mapModel = new HashMap();
			mapModel.put("model_id", model_id);
			List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(mapModel);
			for(Fields f : fieldsList){
				mapFileType.put(f.getField_enname().toLowerCase(), f.getField_type());
			}
			
			StringBuffer sb_key = new StringBuffer("");
			StringBuffer sb_val = new StringBuffer("");
			
			StringBuffer sb_val_files = new StringBuffer("");
			Map sqlMap = new HashMap();
			
			Map map3 = MapManager.mapKeyValueToLow(map);
			//System.out.println("----insterInfoCustonByMapTableName----"+map3);
			Iterator it = map3.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				Object valO = (Object)map3.get(key);
				if(mapFileType.containsKey(key)){
					sb_key.append(key+",");
					sb_val_files.append("#{"+key+"},");
					if(mapFileType.get(key).equals("4")){//判断是不是字段类型是不是int
						sb_val.append((String)valO+",");
						sqlMap.put(key, (String)valO);
					}else{
						sb_val.append("'"+(String)valO+"',");
						sqlMap.put(key, (String)valO);
					}
				}else{
					if(key.equals("id")){
						    sb_key.append(key+",");
						    sb_val_files.append("#{"+key+"},");
							sb_val.append((Integer)valO+",");
							sqlMap.put(key, (Integer)valO);
					}
				}
			 }
			String insertSql = "insert into "+tableName+" ("+sb_key.substring(0,sb_key.length()-1)+") values ("+sb_val_files.substring(0,sb_val_files.length()-1)+")";
			//System.out.println("sql : " + insertSql);
            //System.out.println("sqlMap : " + sqlMap);
			
			sqlMap.put("sql", insertSql);
			PublicTableDAO.createSql(sqlMap);
		    
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//修改自定义表单的数据
	public static boolean updateInfoCuston(String model_id,Map map){
		try{
			//得到内容模型bean
			ModelBean modelBean = ModelManager.getModelBean(Integer.valueOf(model_id));
			String table_name = modelBean.getTable_name();
			updateInfoCustonByMapTableName(model_id,table_name,map);
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	//修改自定义表单的数据
	//sql update tablename set aa='11',bb='22',cc=33 where id=123
	public static boolean updateInfoCustonByMapTableName(String model_id,String tableName,Map map){
		try{
			//得到表单字段列表
			Map<String,String> mapFileType = new HashMap<String,String>();
			Map mapModel = new HashMap();
			mapModel.put("model_id", model_id);
			List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(mapModel);
			for(Fields f : fieldsList){
				mapFileType.put(f.getField_enname().toLowerCase(), f.getField_type());
			}
			
			String info_id = "0";
			StringBuffer sb_update = new StringBuffer("");
			Map map3 = MapManager.mapKeyValueToLow(map);
			//System.out.println("updateInfoCustonByMapTableName()----map3 = " + map3);
			Iterator it = map3.keySet().iterator();
			//System.out.println("MapManager.mapKeyValueToLow(map):::"+MapManager.mapKeyValueToLow(map));
			while(it.hasNext()){
				String key = (String)it.next();
				Object valO = (Object)map3.get(key);
				if(key.equals("id")){
					info_id = (Integer)valO+"";
				}else{
					if(mapFileType.containsKey(key)){
						if(mapFileType.get(key).equals("4")){//判断是不是字段类型是不是int
							sb_update.append(" " + key + "=" +(String)valO+",");
						}else{
							sb_update.append(" " +key + "=" + "'"+(String)valO+"',");
						}
					}
				}
				
			}
			if(!sb_update.toString().trim().equals("")){
				String sql = "update "+tableName+" set "+sb_update.substring(0,sb_update.length()-1)+" where id="+info_id;
				System.out.println("sql info_id : " + info_id);

				PublicTableDAO.executeDynamicSql(sql);
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	*/
	
	//修改自定义表单的数据  -- 李苏培改
	//sql update tablename set aa='11',bb='22',cc=33 where id=123
	public static boolean updateInfoCustonByMapTableName(String model_id,String tableName,Map map){
		try{
			
			Map sqlMap = new HashMap();
			
			//得到表单字段列表
			Map<String,String> mapFileType = new HashMap<String,String>();
			Map mapModel = new HashMap();
			mapModel.put("model_id", model_id);
			List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(mapModel);
			for(Fields f : fieldsList){
				mapFileType.put(f.getField_enname().toLowerCase(), f.getField_type());
			}
			
			String info_id = "0";
			StringBuffer sb_update = new StringBuffer("");
			Map map3 = MapManager.mapKeyValueToLow(map);
			//System.out.println("updateInfoCustonByMapTableName()----map3 = " + map3);
			Iterator it = map3.keySet().iterator();
			//System.out.println("MapManager.mapKeyValueToLow(map):::"+MapManager.mapKeyValueToLow(map));
			while(it.hasNext()){
				String key = (String)it.next();
				Object valO = (Object)map3.get(key);
				if(key.equals("id")){
					info_id = (Integer)valO+"";
				}else{
					if(mapFileType.containsKey(key)){
						if(mapFileType.get(key).equals("4")){//判断是不是字段类型是不是int
							//sb_update.append(" " + key + "=" +(String)valO+",");
							sb_update.append(" " + key + "=#{" +key+"},");
							sqlMap.put(key, (String)valO);
						}else{ 
							//sb_update.append(" " +key + "=" + "'"+(String)valO+"',");
							sb_update.append(" " + key + "=#{" +key+"},");
							sqlMap.put(key, (String)valO);
						}
					}
				}
				
			}
			if(!sb_update.toString().trim().equals("")){
				String sql = "update "+tableName+" set "+sb_update.substring(0,sb_update.length()-1)+" where id="+info_id;
				//System.out.println("sql info_id : " + info_id);
				
				sqlMap.put("sql", sql);
				PublicTableDAO.createSql(sqlMap);
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//通过信息id得到cs_info主信息
	public static ArticleBean getArticle(String infoId){
		return InfoCustomDao.getArticle(infoId);
	}
	
	//通过信息id得到用户自定义数据
	public static Map getCustomInfoMap(String model_id,String info_id){
		Map map = new HashMap();
		try{
			ModelBean modelBean = ModelManager.getModelBean(Integer.valueOf(model_id));
            //System.out.println("model_idmodel_idmodel_idmodel_id-----------"+ model_id);
			String table_name = modelBean.getTable_name();
			String sql = "select * from "+ table_name + " where id="+info_id;
			map = InfoCustomDao.getMapBySql(sql);
			//System.out.println("getCustomInfoMap ---- map = " + map); 
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}
	
	
	//修改引用此信息的信息
	public static boolean updateQuoteInfoCustom(ArticleBean articleBean, Map cusBean,String model_ename,HttpServletRequest request){
		try{
			
			String info_id = articleBean.getInfo_id()+"";
			//得到引用该信息的信息列表
			List<InfoBean> quote_list = InfoBaseRPC.getQuoteInfoList(info_id);
			if(quote_list != null && quote_list.size() > 0)
			{
				for(InfoBean infoBean : quote_list){
					//System.out.println("infoBean.getInfo_id() = " + infoBean.getInfo_id());
					Object o = ModelUtilRPC.select(String.valueOf(infoBean.getInfo_id()),infoBean.getSite_id(),model_ename);
					if(o!=null){
						Map mapO = (Map)o;
						//将引用信息中不能修改的值放入到原Bean中，组成新Bean，并
						int id = infoBean.getInfo_id();
						articleBean.setId(infoBean.getInfo_id());
						articleBean.setInfo_id(Integer.valueOf(String.valueOf(mapO.get("info_id"))));
						articleBean.setCat_id(Integer.valueOf(String.valueOf(mapO.get("cat_id"))));
						articleBean.setFrom_id(Integer.valueOf(String.valueOf(mapO.get("from_id"))));
						articleBean.setContent_url((String)mapO.get("content_url"));
						articleBean.setWf_id(Integer.valueOf(String.valueOf(mapO.get("wf_id"))));
						articleBean.setStep_id(Integer.valueOf(String.valueOf(mapO.get("step_id"))));
						articleBean.setInfo_status(Integer.valueOf(String.valueOf(mapO.get("info_status"))));
						articleBean.setFinal_status(Integer.valueOf(String.valueOf(mapO.get("final_status"))));
						articleBean.setReleased_dtime((String)mapO.get("released_dtime"));		
						articleBean.setIs_auto_up(Integer.valueOf(String.valueOf(mapO.get("is_auto_up"))));	
						articleBean.setUp_dtime((String)mapO.get("up_dtime"));	
						articleBean.setIs_auto_down(Integer.valueOf(String.valueOf(mapO.get("is_auto_down"))));	
						articleBean.setDown_dtime((String)mapO.get("down_dtime"));	
						articleBean.setSite_id((String)mapO.get("site_id"));
						
						//更新info主信息表数据
						ModelUtilRPC.update(articleBean, model_ename, request);
						
						//更新自定义表数据  
						cusBean.put("id", id);
						InfoCustomRPC.updateInfoCuston(String.valueOf(mapO.get("model_id")),cusBean); 
					}
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//通过信息id得到cs_info主信息
	public static Map getGKInfo(String infoId){
		String sql = "select * from cs_gk_info where info_id="+infoId;
		return (Map)InfoCustomDao.getMapBySql(sql);
	}
	
	
	//修改引用此信息的信息  -- 公开用
	public static boolean updateQuoteInfoCustomGk(GKInfoBean gkInfoBean, Map cusBean,String model_ename,HttpServletRequest request){
		try{
			
			String info_id = gkInfoBean.getInfo_id()+"";
			//得到引用该信息的信息列表
			List<InfoBean> quote_list = InfoBaseRPC.getQuoteInfoList(info_id);
			if(quote_list != null && quote_list.size() > 0)
			{
				for(InfoBean infoBean : quote_list){
					//System.out.println("infoBean.getInfo_id() = " + infoBean.getInfo_id());
					Object o = ModelUtilRPC.select(String.valueOf(infoBean.getInfo_id()),infoBean.getSite_id(),model_ename);
					if(o!=null){
						Map mapO = (Map)o;
						//将引用信息中不能修改的值放入到原Bean中，组成新Bean，并
						int id = infoBean.getInfo_id();
						gkInfoBean.setId(infoBean.getInfo_id());
						gkInfoBean.setInfo_id(Integer.valueOf(String.valueOf(mapO.get("info_id"))));
						gkInfoBean.setCat_id(Integer.valueOf(String.valueOf(mapO.get("cat_id"))));
						gkInfoBean.setFrom_id(Integer.valueOf(String.valueOf(mapO.get("from_id"))));
						gkInfoBean.setContent_url((String)mapO.get("content_url"));
						gkInfoBean.setWf_id(Integer.valueOf(String.valueOf(mapO.get("wf_id"))));
						gkInfoBean.setStep_id(Integer.valueOf(String.valueOf(mapO.get("step_id"))));
						gkInfoBean.setInfo_status(Integer.valueOf(String.valueOf(mapO.get("info_status"))));
						gkInfoBean.setFinal_status(Integer.valueOf(String.valueOf(mapO.get("final_status"))));
						gkInfoBean.setReleased_dtime((String)mapO.get("released_dtime"));		
						gkInfoBean.setIs_auto_up(Integer.valueOf(String.valueOf(mapO.get("is_auto_up"))));	
						gkInfoBean.setUp_dtime((String)mapO.get("up_dtime"));	
						gkInfoBean.setIs_auto_down(Integer.valueOf(String.valueOf(mapO.get("is_auto_down"))));	
						gkInfoBean.setDown_dtime((String)mapO.get("down_dtime"));	
						gkInfoBean.setSite_id((String)mapO.get("site_id"));
						
						//更新info主信息表数据
						ModelUtilRPC.update(gkInfoBean, model_ename, request);
						
						//更新自定义表数据  
						cusBean.put("id", id);
						InfoCustomRPC.updateInfoCuston(String.valueOf(mapO.get("model_id")),cusBean); 
					}
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
