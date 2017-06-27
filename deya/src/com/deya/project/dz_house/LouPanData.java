/*     */ package com.deya.project.dz_house;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*     */ public class LouPanData
/*     */ {
/*  13 */   private static int cur_page = 1;
/*  14 */   private static int page_size = 15;
/*     */ 
/*     */   public static void getLouPanSearchCon(String params, Map<String, String> con_map)
/*     */   {
/*  19 */     String orderby = "lpcode desc";
/*  20 */     String[] tempA = params.split(";");
/*  21 */     for (int i = 0; i < tempA.length; i++)
/*     */     {
/*  42 */       if (tempA[i].toLowerCase().startsWith("search="))
/*     */       {
/*  44 */         String  search= FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */
/*  46 */         if ((!"".equals(search)) && (!search.startsWith("search")) && (FormatUtil.isValiditySQL(search)))
/*     */         {
/*  48 */           con_map.put("name", search);
/*     */         }
/*     */       }
/*  42 */       if (tempA[i].toLowerCase().startsWith("type="))
/*     */       {
/*  44 */         String  type= FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */
/*  46 */         if ((!"".equals(type)) && (!type.startsWith("type")) && (FormatUtil.isValiditySQL(type)))
/*     */         {
/*  48 */           con_map.put("type", type);
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
/*     */   public static TurnPageBean getLouPanCount(String params)
/*     */   {
/* 109 */     Map con_map = new HashMap();
/* 110 */     getLouPanSearchCon(params, con_map);
/*     */ 
/* 112 */     TurnPageBean tpb = new TurnPageBean();
/* 113 */     tpb.setCount(Integer.parseInt(LouPanManager.getLouPanCount(con_map)));
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
/*     */   public static List<LouPanBean> getLouPanList(String params) {
/* 143 */     Map con_map = new HashMap();
/* 144 */     getLouPanSearchCon(params, con_map);
/* 145 */     int start_page = Integer.parseInt((String)con_map.get("current_page"));
/* 146 */     int page_size = Integer.parseInt((String)con_map.get("page_size"));
/* 147 */     con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
/* 148 */     con_map.put("page_size", Integer.valueOf(page_size));
/* 149 */     return LouPanManager.getLouPanList(con_map);
/*     */   }
/*     */ 
/*     */   public static List<LouPanBean> getAllLouPanList() {
/* 153 */     return LouPanManager.getLouPanAllList();
/*     */   }
/*     */
/*     */
/*     */   public static LouPanBean getLouPanObject(String id)
/*     */   {
/* 169 */     return LouPanManager.getLouPanById(id);
/*     */   }
/*     */ }