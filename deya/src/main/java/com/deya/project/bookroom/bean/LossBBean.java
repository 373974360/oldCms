/*    */ package com.deya.project.bookroom.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class LossBBean
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8824578216381118194L;
/* 14 */   private int id = 0;
/* 15 */   private int room_id = 0;
/* 16 */   private String only_number = "";
/* 17 */   private String class_number = "";
/* 18 */   private String book_cname = "";
/* 19 */   private String cate_name = "";
/* 20 */   private String loss_time = "";
/* 21 */   private int user_id = 0;
/* 22 */   private String beizhu = "";
/* 23 */   private int loss_type = 0;
/*    */ 
/*    */   public int getRoom_id()
/*    */   {
/* 29 */     return this.room_id;
/*    */   }
/*    */   public void setRoom_id(int roomId) {
/* 32 */     this.room_id = roomId;
/*    */   }
/*    */   public int getId() {
/* 35 */     return this.id;
/*    */   }
/*    */   public String getOnly_number() {
/* 38 */     return this.only_number;
/*    */   }
/*    */   public String getClass_number() {
/* 41 */     return this.class_number;
/*    */   }
/*    */   public String getBook_cname() {
/* 44 */     return this.book_cname;
/*    */   }
/*    */   public String getCate_name() {
/* 47 */     return this.cate_name;
/*    */   }
/*    */   public String getLoss_time() {
/* 50 */     return this.loss_time;
/*    */   }
/*    */   public int getUser_id() {
/* 53 */     return this.user_id;
/*    */   }
/*    */   public String getBeizhu() {
/* 56 */     return this.beizhu;
/*    */   }
/*    */   public int getLoss_type() {
/* 59 */     return this.loss_type;
/*    */   }
/*    */   public void setId(int id) {
/* 62 */     this.id = id;
/*    */   }
/*    */   public void setOnly_number(String onlyNumber) {
/* 65 */     this.only_number = onlyNumber;
/*    */   }
/*    */   public void setClass_number(String classNumber) {
/* 68 */     this.class_number = classNumber;
/*    */   }
/*    */   public void setBook_cname(String bookCname) {
/* 71 */     this.book_cname = bookCname;
/*    */   }
/*    */   public void setCate_name(String cateName) {
/* 74 */     this.cate_name = cateName;
/*    */   }
/*    */   public void setLoss_time(String lossTime) {
/* 77 */     this.loss_time = lossTime;
/*    */   }
/*    */   public void setUser_id(int userId) {
/* 80 */     this.user_id = userId;
/*    */   }
/*    */   public void setBeizhu(String beizhu) {
/* 83 */     this.beizhu = beizhu;
/*    */   }
/*    */   public void setLoss_type(int lossType) {
/* 86 */     this.loss_type = lossType;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.LossBBean
 * JD-Core Version:    0.6.2
 */