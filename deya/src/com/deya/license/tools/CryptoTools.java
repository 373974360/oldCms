
package com.deya.license.tools;


import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.RandomStringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;


public class CryptoTools {
    private final byte[] DESkey = "deyatech".getBytes();
    private final byte[] DESIV = {18, 52, 86, 120, 1, 2, 3, 4};

    private AlgorithmParameterSpec iv = null;
    private Key key = null;


    public CryptoTools() {

        try {
            DESKeySpec keySpec = new DESKeySpec(this.DESkey);
            this.iv = new IvParameterSpec(this.DESIV);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(keySpec);

        } catch (Exception e) {
            e.printStackTrace(System.out);

        }

    }


    public String encode(String data) {

        try {
            Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            enCipher.init(1, this.key, this.iv);
            byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return "=#=" + base64Encoder.encode(pasByte);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;

    }


    public String decode(String data) {

        try {
            if (data.startsWith("=#=")) {
                Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                deCipher.init(2, this.key, this.iv);
                BASE64Decoder base64Decoder = new BASE64Decoder();
                byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data.substring(3)));
                return new String(pasByte, "UTF-8");
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;

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

    public static void main(String[] args) {
        CryptoTools ct = new CryptoTools();
        System.out.println(ct.license("=#=rU65f7rwo/hbgq1f9ppf0sHj62sF65b2URQ0ee8Xth2FyHm8+SqaOL/Ca87j1EbBG8TqgkNs5eo="));

        //System.out.println(ct.decode("=#=ODzJPka+2bU="));


        /*System.out.println(ct.decode("=#=NVgDPsTZx1u8mkVkWu4qP7LYnMswb2Zvy77R8udeaag8GrsDF2Wi1dMLGOZ3pbg/zm6ilcMfB+3j\n" +
                "jz09sVFxmNkuGWerfTWqXhX24nWoLNd+846JxcqhwHX7LOIlgc13ewgUeZyHc+tIFOw/DiQQwHg6\n" +
                "E30lvPmb+bkgGGRSVqxLfr+7c4G9RifY7hzKeJP5KJjrWhNtlClooiSdi+89cEWS1Z5baCD1I+p/\n" +
                "pRd4OuyH0rlPq8JazsKhmuj/cwU1NkO1IhtvmF6qOb+ynurKLsWOD9uTX0+fnFCh2MnX/fczYSs4\n" +
                "73ieLzKRftJmnn1t5xo1iRgas55GB+6VhyJEVnlIbwwzhBfLbsoRuwkBSztv0yyasCQCog=="));
        */
        //String key = RandomStrg.getRandomStr("", RandomStrg.getRandomStr("0-9", "1")) + "," + DateUtil.dateToTimestamp();
        //key = ct.encode(key).replace("+", "WcMrEPlAcE").substring(3);
    }
}
