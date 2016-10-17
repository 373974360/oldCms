package com.deya.wcm.template.velocity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-4-19 下午03:35:28
 */
public class VelocityContextUtils {

	private static VelocityContext baseContext = new VelocityContext();
	
	static {
		loadPublicContext();
		loadClassContext();
	}
	
	/**
	 * 得到标签解析上下文环境
	 * @return VelocityContext
	 */
	public static VelocityContext getContext(){
		return baseContext;
	}
	
	/**
	 * 加载公共信息
	 *  void
	 */
	public static void loadPublicContext(){
		//从数据库，配置文件等资源里边加载公共信息，比如站点名称
		baseContext.put("name", "站点ID");
	}
	
	/**
	 * 加载所有前台类
	 *  void
	 */
	public static void loadClassContext(){
		Map<String, String> map = getClassList();
		Set<String> keys = map.keySet();
		for(String key : keys){
			try {
				Class<?> obj = Class.forName(map.get(key));
				Object o = obj.newInstance();
				baseContext.put(key, o);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateContext(String key, Object value){
		baseContext.remove(key);
		baseContext.put(key, value);
	}
	
	public static void removeContext(String key){
		baseContext.remove(key);
	}
	
	/**
	 * 从配置文件中或者数据库中读取要加载的类的列表
	 * @return Map<String,String>
	 */
	private static Map<String, String> getClassList(){
		Map<String, String> map = new HashMap<String, String>();
		//从配置文件中或者数据库中读取要加载的类的列表
		map.put("person", "test.Person");
		map.put("msg", "test.Messages");
		map.put("toytool", "ToyTool");
		return map;
	}
	public static void main(String[] args) {
		loadClassContext();
	}
}
