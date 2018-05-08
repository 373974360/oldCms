/*    */ package com.deya.project.dz_wzjhy.person;
/*    */ 
/*    */ import com.deya.license.tools.CryptoTools;
/*    */ import com.deya.wcm.services.org.user.SessionManager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class PersonLogin
/*    */ {
/* 15 */   private static Map<String, PersonBean> en_map = new HashMap();
/*    */ 
/*    */   static {
/* 18 */     raloadCatch();
/*    */   }
/*    */ 
/*    */   public static void raloadCatch()
/*    */   {
/* 23 */     en_map.clear();
/*    */     try {
/* 25 */       List<PersonBean> l = PersonDAO.getPersonBeanAllList();
/* 26 */       if ((l != null) && (l.size() > 0))
/*    */       {
/* 28 */         for (PersonBean eb : l)
/*    */         {
/* 30 */           en_map.put(eb.getUsername(), eb);
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
/* 42 */     List<PersonBean> l = new ArrayList(en_map.values());
/* 43 */     if ((l != null) && (l.size() > 0))
/*    */     {
/* 45 */       for (PersonBean eb : l)
/*    */       {
/* 47 */         if (eb.getId() == ent_id)
/*    */         {
/* 49 */           cname = eb.getXm();
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
/* 68 */       PersonBean eb = (PersonBean)en_map.get(user_name);
/* 69 */       CryptoTools ct = new CryptoTools();
/* 70 */       if (eb.getPassword().equals(ct.encode(pw)))
/*    */       {
/* 72 */         if (Integer.parseInt(eb.getStatus()) == 0)
/*    */         {
/* 74 */           result = "-2";
/*    */         }
/* 77 */         else if (Integer.parseInt(eb.getStatus()) == -1)
/*    */         {
/* 79 */           result = "-3";
/*    */         }
/*    */         else {
/* 82 */           result = "0";
/* 83 */           SessionManager.set(request, "jpw_login_person", eb);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 88 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.PersonLogin
 * JD-Core Version:    0.6.2
 */