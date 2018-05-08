/*    */ package com.deya.project.dz_wzjhy.jobs;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.services.Log.LogManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WZJJobsRPC
/*    */ {
/*    */   public static List<JobsBean> getJobsBeanList(Map<String, String> m)
/*    */   {
/* 16 */     return JobsManager.getJobsBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getJobsBeanCount(Map<String, String> m)
/*    */   {
/* 21 */     return JobsManager.getJobsBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static JobsBean getJobsBean(Map<String, String> m)
/*    */   {
/* 26 */     return JobsManager.getJobsBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean publishJobsBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 31 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 32 */     if (stl != null) {
/* 33 */       return JobsManager.publishJobsBean(m, stl);
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteJobsBean(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 40 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 41 */     if (stl != null) {
/* 42 */       return JobsManager.deleteJobsBean(m, stl);
/*    */     }
/* 44 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.jobs.JPWJobsRPC
 * JD-Core Version:    0.6.2
 */