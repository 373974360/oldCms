/*    */ package com.deya.project.searchkeys;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchKeyService
/*    */ {
/*    */   public static List<SearchKey> getSearchKeysList(Map map)
/*    */   {
/* 30 */     return SearchKeyDao.getSearchKeysList(map);
/*    */   }
/*    */ 
/*    */   public static int getSearchKeysListCount(Map map)
/*    */   {
/* 40 */     return SearchKeyDao.getSearchKeysListCount(map);
/*    */   }
/*    */ 
/*    */   public static SearchKey getSearchKeyById(String id)
/*    */   {
/* 46 */     return SearchKeyDao.getSearchKeyById(id);
/*    */   }
/*    */ 
/*    */   public static boolean addSearchKey(SearchKey searchKey)
/*    */   {
/* 55 */     return SearchKeyDao.addSearchKey(searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean updateSearchKeyById(SearchKey searchKey)
/*    */   {
/* 65 */     return SearchKeyDao.updateSearchKeyById(searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean deleteSearchKeys(String ids)
/*    */   {
/* 70 */     String[] id = ids.split(",");
/* 71 */     StringBuffer sb = new StringBuffer();
/* 72 */     for (int i = 0; i < id.length; i++) {
/* 73 */       String s_id = id[i];
/* 74 */       if ((s_id != null) && (!"".equals(s_id))) {
/* 75 */         if (i != id.length - 1)
/* 76 */           sb.append(s_id + ",");
/*    */         else {
/* 78 */           sb.append(s_id);
/*    */         }
/*    */       }
/*    */     }
/* 82 */     return SearchKeyDao.deleteSearchKeys(sb.toString());
/*    */   }
/*    */ 
/*    */   public static boolean sortSearchKeys(String ids)
/*    */   {
/* 88 */     return SearchKeyDao.sortSearchKeys(ids);
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchKeyService
 * JD-Core Version:    0.6.2
 */