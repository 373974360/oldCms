package com.deya.wcm.services.cms.info;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

/**
 * 模型配置解析类
 * 这个类通过Jconfig加载modelConfig.xml文件，并将信息储存在一个Map中
 * @author Administrator
 *
 */
public class ModelConfig {

	private static Map<String, Map<String, String>> mp = new HashMap<String, Map<String, String>>();
	
	static{
		readConfig();
	}
	
	/**
	 * 初始化加载modelConfig.xml配置文件
	 */
	private static void readConfig()
	{
		JconfigUtil jf = JconfigFactory.getJconfigUtilInstance("model_config");
		Set<String> categorys = jf.getCategorys();
		for(String s : categorys)
		{
			Map<String, String> model_values_mp = new HashMap<String, String>();			
			model_values_mp.put("TableName", jf.getProperty("TableName", "", s));
			model_values_mp.put("AddSQL", jf.getProperty("AddSQL", "", s));
			model_values_mp.put("UpdateSQL", jf.getProperty("UpdateSQL", "", s));
			model_values_mp.put("SelectSQL", jf.getProperty("SelectSQL", "", s));
			model_values_mp.put("class_name", jf.getProperty("class_name", "", s));
			mp.put(s, model_values_mp);
		}
	}
	
	public static Map<String, String> getModelMap(String modelName) {
		return mp.get(modelName);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> mp = ModelConfig.getModelMap("gkvideo");
		//mp.isEmpty();
		//System.out.println("------------"+mp.get("AddSQL"));
	}


}
