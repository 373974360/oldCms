/*     */ package com.deya.project.comment;
/*     */ 
/*     */ import com.deya.util.DateUtil;
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.util.OutExcel;
/*     */ import com.deya.util.jconfig.JconfigFactory;
/*     */ import com.deya.util.jconfig.JconfigUtil;
/*     */ import com.deya.wcm.bean.comment.CommentBean;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class CommService
/*     */ {
/*     */   public static Map getComCount(Map<String, String> map)
/*     */   {
/*  18 */     Map count_map = new HashMap();
/*  19 */     List comment_list = CommentDao.getAllcomment(map);
/*  20 */     if ((comment_list != null) && (comment_list.size() > 0)) {
/*  21 */       setCommentMap(count_map, comment_list);
/*     */     }
/*     */ 
/*  24 */     String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
/*  25 */     createExcel(file_name, getCommentData(count_map), "comment", "评论列表");
/*  26 */     count_map.put("file_path", DateUtil.getCurrentDate() + "/" + file_name + ".xls");
/*  27 */     return count_map;
/*     */   }
/*     */ 
/*     */   public static String[][] getCommentData(Map m)
/*     */   {
/*  33 */     String[][] data = new String[m.size() + 1][7];
/*     */ 
/*  35 */     data[0][0] = "标题";
/*  36 */     data[0][1] = "评论内容";
/*  37 */     data[0][2] = "评论IP";
/*  38 */     data[0][3] = "支持度";
/*  39 */     data[0][4] = "评论时间";
/*  40 */     data[0][5] = "评论人";
/*  41 */     data[0][6] = "状态";
/*     */ 
/*  44 */     Iterator iter = m.entrySet().iterator();
/*  45 */     int i = 1;
/*  46 */     while (iter.hasNext()) {
/*  47 */       Map.Entry entry = (Map.Entry)iter.next();
/*  48 */       CommentBean comm = (CommentBean)entry.getValue();
/*     */ 
/*  50 */       data[i][0] = comm.getTitle();
/*  51 */       data[i][1] = comm.getContent();
/*  52 */       data[i][2] = comm.getIp();
/*  53 */       data[i][3] = ("[顶]" + comm.getSupport_count());
/*  54 */       data[i][4] = comm.getAdd_time();
/*  55 */       String name = comm.getNick_name().trim();
/*     */ 
/*  57 */       if ((name == "") || (name == null) || (name == "null"))
/*     */       {
/*  59 */         data[i][5] = "匿名";
/*     */       }
/*  61 */       else data[i][5] = name;
/*     */ 
/*  63 */       int is_status = comm.getIs_status();
/*  64 */       if (is_status == 0)
/*  65 */         data[i][6] = "为审核";
/*     */       else {
/*  67 */         data[i][6] = "已审核";
/*     */       }
/*  69 */       i++;
/*     */     }
/*  71 */     return data;
/*     */   }
/*     */ 
/*     */   public static void setCommentMap(Map m, List<CommentBean> comment_list)
/*     */   {
/*  76 */     for (int i = 0; i < comment_list.size(); i++)
/*  77 */       m.put(Integer.valueOf(((CommentBean)comment_list.get(i)).getId()), comment_list.get(i));
/*     */   }
/*     */ 
/*     */   public static void createExcel(String file_name, String[][] data, String type, String sheelName)
/*     */   {
/*  82 */     JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
/*  83 */     String path = bc.getProperty("path", "", "manager_path").trim() + "/comment";
/*  84 */     String tempPath = path + "/" + DateUtil.getCurrentDate();
/*  85 */     File file2 = new File(FormatUtil.formatPath(tempPath));
/*  86 */     if (!file2.exists()) {
/*  87 */       file2.mkdir();
/*     */     }
/*  89 */     String xFile = FormatUtil.formatPath(tempPath + "/" + file_name + ".xls");
/*     */ 
/*  91 */     OutExcel.deleteFile(path);
/*     */ 
/*  93 */     OutExcel oe = new OutExcel(sheelName);
/*     */ 
/* 100 */     oe.doOut(xFile, data);
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.comment.CommService
 * JD-Core Version:    0.6.2
 */