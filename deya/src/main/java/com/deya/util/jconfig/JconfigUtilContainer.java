package com.deya.util.jconfig;

public class JconfigUtilContainer {
	private static JconfigUtil bashConfig = null;
	private static JconfigUtil systemRole = null;
	private static JconfigUtil apacheConfig = null;
	private static JconfigUtil managerPagePath = null;
	private static JconfigUtil velocityTemplateConfig = null;
	
	static{
		
		bashConfig = JconfigFactory.getJconfigUtilInstance("bashConfig");
		bashConfig.setParentConfig();
		systemRole = JconfigFactory.getJconfigUtilInstance("systemRole");
		systemRole.setParentConfig();
		apacheConfig = JconfigFactory.getJconfigUtilInstance("apacheConfigInfo");
		apacheConfig.setParentConfig();
		managerPagePath = JconfigFactory.getJconfigUtilInstance("managerPagePath");
		managerPagePath.setParentConfig();
		velocityTemplateConfig = JconfigFactory.getJconfigUtilInstance("velocityTemplate");
		velocityTemplateConfig.setParentConfig();
	}
	
	public static JconfigUtil getJconfigUtil(String config_name)
	{		
		return JconfigFactory.getJconfigUtilInstance(config_name);		
	}
	
	public static JconfigUtil velocityTemplateConfig()
	{		
		return velocityTemplateConfig;
	}
	
	public static JconfigUtil bashConfig()
	{		
		return bashConfig;
	}
	
	public static JconfigUtil systemRole()
	{		
		return systemRole;
	}
	
	public static JconfigUtil apacheConfig()
	{
		return apacheConfig;
	}
	
	public static JconfigUtil managerPagePath()
	{
		return managerPagePath;
	}
}
