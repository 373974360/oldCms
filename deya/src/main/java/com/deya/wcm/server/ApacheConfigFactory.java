package com.deya.wcm.server;

/**
 * apache配置对象的工厂类
 * <p>Title: apache配置对象的工厂类 </p>
 * <p>Description: apache配置对象的工厂类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 */
public class ApacheConfigFactory {
    /**
     * 默认类型,在WCM没有配置,配置错误,提供参数错误等情况下设置为该类型
     */
    private static final String DEFAULT_TYPE = IApacheConfig.TOMCAT_SERVER;

    /**
     * 工厂方法,根据当前WCM中的配置返回相应的ServerConfig实现的对象
     * @return ServerConfig
     */
    public static IApacheConfig getApacheConfig() {
    	System.out.println("web服务器是否是tomcat：" + ServerManager.isTomcat());
        if (ServerManager.isTomcat()) { //tomcat
        	System.out.println("代理服务器是：" + ServerManager.getProxyServer());
        	if("nginx".equals(ServerManager.getProxyServer()))
        	{
        		return new NginxConfigTomcatImpl();
        	}
        	else
        		return new ApacheConfigTomcatImpl();
        }/*
        if (ServerManager.isWeblogic()) { //Weblogic
            return new AapcheConfigWelogicImpl();
        }
        if (ServerManager.isWebsphere()) { //Websphere
            return new AapcheConfigWesphereImpl();
        }
        if (ServerManager.isTongWeb()) { //Tongweb
            return new AapcheConfigTongwebImpl();
        }
        if (ServerManager.isJBoss()) { //JBoss
            return new IApacheConfigJBossImpl();
        }*/
        return getApacheConfig(DEFAULT_TYPE); //not found,return default type
    }

    /**
     * 根据提供的参数返回相应的实现
     * @param type String 字符串参数,该字符串应该包含应用服务器全名
     * @return ServerConfig
     */
    public static IApacheConfig getApacheConfig(String type) {
        //检查和处理参数
        if (type == null || (type = type.trim()).length() == 0) {
            type = DEFAULT_TYPE; //set as default type
        }
        type = type.toLowerCase();
        if (type.indexOf(IApacheConfig.TOMCAT_SERVER) != -1) { //tomcat
            return new ApacheConfigTomcatImpl();
        }/*
        if (type.indexOf(IApacheConfig.WEBLOGIC_SERVER) != -1) { //Weblogic
            return new AapcheConfigWelogicImpl();
        }
        if (type.indexOf(IApacheConfig.WESPHERE_SERVER) != -1) { //Websphere
            return new AapcheConfigWesphereImpl();
        }
        if (type.indexOf(IApacheConfig.TONGWEB_SERVER) != -1) { //Tongweb
            return new AapcheConfigTongwebImpl();
        }
        if (type.indexOf(IApacheConfig.JBOSS_SERVER) != -1) { //JBoss
            return new IApacheConfigJBossImpl();
        }
		*/
        return getApacheConfig(DEFAULT_TYPE); //not found,return default type
    }
}

