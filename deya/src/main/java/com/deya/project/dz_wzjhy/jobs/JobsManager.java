/*    */ package com.deya.project.dz_wzjhy.jobs;
/*    */ 
/*    */ import com.deya.project.dz_wzjhy.enterprise.EnterpriseLogin;
/*    */ import com.deya.util.DateUtil;
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class JobsManager
/*    */ {
/*    */   public static List<JobsBean> getJobsBeanList(Map<String, String> m)
/*    */   {
/* 16 */     List<JobsBean> l = JobsDAO.getJobsBeanList(m);
/* 17 */     if ((l != null) && (l.size() > 0))
/*    */     {
/* 19 */       for (JobsBean jb : l)
/*    */       {
/* 21 */         jb.setCname(EnterpriseLogin.getCname(Integer.parseInt(jb.getEnt_id())));
/*    */       }
/*    */     }
/* 24 */     return l;
/*    */   }
/*    */ 
/*    */   public static String getJobsBeanCount(Map<String, String> m)
/*    */   {
/* 29 */     return JobsDAO.getJobsBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static JobsBean getJobsBean(Map<String, String> m)
/*    */   {
/* 34 */     return JobsDAO.getJobsBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean insertJobsBean(JobsBean rb)
/*    */   {
/* 39 */     rb.setId(PublicTableDAO.getIDByTableName("wzj_jobs"));
/* 40 */     rb.setAdd_dtime(DateUtil.getCurrentDateTime());
/* 41 */     return JobsDAO.insertJobsBean(rb);
/*    */   }
/*    */ 
/*    */   public static boolean updateJobsBean(JobsBean rb)
/*    */   {
/* 46 */     rb.setUpdate_dtime(DateUtil.getCurrentDateTime());
/* 47 */     return JobsDAO.updateJobsBean(rb);
/*    */   }
/*    */ 
/*    */   public static boolean publishJobsBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 52 */     if ("1".equals(m.get("is_publish")))
/* 53 */       m.put("publish_dtime", DateUtil.getCurrentDateTime());
/*    */     else
/* 55 */       m.put("publish_dtime", "");
/* 56 */     return JobsDAO.publishJobsBean(m, stl);
/*    */   }
/*    */ 
/*    */   public static boolean deleteJobsBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 61 */     return JobsDAO.deleteJobsBean(m, stl);
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.jobs.JobsManager
 * JD-Core Version:    0.6.2
 */