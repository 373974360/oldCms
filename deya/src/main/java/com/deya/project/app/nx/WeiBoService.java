/*     */ package com.deya.project.app.nx;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import jxl.Cell;
/*     */ import jxl.DateCell;
/*     */ import jxl.Sheet;
/*     */ import jxl.Workbook;
/*     */ 
/*     */ public class WeiBoService
/*     */ {
/*  18 */   private static List<WeiBoBean> list = new ArrayList();
/*  19 */   private static Map<String, WeiBoBean> mapAll = new HashMap();
/*     */ 
/*     */   public static void clearList() {
/*  22 */     list.clear();
/*  23 */     mapAll.clear();
/*     */   }
/*     */ 
/*     */   public static void readWeiboListAll(String file) {
/*     */     try {
/*  28 */       clearList();
/*  29 */       if (list.size() <= 0) {
/*  30 */         Workbook book = Workbook.getWorkbook(new File(file));
/*  31 */         if (book != null)
/*     */         {
/*  36 */           Sheet sheet = book.getSheet(1);
/*  37 */           for (int i = 1; i < sheet.getRows(); i++)
/*     */             try
/*     */             {
/*  40 */               String time = "";
/*  41 */               if (sheet.getRow(i)[0].getContents() != null)
/*     */               {
/*  43 */                 time = sheet.getRow(i)[0].getContents();
/*  44 */                 DateCell dc = (DateCell)sheet.getRow(i)[0];
/*  45 */                 Date date = dc.getDate();
/*  46 */                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  47 */                 time = sdf.format(date);
/*     */ 
/*  50 */                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  51 */                 Date date1 = formatter.parse(time);
/*  52 */                 Calendar rightNow = Calendar.getInstance();
/*  53 */                 rightNow.setTime(date1);
/*  54 */                 rightNow.add(10, -8);
/*  55 */                 Date date2 = rightNow.getTime();
/*  56 */                 String str2 = formatter.format(date2);
/*  57 */                 time = str2;
/*     */               }
/*  59 */               String code = "";
/*  60 */               if (sheet.getRow(i)[1].getContents() != null)
/*     */               {
/*  62 */                 code = sheet.getRow(i)[1].getContents();
/*     */               }
/*  64 */               String content = "";
/*  65 */               if (sheet.getRow(i)[2].getContents() != null)
/*     */               {
/*  67 */                 content = sheet.getRow(i)[2].getContents();
/*     */               }
/*  69 */               String user_code = "";
/*  70 */               if (sheet.getRow(i)[3].getContents() != null)
/*     */               {
/*  72 */                 user_code = sheet.getRow(i)[3].getContents();
/*     */               }
/*  74 */               String user_name = "";
/*  75 */               if (sheet.getRow(i)[4].getContents() != null)
/*     */               {
/*  77 */                 user_name = sheet.getRow(i)[4].getContents();
/*     */               }
/*  79 */               String weibo_type = "";
/*  80 */               if (sheet.getRow(i)[5].getContents() != null)
/*     */               {
/*  82 */                 weibo_type = sheet.getRow(i)[5].getContents();
/*     */               }
/*  84 */               String source = "";
/*  85 */               if (sheet.getRow(i)[6].getContents() != null)
/*     */               {
/*  87 */                 source = sheet.getRow(i)[6].getContents();
/*     */               }
/*  89 */               String weibo_url = "";
/*  90 */               if (sheet.getRow(i)[7].getContents() != null)
/*     */               {
/*  92 */                 weibo_url = sheet.getRow(i)[7].getContents();
/*     */               }
/*  94 */               String keywords = "";
/*  95 */               if (sheet.getRow(i)[8].getContents() != null)
/*     */               {
/*  97 */                 keywords = sheet.getRow(i)[8].getContents();
/*     */               }
/*     */ 
/* 101 */               WeiBoBean weiBoBean = new WeiBoBean();
/* 102 */               weiBoBean.setCode(code.trim());
/* 103 */               weiBoBean.setContent(content.trim());
/* 104 */               weiBoBean.setKeywords(keywords.trim());
/* 105 */               weiBoBean.setSource(source.trim());
/* 106 */               weiBoBean.setTime(time.trim());
/* 107 */               weiBoBean.setUser_code(user_code.trim());
/* 108 */               weiBoBean.setUser_name(user_name.trim());
/* 109 */               weiBoBean.setWeibo_type(weibo_type.trim());
/* 110 */               weiBoBean.setWeibo_url(weibo_url.trim());
/* 111 */               list.add(weiBoBean);
/* 112 */               mapAll.put(code.trim(), weiBoBean);
/*     */             }
/*     */             catch (Exception e) {
/* 115 */               e.printStackTrace();
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 122 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List<WeiBoBean> readWeiboList(int page, int size)
/*     */   {
/* 129 */     List listh = new ArrayList();
/*     */     try {
/* 131 */       int k = (page - 1) * size;
/*     */ 
/* 133 */       if (list.size() > 0) {
/* 134 */         for (int i = k; i < k + size; i++) {
/* 135 */           listh.add((WeiBoBean)list.get(i));
/*     */         }
/*     */       }
/* 138 */       return listh;
/*     */     } catch (Exception e) {
/* 140 */       e.printStackTrace();
/* 141 */     }return listh;
/*     */   }
/*     */ 
/*     */   public static WeiBoBean readWeiboByCode(String code)
/*     */   {
/* 147 */     WeiBoBean weiBoBean = new WeiBoBean();
/*     */     try {
/* 149 */       //System.out.println("code----" + code);
/* 150 */       //System.out.println(mapAll);
/* 151 */       if (mapAll.containsKey(code));
/* 152 */       return (WeiBoBean)mapAll.get(code);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 156 */       e.printStackTrace();
/* 157 */     }return weiBoBean;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 167 */     String file_name = "src\\data\\nxdata.xls";
/* 168 */     readWeiboListAll(file_name);
/* 169 */     List listc = new ArrayList();
/* 170 */     listc = readWeiboList(2, 10);
/* 171 */     for (int i = 0; i < listc.size(); i++) {
/* 172 */       //System.out.println(((WeiBoBean)listc.get(i)).getCode() + "---" + ((WeiBoBean)listc.get(i)).getTime() + "---" + ((WeiBoBean)listc.get(i)).getContent());
/* 173 */       WeiBoBean boBean = (WeiBoBean)listc.get(i);
/* 174 */       String time_c = boBean.getTime().trim();
/* 175 */       //System.out.println("time_c----" + time_c);
/* 176 */       boBean.setTime(time_c.substring(4));
/*     */     }
/*     */ 
/* 180 */     WeiBoBean weiBoBean = readWeiboByCode("3570225859220280");
/* 181 */     //System.out.println(weiBoBean.getCode());
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.app.nx.WeiBoService
 * JD-Core Version:    0.6.2
 */