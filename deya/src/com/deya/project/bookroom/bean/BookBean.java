/*     */ package com.deya.project.bookroom.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BookBean
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8672643121726867771L;
/*  23 */   private int book_id = 0;
/*  24 */   private int cate_id = 0;
/*  25 */   private String cate_name = "";
/*  26 */   private String book_cname = "";
/*  27 */   private String book_ename = "";
/*  28 */   private String only_number = "";
/*  29 */   private String class_number = "";
/*  30 */   private String standard = "";
/*  31 */   private String banci = "";
/*  32 */   private String juance = "";
/*  33 */   private String price = "";
/*  34 */   private int pages = 0;
/*  35 */   private String kaiben = "";
/*  36 */   private String author = "";
/*  37 */   private String publishing_name = "";
/*  38 */   private String publishing_address = "";
/*  39 */   private String banmian = "";
/*  40 */   private String lanauge = "";
/*  41 */   private String type = "";
/*  42 */   private String with_items = "";
/*  43 */   private String add_time = "";
/*  44 */   private String location = "";
/*  45 */   private String pic_url = "";
/*  46 */   private int numbers = 0;
/*  47 */   private String description = "";
/*  48 */   private String dearl_usename = "";
/*  49 */   private int room_id = 0;
/*  50 */   private String add_year = "";
/*  51 */   private String yinzhang = "";
/*     */ 
/*     */   public String getAdd_year()
/*     */   {
/*  57 */     return this.add_year;
/*     */   }
/*     */   public String getYinzhang() {
/*  60 */     return this.yinzhang;
/*     */   }
/*     */   public void setAdd_year(String addYear) {
/*  63 */     this.add_year = addYear;
/*     */   }
/*     */   public void setYinzhang(String yinzhang) {
/*  66 */     this.yinzhang = yinzhang;
/*     */   }
/*     */   public int getRoom_id() {
/*  69 */     return this.room_id;
/*     */   }
/*     */   public void setRoom_id(int roomId) {
/*  72 */     this.room_id = roomId;
/*     */   }
/*     */   public String getCate_name() {
/*  75 */     return this.cate_name;
/*     */   }
/*     */   public void setCate_name(String cateName) {
/*  78 */     this.cate_name = cateName;
/*     */   }
/*     */   public String getClass_number() {
/*  81 */     return this.class_number;
/*     */   }
/*     */   public void setClass_number(String classNumber) {
/*  84 */     this.class_number = classNumber;
/*     */   }
/*     */   public int getBook_id() {
/*  87 */     return this.book_id;
/*     */   }
/*     */   public int getCate_id() {
/*  90 */     return this.cate_id;
/*     */   }
/*     */   public String getBook_cname() {
/*  93 */     return this.book_cname;
/*     */   }
/*     */   public String getBook_ename() {
/*  96 */     return this.book_ename;
/*     */   }
/*     */   public String getStandard() {
/*  99 */     return this.standard;
/*     */   }
/*     */   public String getBanci() {
/* 102 */     return this.banci;
/*     */   }
/*     */   public String getJuance() {
/* 105 */     return this.juance;
/*     */   }
/*     */   public String getPrice() {
/* 108 */     return this.price;
/*     */   }
/*     */   public int getPages() {
/* 111 */     return this.pages;
/*     */   }
/*     */   public String getKaiben() {
/* 114 */     return this.kaiben;
/*     */   }
/*     */   public String getAuthor() {
/* 117 */     return this.author;
/*     */   }
/*     */   public String getPublishing_name() {
/* 120 */     return this.publishing_name;
/*     */   }
/*     */   public String getPublishing_address() {
/* 123 */     return this.publishing_address;
/*     */   }
/*     */   public String getBanmian() {
/* 126 */     return this.banmian;
/*     */   }
/*     */   public String getLanauge() {
/* 129 */     return this.lanauge;
/*     */   }
/*     */   public String getType() {
/* 132 */     return this.type;
/*     */   }
/*     */   public String getWith_items() {
/* 135 */     return this.with_items;
/*     */   }
/*     */   public String getAdd_time() {
/* 138 */     return this.add_time;
/*     */   }
/*     */   public String getLocation() {
/* 141 */     return this.location;
/*     */   }
/*     */   public String getOnly_number() {
/* 144 */     return this.only_number;
/*     */   }
/*     */   public String getPic_url() {
/* 147 */     return this.pic_url;
/*     */   }
/*     */   public int getNumbers() {
/* 150 */     return this.numbers;
/*     */   }
/*     */   public String getDescription() {
/* 153 */     return this.description;
/*     */   }
/*     */   public String getDearl_usename() {
/* 156 */     return this.dearl_usename;
/*     */   }
/*     */   public void setBook_id(int bookId) {
/* 159 */     this.book_id = bookId;
/*     */   }
/*     */   public void setCate_id(int cateId) {
/* 162 */     this.cate_id = cateId;
/*     */   }
/*     */   public void setBook_cname(String bookCname) {
/* 165 */     this.book_cname = bookCname;
/*     */   }
/*     */   public void setBook_ename(String bookEname) {
/* 168 */     this.book_ename = bookEname;
/*     */   }
/*     */   public void setStandard(String standard) {
/* 171 */     this.standard = standard;
/*     */   }
/*     */   public void setBanci(String banci) {
/* 174 */     this.banci = banci;
/*     */   }
/*     */   public void setJuance(String juance) {
/* 177 */     this.juance = juance;
/*     */   }
/*     */   public void setPrice(String price) {
/* 180 */     this.price = price;
/*     */   }
/*     */   public void setPages(int pages) {
/* 183 */     this.pages = pages;
/*     */   }
/*     */   public void setKaiben(String kaiben) {
/* 186 */     this.kaiben = kaiben;
/*     */   }
/*     */   public void setAuthor(String author) {
/* 189 */     this.author = author;
/*     */   }
/*     */   public void setPublishing_name(String publishingName) {
/* 192 */     this.publishing_name = publishingName;
/*     */   }
/*     */   public void setPublishing_address(String publishingAddress) {
/* 195 */     this.publishing_address = publishingAddress;
/*     */   }
/*     */   public void setBanmian(String banmian) {
/* 198 */     this.banmian = banmian;
/*     */   }
/*     */   public void setLanauge(String lanauge) {
/* 201 */     this.lanauge = lanauge;
/*     */   }
/*     */   public void setType(String type) {
/* 204 */     this.type = type;
/*     */   }
/*     */   public void setWith_items(String withItems) {
/* 207 */     this.with_items = withItems;
/*     */   }
/*     */   public void setAdd_time(String addTime) {
/* 210 */     this.add_time = addTime;
/*     */   }
/*     */   public void setLocation(String location) {
/* 213 */     this.location = location;
/*     */   }
/*     */   public void setOnly_number(String onlyNumber) {
/* 216 */     this.only_number = onlyNumber;
/*     */   }
/*     */   public void setPic_url(String picUrl) {
/* 219 */     this.pic_url = picUrl;
/*     */   }
/*     */   public void setNumbers(int numbers) {
/* 222 */     this.numbers = numbers;
/*     */   }
/*     */   public void setDescription(String description) {
/* 225 */     this.description = description;
/*     */   }
/*     */   public void setDearl_usename(String earlUsename) {
/* 228 */     this.dearl_usename = earlUsename;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.BookBean
 * JD-Core Version:    0.6.2
 */