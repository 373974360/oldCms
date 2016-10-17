package com.deya.wcm.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.services.control.domain.SiteDomainManager;
/**
 * apache配置类的tomcat实现
 * <p>Title: apache配置类的tomcat实现</p>
 * <p>Description: apache配置类的tomcat实现</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.1
 */
public class ApacheConfigTomcatImpl implements IApacheConfig{
    /**
     * 行分隔符,windows为\r\n,其它为\n
     */
    private static String LINE_SEPARATOR="\n";
    static{
        if(ServerManager.isWindows()){
            LINE_SEPARATOR="\r\n";
        }
    }

    /**
     * 得到实现的类别描述
     * @return String
     */
    public String getType(){
        return IApacheConfig.TOMCAT_SERVER;
    }

    /**
     * 添加一个站点
     * @param info Map
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop 
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addSite(Map info,String apacheConfig_name){
        return addVhost(info,apacheConfig_name);
    }

    /**
     * 修改站点域名
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String updateSite(Map infos){
    	updateVhost(infos);
        return "";
    }
    
    /**
     * 删除一个站点
     * @param infos Map
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delSite(Map infos){
    	delVhost(infos);
        return "";
    }
    
    /**
     * 在apache中删除多个虚拟主机,用于站点的暂停,如果有多域名的话,需要将多个都删除
     * @param site_id
     * @return String
     */
    public String delMultiVhost(String site_id)
    {
    	//得到apache安装目录
        String apacheRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "apaceh_path");
        String apacheConfFilePath = FormatUtil.formatPath(apacheRoot + "/conf/httpd.conf");
        apacheConfFilePath = FormatUtil.formatPath(apacheConfFilePath);
        File apacheConfFile = new File(apacheConfFilePath);
        if (!apacheConfFile.exists()) {
            return "ERROR";
        }

        //备份文件
        String backupConfFilePath = apacheConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(apacheConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }

