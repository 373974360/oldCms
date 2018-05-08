package com.deya.wcm.services.model.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.model.Fields;


/**
 * 内容模型辅助类.
 * <p>Title: ModelUtil</p>
 * <p>Description: 内容模型辅助类。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class FormUtil {
	
	
	 /**
     * 得到唯一标识码
     * @return String
     */
    public static synchronized String getUniqueString(){
    	String uniqueNumber = java.util.UUID.randomUUID().toString();
        return uniqueNumber;

    }
    
	/**
     * null 转 ""
     * @param s String
     * @return String
     */
    public static String formatQuote(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }
	
	
	/**
     * 生成内容模型的表
     * @param tableName 要创建的表名
     * @return boolean false：失败；true：成功
     */
	public static boolean createTable(String tableName){
	   try{
			Map hmParam = new HashMap();   
	        hmParam.put("tableName", tableName);   
	        FormManagerDAO.createTable(hmParam);
		 return true;
	   }catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
		 return false;
       }	
	}
    
	
	/**
     * 删除内容模型的表
     * @param tableName 要删除的表名
     * @param model_id 内容模型id
     * @return boolean false：失败；true：成功
     */
	public static boolean dropTable(String tableName){
	   try{
		 
		 //删除内容模型表
		 HashMap hmParam = new HashMap();   
         hmParam.put("tableName", tableName);   
         DBManager.update("formMapper.dropTable",hmParam);
		   
		 return true;
	   }catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
		 return false;
       }	
	
	}
	
	
	//删除表结构字段 
	 public static boolean deleteFieldDesc(Map map){
		 return FormManagerDAO.deleteFieldDesc(map);
	 }
	
	 
	//添加时更新表结构
	public static boolean updateFieldDesc(String type,Map map){
		return FormManagerDAO.updateFieldDesc(type, map);
	}
	/**
     * 初始化内容模型的表字段
     * @param model_id 内容模型id
     * @return boolean false：失败；true：成功
     */
	/*
	public static boolean initModelFields(String model_id){
		
		try{
			
			List<Fields> list = new ArrayList<Fields>();
			
			Fields fields = new Fields();
			fields.setModel_id(model_id);
			String fields_id = ModelUtil.getUniqueString();
			fields.setFields_id(fields_id);
			fields.setFiled_enname("cicro_cp_address");
			fields.setField_cnname("地区");  
			fields.setField_type("8");
			fields.setField_maxnum("200");
			fields.setIs_sys("0");
			fields.setIs_null("0");
			fields.setIs_display("1");
			String fieldInfo = "<fieldInfo><field_maxnum>160</field_maxnum><field_maxlength>30</field_maxlength><validator></validator><default_value></default_value></fieldInfo>";
			fields.setFieldInfo(fieldInfo);
			list.add(fields);    
			
			//初始化字段部门
			Fields fields2 = new Fields();
			fields2.setModel_id(model_id);
			fields_id = ModelUtil.getUniqueString();
			fields2.setFields_id(fields_id);
			fields2.setFiled_enname("cicro_cp_department");
			fields2.setField_cnname("部门");  
			fields2.setField_type("9");
			fields2.setField_maxnum("");
			fields2.setIs_sys("0");
			fields2.setIs_null("0");
			fields2.setIs_display("1");
			fieldInfo = "<fieldInfo><select_item></select_item><select_view>0</select_view><default_value></default_value></fieldInfo>";
			fields2.setFieldInfo(fieldInfo);
			list.add(fields2);  
			
			//初始化名字字段
			Fields fields3 = new Fields();
			fields3.setModel_id(model_id);
			fields_id = ModelUtil.getUniqueString();
			fields3.setFields_id(fields_id);
			fields3.setFiled_enname("cicro_cp_name");
			fields3.setField_cnname("申请人");  
			fields3.setField_type("0");
			fields3.setField_maxnum("255");
			fields3.setIs_sys("0");
			fields3.setIs_null("0");
			fields3.setIs_display("1");  
			fieldInfo = "<fieldInfo><field_maxnum>255</field_maxnum><field_maxlength>100</field_maxlength><validator></validator><default_value></default_value></fieldInfo>";
			fields3.setFieldInfo(fieldInfo);
			list.add(fields3); 
			
			//初始化名字字段
			Fields fields4 = new Fields();
			fields4.setModel_id(model_id);
			fields_id = ModelUtil.getUniqueString();
			fields4.setFields_id(fields_id);
			fields4.setFiled_enname("cicro_cp_title");
			fields4.setField_cnname("标题");  
			fields4.setField_type("0");
			fields4.setField_maxnum("255");
			fields4.setIs_sys("0");
			fields4.setIs_null("0");
			fields4.setIs_display("1");  
			fieldInfo = "<fieldInfo><field_maxnum>255</field_maxnum><field_maxlength>200</field_maxlength><validator></validator><default_value></default_value></fieldInfo>";
			fields4.setFieldInfo(fieldInfo);
			list.add(fields4);
			
			//初始化是否公开字段---单选按钮
			Fields fields5 = new Fields();
			fields5.setModel_id(model_id);
			fields_id = ModelUtil.getUniqueString();
			fields5.setFields_id(fields_id);
			fields5.setFiled_enname("cicro_is_public");
			fields5.setField_cnname("是否公开");  
			fields5.setField_type("3");
			//fields4.setField_maxnum("255");
			fields5.setIs_sys("0");
			//fields4.setIs_null("0");
			fields5.setIs_display("1");  
			fieldInfo = "<fieldInfo><data_type>1</data_type><data_type_id></data_type_id><select_item>否|0,是|1</select_item><select_view>2</select_view><default_value>0</default_value></fieldInfo>";
			fields5.setFieldInfo(fieldInfo);
			list.add(fields5);
			
			//初始化诉求类型字段---下拉框（默认）
			Fields fields6 = new Fields();
			fields6.setModel_id(model_id);
			fields_id = ModelUtil.getUniqueString();
			fields6.setFields_id(fields_id);
			fields6.setFiled_enname("cicro_b_type_id");
			fields6.setField_cnname("诉求类型");  
			fields6.setField_type("3");
			//fields4.setField_maxnum("255");
			fields6.setIs_sys("0");
			//fields4.setIs_null("0");
			fields6.setIs_display("1");  
			fieldInfo = "<fieldInfo><data_type><![CDATA[2]]></data_type><data_type_id><![CDATA[0]]></data_type_id><select_item><![CDATA[]]></select_item><select_view><![CDATA[0]]></select_view><default_value><![CDATA[0]]></default_value></fieldInfo>";
			fields6.setFieldInfo(fieldInfo);
			list.add(fields6);
			
			for(Fields fieldsAll : list){
				FieldsService.insertFieldInit(fieldsAll); 
			}
			
			
			return true; 
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
*/
	  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//initModelFields("2b6a732e-0d18-4048-b813-4171865e4b09");
	}

}
