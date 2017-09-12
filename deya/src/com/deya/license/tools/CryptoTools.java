//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.license.tools;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoTools {
    private final byte[] DESkey = "cicioweb".getBytes();
//    private final byte[] DESkey = "deyatech".getBytes();
    private final byte[] DESIV = new byte[]{18, 52, 86, 120, 1, 2, 3, 4};
    private AlgorithmParameterSpec iv = null;
    private Key key = null;

    public CryptoTools() {
        try {
            DESKeySpec keySpec = new DESKeySpec(this.DESkey);
            this.iv = new IvParameterSpec(this.DESIV);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(keySpec);
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
        }

    }

    public String encode(String data) {
        try {
            Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            enCipher.init(1, this.key, this.iv);
            byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return "=#=" + base64Encoder.encode(pasByte);
        } catch (Exception var5) {
            var5.printStackTrace(System.out);
            return data;
        }
    }

    public String decode(String data) {
        try {
            if (data.startsWith("=#=")) {
                Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                deCipher.init(2, this.key, this.iv);
                BASE64Decoder base64Decoder = new BASE64Decoder();
                byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data.substring(3)));
                return new String(pasByte, "UTF-8");
            } else {
                return data;
            }
        } catch (Exception var5) {
            var5.printStackTrace(System.out);
            return data;
        }
    }

    public static void main(String[] args) {
        CryptoTools ct = new CryptoTools();
        System.out.println(ct.encode("zzzz"));
        System.out.println(ct.decode("=#=fK9OqyFpGk3L2/NxmM4Y7A=="));
    }
}
