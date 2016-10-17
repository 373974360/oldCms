/*    */ package com.deya.license.tools;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.LineNumberReader;
/*    */ import java.io.PrintStream;
/*    */ import org.xvolks.jnative.JNative;
/*    */ import org.xvolks.jnative.Type;
/*    */ import org.xvolks.jnative.exceptions.NativeException;
/*    */ 
/*    */ public class CPUInfo
/*    */ {
/*    */   private String getWindowsCPUID()
/*    */   {
/* 14 */     JNative n = null;
/* 15 */     String str = "";
/*    */     try {
/* 17 */       n = new JNative("Reg.dll", "GetCPUID");
/*    */       try {
/* 19 */         n.setRetVal(Type.STRING);
/* 20 */         n.invoke();
/* 21 */         str = str + n.getRetVal();
/*    */       }
/*    */       catch (IllegalAccessException e) {
/* 24 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */     catch (NativeException e)
/*    */     {
/* 29 */       e.printStackTrace();
/*    */     }
/* 31 */     return str;
/*    */   }
/*    */ 
/*    */   private String getLinuxCPUID()
/*    */   {
/* 36 */     String cpuInfo = "";
/* 37 */     String[] LinuxCmd = { "/bin/sh", "-c", "dmidecode |grep -A42 \"Processor\"|more" };
/*    */     try {
/* 39 */       Process proc = Runtime.getRuntime().exec(LinuxCmd);
/* 40 */       InputStreamReader ir = new InputStreamReader(proc.getInputStream());
/* 41 */       LineNumberReader input = new LineNumberReader(ir);
/* 42 */       int i = 0;
/* 43 */       while (i++ < 6) {
/* 44 */         cpuInfo = input.readLine();
/*    */       }
/* 46 */       input.close();
/* 47 */       ir.close();
/*    */     }
/*    */     catch (IOException e) {
/* 50 */       e.printStackTrace();
/*    */     }
/* 52 */     return proceesID(cpuInfo.trim().substring(4, 15));
/*    */   }
/*    */ 
/*    */   private String proceesID(String cpuID)
/*    */   {
/* 57 */     String[] id = cpuID.split(" ");
/* 58 */     StringBuffer cpustr = new StringBuffer();
/* 59 */     for (int i = 0; i < id.length; i++)
/* 60 */       cpustr.append(id[i]);
/* 61 */     return cpustr.toString();
/*    */   }
/*    */ 
/*    */   public String getCPUID()
/*    */   {
/* 66 */     String id = "";
/* 67 */     String os = System.getProperty("os.name").toLowerCase();
/* 68 */     if (os.startsWith("windows"))
/* 69 */       id = getWindowsCPUID();
/* 70 */     if (os.startsWith("linux"))
/* 71 */       id = getLinuxCPUID();
/* 72 */     return id;
/*    */   }
/*    */   public static void main(String[] args) {
/* 75 */     System.out.println(new CPUInfo().getCPUID());
/*    */   }
/*    */ }

/* Location:           C:\Users\hp\Desktop\WCMLicense.jar
 * Qualified Name:     com.deya.license.tools.CPUInfo
 * JD-Core Version:    0.6.0
 */