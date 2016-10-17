/*     */ package com.deya.wcm.startup;
/*     */ 
/*     */ import com.deya.util.DebugLog;
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.util.jconfig.JconfigFactory;
/*     */ import com.deya.util.jconfig.JconfigUtil;
/*     */ import com.deya.util.jconfig.JconfigUtilContainer;
/*     */ import com.deya.wcm.catchs.ISyncCatchImpl;
/*     */ import com.deya.wcm.dao.org.rmi.OrgRmiImpl;
/*     */ import com.deya.wcm.rmi.ICustomFormRMI;
/*     */ import com.deya.wcm.rmi.IFileRmi;
/*     */ import com.deya.wcm.rmi.IOrgRmi;
/*     */ import com.deya.wcm.rmi.ISiteRmi;
/*     */ import com.deya.wcm.rmi.ISyncCatchRmi;
/*     */ import com.deya.wcm.rmi.file.FileRmiImpl;
/*     */ import com.deya.wcm.server.LicenseCheck;
/*     */ import com.deya.wcm.services.control.rmi.SiteRmiImpl;
/*     */ import com.deya.wcm.services.model.services.CustomFormRMIImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.registry.LocateRegistry;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ 
/*     */ public class ServerListener
/*     */   implements ServletContextListener
/*     */ {
/*  39 */   String rmiIp = null;
/*  40 */   String rmiPort = null;
/*     */ 
/*  43 */   public static boolean isLicenseExist = true;
/*  44 */   public static boolean isLicenseRight = false;
/*     */ 
/*     */   public ServerListener()
/*     */     throws IOException
/*     */   {
/*  50 */     this.rmiIp = JconfigUtilContainer.bashConfig().getProperty("ip", "", "rmi_config");
/*  51 */     this.rmiPort = JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config");
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent sce)
/*     */   {
/*     */     try
/*     */     {
/*  58 */       System.setProperty("java.rmi.server.hostname", this.rmiIp);
/*  59 */       LocateRegistry.createRegistry(Integer.parseInt(this.rmiPort));
/*  60 */       Context namingContext = new InitialContext();
/*  61 */       registerRMI(namingContext);
/*     */ 
/*  64 */       JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");
/*  65 */       String[] class_arr = bc.getPropertyNamesByCategory("startloadclass");
/*  66 */       if ((class_arr != null) && (class_arr.length > 0))
/*     */       {
/*  68 */         DebugLog.info("tomcat startup load classes begin");
/*     */ 
/*  70 */         for (int i = class_arr.length; i > 0; i--) {
/*     */           try
/*     */           {
/*  73 */             System.out.println("class_arr---" + i + "   " + class_arr[(i - 1)] + "   " + bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
/*  74 */             Class.forName(bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
/*  75 */             DebugLog.info(bc.getProperty(class_arr[(i - 1)], "", "startloadclass"));
/*     */           }
/*     */           catch (Exception e) {
/*  78 */             e.printStackTrace();
/*     */           }
/*     */         }
/*  81 */         DebugLog.info("tomcat startup load classes end");
/*     */       }
/*     */ 
/*  84 */       checkLicense();
/*     */     } catch (Exception e) {
/*  86 */       e.printStackTrace(System.out);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void registerRMI(Context namingContext)
/*     */     throws RemoteException
/*     */   {
/*  95 */     ISiteRmi siteRmi = new SiteRmiImpl();
/*  96 */     registerRMIServer(namingContext, "siteRmi", siteRmi, "站点");
/*     */ 
/*  98 */     IOrgRmi orgRmi = new OrgRmiImpl();
/*  99 */     registerRMIServer(namingContext, "orgRmi", orgRmi, "部门");
/*     */ 
/* 101 */     ISyncCatchRmi catchRmi = new ISyncCatchImpl();
/* 102 */     registerRMIServer(namingContext, "catchRmi", catchRmi, "缓存");
/*     */ 
/* 104 */     IFileRmi fileRmi = new FileRmiImpl();
/* 105 */     registerRMIServer(namingContext, "fileRmi", fileRmi, "文件");
/*     */ 
/* 107 */     ICustomFormRMI customRmi = new CustomFormRMIImpl();
/* 108 */     registerRMIServer(namingContext, "customRmi", customRmi, "资源库调用");
/*     */   }
/*     */ 
/*     */   public void registerRMIServer(Context namingContext, String rmiName, Object o, String desc)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       namingContext.rebind("rmi://" + this.rmiIp + ":" + this.rmiPort + "/" + rmiName, o);
/* 122 */       System.out.println("注册" + desc + "rmi服务成功");
/*     */     }
/*     */     catch (Exception e) {
/* 125 */       System.out.println("注册" + desc + "rmi服务失败");
/* 126 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkLicense() {
/* 131 */     System.out.println("checkLicense----------begin-----");
/*     */ 
/* 135 */     if (LicenseCheck.isLicenseExist()) {
/* 136 */       if (!LicenseCheck.check())
/*     */       {
/* 139 */         System.out.println("KEY IS NOT RIGHT");
/*     */         try
/*     */         {
/* 143 */           isLicenseExist = false;
/* 144 */           isLicenseRight = false;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 148 */           System.out.println(e);
/*     */         }
/*     */       } else {
/* 151 */         isLicenseExist = true;
/* 152 */         isLicenseRight = true;
/*     */       }
/*     */     } else {
/* 155 */       isLicenseExist = false;
/* 156 */       isLicenseRight = false;
/*     */     }
/* 158 */     System.out.println("checkLicense----------end-----");
/*     */   }
/*     */ 
/*     */   private void shutdownTomcat() throws IOException
/*     */   {
/* 163 */     String tomcatPath = JconfigUtilContainer.bashConfig().getProperty("path", "", "application_server_path");
/* 164 */     String[] windowsCommand = { FormatUtil.formatPath(tomcatPath + "/bin/shutdown.bat"), "" };
/* 165 */     String[] linuxCommand = { JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + "/bin/stopas.sh", "" };
/*     */ 
/* 167 */     String os = System.getProperty("os.name");
/*     */     String[] command;
/* 170 */     if (os.startsWith("Windows")) {
/* 171 */       command = windowsCommand;
/*     */     }
/*     */     else
/*     */     {
/* 172 */       if (os.startsWith("Linux"))
/* 173 */         command = linuxCommand;
/*     */       else
/* 175 */         throw new IOException("Unknown operating system: " + os);
/*     */     }
/* 178 */     Process process = Runtime.getRuntime().exec(command);
/*     */   }
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent sce)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.wcm.startup.ServerListener
 * JD-Core Version:    0.6.2
 */