/*    */ package com.deya.project.dz_wzjhy.jobs;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class JobsDAO
/*    */ {
/*    */   public static List<JobsBean> getJobsBeanList(Map<String, String> m)
/*    */   {
/* 15 */     return DBManager.queryFList("getwzjJobsBeanList", m);
/*    */   }
/*    */ 
/*    */   public static String getJobsBeanCount(Map<String, String> m)
/*    */   {
/* 20 */     return DBManager.getString("getwzjJobsBeanCount", m);
/*    */   }
/*    */ 
/*    */   public static JobsBean getJobsBean(Map<String, String> m)
/*    */   {
/* 25 */     return (JobsBean)DBManager.queryFObj("getwzjJobsBean", m);
/*    */   }
/*    */ 
/*    */   public static boolean insertJobsBean(JobsBean rb)
/*    */   {
/* 30 */     return DBManager.insert("insert_wzj_jobs", rb);
/*    */   }
/*    */ 
/*    */   public static boolean updateJobsBean(JobsBean rb)
/*    */   {
/* 35 */     return DBManager.update("update_wzj_jobs", rb);
/*    */   }
/*    */ 
/*    */   public static boolean publishJobsBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 40 */     if (DBManager.update("publish_wzj_jobs", m))
/*    */     {
/* 42 */       PublicTableDAO.insertSettingLogs("修改", "招聘信息发布状态", (String)m.get("id"), stl);
/* 43 */       return true;
/*    */     }
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteJobsBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 50 */     if (DBManager.delete("delete_wzj_jobs", m))
/*    */     {
/* 52 */       PublicTableDAO.insertSettingLogs("删除", "招聘信息", (String)m.get("id"), stl);
/* 53 */       return true;
/*    */     }
/* 55 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.jobs.JobsDAO
 * JD-Core Version:    0.6.2
 */