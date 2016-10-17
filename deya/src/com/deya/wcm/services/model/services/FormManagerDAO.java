package com.deya.wcm.services.model.services;

import java.util.Map;

import com.deya.wcm.db.DBManager;


public class FormManagerDAO {

	
	
	
	//添加时更新表结构
	public static boolean updateFieldDesc(String type,Map map){
		try{
			if("addFieldVarchar".equals(type)){
				DBManager.update("formMapper.addFieldVarchar", map);
			}else if("addFieldLongtext".equals(type)){
				DBManager.update("formMapper.addFieldLongtext", map);
			}else if("addFieldBigint".equals(type)){
				DBManager.update("formMapper.addFieldBigint", map);
			}
			
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	

	//修改时更新表结构
	public static boolean updateFieldDescByUpdate(Map map){
		try{
			
			DBManager.update("formMapper.modifyFieldTypeToLongText1",map);
			DBManager.update("formMapper.modifyFieldTypeToLongText2",map);
			DBManager.update("formMapper.modifyFieldTypeToLongText3",map);
			DBManager.update("formMapper.modifyFieldTypeToLongText4",map);
			
			return true; 
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//修改时更新表结构
	public static boolean updateFieldDescByUpdateVarchar(Map map){
		try{
			
			DBManager.update("formMapper.modifyFieldTypeToVarchar1",map);
			DBManager.update("formMapper.modifyFieldTypeToVarchar2",map);
			DBManager.update("formMapper.modifyFieldTypeToVarchar3",map);
			DBManager.update("formMapper.modifyFieldTypeToVarchar4",map);
			
			return true; 
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
		 
	//删除表结构字段 
	 public static boolean deleteFieldDesc(Map map){
		 try{
			 DBManager.update("formMapper.deleteField",map);
			 return true;
		 }catch (Exception e) {
			 // TODO: handle exception
			 e.printStackTrace();
			 return false;
		 }
	 }
	 
	/**
     * 生成内容模型的表
     * @param tableName 要创建的表名
     * @return boolean false：失败；true：成功
     */
	public static boolean createTable(Map map){
	   try{  
	        DBManager.update("formMapper.createTable",map);
		    return true;
	   }catch (Exception e) {
		 e.printStackTrace();
		 return false;
       }	
	}
		 
}
