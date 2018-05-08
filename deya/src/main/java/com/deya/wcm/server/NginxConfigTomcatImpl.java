package com.deya.wcm.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.services.control.domain.SiteDomainManager;

public class NginxConfigTomcatImpl implements IApacheConfig{
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
     * @param String nginxConfig_name nginxConfig站点配置名称，添加站点名为　nginxConfig　暂停的配置名称为　nginxConfig_stop 
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addSite(Map info,String nginxConfig_name){
        return addVhost(info,nginxConfig_name);
    }
    
    /**
     * 在nginx中添加虚拟主机
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String nginxConfig_name nginxConfig站点配置名称，添加站点名为　nginxConfig　暂停的配置名称为　nginxConfig_stop
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String addVhost(Map info,String config_name){
        //得到参数
        String domain=(String)info.get("site_domain");
        String site_path=(String)info.get("site_path");
        String site_id=(String)info.get("site_id");
        //System.out.println("-----------------nginx配置文件中添加信息开始------------------");
        //检查和处理参数
        if (domain == null || (domain = domain.trim()).length() == 0) {
            return "ERROR";
        }
        if (site_path == null || (site_path = site_path.trim()).length() == 0) {
            return "ERROR";
        }
        site_path = FormatUtil.formatPath(site_path);
        //得到wcm根目录       
        String root_path=JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");

        //得到nginx安装目录
        String nginxRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "nginx_path");
        String nginxConfFilePath = "";
        String os = System.getProperty("os.name");
        if (ServerManager.isWindows()) {
        	nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/conf/nginx.conf");
        } else if (os.startsWith("Linux")) {
        	nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/nginx.conf");
        } 
        File nginxConfFile = new File(nginxConfFilePath);
        if (!nginxConfFile.exists()) {
            return "ERROR";
        }      
        //读出配置信息内容
        String nginx_Content = "";
        	
        //备份文件
        String backupConfFilePath = nginxConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
        	nginx_Content = FileOperation.readFileToString(nginxConfFile);
            FileOperation.copyFile(nginxConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        String manager_path = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path"));
        if(ServerManager.isWindows())
        {
        	if(site_path.endsWith("\\"))
        		site_path = site_path.substring(0,site_path.length()-1);
        	
        	site_path = site_path.replaceAll("\\\\", "\\\\\\\\");
        	manager_path = manager_path.replaceAll("\\\\", "\\\\\\\\");
        }
        //替换模板中的可变部分   
        String appendContent = JconfigUtilContainer.apacheConfig().getProperty("value", "", ServerManager.getProxyServer()+config_name).replaceAll("LINE_SEPARATOR", LINE_SEPARATOR);
        appendContent = appendContent.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("'", "\"");
        appendContent = appendContent.replaceAll("\\$domain\\$", domain).replaceAll("\\$site_path\\$", site_path).replaceAll("\\$site_id\\$", site_id);        
        appendContent = appendContent.replaceAll("\\$nginx_path\\$", nginxRoot).replaceAll("\\$root_path\\$", root_path);
        appendContent = appendContent.replaceAll("\\$manager_path\\$", manager_path);
        //将写好的配置信息替换到标识符里
        nginx_Content = nginx_Content.replace("#replace_content_flag#", appendContent);
        //写到nginx配置文件
        try {
            FileOperation.writeStringToFile(nginxConfFilePath, nginx_Content, false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //恢复备份
            try {
                FileOperation.copyFile(backupConfFilePath, nginxConfFilePath, false);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        }  
        //System.out.println("-----------------nginx配置文件中添加信息结束------------------");
        //操作成功,返回
        return IApacheConfig.NO_ERROR;
        
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
     * 在nginx中删除多个虚拟主机,用于站点的暂停,如果有多域名的话,需要将多个都删除
     * @param site_id
     * @return String
     */
    public String delMultiVhost(String site_id)
    {
    	//得到nginx安装目录
        String nginxRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "nginx_path");
        String nginxConfFilePath = "";
        String os = System.getProperty("os.name");
        if (ServerManager.isWindows()) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/conf/nginx.conf");
        } else if (os.startsWith("Linux")) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/nginx.conf");
        }
        File nginxConfFile = new File(nginxConfFilePath);
        if (!nginxConfFile.exists()) {
            return "ERROR";
        }

        //备份文件
        String backupConfFilePath = nginxConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(nginxConfFilePath, backupConfFilePath, true);
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
            reader = new BufferedReader(new FileReader(nginxConfFile));
            while ( (line = reader.readLine()) != null) {
                if (line.indexOf("server {") != -1) {
                    vhost.append(line + "\n");
                    while ( (line = reader.readLine()) != null &&
                           line.indexOf("#end") == -1) {
                        vhost.append(line + "\n");
                    }
                    vhost.append(line + "\n");
                    if (vhost.toString().indexOf(site_id) != -1) {                 	
                    }else{
                        content.append(vhost);
                    }
                    vhost = new StringBuffer();
                }
                else {
                    content.append(line + "\n");
                }
            }
            FileOperation.writeStringToFile(nginxConfFile, content.toString(), false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //操作异常,恢复备份
            try{
                FileOperation.copyFile(backupConfFilePath, nginxConfFilePath, true);
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
		
        //得到nginx安装目录
        String nginxRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "nginx_path");
        String nginxConfFilePath = "";
        String os = System.getProperty("os.name");
        if (ServerManager.isWindows()) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/conf/nginx.conf");
        } else if (os.startsWith("Linux")) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/nginx.conf");
        }
        nginxConfFilePath = FormatUtil.formatPath(nginxConfFilePath);
        File nginxConfFile = new File(nginxConfFilePath);
        if (!nginxConfFile.exists()) {
            return "ERROR";
        }
		
        //备份文件
        String backupConfFilePath = nginxConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(nginxConfFilePath, backupConfFilePath, true);
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
            reader = new BufferedReader(new FileReader(nginxConfFile));
          
            while ( (line = reader.readLine()) != null) {
                if (line.indexOf("server {") != -1) {
                    vhost.append(line + "\n");
                    while ( (line = reader.readLine()) != null &&
                           line.indexOf("#end") == -1) {
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
            FileOperation.writeStringToFile(nginxConfFile, content.toString(), false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //操作异常,恢复备份
            try{
                FileOperation.copyFile(backupConfFilePath, nginxConfFilePath, true);
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
     * 重置nginx服务器配置
     * @param 
     * @return 
     */
    public void reset(){
    	//得到wcm根目录    	
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");
    	String[] windowsCommand = null;
    	String[] LinuxCommand = { root_path+"/bin/restartnginx.sh", "" };
    	try{
    		//ExecuteCommand.executeCommandHandl(windowsCommand,LinuxCommand);  
    	}catch (Throwable t) {
    		   t.printStackTrace();
    	}
    }
    
    /**
     * 在nginx中添加多个虚拟主机,用于站点的恢复,如果有多域名的话,需要将多个都恢复
     * @param info Map 包含添加虚拟主机所需的信息
     * @param String nginxConfig_name nginxConfig站点配置名称，添加站点名为　nginxConfig　暂停的配置名称为　nginx_Config_stop
     * @return String
     */
    public String addMultiVhost(Map info,String config_name)
    {
    	String site_id = (String)info.get("site_id");
    	String site_path=(String)info.get("site_path");
    	String appendContent = "";
    	//读出配置信息内容
        String nginx_content = "";
    	List<SiteDomainBean> dl = SiteDomainManager.getDomainListBySiteID(site_id);
		if(dl != null && dl.size() > 0)
		{			
			site_path = site_path.replace('\\', '/');

	        //得到wcm根目录       
	        String root_path=JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");

	        //得到nginx安装目录
	        String nginxRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "nginx_path");
            String nginxConfFilePath = "";
            String os = System.getProperty("os.name");
            if (ServerManager.isWindows()) {
                nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/conf/nginx.conf");
            } else if (os.startsWith("Linux")) {
                nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/nginx.conf");
            }
	        File nginxConfFile = new File(nginxConfFilePath);
	        if (!nginxConfFile.exists()) {
	            return "ERROR";
	        }      

	        //备份文件
	        String backupConfFilePath = nginxConfFilePath +
	            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
	        try {
	        	nginx_content = FileOperation.readFileToString(nginxConfFile);
	            FileOperation.copyFile(nginxConfFilePath, backupConfFilePath, true);
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

		        //替换模板中的可变部分   
		        appendContent = JconfigUtilContainer.apacheConfig().getProperty("value", "", ServerManager.getProxyServer()+config_name).replaceAll("LINE_SEPARATOR", LINE_SEPARATOR);
		        appendContent = appendContent.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("'", "\"");
		        appendContent = appendContent.replaceAll("\\$domain\\$", domain).replaceAll("\\$site_path\\$", site_path).replaceAll("\\$site_id\\$", site_id);        
		        appendContent = appendContent.replaceAll("\\$nginx_path\\$", nginxRoot).replaceAll("\\$root_path\\$", root_path).replaceAll("\\$manager_path\\$", FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path")));
		              
		        //将写好的配置信息替换到标识符里
        		nginx_content = nginx_content.replace("#replace_content_flag#", appendContent);
			}
			
			//写到nginx配置文件
	        try {
	            FileOperation.writeStringToFile(nginxConfFilePath, nginx_content, false);
	        } catch (Exception e) {
	            e.printStackTrace(System.out);
	            //恢复备份
	            try {
	                FileOperation.copyFile(backupConfFilePath, nginxConfFilePath, false);
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
    
    @SuppressWarnings("unchecked")
	public String updateVhost(Map info)
    {
//    	把字符串组成为name="www.test.gov.cn" ,我们只做名称的替换,路径不能替换
    	if("".equals((String)info.get("old_site_domain")))
    	{
    		return "ERROR";
    	}
    	String old_site_domain = "server_name  "+(String)info.get("old_site_domain");
    	String new_site_domain = "server_name  "+(String)info.get("new_site_domain");
    	
//    	得到nginx安装目录
        String nginxRoot = JconfigUtilContainer.bashConfig().getProperty("path", "", "nginx_path");
        String nginxConfFilePath = "";
        String os = System.getProperty("os.name");
        if (ServerManager.isWindows()) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/conf/nginx.conf");
        } else if (os.startsWith("Linux")) {
            nginxConfFilePath = FormatUtil.formatPath(nginxRoot + "/nginx.conf");
        }
        File nginxConfFile = new File(nginxConfFilePath);
        if (!nginxConfFile.exists()) {
            return "ERROR";
        }
        
        String nginx_content = "";
        try{
        	nginx_content = FileOperation.readFileToString(nginxConfFile);
        	nginx_content = nginx_content.replaceAll(old_site_domain, new_site_domain);
        	
        }catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        
        //备份文件
        String backupConfFilePath = nginxConfFilePath +
            DateUtil.getString(new Date(), "yyyyMMddHHmmss");
        try {
            FileOperation.copyFile(nginxConfFilePath, backupConfFilePath, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "ERROR";
        }
        
        //写到nginx配置文件
        try {
        	FileOperation.writeStringToFile(nginxConfFilePath, nginx_content, false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //恢复备份
            try {
                FileOperation.copyFile(backupConfFilePath, nginxConfFilePath, true);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
            return "ERROR";
        } 
        
        //操作成功,返回
        return IApacheConfig.NO_ERROR;
    }

    /**
     * 在nginx中删除虚拟主机
     * @param info Map 包含删除虚拟主机所需的信息
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String delVhost(Map info){
        //得到参数
        
        String domain=(String)info.get("site_domain");
        return delVhost(domain);
    }
    
    public static void main(String args[])
    {
    	IApacheConfig i = new NginxConfigTomcatImpl();
    	Map info = new HashMap();
    	info.put("site_domain", "www.kisslan.com");
    	i.delVhost(info);
    }
}
