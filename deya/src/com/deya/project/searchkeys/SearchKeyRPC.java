/*    */ package com.deya.project.searchkeys;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchKeyRPC
/*    */ {
/*    */   public static List<SearchKey> getSearchKeysList(Map map)
/*    */   {
/* 30 */     return SearchKeyService.getSearchKeysList(map);
/*    */   }
/*    */ 
/*    */   public static int getSearchKeysListCount(Map map)
/*    */   {
/* 40 */     return SearchKeyService.getSearchKeysListCount(map);
/*    */   }
/*    */ 
/*    */   public static SearchKey getSearchKeyById(String id)
/*    */   {
/* 46 */     return SearchKeyService.getSearchKeyById(id);
/*    */   }
/*    */ 
/*    */   public static boolean addSearchKey(SearchKey searchKey)
/*    */   {
/* 55 */     return SearchKeyService.addSearchKey(searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean updateSearchKeyById(SearchKey searchKey)
/*    */   {
/* 65 */     return SearchKeyService.updateSearchKeyById(searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean deleteSearchKeys(String ids)
/*    */   {
/* 70 */     return SearchKeyService.deleteSearchKeys(ids);
/*    */   }
/*    */ 
/*    */   public static boolean sortSearchKeys(String ids)
/*    */   {
/* 76 */     return SearchKeyService.sortSearchKeys(ids);
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchKeyRPC
 * JD-Core Version:    0.6.2
 */