        //执行删除
        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        String line = "";
        StringBuffer vhost = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(apacheConfFile));
            while ( (line = reader.readLine()) != null) {
                if (line.indexOf("<VirtualHost") != -1) {
                    vhost.append(line + "\n");
                    while ( (line = reader.readLine()) != null &&
                           line.indexOf("</VirtualHost>") == -1) {
                        vhost.append(line + "\n");
                    }
                    vhost.append(line + "\n");
                    if (domainIsExist(vhost.toString(),site_id)) {
                    }else{
                        content.append(vhost);
                    }
                    vhost = new StringBuffer();
                }
                else {
                    content.append(line + "\n");
                }
            }
            FileOperation.writeStringToFile(apacheConfFile, content.toString(), false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //操作异常,恢复备份
            try{
                FileOperation.copyFile(backupConfFilePath, apacheConfFilePath, true);
            }catch(Exception ex){
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        }
        	
        //操作成功,返回
        return IApacheConfig.NO_ERROR;
    }
    
    //判断前字符串中是否包含多域名
    public boolean domainIsExist(String str,String site_id)
    {
    	List<SiteDomainBean> dl = SiteDomainManager.getDomainListBySiteID(site_id);
		if(dl != null && dl.size() > 0)
		{
			for(SiteDomainBean sdb : dl)
			{
				if(str.indexOf(sdb.getSite_domain()) > -1)
				{
					return true;
				}
			}
		}
		return false;
    }

    /**
     * 删除一个虚拟主机配置
     * @param domain String 站点域名
     * @param site_path String 站点路径
     * @return String
     */
    private String delVhost(String domain){
        //检查和处理参数
        if (domain == null || (domain = domain.trim()).length() == 0) {
            return "ERROR";
        }  

        //得到apache安装目录
        String apacheRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "apaceh_path");
        String apacheConfFilePath = FormatUtil.formatPath(apacheRoot + "/conf/httpd.conf");
        apacheConfFilePath = FormatUtil.formatPath(apacheConfFilePath);
        File apacheConfFile = new File(apacheConfFilePath);
        if (!apacheConfFile.exists()) {
            return "ERROR";
        }

        //备份文件
        String backupConfFilePath = apacheConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(apacheConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }

        //执行删除
        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        String line = "";
        StringBuffer vhost = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(apacheConfFile));
            while ( (line = reader.readLine()) != null) {
                if (line.indexOf("<VirtualHost") != -1) {
                    vhost.append(line + "\n");
                    while ( (line = reader.readLine()) != null &&
                           line.indexOf("</VirtualHost>") == -1) {
                        vhost.append(line + "\n");
                    }
                    vhost.append(line + "\n");
                    if (vhost.toString().indexOf(domain) != -1) {
                    }else{
                        content.append(vhost);
                    }
                    vhost = new StringBuffer();
                }
                else {
                    content.append(line + "\n");
                }
            }
            FileOperation.writeStringToFile(apacheConfFile, content.toString(), false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //操作异常,恢复备份
            try{
                FileOperation.copyFile(backupConfFilePath, apacheConfFilePath, true);
            }catch(Exception ex){
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        }
        	
        //操作成功,返回
        return IApacheConfig.NO_ERROR;
    }

    /**
     * 重置apache服务器配置
     * @param 
     * @return 
     */
    public void reset(){
    	if(!ServerManager.isWindows())
    	{
	    	//得到wcm根目录    	
	        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");
	    	String[] windowsCommand = null;
	    	String[] LinuxCommand = { root_path+"/bin/restartapache.sh", "" };
	    	try{
	    		ExecuteCommand.executeCommandHandl(windowsCommand,LinuxCommand);  
	    	}catch (Throwable t) {
	    		   t.printStackTrace();
	    	}
    	}
    }
    
    /**
     * 在apache中添加多个虚拟主机,用于站点的恢复,如果有多域名的话,需要将多个都恢复
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop
     * @return String
     */
    public String addMultiVhost(Map info,String config_name)
    {
    	String site_id = (String)info.get("site_id");
    	String site_path=(String)info.get("site_path");
    	String appendContent = "";
    	String all_str = "";
    	List<SiteDomainBean> dl = SiteDomainManager.getDomainListBySiteID(site_id);
		if(dl != null && dl.size() > 0)
		{		
			String manager_path = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path"));
	        //得到wcm根目录       
	        String root_path=JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");

	        //得到apache安装目录
	        String apacheRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "apaceh_path");
	        String apacheConfFilePath = FormatUtil.formatPath(apacheRoot + "/conf/httpd.conf");
	        File apacheConfFile = new File(apacheConfFilePath);
	        if (!apacheConfFile.exists()) {
	            return "ERROR";
	        }      

	        //备份文件
	        String backupConfFilePath = apacheConfFilePath +
	            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
	        try {
	            FileOperation.copyFile(apacheConfFilePath, backupConfFilePath, true);
	        } catch (Exception e) {
	            e.printStackTrace(System.out);
	            return "ERROR";
	        }
			for(SiteDomainBean sdb : dl)
			{
				String domain=sdb.getSite_domain();
				//检查和处理参数
		        if (domain == null || (domain = domain.trim()).length() == 0) {
		            return "ERROR";
		        }
		        if (site_path == null || (site_path = site_path.trim()).length() == 0) {
		            return "ERROR";
		        }
		        if(ServerManager.isWindows())
		        {
		        	site_path = site_path.replaceAll("\\\\", "\\\\\\\\");
		        	manager_path = manager_path.replaceAll("\\\\", "\\\\\\\\");
		        	apacheRoot = apacheRoot.replaceAll("\\\\", "\\\\\\\\");
		        }
		        //替换模板中的可变部分   
				appendContent = JconfigUtilContainer.apacheConfig().getProperty("value", "", ServerManager.getProxyServer()+config_name).replaceAll("LINE_SEPARATOR", LINE_SEPARATOR);
		        appendContent = appendContent.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("'", "\"");
		        appendContent = appendContent.replaceAll("\\$domain\\$", domain).replaceAll("\\$site_path\\$", site_path);        
		        appendContent = appendContent.replaceAll("\\$apache_path\\$", apacheRoot).replaceAll("\\$root_path\\$", root_path).replaceAll("\\$manager_path\\$", manager_path);
		              
		        all_str += appendContent;
			}
			
			//写到apache配置文件
	        try {
	            FileOperation.writeStringToFile(apacheConfFilePath, all_str, true);
	        } catch (Exception e) {
	            e.printStackTrace(System.out);
	            //恢复备份
	            try {
	                FileOperation.copyFile(backupConfFilePath, apacheConfFilePath, true);
	            } catch (Exception ex) {
	                ex.printStackTrace(System.out);
	            }
	            return "ERROR";
	        }  
	        
	        //操作成功,返回
	        return IApacheConfig.NO_ERROR;
		}
		return "ERROR";
    }
    
    /**
     * 在apache中添加虚拟主机
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String apacheConfig_name apacheConfig站点配置名称，添加站点名为　apacheConfig　暂停的配置名称为　apacheConfig_stop
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addVhost(Map info,String config_name){
        //得到参数
        String domain=(String)info.get("site_domain");
        String site_path=(String)info.get("site_path");
        String manager_path = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path"));
       
        //检查和处理参数
        if (domain == null || (domain = domain.trim()).length() == 0) {
            return "ERROR";
        }
        if (site_path == null || (site_path = site_path.trim()).length() == 0) {
            return "ERROR";
        }
        
        //得到wcm根目录       
        String root_path=JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");

        //得到apache安装目录
        String apacheRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "apaceh_path");
        String apacheConfFilePath = FormatUtil.formatPath(apacheRoot + "/conf/httpd.conf");
        File apacheConfFile = new File(apacheConfFilePath);
        if (!apacheConfFile.exists()) {
            return "ERROR";
        }      

        //备份文件
        String backupConfFilePath = apacheConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(apacheConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        
        if(ServerManager.isWindows())
        {
        	site_path = site_path.replaceAll("\\\\", "\\\\\\\\");
        	manager_path = manager_path.replaceAll("\\\\", "\\\\\\\\");
        	apacheRoot = apacheRoot.replaceAll("\\\\", "\\\\\\\\");
        }

        //替换模板中的可变部分        
        String appendContent = JconfigUtilContainer.apacheConfig().getProperty("value", "", ServerManager.getProxyServer()+config_name).replaceAll("LINE_SEPARATOR", LINE_SEPARATOR);
        appendContent = appendContent.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("'", "\"");
        appendContent = appendContent.replaceAll("\\$domain\\$", domain).replaceAll("\\$site_path\\$", site_path); 
        appendContent = appendContent.replaceAll("\\$apache_path\\$", apacheRoot).replaceAll("\\$root_path\\$", root_path).replaceAll("\\$manager_path\\$", manager_path);
        //写到apache配置文件
        try {
            FileOperation.writeStringToFile(apacheConfFilePath, appendContent, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //恢复备份
            try {
                FileOperation.copyFile(backupConfFilePath, apacheConfFilePath, true);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        }  
        
        //操作成功,返回
        return IApacheConfig.NO_ERROR;

    }
    
    @SuppressWarnings("unchecked")
	public String updateVhost(Map info)
    {
//    	把字符串组成为name="www.test.gov.cn" ,我们只做名称的替换,路径不能替换
    	if("".equals((String)info.get("old_site_domain")))
    	{
    		return "ERROR";
    	}
    	String old_site_domain = "ServerName "+(String)info.get("old_site_domain");
    	String new_site_domain = "ServerName "+(String)info.get("new_site_domain");
    	
//    	得到apache安装目录
        String apacheRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "apaceh_path");
        String apacheConfFilePath = FormatUtil.formatPath(apacheRoot + "/conf/httpd.conf");
        File apacheConfFile = new File(apacheConfFilePath);
        if (!apacheConfFile.exists()) {
            return "ERROR";
        }
        
        String apache_content = "";
        try{
        	apache_content = FileOperation.readFileToString(apacheConfFile);
        	apache_content = apache_content.replaceAll(old_site_domain, new_site_domain);
        	System.out.println("old_site_domain----------------"+old_site_domain);
        	System.out.println("new_site_domain----------------"+new_site_domain);
        }catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        
        //备份文件
        String backupConfFilePath = apacheConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(apacheConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        
        //写到apache配置文件
        try {
        	FileOperation.writeStringToFile(apacheConfFilePath, apache_content, false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //恢复备份
            try {
                FileOperation.copyFile(backupConfFilePath, apacheConfFilePath, true);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        } 
        
        //操作成功,返回
        return IApacheConfig.NO_ERROR;
    }

    /**
     * 在apache中删除虚拟主机
     * @param info Map 包含删除虚拟主机所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delVhost(Map info){
        //得到参数
        String domain=(String)info.get("site_domain");
        return delVhost(domain);
    }

}
