/*     */ package com.deya.license;
/*     */ 
/*     */ import com.deya.license.server.GetServerInfo;
/*     */ import com.deya.license.tools.CryptoTools;
/*     */ import com.deya.license.tools.DomParse;
/*     */ import com.deya.util.FormatUtil;
/*     */ import com.deya.util.jconfig.JconfigUtil;
/*     */ import com.deya.util.jconfig.JconfigUtilContainer;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ public class GetLicense
/*     */ {
/*   9 */   private static String defaultLicensePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + "/license/license.data");
/*     */   private static File LicenseFile;  
/*     */   private static Document licDom;
/*     */ 
/*     */   public GetLicense(String filePath)
/*     */   {
/*  14 */     if ((filePath == null) || ("".equals(filePath)))
/*     */     {
/*  16 */       filePath = defaultLicensePath;
/*     */     }
/*  18 */     setLicenseFile(filePath);
/*     */   }
/*     */ 
/*     */   private void setLicenseFile(String path)
/*     */   {
/*  23 */     LicenseFile = new File(path);
/*  24 */     if (isLicenseFile())
/*     */     {
/*  26 */       String LicenseXml = "";
/*     */       try
/*     */       {
/*  29 */         CryptoTools ct = new CryptoTools();
/*  30 */         LicenseXml = ct.decode(getLicenseCode());
/*  31 */         licDom = DomParse.creaatDomByString(LicenseXml);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*  35 */         ex.printStackTrace(System.out);
/*     */       }
/*     */ 
/*     */     }
/*  40 */     else if (defaultLicensePath.equals(path))
/*     */     {
/*  42 */       System.out.println("license.data does not exist!");
/*     */     }
/*     */     else
/*     */     {
/*  46 */       System.out.println(path + " does not exist!");
/*  47 */       setLicenseFile(defaultLicensePath);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isLicenseFile()
/*     */   {
/*  54 */     return LicenseFile.exists();
/*     */   }
/*     */ 
/*     */   public String getLicenseCode() throws IOException
/*     */   {
/*  59 */     String strR = null;
/*  60 */     InputStream is = new BufferedInputStream(new FileInputStream(LicenseFile));
/*  61 */     int iLength = is.available();
/*  62 */     byte[] b = new byte[iLength];
/*  63 */     is.read(b);
/*  64 */     is.close();
/*  65 */     strR = new String(b);
/*  66 */     return strR;
/*     */   }
/*     */ 
/*     */   public String getDeptNum()
/*     */   {
/*  71 */     return DomParse.getNodeItem(licDom, "dept_num");
/*     */   }
/*     */ 
/*     */   public String getCPUidByLicense()
/*     */   {
/*  76 */     return DomParse.getNodeItem(licDom, "cpuid");
/*     */   }
/*     */ 
/*     */   public String getMACByLicense()
/*     */   {
/*  81 */     return DomParse.getNodeItem(licDom, "mac");
/*     */   }
/*     */ 
/*     */   public boolean ifServerIdentical()
/*     */   {
/*  87 */     return (GetServerInfo.getCPUID().equals(getCPUidByLicense())) && (GetServerInfo.getMac().equals(getMACByLicense()));
/*     */   }
/*     */ 
/*     */   public String getLicenseItemValue(String itemName)
/*     */   {
/*  95 */     return DomParse.getNodeItem(licDom, itemName);
/*     */   }
/*     */ 
/*     */   public String getLicenseItemValue(String parentName, String itemName)
/*     */   {
/* 100 */     return DomParse.getNodeItem(licDom, parentName, itemName);
/*     */   }
/*     */ 
/*     */   public static void main(String[] arg)
/*     */   {
/* 105 */     GetLicense gl = new GetLicense("");
/* 106 */     System.out.println(gl.getMACByLicense());
/*     */   }
/*     */ }

/* Location:           C:\Users\hp\Desktop\WCMLicense.jar
 * Qualified Name:     com.deya.license.GetLicense
 * JD-Core Version:    0.6.0
 */