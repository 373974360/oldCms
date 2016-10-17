/*    */ package com.deya.project.dz_wzjhy.person;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.deya.license.tools.CryptoTools;
import com.deya.project.dz_wzjhy.enterprise.EnterpriseBean;
import com.deya.project.dz_wzjhy.enterprise.EnterpriseDAO;
import com.deya.project.dz_wzjhy.enterprise.EnterpriseLogin;
import com.deya.project.dz_wzjhy.jobs.JobsDAO;
import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
/*    */ 
/*    */ public class PersonManager
/*    */ {
			
			public static List<PersonBean> getPersonBeanList(Map<String, String> m)
/*    */   {
/* 14 */     return PersonDAO.getPersonBeanList(m);
/*    */   }
/*    */ 
/*    */   public static String getPersonBeanCount(Map<String, String> m) {
/* 18 */     return PersonDAO.getPersonBeanCount(m);
/*    */   }
/*    */ 
/*    */   public static PersonBean getPersonBean(Map<String, String> m) {
/* 22 */     return PersonDAO.getPersonBean(m);
/*    */   }
/*    */ 
/*    */   public static boolean insertPersonBean(PersonBean eb)
/*    */   {
/* 27 */     eb.setId(PublicTableDAO.getIDByTableName("wzj_person"));
/* 28 */     CryptoTools ct = new CryptoTools();
/* 29 */     eb.setPassword((ct.encode(eb.getPassword())));
/* 30 */     eb.setAddTime((DateUtil.getCurrentDateTime()));
/* 31 */     if (PersonDAO.insertPersonBean(eb))
/*    */     {
/* 33 */       PersonLogin.raloadCatch();
/* 34 */       return true;
/*    */     }
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updatePersonBean(PersonBean eb)
/*    */   {
/* 41 */     eb.setUpdateTime((DateUtil.getCurrentDateTime()));
/* 42 */     if (PersonDAO.updatePersonBean(eb))
/*    */     {
/* 44 */       PersonLogin.raloadCatch();
/* 45 */       return true;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean auditPerson(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 53 */     if (PersonDAO.auditPerson(m, stl))
/*    */     {
/* 55 */       PersonLogin.raloadCatch();
/* 56 */       return true;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean updatePersonPassword(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 63 */     CryptoTools ct = new CryptoTools();
/* 64 */     m.put("password", ct.encode((String)m.get("password")));
/* 65 */     if (PersonDAO.updatePersonPassword(m, stl))
/*    */     {
/* 67 */       PersonLogin.raloadCatch();
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deletePersonBean(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 75 */     if (PersonDAO.deletePersonBean(m, stl))
/*    */     {
/* 77 */       PersonLogin.raloadCatch();
/* 78 */       m.put("id", (String)m.get("id"));
/* 80 */       JobsDAO.deleteJobsBean(m, stl);
/* 81 */       return true;
/*    */     }
/* 83 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.account.AccountManager
 * JD-Core Version:    0.6.2
 */