package com.deya.wcm.services.model.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.system.formodel.ModelDAO;
import com.deya.wcm.services.model.Fields;

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
public class FieldsService {
	
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFieldsList(Map map) {
		List<Fields> list = new ArrayList<Fields>();
		try{
			list = FieldsDao.getFieldsList(map);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
		
	}
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFieldsListCount(Map map) {
		int count = 0;
		try{
			count = FieldsDao.getFieldsListCount(map);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return count;
		}
	}
	
	
	/**
     * 插入信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean addFields(Fields fields) {
		try{
			String field_type = fields.getField_type();
			String xmlFieldInfo = "";
			if (field_type.equals("0")) {
                
				String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				String field_maxlength = FieldsUtil.formatQuote(fields.getField_maxlength());
				String validator = FieldsUtil.formatQuote(fields.getValidator());
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("field_maxnum", field_maxnum);
				map.put("field_maxlength", field_maxlength);
				map.put("validator", validator);
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
                
			}else if (field_type.equals("1")) {
                
				String width = FieldsUtil.formatQuote(fields.getWidth());
				String height = FieldsUtil.formatQuote(fields.getHeight());
				String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("width", width);
				map.put("height", height);
				map.put("field_maxnum", field_maxnum);
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
                
			}else if (field_type.equals("2")) {
                
				String width = FieldsUtil.formatQuote(fields.getWidth());
				String height = FieldsUtil.formatQuote(fields.getHeight());
				String field_maxnum = FieldsUtil.formatQuote(fields.getField_maxnum());
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("width", width);
				map.put("height", height);
				map.put("field_maxnum", field_maxnum);
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
                
			}else if (field_type.equals("3")) {
                
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

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
           

			}else if (field_type.equals("4")) {
                
				String min_num = FieldsUtil.formatQuote(fields.getMin_num());
				String max_num = FieldsUtil.formatQuote(fields.getMax_num());
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("min_num", min_num);
				map.put("max_num", max_num);
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
			}else if (field_type.equals("5")) {
                
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
			}else if (field_type.equals("6")) {
                
				String default_value = FieldsUtil.formatQuote(fields.getDefault_value());
				HashMap map = new HashMap();
				map.put("default_value", default_value);

				// 通过字段属性拼写xml字符串
				xmlFieldInfo = FieldsUtil.getFieldXml(map, field_type);
			}
			
			fields.setField_info(xmlFieldInfo);
			fields.setAdd_time(DateUtil.getCurrentDateTime());
			
			return FieldsDao.addFields(fields);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//删除信息
	public static boolean deleteFields(String ids){
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
	        
	        int numCount = FormService.getFormFieldsListByFromIdsCount(sb.toString());
	        if(numCount>0){
	        	return false;
	        }
	        
	        return FieldsDao.deleteFields(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//查看信息
	public static Fields getFieldById(String id)
	{   
		Fields fields = new Fields();
		try{
			fields = FieldsDao.getFieldById(id);
			if(fields==null){
				return null;
			}
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
	public static boolean updateFieldsById(Fields fields) {
		try{	
			String xmlFieldInfo = "";
			System.out.println("update 1 ----" + fields.toString());
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
			System.out.println("update 2 ----" + fields.toString());
	        return FieldsDao.updateFieldsById(fields);
	        
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
