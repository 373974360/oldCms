/*     */ package com.deya.project.dz_pxxx;
/*     */ 
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.wcm.bean.template.TurnPageBean;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PxxxData
/*     */ {
/*  13 */   private static int cur_page = 1;
/*  14 */   private static int page_size = 15;
/*     */ 
/*     */   public static void getPxxxSearchCon(String params, Map<String, String> con_map)
/*     */   {
/*  19 */     String orderby = "pxsj desc";
/*  20 */     String[] tempA = params.split(";");
/*  21 */     for (int i = 0; i < tempA.length; i++)
/*     */     {
/*  23 */       if (tempA[i].toLowerCase().startsWith("kw="))
/*     */       {
/*  25 */         String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  27 */         if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw)))
/*     */         {
/*  29 */           con_map.put("keyword", kw);
/*     */         }
/*     */       }
/*  42 */       if (tempA[i].toLowerCase().startsWith("pxmc="))
/*     */       {
/*  44 */         String pxmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  46 */         if ((!"".equals(pxmc)) && (!pxmc.startsWith("$pxmc")) && (FormatUtil.isValiditySQL(pxmc)))
/*     */         {
/*  48 */           con_map.put("pxmc", pxmc);
/*     */         }
/*     */       }
/*  51 */       if (tempA[i].toLowerCase().startsWith("pxlx="))
/*     */       {
/*  53 */         String pxlx = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  55 */         if ((!"".equals(pxlx)) && (!pxlx.startsWith("$pxlx")) && (FormatUtil.isValiditySQL(pxlx)))
/*     */         {
/*  57 */           con_map.put("pxlx", pxlx);
/*     */         }
/*     */       }
/*  79 */       if (tempA[i].toLowerCase().startsWith("orderby="))
/*     */       {
/*  81 */         String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  82 */         if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by)))
/*     */         {
/*  84 */           orderby = o_by;
/*     */         }
/*     */       }
/*  87 */       if (tempA[i].toLowerCase().startsWith("size="))
/*     */       {
/*  89 */         String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  90 */         if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps)))
/*  91 */           page_size = Integer.parseInt(ps);
/*     */       }
/*  93 */       if (tempA[i].toLowerCase().startsWith("cur_page="))
/*     */       {
/*  95 */         String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  96 */         if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp)))
/*  97 */           cur_page = Integer.parseInt(cp);
/*     */       }
/*     */     }
/* 101 */     con_map.put("page_size", page_size+"");
/* 102 */     con_map.put("current_page", cur_page+"");
/* 103 */     con_map.put("orderby", orderby);
/*     */   }
/*     */ 
/*     */   public static TurnPageBean getPxxxCount(String params)
/*     */   {
/* 109 */     Map con_map = new HashMap();
/* 110 */     getPxxxSearchCon(params, con_map);
/*     */ 
/* 112 */     TurnPageBean tpb = new TurnPageBean();
/* 113 */     tpb.setCount(Integer.parseInt(PxxxManager.getPxxxCount(con_map)));
/* 114 */     tpb.setCur_page(cur_page);
/* 115 */     tpb.setPage_size(page_size);
/* 116 */     tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
/*     */ 
/* 118 */     if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
/* 119 */       tpb.setPage_count(tpb.getPage_count() - 1);
/*     */     }
/* 121 */     if (cur_page > 1) {
/* 122 */       tpb.setPrev_num(cur_page - 1);
/*     */     }
/* 124 */     tpb.setNext_num(tpb.getPage_count());
/* 125 */     if (cur_page < tpb.getPage_count()) {
/* 126 */       tpb.setNext_num(cur_page + 1);
/*     */     }
/*     */ 
/* 129 */     if (tpb.getPage_count() > 10)
/*     */     {
/* 131 */       if (cur_page > 5)
/*     */       {
/* 133 */         if (cur_page > tpb.getPage_count() - 4)
/* 134 */           tpb.setCurr_start_num(tpb.getPage_count() - 6);
/*     */         else
/* 136 */           tpb.setCurr_start_num(cur_page - 2);
/*     */       }
/*     */     }
/* 139 */     return tpb;
/*     */   }
/*     */ 
/*     */   public static List<PxxxBean> getPxxxList(String params) {
/* 143 */     Map con_map = new HashMap();
/* 144 */     getPxxxSearchCon(params, con_map);
/* 145 */     int start_page = Integer.parseInt((String)con_map.get("current_page"));
/* 146 */     int page_size = Integer.parseInt((String)con_map.get("page_size"));
/* 147 */     con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
/* 148 */     con_map.put("page_size", Integer.valueOf(page_size));
/* 149 */     return PxxxManager.getPxxxList(con_map);
/*     */   }
/*     */ 
/*     */   public static List<PxxxBean> getAllPxxxList(String params) {
/* 153 */     return PxxxManager.getAllPxxxList();
/*     */   }
/*     */ 
/*     */   public static List<PxxxBean> getPxxxHotList(String params) {
/* 157 */     Map con_map = new HashMap();
/* 158 */     getPxxxSearchCon(params, con_map);
/* 159 */     con_map.put("current_page", "1");
/* 160 */     int start_page = Integer.parseInt((String)con_map.get("current_page"));
/* 161 */     int page_size = Integer.parseInt((String)con_map.get("page_size"));
/* 162 */     con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
/* 163 */     con_map.put("page_size", Integer.valueOf(page_size));
/* 164 */     return PxxxManager.getPxxxList(con_map);
/*     */   }
/*     */ 
/*     */   public static PxxxBean getPxxxObject(String id)
/*     */   {
/* 169 */     return PxxxManager.getPxxxBean(id);
/*     */   }
/*     */ }

/* Location:           E:\Xshell\219.144.222.46(省水保)\classes\com.zip
 * Qualified Name:     com.deya.project.sb_xmgl.Sb_xmglData
 * JD-Core Version:    0.6.2
 */