package com.deya.wcm.server;
import java.util.Map;

/**
 * apache配置类接口,因为不同应用服务器apache的配置不一样,所以定义此接口来抽象变化
 * <p>Title: apache配置类接口</p>
 * <p>Description: 配置类接口,所有的apache配置类实现都要实现此接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 */
public interface IApacheConfig {
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
     * 在apache中添加站点
     * @param info Map 包含添加站点所需的信息
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop 
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addSite(Map info,String apacheConfig_name);

    /**
     * 在apache中修改站点域名
     * @param info Map 包含添加站点所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String updateSite(Map info);
    
    /**
     * 在apache中删除站点
     * @param info Map 包含删除站点所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delSite(Map info);

    /**
     * 在apache中添加虚拟主机
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addVhost(Map info,String apacheConfig_name);

    /**
     * 在apache中添加多个虚拟主机,用于站点的恢复,如果有多域名的话,需要将多个都恢复
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop
     * @return String
     */
    public String addMultiVhost(Map<String,String> info,String apacheConfig_name);
    
    /**
     * 在apache中删除多个虚拟主机,用于站点的暂停,如果有多域名的话,需要将多个都删除
     * @param site_id
     * @return String
     */
    public String delMultiVhost(String site_id);
    
    /**
     * 在apache中修改虚拟主机
     * @param info Map 包含删除虚拟主机所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String updateVhost(Map info);
    
    /**
     * 在apache中删除虚拟主机
     * @param info Map 包含删除虚拟主机所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delVhost(Map info);


    /**
     * 重置apache服务器配置
     * @param info Map
     * @return String
     */
    public void reset();

}

