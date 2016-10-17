package com.deya.util.jconfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.XMLFileHandler;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.wcm.server.ServerManager;

public class JconfigUtil {

	private Configuration configuration = null; 
	private ConfigurationManager cm = null;

	public JconfigUtil(){
	}

	public JconfigUtil(String path){
		try {			
			loadPropertyFile(path);
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实现单文件加载
	 * @param path
	 * @throws ConfigurationManagerException
	 */
	private void loadPropertyFile(String path) throws ConfigurationManagerException{
		try{
			if(ServerManager.isWindows() == true &&  path.startsWith("/"))
				path = path.substring(1);
			
			path = path.replaceAll("%20", " ");	
			path = FormatUtil.formatPath(path);
			//System.out.println(path);
			File file = new File(path);
			if(!file.isFile()){
				FileOperation.writeStringToFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<properties>\n</properties>", false);
			}
			XMLFileHandler handler = new XMLFileHandler();
			handler.setFile(file);
			cm = ConfigurationManager.getInstance();
			
			cm.load(handler, "myConfig");
			configuration = ConfigurationManager.getConfiguration("myConfig");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 实现多文件加载
	 * @param path
	 * @param fileName
	 * @throws ConfigurationManagerException
	 */
	public void loadPropertyFile(String path, String fileName){
		try {
			path = FormatUtil.formatPath(path);
			File file = new File(path);
			if(!file.isFile()){
				FileOperation.writeStringToFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<properties>\n</properties>", false);
			}
			XMLFileHandler handler = new XMLFileHandler();
			handler.setFile(file);
			cm = ConfigurationManager.getInstance();
			cm.load(handler, fileName);
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置指定分类的属性名及属性值
	 * @param key      属性名
	 * @param value    属性名
	 * @param category 分类名
	 */
	public void setProperty(String key, String value, String category){
		try {
			configuration.setProperty(key, value, category);
			cm.save("myConfig");
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置变量值
	 * @param key           指定的变量名
	 * @param value         指定的变量值
	 */
	public void setVariable(String key, String value){
		try {
			configuration.setVariable(key, value);
			cm.save("myConfig");
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择指定的配置文件
	 * 此方法在同时加载多个配置文件时使用
	 * @param propertyFile
	 */
	public void choosePropertyFile(String propertyFile){
		configuration = ConfigurationManager.getConfiguration(propertyFile);
	}

	/**
	 * 给指定配置文件设置指定分类的属性及属性值（设置前先调用choosePropertyFile方法选择要设置的配置文件）
	 * @param propertyFile  配置文件
	 * @param key           属性名
	 * @param value         属性值
	 * @param category      分类名
	 */
	public void setProperty(String propertyFile, String key, String value, String category){
		try {
			configuration.setProperty(key, value, category);
			cm.save(propertyFile);
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给指定配置文件设置变量值（设置前先调用choosePropertyFile方法选择要设置的配置文件）
	 * 此方法在同时加载多个配置文件时使用
	 * @param propertyFile  指定的配置文件
	 * @param key           指定的变量名
	 * @param value         指定的变量值
	 */
	public void setVariable(String propertyFile, String key, String value){
		try {
			configuration.setVariable(key, value);
			cm.save(propertyFile);
		} catch (ConfigurationManagerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到指定分类的指定属性值(如果指定属性为空或值为空 则返回指定的默认属性值)
	 * 此方法在同时加载多个配置文件时使用
	 * @param key       指定属性
	 * @param value     指定的默认属性值
	 * @param category  指定分类
	 * @return
	 */
	public String getProperty(String key, String defaultValue, String category){
		return configuration.getProperty(key, defaultValue, category);
	}

	/**
	 * 删除指定分类的指定属性
	 * @param key       指定属性
	 * @param category  指定分类
	 */
	public void removeProperty(String key, String category){
		configuration.removeProperty(key, category);
	}
	
	/**
	 * 得到此配置文件中的所有分类名称
	 * @return
	 */
	public String[] getCategorys(){
		return configuration.getCategoryNames();
	}
	
	/**
	 * 得到指定分类的所有属性名称
	 * @param categoryName
	 * @return
	 */
	public String[] getPropertyNamesByCategory(String categoryName){
		return configuration.getPropertyNames(categoryName);
	}
	
	/**
	 * 得到此配置文件中的所有变量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getVariables(){
		return configuration.getVariables();
	}
	
	public void setParentConfig(){
		loadPropertyFile(JconfigFactory.getAllConfigPath(),"configV");
		configuration.setBaseConfiguration("configV");
	}
	

	public static void main(String[] args) {
		
	}
}

