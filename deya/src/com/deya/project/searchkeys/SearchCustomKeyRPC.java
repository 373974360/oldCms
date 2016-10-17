/*    */ package com.deya.project.searchkeys;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchCustomKeyRPC
/*    */ {
/*    */   public static List<SearchCustomKey> getSearchCustomKeysList(Map map)
/*    */   {
/* 30 */     return SearchCustomKeyService.getSearchCustomKeysList(map);
/*    */   }
/*    */ 
/*    */   public static int getSearchCustomKeysListCount(Map map)
/*    */   {
/* 40 */     return SearchCustomKeyService.getSearchCustomKeysListCount(map);
/*    */   }
/*    */ 
/*    */   public static boolean deleteSearchCustomKeys(String ids)
/*    */   {
/* 45 */     return SearchCustomKeyService.deleteSearchCustomKeys(ids);
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchCustomKeyRPC
 * JD-Core Version:    0.6.2
 */