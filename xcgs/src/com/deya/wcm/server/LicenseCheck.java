/*     */ package com.deya.wcm.server;
/*     */ 
/*     */ /*     */ import java.io.File;
/*     */ import java.util.Date;

import com.deya.license.GetLicense;
/*     */ import com.deya.util.DateUtil;
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.util.io.FileOperation;
/*     */ import com.deya.util.jconfig.JconfigUtilContainer;
/*     */ import com.deya.wcm.services.control.site.SiteManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LicenseCheck
/*     */ {
/*  17 */   //public static String HAVE_APP_IDS = "system,org,cms,control,zwgk,appeal,ggfw";
			//public static String HAVE_APP_IDS = "system,org,cms,control,zwgk,appeal,ggfw";
			private static String HAVE_APP_IDS = JconfigUtilContainer.bashConfig().getProperty("apps", "", "have_apps");
/*  18 */   public static int LICENSE_SITE_NUM = 100;
/*     */ 
/*  24 */   private static String defaultLicensePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + File.separator + "cicroAuthorization" + File.separator + "cicroAuthorization.data");
/*  25 */   private static String defaultLicensePathRoot = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + File.separator + "cicroAuthorization");
/*     */ 
/*     */   public static boolean isHaveApp(String app_id)
/*     */   {
/*  30 */     if ("all".equals(HAVE_APP_IDS))
/*     */     {
/*  32 */       return true;
/*     */     }
/*     */ 
/*  35 */     return HAVE_APP_IDS.contains(app_id);
/*     */   }
/*     */ 
/*     */   public static boolean createLicense(String key)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       File fileRoot = new File(defaultLicensePathRoot);
/*  43 */       if (!fileRoot.exists()) {
/*  44 */         fileRoot.mkdir();
/*     */       }
/*  46 */       FileOperation.writeStringToFile(new File(defaultLicensePath), key, false);
/*  47 */       if (!check()) {
/*  48 */         com.deya.wcm.startup.ServerListener.isLicenseRight = false;
/*  49 */         FileOperation.deleteDir(defaultLicensePathRoot);
/*  50 */         return false;
/*     */       }
/*  52 */       com.deya.wcm.startup.ServerListener.isLicenseExist = true;
/*  53 */       com.deya.wcm.startup.ServerListener.isLicenseRight = true;
/*  54 */       return true;
/*     */     } catch (Exception e) {
/*  56 */       e.printStackTrace();
/*  57 */     }return false;
/*     */   }
/*     */ 
/*     */   public static String getAppForLicense()
/*     */   {
/*  62 */     return HAVE_APP_IDS;
/*     */   }
/*     */ 
/*     */   public static boolean isLicenseExist()
/*     */   {
/*     */     try
/*     */     {
/*  69 */       if (new File(defaultLicensePath).exists()) {
/*  70 */         return true;
/*     */       }
/*  72 */       System.out.println("License----------not found-----");
/*  73 */       return false;
/*     */     }
/*     */     catch (Exception e) {
/*  76 */       e.printStackTrace();
/*  77 */     }return false;
/*     */   }
/*     */ 
/*     */   public static boolean check()
/*     */   {
/*     */     try
/*     */     {
/*  84 */       GetLicense lic = new GetLicense("");
/*     */ 
/*  86 */       if (!lic.ifServerIdentical())
/*     */       {
/*  88 */         System.out.println("LicenseCheck : server CPU or MAC inconsistent  ");
/*  89 */         return false;
/*     */       }
/*     */ 
/*  92 */       if (!checkTimeOut(lic.getLicenseItemValue("wcm", "time_limit"), lic.getLicenseItemValue("wcm", "start_time")))
/*     */       {
/*  94 */         System.out.println("LicenseCheck : Licence timeout");
/*  95 */         return false;
/*     */       }
/*     */ 
/*  98 */       LICENSE_SITE_NUM = Integer.parseInt(lic.getLicenseItemValue("wcm", "site_num"));
/*  99 */       if (checkSiteCount(LICENSE_SITE_NUM))
/*     */       {
/* 101 */         System.out.println("LicenseCheck : More than the number of site licenses");
/* 102 */         return false;
/*     */       }
/*     */ 
/* 105 */       String app_ids = lic.getLicenseItemValue("wcm", "app_ids");
/*     */ 
/* 107 */       if (app_ids != null)
/*     */       {
/* 109 */         HAVE_APP_IDS = app_ids;
/*     */       }
/*     */ 
/* 112 */       System.out.println("LicenseCheck : it's OK");
/*     */     }
/*     */     catch (Exception e) {
/* 115 */       e.printStackTrace(System.out);
/* 116 */       return false;
/*     */     }
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean checkSiteCount(int lic_site_num)
/*     */   {
/*     */     try
/*     */     {
/* 125 */       int site_count = SiteManager.getSiteList().size();
/*     */ 
/* 127 */       if (site_count == 0) {
/* 128 */         return false;
/*     */       }
/*     */ 
/* 131 */       return site_count > lic_site_num;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 135 */       System.out.print(e);
/* 136 */     }return true;
/*     */   }
/*     */ 
/*     */   public static boolean checkTimeOut(String time_limit, String start_time)
/*     */   {
/* 141 */     if ((time_limit == null) || ("".equals(time_limit)) || ("0".equals(time_limit)))
/*     */     {
/* 143 */       return true;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 148 */       System.out.println("startupDate===============" + start_time);
/*     */ 
/* 150 */       if ((start_time == null) || ("".equals(start_time)))
/*     */       {
/* 152 */         System.out.println("LicenseCheck：startup time is null!");
/* 153 */         return false;
/*     */       }
/* 155 */       Date endDate_D = DateUtil.getDateTimesAfter(start_time + " 00:00:00", Integer.parseInt(time_limit) * 30);
/* 156 */       Date current_date = new Date();
/* 157 */       return endDate_D.after(current_date);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 161 */       System.out.println(e);
/* 162 */     }return false;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.wcm.server.LicenseCheck
 * JD-Core Version:    0.6.2
 */