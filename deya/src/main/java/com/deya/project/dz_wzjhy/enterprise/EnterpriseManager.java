/*    */ package com.deya.project.dz_wzjhy.enterprise;
/*    */ 
/*    */ import com.deya.license.tools.CryptoTools;
/*    */ import com.deya.project.dz_wzjhy.jobs.JobsDAO;
/*    */ import com.deya.util.DateUtil;
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EnterpriseManager
/*    */ {
/*    */   public static List<EnterpriseBean> getEnterpriseBeanList(Map<String, String> m)
/*    */   {
/* 14 */     return EnterpriseDAO.getEnterpriseBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getEnterpriseBeanCount(Map<String, String> m) {
/* 18 */     return EnterpriseDAO.getEnterpriseBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static EnterpriseBean getEnterpriseBean(Map<String, String> m) {
/* 22 */     return EnterpriseDAO.getEnterpriseBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean insertEnterpriseBean(EnterpriseBean eb)
/*    */   {
/* 27 */     eb.setId(PublicTableDAO.getIDByTableName("wzj_enterprise"));
/* 28 */     CryptoTools ct = new CryptoTools();
/* 29 */     eb.setPw(ct.encode(eb.getPw()));
/* 30 */     eb.setAdd_dtime(DateUtil.getCurrentDateTime());
/* 31 */     if (EnterpriseDAO.insertEnterpriseBean(eb))
/*    */     {
/* 33 */       EnterpriseLogin.raloadCatch();
/* 34 */       return true;
/*    */     }
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updateEnterpriseBean(EnterpriseBean eb)
/*    */   {
/* 41 */     eb.setUpdate_dtime(DateUtil.getCurrentDateTime());
/* 42 */     if (EnterpriseDAO.updateEnterpriseBean(eb))
/*    */     {
/* 44 */       EnterpriseLogin.raloadCatch();
/* 45 */       return true;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean auditEnterprise(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 52 */     m.put("audit_dtime", DateUtil.getCurrentDateTime());
/* 53 */     if (EnterpriseDAO.auditEnterprise(m, stl))
/*    */     {
/* 55 */       EnterpriseLogin.raloadCatch();
/* 56 */       return true;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updateEnterprisePassword(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 63 */     CryptoTools ct = new CryptoTools();
/* 64 */     m.put("pw", ct.encode((String)m.get("pw")));
/* 65 */     if (EnterpriseDAO.updateEnterprisePassword(m, stl))
/*    */     {
/* 67 */       EnterpriseLogin.raloadCatch();
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteEnterpriseBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 75 */     if (EnterpriseDAO.deleteEnterpriseBean(m, stl))
/*    */     {
/* 77 */       EnterpriseLogin.raloadCatch();
/* 78 */       m.put("ent_id", (String)m.get("id"));
/* 79 */       m.remove("id");
/* 80 */       JobsDAO.deleteJobsBean(m, stl);
/* 81 */       return true;
/*    */     }
/* 83 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.EnterpriseManager
 * JD-Core Version:    0.6.2
 */