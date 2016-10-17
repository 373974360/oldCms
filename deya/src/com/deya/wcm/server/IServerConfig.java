package com.deya.wcm.server;

import java.util.Map;

/**
 *
 * <p>Title: 应用服务器配置类接口</p>
 * <p>Description: 应用服务器配置类接口,所有应用服务器配置类要实现该接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 */
public interface IServerConfig {
    /**
     * 各种服务器类型常量
     */
    public static final String TOMCAT_SERVER="tomcat";
    public static final String WEBLOGIC_SERVER="weblogic";
    public static final String WESPHERE_SERVER="websphere";
    public static final String JETTY_SERVER="jetty";
    public static final String JRUN_SERVER="jrun";
    public static final String JBOSS_SERVER="jboss";
    public static final String TONGWEB_SERVER="tongweb";
    public static final String INFOWEB_SERVER="infoweb";

    /**
     * 返回值常量,代表操作成功
     */
    public static final String NO_ERROR="NO_ERROR";

    /**
     * 得到实现的类别描述
     * @return String
     */
    public String getType();

    /**
     * 在应用服务器中添加站点
     * @param infos Map 参数集合,应该包含建站所需信息,如域名,站点标识等
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addSite(Map infos);

    /**
     * 在应用服务器中修改站点域名
     * @param infos Map 参数集合,应该包含建站所需信息,如域名,站点标识等
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String updateSite(Map infos);
    
    /**
     * 在应用服务器中删除站点
     * @param infos Map 参数集合,应该包含删除站点所需信息,如站点标识等
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delSite(Map infos);

    /**
     * 给站点标识为site_id的站点增加域名别名，用于站点多域名配置
     * @param site_id String 站点id
     * @param domain String  别名
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addAlias(Map infos);

    /**
     * 删除站点标识为site_id的站点的别名，用于站点多域名配置
     * @param site_id String  站点id
     * @param domain String  别名
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delAlias(Map infos);

    /**
     * 应用服务器重置,删除所有站点,虚拟主机等配置
     * @param infos Map 包含服务器重置所需信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String reset(Map infos);
}
