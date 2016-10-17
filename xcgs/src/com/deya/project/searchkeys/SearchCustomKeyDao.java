/*    */ package com.deya.project.searchkeys;
/*    */ 
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchCustomKeyDao
/*    */ {
/* 23 */   private static String TABLE_NAME = "dz_search_customkeys";
/*    */ 
/*    */   public static List<SearchCustomKey> getSearchCustomKeysList(Map map)
/*    */   {
/* 32 */     return DBManager.queryFList("getSearchCustomKeysList", map);
/*    */   }
/*    */ 
/*    */   public static int getSearchCustomKeysListCount(Map map)
/*    */   {
/* 42 */     return Integer.valueOf(((Integer)DBManager.queryFObj("getSearchCustomKeysListCount", map)).intValue()).intValue();
/*    */   }
/*    */ 
/*    */   public static SearchCustomKey getSearchCustomKeyByTitle(String title, String site_id)
/*    */   {
/* 48 */     Map map = new HashMap();
/* 49 */     map.put("title", title);
/* 50 */     map.put("site_id", site_id);
/* 51 */     return (SearchCustomKey)DBManager.queryFObj("getSearchCustomKeyByTitle", map);
/*    */   }
/*    */ 
/*    */   public static boolean addSearchCustomKey(SearchCustomKey searchKey)
/*    */   {
/* 60 */     searchKey.setId(PublicTableDAO.getIDByTableName(TABLE_NAME));
/*    */ 
/* 62 */     return DBManager.insert("addSearchCustomKey", searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean updateSearchCustomKeyById(SearchCustomKey searchKey)
/*    */   {
/* 72 */     return DBManager.update("updateSearchCustomKeyById", searchKey);
/*    */   }
/*    */ 
/*    */   public static boolean deleteSearchCustomKeys(String ids)
/*    */   {
/* 77 */     Map map = new HashMap();
/* 78 */     map.put("ids", ids);
/* 79 */     return DBManager.delete("deleteSearchCustomKeys", map);
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchCustomKeyDao
 * JD-Core Version:    0.6.2
 */