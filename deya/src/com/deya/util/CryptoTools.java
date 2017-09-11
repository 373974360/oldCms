package com.deya.util;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.Key;

import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 使用DES加密和解密的方法
 *
 *  */
public class CryptoTools {
    private final byte [] DESkey = "cicioweb".getBytes();//设置密钥，略去
    private final byte[] DESIV = { 0x12, 0x34, 0x56, 0x78, 0x01,0x02,0x03,0x04};//设置向量，略去

    private AlgorithmParameterSpec iv =null;//加密算法的参数接口，IvParameterSpec是它的一个实现
    private Key key =null;


    public CryptoTools() {
    	try
    	{
	         DESKeySpec keySpec = new DESKeySpec(DESkey);//设置密钥参数
	         iv = new IvParameterSpec(DESIV);//设置向量
	         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//获得密钥工厂
	         key = keyFactory.generateSecret(keySpec);//得到密钥对象
    	}
    	catch (Exception e) {
            e.printStackTrace(System.out);              
		}
         
    }

    public String encode(String data){
    	try
    	{
	        Cipher enCipher  =  Cipher.getInstance("DES/CBC/PKCS5Padding");//得到加密对象Cipher
	        enCipher.init(Cipher.ENCRYPT_MODE,key,iv);//设置工作模式为加密模式，给出密钥和向量
	        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
	        BASE64Encoder base64Encoder = new BASE64Encoder();
	        return "=#="+base64Encoder.encode(pasByte);
    	}
    	catch (Exception e) {
            e.printStackTrace(System.out);  
            return data;
		}
    }

    public String decode(String data){
    	try
    	{
	    	if(data.startsWith("=#="))
	    	{
		        Cipher deCipher   =  Cipher.getInstance("DES/CBC/PKCS5Padding");
		        deCipher.init(Cipher.DECRYPT_MODE,key,iv);
		           BASE64Decoder base64Decoder = new BASE64Decoder();
		
		        byte[] pasByte=deCipher.doFinal(base64Decoder.decodeBuffer(data.substring(3)));
		
		        return new String(pasByte,"UTF-8");
	    	}
	    	else
	    		return data;
    	}
    	catch (Exception e) {
            e.printStackTrace(System.out);  
            return data;
		}
    }

	public String license(String value) {
		String cp_code = decode(value);
		String[] codes = cp_code.split("[*]");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int site_num = 20;
		String app_ids = "system,org,cms,control,appeal,zwgk,ggfw";
		if (codes != null && codes.length > 0) {
			String cupid = codes[1].replaceAll("[$]", "");
			String mac = codes[2].replaceAll("[$]", "");
			//System.out.println(cupid);
			//System.out.println(mac);
			String xml = "<license><cpuid><![CDATA[" + cupid + "]]></cpuid>" +
					"<mac><![CDATA[" + mac + "]]></mac>" +
					"<wcm><start_time>" + date + "</start_time>" +
					"<time_limit><![CDATA[]]></time_limit>" +
					"<site_num>" + site_num + "</site_num>" +
					"<app_ids>" + app_ids + "</app_ids>" +
					"</wcm></license>";
			//System.out.println(xml);
			return encode(xml);
		}
		return "";
	}

    public static void main(String args[])
    {             
        CryptoTools ct = new CryptoTools();
		System.out.println(ct.license("=#=rU65f7rwo/hWXSyuhaYqPioVVeczo0hDDum40B5uR8yS40SobA8G4gbWMBfTOMoN7zvxLzxj4GnZAtMqnP/c1g=="));
        System.out.println(ct.encode("zzzz"));
        System.out.println(ct.decode("=#=rU65f7rwo/hWXSyuhaYqPioVVeczo0hDDum40B5uR8yS40SobA8G4gbWMBfTOMoN7zvxLzxj4GnZAtMqnP/c1g=="));
    }
}
