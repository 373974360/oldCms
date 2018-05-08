/*    */ package com.deya.project.searchkeys;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchCustomKeyService
/*    */ {
/*    */   public static List<SearchCustomKey> getSearchCustomKeysList(Map map)
/*    */   {
/* 26 */     return SearchCustomKeyDao.getSearchCustomKeysList(map);
/*    */   }
/*    */ 
/*    */   public static int getSearchCustomKeysListCount(Map map)
/*    */   {
/* 36 */     return SearchCustomKeyDao.getSearchCustomKeysListCount(map);
/*    */   }
/*    */ 
/*    */   public static SearchCustomKey getSearchCustomKeyByTitle(String title, String site_id)
/*    */   {
/* 42 */     return SearchCustomKeyDao.getSearchCustomKeyByTitle(title, site_id);
/*    */   }
/*    */ 
/*    */   public static boolean addSearchCustomKey(SearchCustomKey searchCustomKey)
/*    */   {
/* 51 */     return SearchCustomKeyDao.addSearchCustomKey(searchCustomKey);
/*    */   }
/*    */ 
/*    */   public static boolean updateSearchCustomKeyById(SearchCustomKey searchCustomKey)
/*    */   {
/* 61 */     return SearchCustomKeyDao.updateSearchCustomKeyById(searchCustomKey);
/*    */   }
/*    */ 
/*    */   public static boolean deleteSearchCustomKeys(String ids)
/*    */   {
/* 66 */     String[] id = ids.split(",");
/* 67 */     StringBuffer sb = new StringBuffer();
/* 68 */     for (int i = 0; i < id.length; i++) {
/* 69 */       String s_id = id[i];
/* 70 */       if ((s_id != null) && (!"".equals(s_id))) {
/* 71 */         if (i != id.length - 1)
/* 72 */           sb.append(s_id + ",");
/*    */         else {
/* 74 */           sb.append(s_id);
/*    */         }
/*    */       }
/*    */     }
/* 78 */     return SearchCustomKeyDao.deleteSearchCustomKeys(sb.toString());
/*    */   }
/*    */ 
/*    */   public static void addKeys(String title, String site_id)
/*    */   {
/*    */     try
/*    */     {
/* 86 */       title = title.trim();
/* 87 */       if ((title != null) && (!"".equals(title))) {
/* 88 */         SearchCustomKey searchCustomKey = getSearchCustomKeyByTitle(title, site_id);
/* 89 */         if (searchCustomKey == null) {
/* 90 */           SearchCustomKey searchCustomKeyAdd = new SearchCustomKey();
/* 91 */           searchCustomKeyAdd.setSite_id(site_id);
/* 92 */           searchCustomKeyAdd.setTitle(title);
/* 93 */           SearchCustomKeyDao.addSearchCustomKey(searchCustomKeyAdd);
/*    */         } else {
/* 95 */           SearchCustomKeyDao.updateSearchCustomKeyById(searchCustomKey);
/*    */         }
/*    */       }
/*    */     } catch (Exception e) {
/* 99 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchCustomKeyService
 * JD-Core Version:    0.6.2
 */