/*     */ package com.deya.project.dz_wzjhy.enterprise;
/*     */ 
/*     */ import com.deya.project.dz_wzjhy.jobs.JobsBean;
/*     */ import com.deya.project.dz_wzjhy.jobs.JobsManager;
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.wcm.bean.template.TurnPageBean;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WZJEnterpriseData
/*     */ {
/*     */   public static EnterpriseBean getEnterpriseObject(String id)
/*     */   {
/*  14 */     Map m = new HashMap();
/*  15 */     m.put("id", id);
/*  16 */     m.put("is_audit", "1");
/*  17 */     return EnterpriseDAO.getEnterpriseBean(m);
/*     */   }
/*     */ 
/*     */   public static JobsBean getJobsBean(String id) {
/*  21 */     Map m = new HashMap();
/*  22 */     m.put("id", id);
/*  23 */     return JobsManager.getJobsBean(m);
/*     */   }
/*     */ 
/*     */   public static JobsBean getJobsBeanPub(String id) {
/*  27 */     Map m = new HashMap();
/*  28 */     m.put("id", id);
/*  29 */     m.put("is_publish", "1");
/*  30 */     return JobsManager.getJobsBean(m);
/*     */   }
/*     */ 
/*     */   public static List<JobsBean> getJobsBeanList(String params)
/*     */   {
/*  35 */     Map map = new HashMap();
/*  36 */     getJobsSearchCon(params, map);
/*  37 */     int start_page = Integer.parseInt((String)map.get("current_page"));
/*  38 */     int page_size = Integer.parseInt((String)map.get("page_size"));
/*  39 */     map.put("start_num", (start_page - 1) * page_size);
/*  40 */     map.put("page_size", page_size);
/*  41 */     return JobsManager.getJobsBeanList(map);
/*     */   }
/*     */ 
/*     */   public static TurnPageBean getInfoCount(String params)
/*     */   {
/*  46 */     Map con_map = new HashMap();
/*  47 */     getJobsSearchCon(params, con_map);
/*  48 */     TurnPageBean tpb = new TurnPageBean();
/*  49 */     tpb.setCount(Integer.parseInt(JobsManager.getJobsBeanCount(con_map)));
/*  50 */     int cur_page = Integer.parseInt((String)con_map.get("current_page"));
/*  51 */     int page_size = Integer.parseInt((String)con_map.get("page_size"));
/*     */ 
/*  53 */     tpb.setCur_page(cur_page);
/*  54 */     tpb.setPage_size(page_size);
/*  55 */     tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
/*     */ 
/*  57 */     if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
/*  58 */       tpb.setPage_count(tpb.getPage_count() - 1);
/*     */     }
/*  60 */     if (cur_page > 1) {
/*  61 */       tpb.setPrev_num(cur_page - 1);
/*     */     }
/*  63 */     tpb.setNext_num(tpb.getPage_count());
/*  64 */     if (cur_page < tpb.getPage_count()) {
/*  65 */       tpb.setNext_num(cur_page + 1);
/*     */     }
/*     */ 
/*  68 */     if (tpb.getPage_count() > 10)
/*     */     {
/*  71 */       if (cur_page > 5)
/*     */       {
/*  73 */         if (cur_page > tpb.getPage_count() - 4)
/*  74 */           tpb.setCurr_start_num(tpb.getPage_count() - 6);
/*     */         else
/*  76 */           tpb.setCurr_start_num(cur_page - 2);
/*     */       }
/*     */     }
/*  79 */     return tpb;
/*     */   }
/*     */ 
/*     */   public static void getJobsSearchCon(String params, Map<String, String> con_map)
/*     */   {
/*  84 */     int cur_page = 1;
/*  85 */     int page_size = 15;
/*  86 */     String orderby = "publish_dtime desc";
/*  87 */     String[] tempA = params.split(";");
/*  88 */     for (int i = 0; i < tempA.length; i++)
/*     */     {
/*  90 */       if (tempA[i].toLowerCase().startsWith("orderby="))
/*     */       {
/*  92 */         String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/*  93 */         if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by)))
/*     */         {
/*  95 */           orderby = o_by;
/*     */         }
/*     */       }
/*  98 */       if (tempA[i].toLowerCase().startsWith("size="))
/*     */       {
/* 100 */         String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 101 */         if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps)))
/* 102 */           page_size = Integer.parseInt(ps);
/*     */       }
/* 104 */       if (tempA[i].toLowerCase().startsWith("ent_id="))
/*     */       {
/* 106 */         String ent_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 107 */         if ((!"".equals(ent_id)) && (!ent_id.startsWith("$ent_id")) && (FormatUtil.isNumeric(ent_id)))
/* 108 */           con_map.put("ent_id", ent_id);
/*     */       }
/* 110 */       if (tempA[i].toLowerCase().startsWith("is_publish="))
/*     */       {
/* 112 */         String is_publish = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 113 */         if ((!"".equals(is_publish)) && (!is_publish.startsWith("$is_publish")) && (FormatUtil.isNumeric(is_publish)))
/* 114 */           con_map.put("is_publish", "1");
/*     */       }
/* 116 */       if (tempA[i].toLowerCase().startsWith("cur_page="))
/*     */       {
/* 118 */         String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
/* 119 */         if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp)))
/* 120 */           cur_page = Integer.parseInt(cp);
/*     */       }
/*     */     }
/* 123 */     con_map.put("page_size", page_size+"");
/* 124 */     con_map.put("current_page", cur_page+"");
/* 125 */     con_map.put("order_by", orderby);
/*     */   }
/*     */ }

/* Location:           E:\Xshell\1.85.18.181(人社局新网站)\dz_wzjhy.zip
 * Qualified Name:     dz_wzjhy.enterprise.EnterpriseData
 * JD-Core Version:    0.6.2
 */