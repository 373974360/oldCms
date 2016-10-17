/*    */ package com.deya.project.db;
/*    */ 
/*    */ import com.deya.util.jconfig.JconfigFactory;
/*    */ import com.deya.util.jconfig.JconfigUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.Reader;
/*    */ import java.sql.Connection;
/*    */ import org.apache.ibatis.io.Resources;
/*    */ import org.apache.ibatis.session.Configuration;
/*    */ import org.apache.ibatis.session.ExecutorType;
/*    */ import org.apache.ibatis.session.SqlSession;
/*    */ import org.apache.ibatis.session.SqlSessionFactory;
/*    */ import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*    */ import org.apache.ibatis.session.TransactionIsolationLevel;
/*    */ 
/*    */ public class IbatisSessionFactory
/*    */   implements SqlSessionFactory
/*    */ {
/* 50 */   private static SqlSessionFactory sqlSessionFactory = null;
/*    */ 
/*    */   public static SqlSessionFactory getInstance()
/*    */   {
/* 27 */     if (sqlSessionFactory == null) {
/* 28 */       initSqlSessionFactory();
/*    */     }
/* 30 */     return sqlSessionFactory;
/*    */   }
/*    */ 
/*    */   private static void initSqlSessionFactory() {
/* 34 */     JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("project_config");
/* 35 */     String sqlmap_name = bc.getProperty("sqlmap_name", "", "sqsms");
/* 36 */     String resource = sqlmap_name;
/* 37 */     Reader reader = null;
/*    */     try {
/* 39 */       reader = Resources.getResourceAsReader(resource);
/* 40 */       SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
/*    */ 
/* 42 */       sqlSessionFactory = builder.build(reader);
/*    */     } catch (IOException e) {
/* 44 */       //System.out.println("创建iBATIS数据工厂时出现错误！");
/* 45 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Configuration getConfiguration()
/*    */   {
/* 53 */     return sqlSessionFactory.getConfiguration();
/*    */   }
/*    */ 
/*    */   public SqlSession openSession() {
/* 57 */     return sqlSessionFactory.openSession();
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(boolean arg0) {
/* 61 */     return sqlSessionFactory.openSession(arg0);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(Connection arg0) {
/* 65 */     return sqlSessionFactory.openSession(arg0);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(ExecutorType arg0) {
/* 69 */     return sqlSessionFactory.openSession(arg0);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(ExecutorType arg0, boolean arg1) {
/* 73 */     return sqlSessionFactory.openSession(arg0, arg1);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(ExecutorType arg0, Connection arg1) {
/* 77 */     return sqlSessionFactory.openSession(arg0, arg1);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(TransactionIsolationLevel arg0)
/*    */   {
/* 82 */     return sqlSessionFactory.openSession(arg0);
/*    */   }
/*    */ 
/*    */   public SqlSession openSession(ExecutorType arg0, TransactionIsolationLevel arg1)
/*    */   {
/* 88 */     return sqlSessionFactory.openSession(arg0, arg1);
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.db.IbatisSessionFactory
 * JD-Core Version:    0.6.2
 */