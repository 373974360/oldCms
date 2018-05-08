/*    */ package com.deya.project.dz_wzjhy.resume;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.services.Log.LogManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WZJResumeRPC
/*    */ {
/*    */   public static List<ResumeBean> getResumeBeanList(Map<String, String> m)
/*    */   {
/* 14 */     return ResumeManager.getResumeBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getResumeBeanCount(Map<String, String> m)
/*    */   {
/* 19 */     return ResumeManager.getResumeBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static ResumeBean getResumeBean(Map<String, String> m)
/*    */   {
/* 24 */     return ResumeManager.getResumeBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean publishResumeBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 29 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 30 */     if (stl != null) {
/* 31 */       return ResumeManager.publishResumeBean(m, stl);
/*    */     }
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean tuijianResumeBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 38 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 39 */     if (stl != null) {
/* 40 */       return ResumeManager.tuijianResumeBean(m, stl);
/*    */     }
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteResumeBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 47 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 48 */     if (stl != null) {
/* 49 */       return ResumeManager.deleteResumeBean(m, stl);
/*    */     }
/* 51 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.resume.JPWResumeRPC
 * JD-Core Version:    0.6.2
 */