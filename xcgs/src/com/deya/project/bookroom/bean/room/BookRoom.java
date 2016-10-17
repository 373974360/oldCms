/*     */ package com.deya.project.bookroom.bean.room;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BookRoom
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7462251726724302549L;
/*  10 */   private int id = 0;
/*  11 */   private String room_name = "";
/*  12 */   private String room_address = "";
/*  13 */   private String open_time = "";
/*  14 */   private int bookall_num = 0;
/*  15 */   private int manager_id = 0;
/*  16 */   private String manager_name = "";
/*  17 */   private String manager_phone = "";
/*  18 */   private String infomations = "";
/*  19 */   private int limit_day = 0;
/*  20 */   private int borrow_num = 0;
/*  21 */   private String bookall_desc = "";
/*  22 */   private String book_photo = "";
/*  23 */   private int dept_id = 0;
/*  24 */   private String dept_position = "";
/*  25 */   private String dept_name = "";
/*     */ 
/*     */   public String getDept_name() {
/*  28 */     return this.dept_name;
/*     */   }
/*     */   public void setDept_name(String deptName) {
/*  31 */     this.dept_name = deptName;
/*     */   }
/*     */   public int getDept_id() {
/*  34 */     return this.dept_id;
/*     */   }
/*     */   public void setDept_id(int deptId) {
/*  37 */     this.dept_id = deptId;
/*     */   }
/*     */   public String getDept_position() {
/*  40 */     return this.dept_position;
/*     */   }
/*     */   public void setDept_position(String deptPosition) {
/*  43 */     this.dept_position = deptPosition;
/*     */   }
/*     */   public int getBorrow_num() {
/*  46 */     return this.borrow_num;
/*     */   }
/*     */   public void setBorrow_num(int borrowNum) {
/*  49 */     this.borrow_num = borrowNum;
/*     */   }
/*     */   public String getManager_name() {
/*  52 */     return this.manager_name;
/*     */   }
/*     */   public void setManager_name(String managerName) {
/*  55 */     this.manager_name = managerName;
/*     */   }
/*     */   public int getId() {
/*  58 */     return this.id;
/*     */   }
/*     */   public void setId(int id) {
/*  61 */     this.id = id;
/*     */   }
/*     */   public String getRoom_name() {
/*  64 */     return this.room_name;
/*     */   }
/*     */   public void setRoom_name(String roomName) {
/*  67 */     this.room_name = roomName;
/*     */   }
/*     */   public String getRoom_address() {
/*  70 */     return this.room_address;
/*     */   }
/*     */   public void setRoom_address(String roomAddress) {
/*  73 */     this.room_address = roomAddress;
/*     */   }
/*     */   public String getOpen_time() {
/*  76 */     return this.open_time;
/*     */   }
/*     */   public void setOpen_time(String openTime) {
/*  79 */     this.open_time = openTime;
/*     */   }
/*     */   public int getBookall_num() {
/*  82 */     return this.bookall_num;
/*     */   }
/*     */   public void setBookall_num(int bookallNum) {
/*  85 */     this.bookall_num = bookallNum;
/*     */   }
/*     */   public int getManager_id() {
/*  88 */     return this.manager_id;
/*     */   }
/*     */   public void setManager_id(int managerId) {
/*  91 */     this.manager_id = managerId;
/*     */   }
/*     */   public String getManager_phone() {
/*  94 */     return this.manager_phone;
/*     */   }
/*     */   public void setManager_phone(String managerPhone) {
/*  97 */     this.manager_phone = managerPhone;
/*     */   }
/*     */   public String getInfomations() {
/* 100 */     return this.infomations;
/*     */   }
/*     */   public void setInfomations(String infomations) {
/* 103 */     this.infomations = infomations;
/*     */   }
/*     */   public int getLimit_day() {
/* 106 */     return this.limit_day;
/*     */   }
/*     */   public void setLimit_day(int limitDay) {
/* 109 */     this.limit_day = limitDay;
/*     */   }
/*     */   public String getBookall_desc() {
/* 112 */     return this.bookall_desc;
/*     */   }
/*     */   public void setBookall_desc(String bookallDesc) {
/* 115 */     this.bookall_desc = bookallDesc;
/*     */   }
/*     */   public String getBook_photo() {
/* 118 */     return this.book_photo;
/*     */   }
/*     */   public void setBook_photo(String bookPhoto) {
/* 121 */     this.book_photo = bookPhoto;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.room.BookRoom
 * JD-Core Version:    0.6.2
 */