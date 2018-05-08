/*     */ package com.deya.project.dz_wzjhy.resume;
/*     */ 
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.wcm.bean.template.TurnPageBean;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WZJResumeData
/*     */ {
/*     */   public static ResumeBean getResumeForAccount(String account)
/*     */   {
/*  13 */     Map m = new HashMap();
/*  14 */     m.put("account", account);
/*  15 */     return ResumeManager.getResumeBean(m);
/*     */   }
/*     */ 
/*     */   public static ResumeBean getResumeObject(String id)
/*     */   {
/*  20 */     Map m = new HashMap();
/*  21 */     m.put("id", id);
/*  22 */     m.put("is_publish", "1");
/*  23 */     return ResumeManager.getResumeBean(m);
/*     */   }
/*     */ 
/*     */   public static List<ResumeBean> getResumeBeanList(String params)
/*     */   {
/*  28 */     Map map = new HashMap();
/*  29 */     getResumeSearchCon(params, map);
/*  30 */     int start_page = Integer.parseInt((String)map.get("current_page"));
/*  31 */     int page_size = Integer.parseInt((String)map.get("page_size"));
/*  32 */     map.put("start_num", (start_page - 1) * page_size);
/*  33 */     map.put("page_size", page_size);
/*  34 */     return ResumeManager.getResumeBeanList(map);
/*     */   }
/*     */ 
/*     */   public static TurnPageBean getInfoCount(String params)
/*     */   {
/*  39 */     Map con_map = new HashMap();
/*  40 */     getResumeSearchCon(params, con_map);
/*  41 */     TurnPageBean tpb = new TurnPageBean();
/*  42 */     tpb.setCount(Integer.parseInt(ResumeManager.getResumeBeanCount(con_map)));
/*  43 */     int cur_page = Integer.parseInt((String)con_map.get("current_page"));
/*  44 */     int page_size = Integer.parseInt((String)con_map.get("page_size"));
/*     */ 
/*  46 */     tpb.setCur_page(cur_page);
/*  47 */     tpb.setPage_size(page_size);
/*  48 */     tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
/*     */ 
/*  50 */     if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
/*  51 */       tpb.setPage_count(tpb.getPage_count() - 1);
/*     */     }
/*  53 */     if (cur_page > 1) {
/*  54 */       tpb.setPrev_num(cur_page - 1);
/*     */     }
/*  56 */     tpb.setNext_num(tpb.getPage_count());
/*  57 */     if (cur_page < tpb.getPage_count()) {
/*  58 */       tpb.setNext_num(cur_page + 1);
/*     */     }
/*     */ 
/*  61 */     if (tpb.getPage_count() > 10)
/*     */     {
/*  64 */       if (cur_page > 5)
/*     */       {
/*  66 */         if (cur_page > tpb.getPage_count() - 4)
/*  67 */           tpb.setCurr_start_num(tpb.getPage_count() - 6);
/*     */         else
/*  69 */           tpb.setCurr_start_num(cur_page - 2);
/*     */       }
/*     */     }
/*  72 */     return tpb;
/*     */   }
/*     */ 
/*     */   public static void getResumeSearchCon(String params, Map<String, String> con_map)
/*     */   {
/*  77 */     int cur_page = 1;
/*  78 */     int page_size = 15;
/*  79 */     con_map.put("is_publish", "1");
/*  80 */     String orderby = "publish_dtime desc";
/*  81 */     String[] tempA = params.split(";");
/*  82 */     for (int i = 0; i < tempA.length; i++)
/*     */     {
/*  84 */       if (tempA[i].toLowerCase().startsWith("orderby="))
/*     */       {
/*  86 */         String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  87 */         if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by)))
/*     */         {
/*  89 */           orderby = o_by;
/*     */         }
/*     */       }
/*  92 */       if (tempA[i].toLowerCase().startsWith("size="))
/*     */       {
/*  94 */         String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  95 */         if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps)))
/*  96 */           page_size = Integer.parseInt(ps);
/*     */       }
/*  98 */       if (tempA[i].toLowerCase().startsWith("cur_page="))
/*     */       {
/* 100 */         String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 101 */         if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp)))
/* 102 */           cur_page = Integer.parseInt(cp);
/*     */       }
/*     */     }
/* 105 */     con_map.put("page_size", page_size+"");
/* 106 */     con_map.put("current_page", cur_page+"");
/* 107 */     con_map.put("order_by", orderby);
/*     */   }
/*     */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.resume.JPWResumeData
 * JD-Core Version:    0.6.2
 */