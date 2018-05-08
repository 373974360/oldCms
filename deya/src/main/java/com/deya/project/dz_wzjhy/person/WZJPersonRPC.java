/*    */ package com.deya.project.dz_wzjhy.person;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.services.Log.LogManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WZJPersonRPC
/*    */ {
/*    */   public static List<PersonBean> getPersonBeanList(Map<String, String> m)
/*    */   {
/* 13 */     return PersonManager.getPersonBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getPersonBeanCount(Map<String, String> m) {
/* 17 */     return PersonManager.getPersonBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static PersonBean getPersonBean(Map<String, String> m) {
/* 21 */     return PersonManager.getPersonBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean auditPerson(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 26 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 27 */     if (stl != null) {
/* 28 */       return PersonManager.auditPerson(m, stl);
/*    */     }
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updatePersonPassword(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 35 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 36 */     if (stl != null) {
/* 37 */       return PersonManager.updatePersonPassword(m, stl);
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deletePersonBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 44 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 45 */     if (stl != null) {
/* 46 */       return PersonManager.deletePersonBean(m, stl);
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.JPWPersonRPC
 * JD-Core Version:    0.6.2
 */