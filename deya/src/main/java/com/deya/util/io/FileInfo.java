/*
 * @(#) FileInfo.java 1.0 02/12/24
 *
 * Copyright(c) 2002-2007 Cicro SoftWare.
 * Xi'an, Shannxi, China.
 * All rights reserved.
 *
 */

package com.deya.util.io;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 获取文件信息.
 *
 * @version 1.0 02/12/24
 * @authour 曲彦宾
 */
public class FileInfo{

    /**
     * 获得文件的大小(单位k).
     * @param String 文件路径
     * @return String 文件的大小
     */
    public static String getSize(String fileName){
        if(fileName == null) return null;
        File f = new File(fileName);
        return getSize(f);
    }

    /**
     * 获得文件的大小(单位k).
     * @param File 文件对象
     * @return　String 文件的大小
     */
    public static String getSize(File fileIn){
        if(fileIn == null) return null;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        double d = (double)(fileIn.length())/1024.0d;
        return nf.format(d);
    }

    /**
     * 获得图片文件的尺寸.
     * @param String 文件路径
     * @return String[] 文件宽、高
     */
    public static String[] getWidthHeight(String fileName){
        if(fileName == null) return null;
        File f = new File(fileName);
        return getWidthHeight(f);
    }

    /**
     * 获得图片文件的尺寸.
     * @param File 文件对象
     * @return String[] 文件宽、高
     */
    public static String[] getWidthHeight(File fileIn){
        if(fileIn == null) return null;
        try{
            BufferedImage bufferedImage = ImageIO.read(fileIn);
            String[] straReturn = new String[2];
            straReturn[0] = "" + bufferedImage.getWidth();
            straReturn[1] = "" + bufferedImage.getHeight();
            return straReturn;
        }catch(IOException e){
            return null;
        }
    }

    /**
     * 获得图片文件的类型.
     * @param String 文件路径
     * @return String 文件的类型
     */
    public static String getType(String strFileName){
        String strType = "unknown";
        try{
            String strExt = strFileName.substring(strFileName.lastIndexOf(".")+1);
            if(strExt.equalsIgnoreCase("jpg")
                ||strExt.equalsIgnoreCase("jpeg")
                  ||strExt.equalsIgnoreCase("jpe")
                    ||strExt.equalsIgnoreCase("gif")
                      ||strExt.equalsIgnoreCase("png"))
                strType = "image";
            if(strExt.equalsIgnoreCase("swf"))
                strType = "flash";
            return strType;
        }catch(Exception e){
            return strType;
        }
    }

}
