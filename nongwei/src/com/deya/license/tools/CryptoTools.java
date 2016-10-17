/*    */ package com.deya.license.tools;
/*    */ 
/*    */ /*    */ import java.security.Key;
/*    */ import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Date;

/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import javax.crypto.spec.IvParameterSpec;

/*    */ import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
/*    */ 
/*    */ public class CryptoTools
/*    */ {
/* 20 */   private final byte[] DESkey = "cicioweb".getBytes();
/* 21 */   private final byte[] DESIV = { 18, 52, 86, 120, 1, 2, 3, 4 };
/*    */ 
/* 23 */   private AlgorithmParameterSpec iv = null;
/* 24 */   private Key key = null;
/*    */ 
/*    */   public CryptoTools()
/*    */   {
/*    */     try
/*    */     {
/* 30 */       DESKeySpec keySpec = new DESKeySpec(this.DESkey);
/* 31 */       this.iv = new IvParameterSpec(this.DESIV);
/* 32 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 33 */       this.key = keyFactory.generateSecret(keySpec);
/*    */     }
/*    */     catch (Exception e) {
/* 36 */       e.printStackTrace(System.out);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String encode(String data)
/*    */   {
/*    */     try
/*    */     {
/* 44 */       Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 45 */       enCipher.init(1, this.key, this.iv);
/* 46 */       byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
/* 47 */       BASE64Encoder base64Encoder = new BASE64Encoder();
/* 48 */       return "=#=" + base64Encoder.encode(pasByte);
/*    */     }
/*    */     catch (Exception e) {
/* 51 */       e.printStackTrace(System.out);
/* 52 */     }return data;
/*    */   }
/*    */ 
/*    */   public String decode(String data)
/*    */   {
/*    */     try
/*    */     {
/* 59 */       if (data.startsWith("=#="))
/*    */       {
/* 61 */         Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 62 */         deCipher.init(2, this.key, this.iv);
/* 63 */         BASE64Decoder base64Decoder = new BASE64Decoder();
/*    */ 
/* 65 */         byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data.substring(3)));
/*    */ 
/* 67 */         return new String(pasByte, "UTF-8");
/*    */       }
/*    */ 
/* 70 */       return data;
/*    */     }
/*    */     catch (Exception e) {
/* 73 */       e.printStackTrace(System.out);
/* 74 */     }return data;
/*    */   }

	  public String license(String value)
	  {
		  String cp_code = decode(value);
			 String[] codes = cp_code.split("[*]");
			 String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) ;
			 int site_num = 3;
			 String app_ids = "system,org,cms,control,appeal,zwgk,ggfw";
			 if(codes != null && codes.length > 0)
			 {
				 String cupid = codes[1].replaceAll("[$]","");
				 String mac = codes[2].replaceAll("[$]","");
				 //System.out.println(cupid);
				 //System.out.println(mac);
				 String xml = "<license><cpuid><![CDATA[" + cupid +"]]></cpuid>"+
						 "<mac><![CDATA[" + mac + "]]></mac>" +
						 "<wcm><start_time>" + date +"</start_time>" +
						 "<time_limit><![CDATA[]]></time_limit>" +
						 "<site_num>"+ site_num + "</site_num>" + 
						 "<app_ids>" + app_ids +"</app_ids>" + 
						 "</wcm></license>";
				 System.out.println(xml);
				 return encode(xml);
			 }	
		  return "";
	  }
	public static void main(String[] args)
	{
		CryptoTools ct = new CryptoTools();
		//System.out.println(ct.license("=#=rU65f7rwo/gkzL0byi9sP44wPpsKCCGIeS8O357jcpKDr96LgynxTWErcw6ofveqALxW2qBEh37I\n" +
        //        "FS9MaXcIVh9zQq5i7sNBPVeZ7A+DJ2tg1h43XEs79Q=="));

        System.out.println(ct.decode("=#=eG9QeKdlMPc="));
		
		//System.out.println(ct.encode("88352636!@#"));
		
		//System.out.println(ct.decode("=#=ODzJPka+2bU="));
		//String key = RandomStrg.getRandomStr("", RandomStrg.getRandomStr("0-9", "1")) + "," + DateUtil.dateToTimestamp();
		//key = ct.encode(key).replace("+", "WcMrEPlAcE").substring(3);
	}
}
