package com.deya.wcm.services.init;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.BoneDataSourceFactory;

/**
 * 初始数据库操作类. 
 * @version 1.0 
 * @author 李苏培
 */

public class InitService {
	
	private static String rootPath = JconfigUtilContainer.bashConfig().getProperty("path", "", "class_Path")+"/initdb/";
	
	public static void initMain(){
		try{
			if(!getTable("cs_wcm_sequence")){
				initTable();
				initSql();
			}
		}catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	//初始化表
	public static void initTable(){
		try{
			String data_type_name = BoneDataSourceFactory.getDataTypeName();			
			
			String fileStr = rootPath+"initTable.xml";
			Document document = XmlManager.createDOM(fileStr);
            NodeList fileNodeList = XmlManager.queryNodeList(document, "root/file");
            System.out.println("*******initSql********start*****");
            for(int i=0;i<fileNodeList.getLength();i++){
        	    Node fileNode = fileNodeList.item(i);
        	    String fileName = XmlManager.queryNodeValue(fileNode, ".");
        	    fileName = data_type_name +"/" + fileName ; 
        	    //System.out.println("fileName====" + fileName);
        	    String sqlFileStr = rootPath+fileName;
        	    Document documentSql = XmlManager.createDOM(sqlFileStr);
                NodeList sqlNodeList = XmlManager.queryNodeList(documentSql, "root/table/sql");
                for(int j=0;j<sqlNodeList.getLength();j++){
                	Node sqlNode = sqlNodeList.item(j);
            	    String sqlName = XmlManager.queryNodeValue(sqlNode, ".");
            	    //System.out.println("sqlName====" + sqlName);
            	    Map<String, String> map = new HashMap<String, String>();
            	    map.put("sql",sqlName);
            	    PublicTableDAO.createSql(map);
                }
                //System.out.println(fileName+" end");
            }
            System.out.println("*******initSql********end*****");
		}catch (Exception e) {
			e.printStackTrace();
			//System.out.println("error initSql!!");
		}
	}
	
//	初始化表	
	public static void initSql(){
		try{
			String fileStr = rootPath+"initSql.xml";
			Document documentSql = XmlManager.createDOM(fileStr);
            NodeList sqlNodeList = XmlManager.queryNodeList(documentSql, "root/table/sql");
            for(int j=0;j<sqlNodeList.getLength();j++){
            	Node sqlNode = sqlNodeList.item(j);
        	    String sqlName = XmlManager.queryNodeValue(sqlNode, ".");
        	    //System.out.println("sqlName====" + sqlName);
        	    Map<String, String> map = new HashMap<String, String>();
        	    map.put("sql",sqlName);
        	    PublicTableDAO.createSql(map);
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 判断表是否存在
     * @param Map map 
     * @return boolean 
     */
	public static boolean getTable(String tableName){
		try{
			String sql = "select count(*) from " + tableName;
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql",sql);
			String count = PublicTableDAO.getTable(map);			
			if(count==null){
				return false;
			} 
			Integer.valueOf(count);
			if(Integer.valueOf(count)>=0){
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	删除表
	public static void DropTableAll(){
		try{ 
			String fileStr = rootPath+"tableList.xml";
			Document document = XmlManager.createDOM(fileStr);
            NodeList fileNodeList = XmlManager.queryNodeList(document, "root/file");
            //System.out.println("*******initSql********start*****");
            for(int i=0;i<fileNodeList.getLength();i++){
        	    Node fileNode = fileNodeList.item(i);
        	    String tableName = XmlManager.queryNodeValue(fileNode, ".");
        	    //System.out.println("tableName====" + tableName);
        	    String sql = " drop table " + tableName;
        	    Map<String, String> map = new HashMap<String, String>();
        	    map.put("sql",sql);    
        	    PublicTableDAO.createSql(map);
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		initTable();
	}
}
