/*     */ package com.deya.project.searchkeys;
/*     */ 
/*     */ import com.deya.util.FormatUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SearchKeyTemplate
/*     */ {
/*     */   public static List<SearchKey> getSearchKeysList(String params)
/*     */   {
/*  18 */     Map con_map = new HashMap();
/*  19 */     getInfoSearchCon(params, con_map);
/*  20 */     con_map.put("current_page", "1");
/*  21 */     return SearchKeyService.getSearchKeysList(con_map);
/*     */   }
/*     */ 
/*     */   public static void getInfoSearchCon(String params, Map<String, String> con_map)
/*     */   {
/*  32 */     int cur_page = 1;
/*  33 */     int page_size = 15;
/*  34 */     String orderby = "publish_time desc";
/*  35 */     String[] tempA = params.split(";");
/*  36 */     for (int i = 0; i < tempA.length; i++)
/*     */     {
/*  38 */       if (tempA[i].toLowerCase().startsWith("start_time="))
/*     */       {
/*  40 */         String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  42 */         if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time)))
/*     */         {
/*  44 */           con_map.put("start_time", start_time);
/*     */         }
/*     */       }
/*  47 */       if (tempA[i].toLowerCase().startsWith("end_time="))
/*     */       {
/*  49 */         String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  51 */         if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time)))
/*     */         {
/*  53 */           con_map.put("end_time", end_time);
/*     */         }
/*     */       }
/*  56 */       if (tempA[i].toLowerCase().startsWith("site_id="))
/*     */       {
/*  58 */         String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  59 */         if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id)))
/*     */         {
/*  61 */           con_map.put("site_id", site_id);
/*     */         }
/*     */       }
/*  64 */       if (tempA[i].toLowerCase().startsWith("re_user_id="))
/*     */       {
/*  66 */         String re_user_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  67 */         if ((!"".equals(re_user_id)) && (!re_user_id.startsWith("$re_user_id")) && (FormatUtil.isValiditySQL(re_user_id)))
/*     */         {
/*  69 */           con_map.put("re_user_id", re_user_id);
/*     */         }
/*     */       }
/*  72 */       if (tempA[i].toLowerCase().startsWith("cate_name="))
/*     */       {
/*  74 */         String cate_name = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  75 */         if ((!"".equals(cate_name)) && (!cate_name.startsWith("$cate_name")) && (FormatUtil.isNumeric(cate_name)))
/*  76 */           con_map.put("cate_name", cate_name);
/*     */       }
/*  78 */       if (tempA[i].toLowerCase().startsWith("id="))
/*     */       {
/*  80 */         String id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  81 */         if ((!"".equals(id)) && (!id.startsWith("$id")) && (FormatUtil.isNumeric(id)))
/*  82 */           con_map.put("id", id);
/*     */       }
/*  84 */       if (tempA[i].toLowerCase().startsWith("xmmc="))
/*     */       {
/*  86 */         String xmmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  87 */         if ((!"".equals(xmmc)) && (!xmmc.startsWith("$xmmc")) && (FormatUtil.isNumeric(xmmc)))
/*  88 */           con_map.put("xmmc", xmmc);
/*     */       }
/*  90 */       if (tempA[i].toLowerCase().startsWith("bq_type="))
/*     */       {
/*  92 */         String bq_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/*  94 */         if ((!"".equals(bq_type)) && (!bq_type.startsWith("$bq_type")) && (FormatUtil.isValiditySQL(bq_type)))
/*     */         {
/*  96 */           con_map.put("bq_type", bq_type);
/*     */         }
/*     */       }
/*  99 */       if (tempA[i].toLowerCase().startsWith("shenhe_flag="))
/*     */       {
/* 101 */         String shenhe_flag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/* 103 */         if ((!"".equals(shenhe_flag)) && (!shenhe_flag.startsWith("$shenhe_flag")) && (FormatUtil.isValiditySQL(shenhe_flag)))
/*     */         {
/* 105 */           con_map.put("shenhe_flag", shenhe_flag);
/*     */         }
/*     */       }
/* 108 */       if (tempA[i].toLowerCase().startsWith("size="))
/*     */       {
/* 110 */         String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 111 */         if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps)))
/* 112 */           page_size = Integer.parseInt(ps);
/*     */       }
/* 114 */       if (tempA[i].toLowerCase().startsWith("cur_page="))
/*     */       {
/* 116 */         String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 117 */         if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp))) {
/* 118 */           cur_page = Integer.parseInt(cp);
/*     */         }
/*     */       }
/* 121 */       if (tempA[i].toLowerCase().startsWith("orderby="))
/*     */       {
/* 123 */         String order = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*     */ 
/* 125 */         if ((!"".equals(order)) && (!orderby.startsWith("$order")) && (FormatUtil.isValiditySQL(order)))
/*     */         {
/* 127 */           orderby = order;
/*     */         }
/*     */       }
/*     */     }
/* 131 */     con_map.put("page_size", page_size+"");
/* 132 */     con_map.put("current_page", cur_page+"");
/* 133 */     con_map.put("start_num", (cur_page - 1) * page_size+"");
/*     */ 
/* 135 */     String[] oy = orderby.split(" ");
/* 136 */     con_map.put("sort_name", oy[0]);
/* 137 */     con_map.put("sort_type", oy[1]);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.searchkeys.SearchKeyTemplate
 * JD-Core Version:    0.6.2
 */