/*    */ package com.deya.project.dz_xmk;
/*    */ 
/*    */ import com.deya.util.DateUtil;
/*    */ import com.deya.util.FormatUtil;
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.dao.PublicTableDAO;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmkManager
/*    */ {
/*    */   public static String getXmkCount(Map<String, String> m)
/*    */   {
/* 15 */     if (m.containsKey("key_word"))
/*    */     {
/* 17 */       if (!FormatUtil.isValiditySQL((String)m.get("key_word")))
/* 18 */         return "0";
/*    */     }
/* 20 */     return XmkDAO.getXmkCount(m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getXmkList(Map<String, String> m) {
/* 24 */     if (m.containsKey("key_word"))
/*    */     {
/* 26 */       if (!FormatUtil.isValiditySQL((String)m.get("key_word")))
/* 27 */         return new ArrayList();
/*    */     }
/* 29 */     return XmkDAO.getXmkList(m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getAllXmkList()
/*    */   {
/* 34 */     return XmkDAO.getAllXmkList();
/*    */   }
/*    */ 
/*    */   public static XmkBean getXmkBean(String id, boolean is_browser)
/*    */   {
/* 39 */     return XmkDAO.getXmkBean(id, is_browser);
/*    */   }
/*    */ 
/*    */   public static boolean insertXmk(XmkBean hb, SettingLogsBean stl)
/*    */   {
/* 44 */     hb.setId(PublicTableDAO.getIDByTableName("dz_xmk"));
/* 45 */     return XmkDAO.insertXmk(hb, stl);
/*    */   }
/*    */ 
/*    */   public static boolean insertSb_xmgl(XmkBean hb)
/*    */   {
/* 50 */     hb.setId(PublicTableDAO.getIDByTableName("dz_xmk"));
			 hb.setAddTime(DateUtil.getCurrentDateTime());
/* 51 */     return XmkDAO.insertXmk(hb);
/*    */   }
/*    */ 
/*    */   public static boolean updateXmk(XmkBean hb, SettingLogsBean stl)
/*    */   {
/* 56 */     return XmkDAO.updateXmk(hb, stl);
/*    */   }
/*    */ 
/*    */   public static boolean publishXmk(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 61 */     if ("1".equals(m.get("status")))
/* 62 */       m.put("pubTime", DateUtil.getCurrentDateTime());
/*    */     else
/* 64 */       m.put("pubTime", "");
/* 65 */     return XmkDAO.publishXmk(m, stl);
/*    */   }
/*    */ 
/*    */   public static boolean deleteXmk(Map<String, String> m, SettingLogsBean stl)
/*    */   {
/* 70 */     return XmkDAO.deleteXmk(m, stl);
/*    */   }
/*    */ }

/* Location:           E:\Xshell\219.144.222.46(省水保)\classes\com.zip
 * Qualified Name:     com.deya.project.sb_xmgl.Sb_xmglManager
 * JD-Core Version:    0.6.2
 */