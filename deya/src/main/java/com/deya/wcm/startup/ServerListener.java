package com.deya.wcm.startup;

import com.deya.util.DebugLog;
import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.catchs.ISyncCatchImpl;
import com.deya.wcm.dao.org.rmi.OrgRmiImpl;
import com.deya.wcm.rmi.ICustomFormRMI;
import com.deya.wcm.rmi.IFileRmi;
import com.deya.wcm.rmi.IOrgRmi;
import com.deya.wcm.rmi.ISiteRmi;
import com.deya.wcm.rmi.ISyncCatchRmi;
import com.deya.wcm.rmi.file.FileRmiImpl;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.control.rmi.SiteRmiImpl;
import com.deya.wcm.services.model.services.CustomFormRMIImpl;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerListener
        implements ServletContextListener {
    String rmiIp = null;
    String rmiPort = null;

    public static boolean isLicenseExist = true;
    public static boolean isLicenseRight = false;

    public ServerListener()
            throws IOException {
        this.rmiIp = JconfigUtilContainer.bashConfig().getProperty("ip", "", "rmi_config");
        this.rmiPort = JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config");
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.setProperty("java.rmi.server.hostname", this.rmiIp);
            LocateRegistry.createRegistry(Integer.parseInt(this.rmiPort));
            Context namingContext = new InitialContext();
            registerRMI(namingContext);

            JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");
            String[] class_arr = bc.getPropertyNamesByCategory("startloadclass");
            if ((class_arr != null) && (class_arr.length > 0)) {
                DebugLog.info("tomcat startup load classes begin");

                for (int i = class_arr.length; i > 0; i--) {
                    try {
                        //System.out.println("class_arr---" + i + "   " + class_arr[(i - 1)] + "   " + bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
                        Class.forName(bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
                        DebugLog.info(bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                DebugLog.info("tomcat startup load classes end");
            }

            checkLicense();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void registerRMI(Context namingContext)
            throws RemoteException {
        ISiteRmi siteRmi = new SiteRmiImpl();
        registerRMIServer(namingContext, "siteRmi", siteRmi, "站点");

        IOrgRmi orgRmi = new OrgRmiImpl();
        registerRMIServer(namingContext, "orgRmi", orgRmi, "部门");

        ISyncCatchRmi catchRmi = new ISyncCatchImpl();
        registerRMIServer(namingContext, "catchRmi", catchRmi, "缓存");

        IFileRmi fileRmi = new FileRmiImpl();
        registerRMIServer(namingContext, "fileRmi", fileRmi, "文件");

        ICustomFormRMI customRmi = new CustomFormRMIImpl();
        registerRMIServer(namingContext, "customRmi", customRmi, "资源库调用");
    }

    public void registerRMIServer(Context namingContext, String rmiName, Object o, String desc) {
        try {
            namingContext.rebind("rmi://" + this.rmiIp + ":" + this.rmiPort + "/" + rmiName, o);
            //System.out.println("注册" + desc + "rmi服务成功");
        } catch (Exception e) {
            //System.out.println("注册" + desc + "rmi服务失败");
            e.printStackTrace();
        }
    }

    private void checkLicense() {

        //System.out.println("checkLicense----------begin-----");

        if (LicenseCheck.isLicenseExist()) {
            if (!LicenseCheck.check()) {
                //System.out.println("KEY IS NOT RIGHT");
                try {
                    isLicenseExist = false;
                    isLicenseRight = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println(e);
                }
            } else {
                isLicenseExist = true;
                isLicenseRight = true;
            }
        } else {
            isLicenseExist = false;
            isLicenseRight = false;
        }
        //System.out.println("checkLicense----------end-----");
    }

    private void shutdownTomcat() throws IOException {
        String tomcatPath = JconfigUtilContainer.bashConfig().getProperty("path", "", "application_server_path");
        String[] windowsCommand = {FormatUtil.formatPath(tomcatPath + "/bin/shutdown.bat"), ""};
        String[] linuxCommand = {JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + "/bin/stopas.sh", ""};

        String os = System.getProperty("os.name");
        String[] command;
        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else {
            if (os.startsWith("Linux"))
                command = linuxCommand;
            else
                throw new IOException("Unknown operating system: " + os);
        }
        Process process = Runtime.getRuntime().exec(command);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}