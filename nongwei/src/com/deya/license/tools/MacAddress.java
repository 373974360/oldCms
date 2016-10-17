/*    */ package com.deya.license.tools;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class MacAddress
/*    */ {
/*  8 */   private static final String[] windowsCommand = { "ipconfig", "/all" };
/*  9 */   private static final String[] linuxCommand = { "/sbin/ifconfig", "-a" };
/* 10 */   private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", 2);
/*    */ 
/*    */   private static final List<String> getMacAddressList() throws IOException {
/* 13 */     ArrayList macAddressList = new ArrayList();
/*    */ 
/* 15 */     String os = System.getProperty("os.name");
/*    */     String[] command;
/* 18 */     if (os.startsWith("Windows")) {
/* 19 */       command = windowsCommand;
/*    */     }
/*    */     else
/*    */     {
/* 20 */       if (os.startsWith("Linux"))
/* 21 */         command = linuxCommand;
/*    */       else
/* 23 */         throw new IOException("Unknown operating system: " + os);
/*    */     }
/* 26 */     Process process = Runtime.getRuntime().exec(command);
/*    */ 
/* 41 */     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
/* 42 */     for (String line = null; (line = reader.readLine()) != null; ) {
/* 43 */       Matcher matcher = macPattern.matcher(line);
/* 44 */       if (!matcher.matches())
/*    */         continue;
/* 46 */       macAddressList.add(matcher.group(1).replaceAll("[-:]", ""));
/*    */     }
/*    */ 
/* 49 */     reader.close();
/* 50 */     return macAddressList;
/*    */   }
/*    */ 
/*    */   public static String getMacAddress() {
/*    */     try {
/* 55 */       List addressList = getMacAddressList();
/* 56 */       StringBuffer sb = new StringBuffer();
/* 57 */       for (Iterator iter = addressList.iterator(); iter.hasNext(); ) {
/* 58 */         sb.append((String)iter.next());
/*    */       }
/* 60 */       return sb.toString(); } catch (IOException e) {
/*    */     }
/* 62 */     return null;
/*    */   }
/*    */ 
/*    */   public static final void main(String[] args)
/*    */   {
/*    */     try {
/* 68 */       System.out.println("  MAC Address: " + getMacAddress());
/*    */     } catch (Throwable t) {
/* 70 */       t.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\hp\Desktop\WCMLicense.jar
 * Qualified Name:     com.deya.license.tools.MacAddress
 * JD-Core Version:    0.6.0
 */