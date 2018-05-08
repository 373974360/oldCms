/*    */ package com.deya.project.dz_wzjhy.person;
/*    */ 
/*    */ import com.deya.project.dz_wzjhy.person.PersonBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;

/*    */ import java.util.List;
import java.util.Map;
/*    */ 
/*    */ public class PersonDAO
/*    */ {
/*    */    public static List<PersonBean> getPersonBeanAllList()
/*    */   {
/* 13 */     return DBManager.queryFList("getPersonBeanAllList", "");
/*    */   }
/*    */ 
/*    */   public static List<PersonBean> getPersonBeanList(Map<String, String> m)
/*    */   {
/* 18 */     return DBManager.queryFList("getPersonBeanList", m);
/*    */   }
/*    */ 
/*    */   public static String getPersonBeanCount(Map<String, String> m) {
/* 22 */     return DBManager.getString("getPersonBeanCount", m);
/*    */   }
/*    */ 
/*    */   public static PersonBean getPersonBean(Map<String, String> m) {
/* 26 */     return (PersonBean)DBManager.queryFObj("getPersonBean", m);
/*    */   }
/*    */ 
/*    */   public static boolean insertPersonBean(PersonBean eb)
/*    */   {
/* 31 */     return DBManager.insert("insert_wzj_person", eb);
/*    */   }
/*    */ 
/*    */   public static boolean updatePersonBean(PersonBean eb)
/*    */   {
/* 36 */     return DBManager.update("update_wzj_person", eb);
/*    */   }
/*    */ 
/*    */   public static boolean auditPerson(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 41 */     if (DBManager.update("audit_wzj_person", m))
/*    */     {
/* 43 */       if (stl != null)
/* 44 */         PublicTableDAO.insertSettingLogs("修改", "个人用户审核状态", (String)m.get("id"), stl);
/* 45 */       return true;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updatePersonPassword(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 52 */     if (DBManager.update("update_wzj_person_pw", m))
/*    */     {
/* 54 */       if (stl != null)
/* 55 */         PublicTableDAO.insertSettingLogs("修改", "个人用户密码", (String)m.get("id"), stl);
/* 56 */       return true;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deletePersonBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 63 */     if (DBManager.delete("delete_wzj_person", m))
/*    */     {
/* 65 */       PublicTableDAO.insertSettingLogs("删除", "个人用户", (String)m.get("id"), stl);
/* 66 */       return true;
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.account.AccountDAO
 * JD-Core Version:    0.6.2
 */