/*    */ package com.deya.project.dz_wzjhy.enterprise;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EnterpriseDAO
/*    */ {
/*    */   public static List<EnterpriseBean> getEnterpriseBeanAllList()
/*    */   {
/* 13 */     return DBManager.queryFList("getwzjEnterpriseBeanAllList", "");
/*    */   }
/*    */ 
/*    */   public static List<EnterpriseBean> getEnterpriseBeanList(Map<String, String> m)
/*    */   {
/* 18 */     return DBManager.queryFList("getwzjEnterpriseBeanList", m);
/*    */   }
/*    */ 
/*    */   public static String getEnterpriseBeanCount(Map<String, String> m) {
/* 22 */     return DBManager.getString("getwzjEnterpriseBeanCount", m);
/*    */   }
/*    */ 
/*    */   public static EnterpriseBean getEnterpriseBean(Map<String, String> m) {
/* 26 */     return (EnterpriseBean)DBManager.queryFObj("getwzjEnterpriseBean", m);
/*    */   }
/*    */ 
/*    */   public static boolean insertEnterpriseBean(EnterpriseBean eb)
/*    */   {
/* 31 */     return DBManager.insert("insert_wzj_enterprise", eb);
/*    */   }
/*    */ 
/*    */   public static boolean updateEnterpriseBean(EnterpriseBean eb)
/*    */   {
/* 36 */     return DBManager.update("update_wzj_enterprise", eb);
/*    */   }
/*    */ 
/*    */   public static boolean auditEnterprise(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 41 */     if (DBManager.update("audit_wzj_enterprise", m))
/*    */     {
/* 43 */       if (stl != null)
/* 44 */         PublicTableDAO.insertSettingLogs("修改", "企业用户审核状态", (String)m.get("id"), stl);
/* 45 */       return true;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updateEnterprisePassword(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 52 */     if (DBManager.update("update_wzj_enterprise_pw", m))
/*    */     {
/* 54 */       if (stl != null)
/* 55 */         PublicTableDAO.insertSettingLogs("修改", "企业用户密码", (String)m.get("id"), stl);
/* 56 */       return true;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteEnterpriseBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 63 */     if (DBManager.delete("delete_wzj_enterprise", m))
/*    */     {
/* 65 */       PublicTableDAO.insertSettingLogs("删除", "企业用户", (String)m.get("id"), stl);
/* 66 */       return true;
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.EnterpriseDAO
 * JD-Core Version:    0.6.2
 */