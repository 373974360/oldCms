package com.deya.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VideoUtils {

    public static void main(String args[]) {
        boolean b = executeCodecs("/deya/cms/", "201811060233033.mp4");
    }

    /**
     * 调用ffmpeg命令
     *
     * @param filePath 文件存放路径
     * @param fileName 视频名称
     */
    public static boolean executeCodecs(String filePath,String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        if(!ext.equals(".mp4")){
            String newName = DateUtil.getCurrentDateTime("yyyyMMddHHmmss")+".mp4";
            System.out.println("原始视频为："+ext+"；即将转码为MP4格式。。。");
            String mp4Command = "ffmpeg -i "+filePath+fileName+" -c:v libx264 -strict -2 "+filePath+newName+"";
            runCmd(mp4Command);
            System.out.println("原视频已转为MP4格式，地址为："+filePath+newName);
            fileName = newName;
        }
        System.out.println("开始转码M3U8......");
        System.out.println("源文件地址：" + filePath+fileName);
        String commandTs = "ffmpeg -i "+filePath+fileName+" -codec copy -vbsf h264_mp4toannexb -map 0 -f segment -segment_list "+filePath+"out.m3u8 -segment_time 10 "+filePath+"out%03d.ts";
        runCmd(commandTs);
        return true;
    }

    public static void runCmd(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            System.out.println(t);
            t.printStackTrace();
        }

    }
}
