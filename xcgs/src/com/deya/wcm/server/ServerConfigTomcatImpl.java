package com.deya.wcm.server;

import java.util.Date;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.util.xml.XmlManager;

/**
 * 服务器配置的tomcat实现，主要功能是在建立，删除站点和服务重置时修改服务器的配置
 * <p>Title: 服务器配置的tomcat实现</p>
 * <p>Description: 服务器配置的tomcat实现</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Cicro</p>
 * @author xiecs
 * @version 1.0
 */
public class ServerConfigTomcatImpl
    implements IServerConfig {

    private static final String asConfigFile = FormatUtil.formatPath(ServerManager.getServerRoot() +
        "/conf/server.xml");       
    private static String DEFAULT_HOST_TEMPLATE = "<Host className=\"org.apache.catalina.core.StandardHost\" autoDeploy=\"true\" "
    	+ "configClass=\"org.apache.catalina.startup.ContextConfig\" contextClass=\"org.apache.catalina.core.StandardContext\" " 
    	+ " deployXML=\"true\" errorReportValveClass=\"org.apache.catalina.valves.ErrorReportValve\" "
    	+ "name=\"$domain$\" unpackWARs=\"true\" appBase=\"$site-path$\">"
    	+ "<Context className=\"org.apache.catalina.core.StandardContext\" "
    	+ "charsetMapperClass=\"org.apache.catalina.util.CharsetMapper\" cookies=\"true\" crossContext=\"false\" debug=\"0\" "
    	+ "docBase=\"$manager_path$\" path=\"/sys\" privileged=\"false\" reloadable=\"false\" swallowOutput=\"false\" "
    	+ "useNaming=\"false\" wrapperClass=\"org.apache.catalina.core.StandardWrapper\"/>"
    	+ "</Host>";   

    private String backupFilePath="";

    /**
     * 得到实现的类别描述
     * @return String
     */
    public String getType() {
        return IServerConfig.TOMCAT_SERVER;
    }

    /**
     * 在服务器配置中添加一个站点
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addSite(Map infos) {        
        String domain = (String) infos.get("site_domain");
        String sitePath = FormatUtil.formatPath((String) infos.get("site_path"));
        sitePath = sitePath.substring(0,sitePath.length()-4);
        if(sitePath.endsWith("/"))
        	sitePath = sitePath.substring(0,sitePath.length()-1);        
        try {
        	String manager_path = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path"));
            String host_content = DEFAULT_HOST_TEMPLATE;
            if(ServerManager.isWindows())
            {
            	if(sitePath.endsWith("\\"))
                	sitePath = sitePath.substring(0,sitePath.length()-1);
            	
            	System.out.println("addSite------------"+sitePath);
            	sitePath = sitePath.replaceAll("\\\\", "\\\\\\\\");
            	manager_path = manager_path.replaceAll("\\\\", "\\\\\\\\");
            	
            }
            host_content = host_content.replaceAll("\\$domain\\$", domain);
            host_content = host_content.replaceAll("\\$site-path\\$", sitePath);  
            host_content = host_content.replaceAll("\\$manager_path\\$", manager_path); 
            Node hostNode = XmlManager.createNode(host_content);
            
            Document doc = XmlManager.createDOMByFile(asConfigFile);            
            String xpath = "/Server/Service/Engine";
            Node engineNode = XmlManager.queryNode(doc, xpath);
            XmlManager.insertNode(engineNode, hostNode);
            backupConfFile();//修改前先备份
            
            FileOperation.writeStringToFile(asConfigFile, XmlManager.node2String(doc), false);
        } catch (Exception ex) {
            restoreConfFile();//恢复备份
            ex.printStackTrace(System.out);
            return "ERROR_WHEN_WRITE_SERVER_CONFIG";
        }
        return IServerConfig.NO_ERROR;
    }
    
    /**
     * 在服务器配置中修改一个站点(包括多域名中的修改)
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String updateSite(Map infos)
    {
    	//把字符串组成为name="www.test.gov.cn" ,我们只做名称的替换,路径不能替换
    	String old_site_domain = "name=\""+(String)infos.get("old_site_domain")+"\"";
    	String new_site_domain = "name=\""+(String)infos.get("new_site_domain")+"\"";
    	String alices_old_site_domain = "<Alias>"+(String)infos.get("old_site_domain")+"</Alias>";
    	String alices_new_site_domain = "<Alias>"+(String)infos.get("new_site_domain")+"</Alias>";
    	
    	try {
    		String doc_str = FileOperation.readFileToString(asConfigFile);//XmlManager.createDOMByFile(asConfigFile);
            doc_str = doc_str.replaceAll(old_site_domain, new_site_domain).replaceAll(alices_old_site_domain, alices_new_site_domain);                 
	        backupConfFile();//修改前备份
	        FileOperation.writeStringToFile(asConfigFile, doc_str, false);
            
        } catch (Exception ex) {
            restoreConfFile();//恢复备份
            ex.printStackTrace(System.out);
            return "ERROR_WHEN_WRITE_SERVER_CONFIG";
        }
        return IServerConfig.NO_ERROR;
    }
    
    /**
     * 在服务器配置中删除一个站点
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delSite(Map infos) {    	
    	String sitePath = FormatUtil.formatPath((String) infos.get("site_path"));
    	if(ServerManager.isWindows())
        {
    		sitePath = sitePath.replaceAll("\\\\", "\\\\\\\\");
        }
        try {
            
            Document doc = XmlManager.createDOMByFile(asConfigFile);
            String xpath = "/Server/Service/Engine/Host[./@appBase='" + sitePath.substring(0,sitePath.length()-5) + "']";
            Node hostNode = XmlManager.queryNode(doc, xpath);
            if (hostNode != null) {
                XmlManager.removeNode(hostNode);
                backupConfFile();//修改前备份
                FileOperation.writeStringToFile(asConfigFile, XmlManager.node2String(doc), false);
            }
        } catch (Exception ex) {
            restoreConfFile();//恢复备份
            ex.printStackTrace(System.out);
            return "ERROR_WHEN_WRITE_SERVER_CONFIG";
        }
        return IServerConfig.NO_ERROR;
    }

    /**
     * 把服务器配置重置到最初状态
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String reset(Map infos) {
        try {
            
        } catch (Exception ex) {
            
        }
        return IServerConfig.NO_ERROR;
    }

    /**
     * 给站点标识为site_id的站点增加域名别名，用于站点多域名配置
     * @param site_id String 站点id
     * @param domain String  别名
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addAlias(Map infos) {  
    	String site_id = (String) infos.get("site_id");
    	String sitePath = FormatUtil.formatPath((String) infos.get("site_path"));
    	String domain = (String) infos.get("site_domain");    	
    	if(ServerManager.isWindows())
        {
    		//sitePath = sitePath.replaceAll("\\\\", "\\\\\\\\");
        }
    	if (site_id == null || (site_id = site_id.trim()).length() == 0) {
            return "SITE_ID_CAN_NOT_BE_NULL";
        }
        if (domain == null || (domain = domain.trim().toLowerCase()).length() == 0) {
            return "DOMAIN_ID_CAN_NOT_BE_NULL";
        }               
                        
        try {
            Document doc = XmlManager.createDOMByFile(asConfigFile);
            String xpath = "/Server/Service/Engine/Host[./@appBase='" + sitePath.substring(0,sitePath.length()-5) + "']";
            Node hostNode = XmlManager.queryNode(doc, xpath);
            if (hostNode != null) {
                Node aliasNode = XmlManager.createNode("<Alias>" + domain + "</Alias>");
                XmlManager.insertNode(hostNode, aliasNode);
                backupConfFile();//修改前先备份
                FileOperation.writeStringToFile(asConfigFile, XmlManager.node2String(doc), false);               
            }
            else {
                return "SITE_HOST_TAG_NOT_FOUND";
            }
                        
        } catch (Exception e) {
        	e.printStackTrace();
            restoreConfFile();//恢复备份
            return "ERROR_WHEN_ADD_ALIAS";
        }
        return NO_ERROR;
    }

    /**
     * 删除站点标识为site_id的站点的别名，用于站点多域名配置
     * @param site_id String  站点id
     * @param domain String  别名
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delAlias(Map infos) {        
    	String site_id = (String) infos.get("site_id");
    	
    	String domain = (String) infos.get("site_domain");
    	if (site_id == null || (site_id = site_id.trim()).length() == 0) {
            return "SITE_ID_CAN_NOT_BE_NULL";
        }
        if (domain == null || (domain = domain.trim().toLowerCase()).length() == 0) {
            return "DOMAIN_ID_CAN_NOT_BE_NULL";
        }
        
        try {
            Document doc = XmlManager.createDOMByFile(asConfigFile);
            String xpath = "//Alias[text()='" + domain + "']";
            Node aliasNode = XmlManager.queryNode(doc, xpath);
            if (aliasNode != null) {
                XmlManager.removeNode(aliasNode);
                backupConfFile();//修改前备份
                FileOperation.writeStringToFile(asConfigFile, XmlManager.node2String(doc), false);                
            }
        } catch (Exception e) {
            restoreConfFile();//恢复备份
            e.printStackTrace(System.out);
            return "ERROR_WHEN_DELETE_ALIAS";
        }
        return NO_ERROR;
    }    

    /**
     * 备份配置文件
     */
    private void backupConfFile(){
        try{
            backupFilePath = asConfigFile + new Date().getTime();
            FileOperation.copyFile(asConfigFile, backupFilePath, true);
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    /**
     * 恢复备份文件，操作出错时调用
     */
    private void restoreConfFile(){
        try{
            FileOperation.copyFile(backupFilePath,asConfigFile,true);
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        try {
        	
           
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
