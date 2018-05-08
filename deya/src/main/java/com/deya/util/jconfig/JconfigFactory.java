package com.deya.util.jconfig;

import com.deya.util.FormatUtil;

public class JconfigFactory {
	
	private static String defaultPath = JconfigFactory.class.getClassLoader().getResource("AllConfigDescrion_WCM.xml").getFile();
	
	private static JconfigUtil jfu = null;	
				
	private JconfigFactory(){
		
	}	
	
	public static JconfigUtil getJconfigUtilInstance(String configName){	
				
		String AllConfigPath = null;
		if(AllConfigPath == null)
			AllConfigPath = defaultPath;
		if(jfu == null)
			jfu = new JconfigUtil(AllConfigPath);
		
		String path = jfu.getProperty("path", null, configName);
		
		if(path == null)
			return null;
		else
			return new JconfigUtil(path);
	}

	public static void setConfigInfo(String path, String configName){
		if(jfu == null)
			jfu = new JconfigUtil(defaultPath);
		jfu.setProperty("path", path, configName);
	}
	
	public static String getAllConfigPath()
	{
		return defaultPath;
	}
	
	public static void main(String[] args)
	{		
		//System.out.println(getAllConfigPath());
		System.out.println(FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "log4jFile")));
	}
}

