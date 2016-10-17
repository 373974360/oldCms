/*    */ package com.deya.project.dz_wzjhy.resume;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ResumeDAO
/*    */ {
/*    */   public static List<ResumeBean> getResumeBeanList(Map<String, String> m)
/*    */   {
/* 14 */     return DBManager.queryFList("getwzjResumeBeanList", m);
/*    */   }
/*    */ 
/*    */   public static String getResumeBeanCount(Map<String, String> m)
/*    */   {
/* 19 */     return DBManager.getString("getwzjResumeBeanCount", m);
/*    */   }
/*    */ 
/*    */   public static ResumeBean getResumeBean(Map<String, String> m)
/*    */   {
/* 24 */     return (ResumeBean)DBManager.queryFObj("getwzjResumeBean", m);
/*    */   }
/*    */ 
/*    */   public static boolean insertResumeBean(ResumeBean rb)
/*    */   {
/* 29 */     return DBManager.insert("insert_wzj_resume", rb);
/*    */   }
/*    */ 
/*    */   public static boolean updateResumeBean(ResumeBean rb, SettingLogsBean stl)
/*    */   {
/* 34 */     if (DBManager.update("update_wzj_resume", rb))
/*    */     {
/* 36 */       if (stl != null)
/* 37 */         PublicTableDAO.insertSettingLogs("修改", "个人简历", rb.getAccount(), stl);
/* 38 */       return true;
/*    */     }
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean publishResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 45 */     if (DBManager.update("publish_wzj_resume", m))
/*    */     {
/* 47 */       PublicTableDAO.insertSettingLogs("修改", "个人简历发布状态", (String)m.get("id"), stl);
/* 48 */       return true;
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean tuijianResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 55 */     if (DBManager.update("tuijian_wzj_resume", m))
/*    */     {
/* 57 */       PublicTableDAO.insertSettingLogs("修改", "个人简历推荐状态", (String)m.get("id"), stl);
/* 58 */       return true;
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 65 */     if (DBManager.delete("delete_wzj_resume", m))
/*    */     {
/* 67 */       PublicTableDAO.insertSettingLogs("删除", "个人简历", (String)m.get("id"), stl);
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.resume.ResumeDAO
 * JD-Core Version:    0.6.2
 */