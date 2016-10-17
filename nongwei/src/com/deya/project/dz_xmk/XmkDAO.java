/*    */ package com.deya.project.dz_xmk;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import com.deya.wcm.db.DBManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmkDAO
/*    */ {
/*    */   public static String getXmkCount(Map<String, String> m)
/*    */   {
/* 14 */     return DBManager.getString("getXmkCount", m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getXmkList(Map<String, String> m)
/*    */   {
/* 19 */     return DBManager.queryFList("getXmkList", m);
/*    */   }
/*    */ 
/*    */   public static XmkBean getXmkBean(String id, boolean is_browser)
/*    */   {
/* 24 */     Map m = new HashMap();
/* 25 */     m.put("id", id);
/* 26 */     if (is_browser)
/* 27 */       m.put("is_browser", "1");
/* 28 */     return (XmkBean)DBManager.queryFObj("getXmkBean", m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getAllXmkList()
/*    */   {
/* 33 */     return DBManager.queryFList("getAllXmkList", "");
/*    */   }
/*    */ 
/*    */   public static boolean insertXmk(XmkBean xmk, SettingLogsBean stl)
/*    */   {
/* 39 */     if (DBManager.insert("insertXmk", xmk))
/*    */     {
/* 41 */       PublicTableDAO.insertSettingLogs("添加", "项目信息", xmk.getId()+"", stl);
/* 42 */       return true;
/*    */     }
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean insertXmk(XmkBean xmk)
/*    */   {
/* 49 */     return DBManager.insert("insertXmk", xmk);
/*    */   }
/*    */ 
/*    */   public static boolean updateXmk(XmkBean xmk, SettingLogsBean stl)
/*    */   {
/* 55 */     if (DBManager.update("updateXmk", xmk))
/*    */     {
/* 57 */       PublicTableDAO.insertSettingLogs("修改", "项目信息", xmk.getId()+"", stl);
/* 58 */       return true;
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean publishXmk(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 65 */     if (DBManager.update("publishXmk", m))
/*    */     {
/* 67 */       PublicTableDAO.insertSettingLogs("发布设置", "项目信息", (String)m.get("ids"), stl);
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteXmk(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 75 */     if (DBManager.delete("deleteXmk", m))
/*    */     {
/* 77 */       PublicTableDAO.insertSettingLogs("删除", "项目信息", (String)m.get("ids"), stl);
/* 78 */       return true;
/*    */     }
/* 80 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\219.144.222.46(省水保)\classes\com.zip
 * Qualified Name:     com.deya.project.sb_xmgl.Sb_xmglDAO
 * JD-Core Version:    0.6.2
 */