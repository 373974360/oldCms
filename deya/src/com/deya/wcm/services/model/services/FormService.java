package com.deya.wcm.services.model.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.services.model.Fields;
import com.deya.wcm.services.system.formodel.ModelManager;

/**
 *  元数据集操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 元数据集操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class FormService {

	
	private static Map mapLsit = new HashMap();
	
    //清空缓存
	public static void clearMapLsit(){
		mapLsit.clear();
	}
	
	//设置字段列表中的其他属性值
	public static List<Fields> listToResultFields(List<Fields> list){
		List<Fields> result = new ArrayList<Fields>();
		for(Fields field : list){
			String fieldXml = FieldsUtil.formatQuote((String)field.getField_info());
			field = FieldsUtil.getBeanByFieldXml(field, fieldXml,field.getField_type());
			if(field.getField_cnname()==null || "".equals(field.getField_cnname())){
				field.setField_cnname(field.getField_enname());
			}  
			Fields fields = FieldsService.getFieldById(""+field.getFrom_id());
			if(fields==null || fields.getField_cnname()==null || fields.getField_cnname().equals("")){
				field.setFrom_field_cnname("");
			}else{
				field.setFrom_field_cnname(fields.getField_cnname());
			} 
			result.add(field);
		}
		return result;
	}
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelId(Map map) {
		List<Fields> list = new ArrayList<Fields>();
		List<Fields> result = new ArrayList<Fields>();
		try{
			list = FormDao.getFormFieldsListByModelId(map);
			result = listToResultFields(list);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}
	
	/**
     * 得到列表 第三方资源库调用
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelIdN3(Map map) {
		List<Fields> list = new ArrayList<Fields>();
		List<Fields> result = new ArrayList<Fields>();
		list = FormDao.getFormFieldsListByModelId(map);
		result = listToResultFields(list);
		return result;
	}
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelIdN(Map map) {
		List<Fields> list = new ArrayList<Fields>();
		List<Fields> result = new ArrayList<Fields>();
		
		//从缓存里面取
		if(mapLsit.containsKey(map)){
			//System.out.println("----getFormFieldsListByModelIdN----从缓存中得到");
			return (List)mapLsit.get(map);
		}
		try{
			//list = FormDao.getFormFieldsListByModelIdN(map);
			list = FormDao.getFormFieldsListByModelId(map);
			result = listToResultFields(list);
			
			//加进缓存
			mapLsit.put(map, result);
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFormFieldsListByModelIdCount(Map map) {
		int count = 0;
		try{
			count = FormDao.getFormFieldsListByModelIdCount(map);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return count;
		}
	}
	
	//更新表结构
	public static boolean updateForm(Map map){
		
		try{		
			String ids = (String)map.get("ids"); //原来的元数据ID
			String cids = (String)map.get("cids"); //新的元数据ID
			String model_id = (String)map.get("model_id");//表单id
			
			List<String> addListtemp = Arrays.asList(cids.split(",")); //要添加的元数据ID
			List<String> addList = new ArrayList(addListtemp); 
			List<String> deleteListTemp = new ArrayList<String>();//要删除的元数据ID
			List<String> deleteList = new ArrayList(deleteListTemp); 
			
			//System.out.println(addList);
			
			List<String> idsList = Arrays.asList(ids.split(","));
			List<String> cidsList = Arrays.asList(cids.split(","));
			for(String idsStr : idsList){
				if(idsStr==null || "".equals(idsStr)){
					continue;
				}
				if(!cidsList.contains(idsStr)){
					//System.out.println("idsStr=====" + idsStr);
					deleteList.add(idsStr);
				}
				//System.out.println(idsStr);
			}
			addList.removeAll(idsList);
			
			//System.out.println("updateForm addList :: " + addList);
			//System.out.println("updateForm deleteList :: " + deleteList);
			//System.out.println("updateForm model_id :: " + model_id);
			
			//得到model对象
			ModelBean modelBean = ModelManager.getModelBean(Integer.parseInt(model_id));
			String table_name = modelBean.getTable_name();
			
			//删除字段
			StringBuffer sbDelete = new StringBuffer();
			//System.out.println("deleteList=====" + deleteList.size());
			if(deleteList.size()>0){
				Fields fields = null;
				for(int i=0;i<deleteList.size();i++){
					if(deleteList.get(i)==null || "".equals(deleteList.get(i))){
						continue;
					}
					if(i!=deleteList.size()-1){
						sbDelete.append(deleteList.get(i)+",");
					}else{
						sbDelete.append(deleteList.get(i));
					}
					
					
					
					 //把表中的字段删除 --- start
					 fields = FieldsService.getFieldById(deleteList.get(i));
					 //更改该内容模型表结构  
			     	 Map hmParam = new HashMap();   
			         hmParam.put("tableName",table_name);   
			         hmParam.put("fieldName",fields.getField_enname());  
			         //DBManager.update("formMapper.deleteField",hmParam);
			         FormUtil.deleteFieldDesc(hmParam);
					
			         
			         
				}
				//System.out.println("deleteFormFieldsByModeIdAndFromId mothed start");
				deleteFormFieldsByModeIdAndFromId(sbDelete.toString(),model_id);
			}
			
			//添加字段
			if(addList.size()>0){
				Fields fields = null;
				for(int i=0;i<addList.size();i++){
					if(addList.get(i)==null || "".equals(addList.get(i))){
						continue;
					}
					//得到元数据集对象
					fields = FieldsService.getFieldById(addList.get(i));
					fields.setFrom_id(Integer.valueOf(addList.get(i)));
					fields.setModel_id(Integer.valueOf(model_id));
					fields.setField_sort(9999);
					fields.setAdd_time(DateUtil.getCurrentDateTime());
					FormDao.addFields(fields);
					
					
					//在表中添加字段 --- start
					// 更新表结构
					Map hmParam = new HashMap();
					hmParam.put("tableName", table_name);
					hmParam.put("fieldName", fields.getField_enname().toLowerCase());
					if(fields.getField_type().equals("0")){
						hmParam.put("fieldSize","500");
						FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
					}else if(fields.getField_type().equals("1") || fields.getField_type().equals("2")){
						FormUtil.updateFieldDesc("addFieldLongtext", hmParam);
					}else if(fields.getField_type().equals("3")){
						hmParam.put("fieldSize", "20");
						FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
					}else if(fields.getField_type().equals("4")){
						hmParam.put("fieldSize", "20");
						FormUtil.updateFieldDesc("addFieldBigint", hmParam);
					}else if(fields.getField_type().equals("5")){
						hmParam.put("fieldSize", "255");
						FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
					}else if(fields.getField_type().equals("6")){
						hmParam.put("fieldSize", "3000");
						FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
					}

				}
			}

			//清空缓存
			mapLsit.clear();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	//删除信息
	public static boolean deleteFormFieldsByModeIdAndFromId(String ids,String model_id){
		try{	
			String id[]= ids.split(",");
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<id.length;i++){
	        	String s_id = id[i];
	        	if(s_id!=null && !"".equals(s_id)){
	        		if(i!=id.length-1){
	        			sb.append(""+s_id+""+",");
	        		}else{
	        			sb.append(""+s_id+"");
	        		}
	        	}
	        }
	        
	        //清空缓存
			mapLsit.clear();
	        
	        return FormDao.deleteFormFieldsByModeIdAndFromId(sb.toString(),model_id);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//保存排序
	public static boolean saveFormSort(String ids)
	{
		try{
			
			//清空缓存
			mapLsit.clear();
			
			return FormDao.saveFormSort(ids);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//查看信息
	public static Fields getFormFieldById(String id)
	{   
		Fields fields = new Fields();
		try{
			fields = FormDao.getFormFieldById(id); 
			String fieldXml = FieldsUtil.formatQuote((String)fields.getField_info());
			fields = FieldsUtil.getBeanByFieldXml(fields, fieldXml,fields.getField_type());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return fields;
		}
	}
	
	
	
	/**
     * 修改信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean updateFormFieldsById(Fields fields) {
		try{	
			String xmlFieldInfo = "";
			//System.out.println("update 1 ----" + fields.toString());
			String field_type = FieldsUtil.formatQuote(fields.getField_type());
			if(field_type.equals("0")){
				
				 String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				 String field_maxlength =FieldsUtil.formatQuote(fields.getField_maxlength());
				 String validator = FieldsUtil.formatQuote(fields.getValidator());
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("field_maxnum", field_maxnum);
				 map.put("field_maxlength", field_maxlength);
				 map.put("validator", validator);
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
							
			 }else if(field_type.equals("1")){
							
				 String width = FieldsUtil.formatQuote(fields.getWidth());
				 String height = FieldsUtil.formatQuote(fields.getHeight());
				 String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("width", width);
				 map.put("height", height);
				 map.put("field_maxnum", field_maxnum);
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
							
			 }else if(field_type.equals("2")){
							
				 String width = FieldsUtil.formatQuote(fields.getWidth());
				 String height = FieldsUtil.formatQuote(fields.getHeight());
				 String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("width", width);
				 map.put("height", height);
				 map.put("field_maxnum", field_maxnum);
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);				
			 }else if(field_type.equals("3")){
							
				 String select_item = FieldsUtil.formatQuote(fields.getSelect_item());
				 String select_view = FieldsUtil.formatQuote(fields.getSelect_view());
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 String data_type = FieldsUtil.formatQuote(fields.getData_type());
				 String data_type_id = FieldsUtil.formatQuote(fields.getData_type_id());
				 HashMap map = new HashMap();
				 map.put("select_item", select_item);
				 map.put("select_view", select_view);
				 map.put("default_value", default_value);
				 map.put("data_type", data_type);
				 map.put("data_type_id", data_type_id);
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);	
			 }else if(field_type.equals("4")){
							
				 String min_num = FieldsUtil.formatQuote(fields.getMin_num());
				 String max_num = FieldsUtil.formatQuote(fields.getMax_num());
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("min_num", min_num);
				 map.put("max_num", max_num);
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);		
			 }else if(field_type.equals("5")){
							
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
						
			 }else if(field_type.equals("6")){
							
				 String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				 HashMap map = new HashMap();
				 map.put("default_value", default_value);
								
				 //通过字段属性拼写xml字符串
				 xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
						
			 }
			fields.setField_info(xmlFieldInfo);
			//System.out.println("update 2 ----" + fields.toString());
			
			
			//得到表名
			 ModelBean modelBean = ModelManager.getModelBean(fields.getModel_id());
			//如果是自定义的字段，并且是多行文本和单行文本要更新数据库中内容模型表结构
			if(field_type.equals("1")||field_type.equals("2")){
					 //更新数据库中内容模型表结构
					String table_column = ""; 
					Map hmParam = new HashMap();   
			        hmParam.put("tableName", modelBean.getTable_name());   
			        hmParam.put("fieldName", fields.getField_enname()); 
			        FormManagerDAO.updateFieldDescByUpdate(hmParam);	    
			}  
			if(field_type.equals("0")){
					 //更新数据库中内容模型表结构
					Map hmParam = new HashMap();   
			        hmParam.put("tableName", modelBean.getTable_name());   
			        hmParam.put("fieldName", fields.getField_enname());
			        String field_maxnum = (String)fields.getField_maxnum();
			        hmParam.put("fieldSize", "4000");
			        FormManagerDAO.updateFieldDescByUpdateVarchar(hmParam);	    
			}
			
			//清空缓存
			mapLsit.clear();
			
	        return FormDao.updateFormFieldsById(fields);
	        
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFormFieldsListByFromIdsCount(String ids) {
        return FormDao.getFormFieldsListByFromIdsCount(ids.toString());     
	}
	
	
	//删除信息
	public static boolean deleteFormFields(String ids,String model_id){
		try{	
			String id[]= ids.split(",");
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<id.length;i++){
	        	String s_id = id[i];
	        	if(s_id!=null && !"".equals(s_id)){
	        		if(i!=id.length-1){
	        			sb.append(""+s_id+""+",");
	        		}else{
	        			sb.append(""+s_id+"");
	        		}
	        		
	        		//把表中的字段删除 --- start
	        		//得到model对象
	    			ModelBean modelBean = ModelManager.getModelBean(Integer.parseInt(model_id));
	    			String table_name = modelBean.getTable_name();
					Fields fields = getFormFieldById(s_id);
					//更改该内容模型表结构  
			     	 Map hmParam = new HashMap();   
			         hmParam.put("tableName",table_name);   
			         hmParam.put("fieldName",fields.getField_enname());  
			         //DBManager.update("formMapper.deleteField",hmParam);
			         FormUtil.deleteFieldDesc(hmParam); 
	        		
	        	}
	        }
	        
	        //清空缓存
			mapLsit.clear();
	        
	        return FormDao.deleteFormFields(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//设置信息列表header
	public static boolean updateFormFieldFlag(String field_flag,String action,String id){
		try{
			FormDao.updateFormFieldFlag(field_flag, action, id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String str[]){
		 Map map = new HashMap();
		 map.put("ids","");
		 map.put("cids","15,21,31");
		 
		 updateForm(map);
	}

}
