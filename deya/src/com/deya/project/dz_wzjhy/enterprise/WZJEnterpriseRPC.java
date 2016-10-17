/*    */ package com.deya.project.dz_wzjhy.enterprise;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.services.Log.LogManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WZJEnterpriseRPC
/*    */ {
/*    */   public static List<EnterpriseBean> getEnterpriseBeanList(Map<String, String> m)
/*    */   {
/* 13 */     return EnterpriseManager.getEnterpriseBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getEnterpriseBeanCount(Map<String, String> m) {
/* 17 */     return EnterpriseManager.getEnterpriseBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static EnterpriseBean getEnterpriseBean(Map<String, String> m) {
/* 21 */     return EnterpriseManager.getEnterpriseBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean auditEnterprise(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 26 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 27 */     if (stl != null) {
/* 28 */       return EnterpriseManager.auditEnterprise(m, stl);
/*    */     }
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updateEnterprisePassword(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 35 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 36 */     if (stl != null) {
/* 37 */       return EnterpriseManager.updateEnterprisePassword(m, stl);
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteEnterpriseBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 44 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 45 */     if (stl != null) {
/* 46 */       return EnterpriseManager.deleteEnterpriseBean(m, stl);
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.JPWEnterpriseRPC
 * JD-Core Version:    0.6.2
 */