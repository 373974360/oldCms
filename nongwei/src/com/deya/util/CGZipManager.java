package com.deya.util;

import java.io.*;
import java.util.zip.*;
import java.util.Date;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kongxx
 * @version 1.0
 */
public class CGZipManager
{

    /**
     * 压缩文件
     * @param strSource 原文件
     * @param strTarget 压缩后文件
     * @return 空
     */
    public void compress(String strSource,String strTarget)
    {
        try
        {
            File source=new File(strSource);
            long size=source.length();
            InputStream  bis =new FileInputStream(source);
            OutputStream out =
                    new GZIPOutputStream(
                    new FileOutputStream(strTarget));

            byte[] temp=new byte[(int)size];
            while((bis.read(temp)) != -1)
            {
                out.write(temp);
            }
            bis.close();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 压缩字节数组到指定文件
     * @param b 要压缩的字节数组
     * @param strTarget 压缩后文件
     * @return 空
     */
    public void compress(byte []b,String strTarget)
    {
        try
        {
            BufferedOutputStream out =
                    new BufferedOutputStream(
                    new GZIPOutputStream(
                    new FileOutputStream(strTarget)));
            out.write(b);
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        long before=new Date().getTime();
        String strS = "F:/test.txt";
        String strT = "F:/test.gz";
        CGZipManager g = new CGZipManager();
        g.compress(strS,strT);
        long after=new Date().getTime();
        System.out.println("cost:"+(after-before)+" mini-secs");
    }
}
