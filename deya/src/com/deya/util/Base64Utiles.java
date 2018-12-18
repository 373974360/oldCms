package com.deya.util;

import java.io.*;

import org.apache.commons.codec.binary.Base64;

/**
 * base64 与 file 之间的相互转化
 */
public class Base64Utiles {


    // 将 file 转化为 Base64
    public static String fileToBase64(String path) {
        String ext = path.substring(path.lastIndexOf("."),path.length());
        File file = new File(path);
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            String bTemp = Base64.encodeBase64String(buffer)+ext;
            return bTemp;
        } catch (Exception e) {
            throw new RuntimeException("文件路径无效\n" + e.getMessage());
        }
    }

    // 将 base64 转化为 file
    public static boolean base64ToFile(String base64, String path) {
        byte[] buffer;
        try {
            buffer = Base64.decodeBase64(base64);
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("base64字符串异常或地址异常\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String str = fileToBase64("/Users/chaoweima/Downloads/OA接口说明文档.docx");
        base64ToFile(str, "/Users/chaoweima/Downloads/111.docx");
        System.out.println(str);
    }
}
