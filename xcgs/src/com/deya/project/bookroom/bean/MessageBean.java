/*    */ package com.deya.project.bookroom.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MessageBean
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7566971259064668538L;
/* 12 */   private int id = 0;
/* 13 */   private int mess_id = 0;
/* 14 */   private String mess_title = "";
/* 15 */   private String mess_content = "";
/* 16 */   private String mess_addtime = "";
/* 17 */   private int mess_state = 0;
/*    */ 
/* 19 */   public int getId() { return this.id; }
/*    */ 
/*    */   public int getMess_id() {
/* 22 */     return this.mess_id;
/*    */   }
/*    */   public String getMess_title() {
/* 25 */     return this.mess_title;
/*    */   }
/*    */   public String getMess_content() {
/* 28 */     return this.mess_content;
/*    */   }
/*    */   public String getMess_addtime() {
/* 31 */     return this.mess_addtime;
/*    */   }
/*    */   public int getMess_state() {
/* 34 */     return this.mess_state;
/*    */   }
/*    */   public void setId(int id) {
/* 37 */     this.id = id;
/*    */   }
/*    */   public void setMess_id(int messId) {
/* 40 */     this.mess_id = messId;
/*    */   }
/*    */   public void setMess_title(String messTitle) {
/* 43 */     this.mess_title = messTitle;
/*    */   }
/*    */   public void setMess_content(String messContent) {
/* 46 */     this.mess_content = messContent;
/*    */   }
/*    */   public void setMess_addtime(String messAddtime) {
/* 49 */     this.mess_addtime = messAddtime;
/*    */   }
/*    */   public void setMess_state(int messState) {
/* 52 */     this.mess_state = messState;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.MessageBean
 * JD-Core Version:    0.6.2
 */