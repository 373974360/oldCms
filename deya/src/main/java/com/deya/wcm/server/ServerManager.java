package com.deya.wcm.server;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

/**
 * 服务器管理类,提供一些系统级的工具方法
 * <p>Title: 服务器管理类</p>
 * <p>Description: 服务器管理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 */
public final class ServerManager {
    private static String SERVER_TYPE = null;
    private static String IS_LINUX = null;
    private static String IS_WINDOWS = null;
    private static String IS_TOMCAT = null;
    private static String IS_JBOSS = null;
    private static String IS_TONGWEB = null;
    private static String IS_WEBLOGIC = null;
    private static String IS_WEBLOGIC7 = null;
    private static String IS_WEBLOGIC8 = null;
    private static String IS_WEBLOGIC_UNDER7 = null;
    private static String IS_WE = null;
    private static String IS_WEBSPHERE = null;
    private static String APP_SERVER_VERSION = null;
    private static int APP_SERVER_MAJOR_VERSION = -1;
    private static JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
    public static final String LOCAL_IP = bc.getProperty("ip", "", "local_ip");
    
    /**
     * 获得当前服务器的类型
     * @return String
     */
    public static String getServerType() {
        if (SERVER_TYPE != null) {
            return SERVER_TYPE;
        }
        
        String serverType = bc.getProperty("type", "", "application_server_type");
        if (serverType == null || serverType.trim().length() == 0) {
            serverType = IServerConfig.TOMCAT_SERVER; //如果没有该属性则默认为tomcat（向下兼容的考虑）
        }
        serverType = serverType.trim().toLowerCase();
        if (serverType.indexOf("weblogic") != -1) {
            return SERVER_TYPE = IServerConfig.WEBLOGIC_SERVER;
        }
        if (serverType.indexOf("tomcat") != -1) {
            return SERVER_TYPE = IServerConfig.TOMCAT_SERVER;
        }
        if (serverType.indexOf("jboss") != -1) {
            return SERVER_TYPE = IServerConfig.JBOSS_SERVER;
        }
        if (serverType.indexOf("websphere") != -1) {
            return SERVER_TYPE = IServerConfig.WESPHERE_SERVER;
        }
        if (serverType.indexOf("tongweb") != -1) {
            return SERVER_TYPE = IServerConfig.TONGWEB_SERVER;
        }
        return SERVER_TYPE = serverType;
    }
    
    /**
     * 判断当前服务器是否是websphere
     * @return boolean
     */
    public static boolean isWebsphere() {
        if (IS_WEBSPHERE != null) {
            return "true".equals(IS_WEBSPHERE);
        }
        String serverType = getServerType();
        if (IServerConfig.WESPHERE_SERVER == serverType) {
            IS_WEBSPHERE = "true";
            return true;
        }
        IS_WEBSPHERE = "false";
        return false;
    }

    /**
     * 判断当前服务器是否是TOMCAT
     * @return boolean
     */
    public static boolean isTomcat() {
        if (IS_TOMCAT != null) {
            return "true".equals(IS_TOMCAT);
        }
        String serverType = getServerType();
        if (IServerConfig.TOMCAT_SERVER == serverType) {
            IS_TOMCAT = "true";
            return true;
        }
        IS_TOMCAT = "false";
        return false;
    }

    /**
     * 判断当前服务器是否是JBoss
     * @return boolean
     */
    public static boolean isJBoss() {
        if (IS_JBOSS != null) {
            return "true".equals(IS_JBOSS);
        }
        String serverType = getServerType();
        if (IServerConfig.JBOSS_SERVER == serverType) {
            IS_JBOSS = "true";
            return true;
        }
        IS_JBOSS = "false";
        return false;
    }

    /**
     * 判断当前服务器是否是TONGWEB
     * @return boolean
     */
    public static boolean isTongWeb() {
        if (IS_TONGWEB != null) {
            return "true".equals(IS_TONGWEB);
        }
        String serverType = getServerType();
        if (IServerConfig.TONGWEB_SERVER == serverType) {
            IS_TONGWEB = "true";
            return true;
        }
        IS_TONGWEB = "false";
        return false;
    }

    /**
     * 判断当前服务器是否是WEBLOGIC
     * @return boolean
     */
    public static boolean isWeblogic() {
        if (IS_WEBLOGIC != null) {
            return "true".equals(IS_WEBLOGIC);
        }
        String serverType = getServerType();
        if (IServerConfig.WEBLOGIC_SERVER == serverType) {
            IS_WEBLOGIC = "true";
            return true;
        }
        IS_WEBLOGIC = "false";
        return false;
    }

    
    /**
     * 判断当前操作系统是否是Windows
     * @return boolean
     */
    public static boolean isWindows() {
        if (IS_WINDOWS != null) {
            return "true".equals(IS_WINDOWS);
        }
        
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            IS_WINDOWS = "true";
            return true;
        }
        else {
            IS_WINDOWS = "false";
            return false;
        }
    }
    

    /**
     * 得到WCM的安装目录
     * @return String
     */
    public static String getWCMRoot() {
        return bc.getProperty("path", "", "root_path");
    }

    /**
     * 得到WCM的版本
     * @return String
     */
    public static String getWCMVersion() {        
        return bc.getProperty("value", "6.2", "application_server_version");
    }

    /**
     * 得到tomcat的安装目录
     * @return String
     */
    public static String getServerRoot() {        
        return bc.getProperty("path", "", "application_server_path");
    }

    /**
     * 得到apache的安装目录
     * @return String
     */
    public static String getApacheRoot() {
    	return bc.getProperty("path", "", "application_server_path");
    }

    /**
     * 得到虚拟主机主目录
     * @return String
     */
    public static String getHostRoot() {        
        return bc.getProperty("path", "", "hostRoot_path");
    }
    
    /**
     * 得到代理 服务器类型，apache or nginx
     * @return String
     */
    public static String getProxyServer(){
    	return bc.getProperty("value", "apache", "proxy_server");
    }
   
}
