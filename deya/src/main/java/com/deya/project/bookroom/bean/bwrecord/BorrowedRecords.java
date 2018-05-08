/*     */ package com.deya.project.bookroom.bean.bwrecord;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BorrowedRecords
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 869644011449259015L;
/*  10 */   private int id = 0;
/*  11 */   private int room_id = 0;
/*  12 */   private String only_number = "";
/*  13 */   private String book_name = "";
/*  14 */   private String numbs = "";
/*  15 */   private String borrowed_time = "";
/*  16 */   private String back_time = "";
/*  17 */   private String extend_time = "";
/*  18 */   private int operate_type = 0;
/*  19 */   private int back_flag = 0;
/*  20 */   private int borrowed_day = 0;
/*  21 */   private String borrowed_name = "";
/*  22 */   private String borrowed_phone = "";
/*  23 */   private String borrowed_address = "";
/*  24 */   private String borrowed_cardid = "";
/*     */ 
/*     */   public String getBook_name() {
/*  27 */     return this.book_name;
/*     */   }
/*     */   public void setBook_name(String bookName) {
/*  30 */     this.book_name = bookName;
/*     */   }
/*     */   public String getOnly_number() {
/*  33 */     return this.only_number;
/*     */   }
/*     */   public void setOnly_number(String onlyNumber) {
/*  36 */     this.only_number = onlyNumber;
/*     */   }
/*     */   public int getRoom_id() {
/*  39 */     return this.room_id;
/*     */   }
/*     */   public void setRoom_id(int roomId) {
/*  42 */     this.room_id = roomId;
/*     */   }
/*     */   public String getNumbs() {
/*  45 */     return this.numbs;
/*     */   }
/*     */   public void setNumbs(String numbs) {
/*  48 */     this.numbs = numbs;
/*     */   }
/*     */   public int getId() {
/*  51 */     return this.id;
/*     */   }
/*     */   public void setId(int id) {
/*  54 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getBorrowed_time() {
/*  58 */     return this.borrowed_time;
/*     */   }
/*     */   public void setBorrowed_time(String borrowedTime) {
/*  61 */     this.borrowed_time = borrowedTime;
/*     */   }
/*     */   public String getBack_time() {
/*  64 */     return this.back_time;
/*     */   }
/*     */   public void setBack_time(String backTime) {
/*  67 */     this.back_time = backTime;
/*     */   }
/*     */   public String getExtend_time() {
/*  70 */     return this.extend_time;
/*     */   }
/*     */   public void setExtend_time(String extendTime) {
/*  73 */     this.extend_time = extendTime;
/*     */   }
/*     */   public int getOperate_type() {
/*  76 */     return this.operate_type;
/*     */   }
/*     */   public void setOperate_type(int operateType) {
/*  79 */     this.operate_type = operateType;
/*     */   }
/*     */   public int getBack_flag() {
/*  82 */     return this.back_flag;
/*     */   }
/*     */   public void setBack_flag(int backFlag) {
/*  85 */     this.back_flag = backFlag;
/*     */   }
/*     */   public int getBorrowed_day() {
/*  88 */     return this.borrowed_day;
/*     */   }
/*     */   public void setBorrowed_day(int borrowedDay) {
/*  91 */     this.borrowed_day = borrowedDay;
/*     */   }
/*     */   public String getBorrowed_name() {
/*  94 */     return this.borrowed_name;
/*     */   }
/*     */   public void setBorrowed_name(String borrowedName) {
/*  97 */     this.borrowed_name = borrowedName;
/*     */   }
/*     */   public String getBorrowed_phone() {
/* 100 */     return this.borrowed_phone;
/*     */   }
/*     */   public void setBorrowed_phone(String borrowedPhone) {
/* 103 */     this.borrowed_phone = borrowedPhone;
/*     */   }
/*     */   public String getBorrowed_address() {
/* 106 */     return this.borrowed_address;
/*     */   }
/*     */   public void setBorrowed_address(String borrowedAddress) {
/* 109 */     this.borrowed_address = borrowedAddress;
/*     */   }
/*     */   public String getBorrowed_cardid() {
/* 112 */     return this.borrowed_cardid;
/*     */   }
/*     */   public void setBorrowed_cardid(String borrowedCardid) {
/* 115 */     this.borrowed_cardid = borrowedCardid;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.bwrecord.BorrowedRecords
 * JD-Core Version:    0.6.2
 */