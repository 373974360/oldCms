/*     */ package com.deya.project.db;
/*     */ 
/*     */ import com.deya.wcm.db.BoneDataSourceFactory;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.apache.ibatis.session.Configuration;
/*     */ import org.apache.ibatis.session.SqlSession;
/*     */ import org.apache.ibatis.session.SqlSessionFactory;
/*     */ 
/*     */ public class DBM
/*     */ {
/*     */   public static String getString(String sqlName, Object o)
/*     */   {
/*  38 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/*  40 */       Object tempO = s.selectOne(getSqlNameByDataType(sqlName), o);
/*  41 */       if (tempO == null) {
/*  42 */         return "";
/*     */       }
/*  44 */       return tempO.toString();
/*     */     } finally {
/*  46 */       s.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Object queryFObj(String sqlName, Object o)
/*     */   {
/*  59 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/*  61 */       return s.selectOne(getSqlNameByDataType(sqlName), o);
/*     */     } finally {
/*  63 */       s.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List queryFList(String sqlName, Object o)
/*     */   {
/*  76 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/*  78 */       return s.selectList(getSqlNameByDataType(sqlName), o);
/*     */     } finally {
/*  80 */       s.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean insert(String sqlName, Object o)
/*     */   {
/*  94 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/*  96 */       s.insert(getSqlNameByDataType(sqlName), o);
/*     */ 
/*  98 */       s.commit();
/*     */     } catch (Exception ex) {
/* 100 */       ex.printStackTrace();
/* 101 */       return false;
/*     */     }
/*     */     finally {
/* 104 */       s.close();
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean update(String sqlName, Object o)
/*     */   {
/* 119 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/* 121 */       s.update(getSqlNameByDataType(sqlName), o);
/* 122 */       s.commit();
/*     */     } catch (Exception ex) {
/* 124 */       ex.printStackTrace();
/* 125 */       return false;
/*     */     }
/*     */     finally {
/* 128 */       s.close();
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean delete(String sqlName, Object o)
/*     */   {
/* 142 */     SqlSession s = IbatisSessionFactory.getInstance().openSession();
/*     */     try {
/* 144 */       s.delete(getSqlNameByDataType(sqlName), o);
/* 145 */       s.commit();
/*     */     } catch (Exception ex) {
/* 147 */       ex.printStackTrace();
/* 148 */       return false;
/*     */     }
/*     */     finally {
/* 151 */       s.close();
/*     */     }
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getSqlNameByDataType(String sqlName)
/*     */   {
/* 165 */     String data_type_name = BoneDataSourceFactory.getDataTypeName();
/*     */ 
/* 167 */     if ((!"oracle".equals(data_type_name)) && (IbatisSessionFactory.getInstance().getConfiguration().hasStatement(sqlName + "_" + data_type_name)))
/* 168 */       sqlName = sqlName + "_" + data_type_name;
/* 169 */     return sqlName;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 174 */     System.out.println(insert("insertCJSMS", ""));
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.deya.project.db.DBM
 * JD-Core Version:    0.6.2
 */