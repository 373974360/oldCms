package com.deya.wcm.server;
/**
 * 应用服务器配置对象的工厂类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 */
public class ServerConfigFactory {
    /**
     * 默认类型,在CWS没有配置,配置错误,提供参数错误等情况下设置为该类型
     */
    private static final String DEFAULT_TYPE=IServerConfig.TOMCAT_SERVER;

    /**
     * 工厂方法,根据当前CWS中的配置返回相应的ServerConfig实现的对象
     * @return ServerConfig
     */
    public static IServerConfig getServerConfig() {
        if (ServerManager.isTomcat()) {//tomcat
            return new ServerConfigTomcatImpl();
        }/*
        if (ServerManager.isWeblogic()) {//weblogic
            return new ServerConfigWeblogicImpl();
        }
        if (ServerManager.isWebsphere()) {//webbsphere
            return new ServerConfigWebsphereImpl();
        }
        if (ServerManager.isTongWeb()) {//Tongweb
            return new ServerConfigTongwebImpl();
        }
        if (ServerManager.isJBoss()) {//JBoss
            return new ServerConfigJBossImpl();
        }*/
        return getServerConfig(DEFAULT_TYPE);//not found,return default type
    }

    /**
     * 根据提供的参数返回相应的实现
     * @param type String 字符串参数,该字符串应该包含应用服务器全名
     * @return ServerConfig
     */
    public static IServerConfig getServerConfig(String type) {
        //检查和处理参数
        if(type==null||(type=type.trim()).length()==0){
            type=DEFAULT_TYPE;//set as default type
        }
        type=type.toLowerCase();
        if (type.indexOf(IServerConfig.TOMCAT_SERVER)!=-1) {//tomcat
            return new ServerConfigTomcatImpl();
        }/*
        if (type.indexOf(ServerConfig.WEBLOGIC_SERVER)!=-1) {//weblogic
            return new ServerConfigWeblogicImpl();
        }
        if (type.indexOf(ServerConfig.WESPHERE_SERVER)!=-1) {//websphere
            return new ServerConfigWebsphereImpl();
        }
        if (type.indexOf(ServerConfig.TONGWEB_SERVER)!=-1) {//tongweb
            return new ServerConfigTongwebImpl();
        }
        if (type.indexOf(ServerConfig.JBOSS_SERVER)!=-1) {//JBoss
            return new ServerConfigJBossImpl();
        }*/
        return getServerConfig(DEFAULT_TYPE);//not found,return default type
    }
}
