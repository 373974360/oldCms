/*    */ package com.deya.project.dz_wzjhy.resume;
/*    */ 
/*    */ import com.deya.util.DateUtil;
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ResumeManager
/*    */ {
/*    */   public static List<ResumeBean> getResumeBeanList(Map<String, String> m)
/*    */   {
/* 13 */     return ResumeDAO.getResumeBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getResumeBeanCount(Map<String, String> m)
/*    */   {
/* 18 */     return ResumeDAO.getResumeBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static ResumeBean getResumeBean(Map<String, String> m)
/*    */   {
/* 23 */     return ResumeDAO.getResumeBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean insertResumeBean(ResumeBean rb)
/*    */   {
/* 28 */     rb.setId(PublicTableDAO.getIDByTableName("wzj_resume"));
/* 29 */     rb.setAdd_dtime(DateUtil.getCurrentDateTime());
/* 30 */     return ResumeDAO.insertResumeBean(rb);
/*    */   }
/*    */ 
/*    */   public static boolean updateResumeBean(ResumeBean rb, SettingLogsBean stl)
/*    */   {
/* 35 */     rb.setUpdate_dtime(DateUtil.getCurrentDateTime());
/* 36 */     return ResumeDAO.updateResumeBean(rb, stl);
/*    */   }
/*    */ 
/*    */   public static boolean publishResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 41 */     if ("1".equals(m.get("is_publish")))
/* 42 */       m.put("publish_dtime", DateUtil.getCurrentDateTime());
/*    */     else
/* 44 */       m.put("publish_dtime", "");
/* 45 */     return ResumeDAO.publishResumeBean(m, stl);
/*    */   }
/*    */ 
/*    */   public static boolean tuijianResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 50 */     if ("1".equals(m.get("is_tuijian")))
/* 51 */       m.put("tuijian_dtime", DateUtil.getCurrentDateTime());
/*    */     else
/* 53 */       m.put("tuijian_dtime", "");
/* 54 */     return ResumeDAO.tuijianResumeBean(m, stl);
/*    */   }
/*    */ 
/*    */   public static boolean deleteResumeBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 59 */     return ResumeDAO.deleteResumeBean(m, stl);
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.resume.ResumeManager
 * JD-Core Version:    0.6.2
 */