/*    */ package com.deya.project.dz_xmk;
/*    */ 
/*    */ import com.deya.wcm.bean.logs.SettingLogsBean;
/*    */ import com.deya.wcm.services.Log.LogManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class XmkRPC
/*    */ {
/*    */   public static String getXmkCount(Map<String, String> m)
/*    */   {
/* 14 */     return XmkManager.getXmkCount(m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getXmkList(Map<String, String> m)
/*    */   {
/* 19 */     return XmkManager.getXmkList(m);
/*    */   }
/*    */ 
/*    */   public static List<XmkBean> getAllXmkList()
/*    */   {
/* 24 */     return XmkManager.getAllXmkList();
/*    */   }
/*    */ 
/*    */   public static XmkBean getXmkBean(String gq_id, boolean is_browser)
/*    */   {
/* 30 */     return XmkManager.getXmkBean(gq_id, is_browser);
/*    */   }
/*    */ 
/*    */   public static boolean updateXmk(XmkBean hb, HttpServletRequest request)
/*    */   {
/* 35 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 36 */     if (stl != null) {
/* 37 */       return XmkManager.updateXmk(hb, stl);
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean insertXmk(XmkBean hb, HttpServletRequest request)
/*    */   {
/* 44 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 45 */     if (stl != null) {
/* 46 */       return XmkManager.insertXmk(hb, stl);
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean publishXmk(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 53 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 54 */     if (stl != null) {
/* 55 */       return XmkManager.publishXmk(m, stl);
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean deleteXmk(Map<String, String> m, HttpServletRequest request)
/*    */   {
/* 62 */     SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
/* 63 */     if (stl != null) {
/* 64 */       return XmkManager.deleteXmk(m, stl);
/*    */     }
/* 66 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Xshell\219.144.222.46(省水保)\classes\com.zip
 * Qualified Name:     com.deya.project.sb_xmgl.Sb_xmglRPC
 * JD-Core Version:    0.6.2
 */