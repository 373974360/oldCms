/*    */ package com.deya.project.bookroom.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MessageDeptBean
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4002294113468246136L;
/* 13 */   private int id = 0;
/* 14 */   private int send_deptid = 0;
/* 15 */   private int recevie_deptid = 0;
/* 16 */   private int mess_id = 0;
/* 17 */   private int currtent_deptid = 0;
/* 18 */   private String site_id = "";
/* 19 */   private int room_id = 0;
/*    */ 
/*    */   public int getId() {
/* 22 */     return this.id;
/*    */   }
/*    */   public int getSend_deptid() {
/* 25 */     return this.send_deptid;
/*    */   }
/*    */   public int getRecevie_deptid() {
/* 28 */     return this.recevie_deptid;
/*    */   }
/*    */   public int getMess_id() {
/* 31 */     return this.mess_id;
/*    */   }
/*    */   public String getSite_id() {
/* 34 */     return this.site_id;
/*    */   }
/*    */   public int getRoom_id() {
/* 37 */     return this.room_id;
/*    */   }
/*    */   public int getCurrtent_deptid() {
/* 40 */     return this.currtent_deptid;
/*    */   }
/*    */   public void setCurrtent_deptid(int currtentDeptid) {
/* 43 */     this.currtent_deptid = currtentDeptid;
/*    */   }
/*    */ 
/*    */   public void setId(int id) {
/* 47 */     this.id = id;
/*    */   }
/*    */   public void setSend_deptid(int sendDeptid) {
/* 50 */     this.send_deptid = sendDeptid;
/*    */   }
/*    */   public void setRecevie_deptid(int recevieDeptid) {
/* 53 */     this.recevie_deptid = recevieDeptid;
/*    */   }
/*    */   public void setMess_id(int messId) {
/* 56 */     this.mess_id = messId;
/*    */   }
/*    */   public void setSite_id(String siteId) {
/* 59 */     this.site_id = siteId;
/*    */   }
/*    */   public void setRoom_id(int roomId) {
/* 62 */     this.room_id = roomId;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.bookroom.bean.MessageDeptBean
 * JD-Core Version:    0.6.2
 */