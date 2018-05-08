/*    */ package com.deya.project.dz_wzjhy.enterprise;
/*    */ 
/*    */ import com.deya.license.tools.CryptoTools;
/*    */ import com.deya.wcm.services.org.user.SessionManager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class EnterpriseLogin
/*    */ {
/* 15 */   private static Map<String, EnterpriseBean> en_map = new HashMap();
/*    */ 
/*    */   static {
/* 18 */     raloadCatch();
/*    */   }
/*    */ 
/*    */   public static void raloadCatch()
/*    */   {
/* 23 */     en_map.clear();
/*    */     try {
/* 25 */       List<EnterpriseBean> l = EnterpriseDAO.getEnterpriseBeanAllList();
/* 26 */       if ((l != null) && (l.size() > 0))
/*    */       {
/* 28 */         for (EnterpriseBean eb : l)
/*    */         {
/* 30 */           en_map.put(eb.getUser_name(), eb);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 35 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getCname(int ent_id)
/*    */   {
/* 41 */     String cname = "";
/* 42 */     List<EnterpriseBean> l = new ArrayList(en_map.values());
/* 43 */     if ((l != null) && (l.size() > 0))
/*    */     {
/* 45 */       for (EnterpriseBean eb : l)
/*    */       {
/* 47 */         if (eb.getId() == ent_id)
/*    */         {
/* 49 */           cname = eb.getCname();
/* 50 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 54 */     return cname;
/*    */   }
/*    */ 
/*    */   public static boolean usernameISExist(String user_name)
/*    */   {
/* 59 */     return en_map.containsKey(user_name);
/*    */   }
/*    */ 
/*    */   public static String enterpriseLogin(String user_name, String pw, HttpServletRequest request)
/*    */   {
/* 65 */     String result = "-1";
/* 66 */     if (en_map.containsKey(user_name))
/*    */     {
/* 68 */       EnterpriseBean eb = (EnterpriseBean)en_map.get(user_name);
/* 69 */       CryptoTools ct = new CryptoTools();
/* 70 */       if (eb.getPw().equals(ct.encode(pw)))
/*    */       {
/* 72 */         if (eb.getIs_audit() == 0)
/*    */         {
/* 74 */           result = "-2";
/*    */         }
/* 77 */         else if (eb.getIs_audit() == -1)
/*    */         {
/* 79 */           result = "-3";
/*    */         }
/*    */         else {
/* 82 */           result = "0";
/* 83 */           SessionManager.set(request, "wzj_login_enterprise", eb);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 88 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.EnterpriseLogin
 * JD-Core Version:    0.6.2
 */