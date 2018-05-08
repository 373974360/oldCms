/*
 * @(#)WIndowsUtil.java  1.0  2006-02-09
 */
package com.deya.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

/**
 * windows工具类,得到windows上的一些系统信息
 * <p>Title: windows工具类</p>
 * <p>Description: windows工具类,得到windows上的一些系统信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Cicro</p>
 * @author xiecs
 * @version 1.0
 */
public final class WIndowsUtil {

    /**
     * 得到系统网络参数的参数值
     * @param variable_name String 参数名,各参数如下,大小写相关
     * @return String
     Windows IP Configuration

             Host Name . . . . . . . . . . . . : chunson
             Primary Dns Suffix  . . . . . . . :
             Node Type . . . . . . . . . . . . : Unknown
             IP Routing Enabled. . . . . . . . : No
             WINS Proxy Enabled. . . . . . . . : No

     Ethernet adapter 本地连接:

             Connection-specific DNS Suffix  . :
             Description . . . . . . . . . . . : Realtek RTL8139
     rnet NIC
             Physical Address. . . . . . . . . : 00-E0-4C-E7-57-
             Dhcp Enabled. . . . . . . . . . . : No
             IP Address. . . . . . . . . . . . : 192.168.60.146
             Subnet Mask . . . . . . . . . . . : 255.255.255.0
             Default Gateway . . . . . . . . . : 192.168.60.1
             DNS Servers . . . . . . . . . . . : 202.100.4.15
     */
    public static String getNetVariableValue(String variable_name) {
        if (variable_name == null || (variable_name = variable_name.trim()).length() == 0) {
            return "";
        }
        String result = "";
        String os = System.getProperty("os.name").toLowerCase();
        if (os != null && os.startsWith("windows")) {
            try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br =
                    new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                String line;
                while ( (line = br.readLine()) != null) {
                    if (line.indexOf(variable_name) > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        result = line.substring(index);
                        break;
                    }
                }
                br.close();
                return result.trim();
            } catch (IOException e) {}
        }
        return result;
    }

    /**
     * 获取windows系统网卡mac地址
     * @return String
     */
    public static String getMACAddress() {
        return getNetVariableValue("Physical Address");
    }

    /**
     * 得到主机的机器名
     * @return String
     */
    public static String getHostName() {
        return getNetVariableValue("Host Name");
    }

    /**
     * 得到主机的IP地址
     * @return String
     */
    public static String getIP() {
        return getNetVariableValue("IP Address");
    }

    /**
     * 得到网关的ip地址
     * @return String
     */
    public static String getGateWayIP() {
        return getNetVariableValue("Default Gateway");
    }

    /**
     * 得到DNS服务的ip地址
     * @return String
     */
    public static String getDNSServer() {
        return getNetVariableValue("DNS Servers");
    }

    /**
     * 得到系统变量的值
     * @return String 系统变量名
     */
    public static String getSystemVariableValue(String variable_name) {
        if (variable_name == null || (variable_name = variable_name.trim()).length() == 0) {
            return "";
        }
        String result = "";
        String os = System.getProperty("os.name").toLowerCase();
        if (os != null && os.startsWith("windows")) {
            try {
                String command = "cmd.exe /c echo %" + variable_name + "%";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br =
                    new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                String line;
                while ( (line = br.readLine()) != null) {
                    result = line;
                    break;
                }
                br.close();
                return result.trim();
            } catch (IOException e) {}
        }
        return result;
    }

    /**
     * 得到系统PATH
     * @return String
     */
    public static String getPath() {
        return getSystemVariableValue("PATH");
    }

    /**
     * 得到DNS系统安装路径
     * @return String
     */
    public static String getSystemDir() {
        return getSystemVariableValue("windir");
    }

    /**
     * 得到JAVA_HOME路径
     * @return String
     */
    public static String getJavaHome() {
        return getSystemVariableValue("JAVA_HOME");
    }

    /**
     * 得到系统的CLASSPATH
     * @return String
     */
    public static String getClassPath() {
        return getSystemVariableValue("CLASSPATH");
    }

    /**
     * 得到hosts文件的路径
     * @return String
     */
    public static String getHostsPath() {
        String win_dir = getSystemDir();
        String hosts_path = win_dir + "\\system32\\drivers\\etc\\hosts";
        hosts_path = hosts_path.replace('\\', '/');
        File hosts_file=new File(hosts_path);
        if(!hosts_file.exists()){
            //throw new RuntimeException("the hosts file not exist !");
            System.out.println("the hosts file not exist");
        }
        return hosts_path;
    }

    /**
     * test main
     * @param args String[]
     */
    public static void main(String[] args) {
//        System.out.println("MACAddress : " + WIndowsUtil.getMACAddress());
//        System.out.println("HostName : " + WIndowsUtil.getHostName());
//        System.out.println("IP : " + WIndowsUtil.getIP());
//        System.out.println("GateWay : " + WIndowsUtil.getGateWayIP());
//        System.out.println("DNS : " + WIndowsUtil.getDNSServer());
//        System.out.println("PATH : " + WIndowsUtil.getPath());
//        System.out.println("System dir : " + WIndowsUtil.getSystemDir());
//        System.out.println("JAVA_HOME : " + WIndowsUtil.getJavaHome());
//        System.out.println("CLASSPATH : " + WIndowsUtil.getClassPath());
//        System.out.println("SystemVariable(CLASSPATH) : " +
//                           WIndowsUtil.getSystemVariableValue("CLASSPATH"));
//        System.out.println("getNetVariableValue(Physical Address) : " +
//                           WIndowsUtil.getNetVariableValue("Physical Address"));
//        System.out.println("HostsPath : " + WIndowsUtil.getHostsPath());

        System.out.println("西安市<font color='red'>雁</font><font color='red'>塔</font>区经济适用房购买资格审核公示第十一批");
    }

}
