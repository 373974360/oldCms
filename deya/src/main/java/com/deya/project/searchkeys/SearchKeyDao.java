/*     */ package com.deya.project.searchkeys;
/*     */ 
/*     */ import com.deya.wcm.dao.PublicTableDAO;
/*     */ import com.deya.wcm.db.DBManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SearchKeyDao
/*     */ {
/*  23 */   private static String TABLE_NAME = "dz_search_keys";
/*     */ 
/*     */   public static List<SearchKey> getSearchKeysList(Map map)
/*     */   {
/*  32 */     return DBManager.queryFList("searchkeys.getSearchKeysList", map);
/*     */   }
/*     */ 
/*     */   public static int getSearchKeysListCount(Map map)
/*     */   {
/*  42 */     return Integer.valueOf(((Integer)DBManager.queryFObj("searchkeys.getSearchKeysListCount", map)).intValue()).intValue();
/*     */   }
/*     */ 
/*     */   public static SearchKey getSearchKeyById(String id)
/*     */   {
/*  48 */     Map map = new HashMap();
/*  49 */     map.put("id", id);
/*  50 */     return (SearchKey)DBManager.queryFObj("searchkeys.sortSearchKeys.getSearchKeyById", map);
/*     */   }
/*     */ 
/*     */   public static boolean addSearchKey(SearchKey searchKey)
/*     */   {
/*  59 */     searchKey.setId(PublicTableDAO.getIDByTableName(TABLE_NAME));
/*     */ 
/*  61 */     return DBManager.insert("searchkeys.addSearchKey", searchKey);
/*     */   }
/*     */ 
/*     */   public static boolean updateSearchKeyById(SearchKey searchKey)
/*     */   {
/*  71 */     return DBManager.update("searchkeys.updateSearchKeyById", searchKey);
/*     */   }
/*     */ 
/*     */   public static boolean deleteSearchKeys(String ids)
/*     */   {
/*  76 */     Map map = new HashMap();
/*  77 */     map.put("ids", ids);
/*  78 */     return DBManager.delete("searchkeys.deleteSearchKeys", map);
/*     */   }
/*     */ 
/*     */   public static boolean sortSearchKeys(String ids)
/*     */   {
/*  84 */     if ((ids != null) && (!"".equals(ids))) {
/*     */       try
/*     */       {
/*  87 */         Map m = new HashMap();
/*  88 */         String[] tempA = ids.split(",");
/*  89 */         for (int i = 0; i < tempA.length; i++)
/*     */         {
/*  91 */           m.put("sort_id", Integer.valueOf(i + 1));
/*  92 */           m.put("id", tempA[i]);
/*  93 */           DBManager.update("searchkeys.sortSearchKeys", m);
/*     */         }
/*  95 */         return true;
/*     */       }
/*     */       catch (Exception e) {
/*  98 */         e.printStackTrace();
/*  99 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 103 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchKeyDao
 * JD-Core Version:    0.6.2
 */