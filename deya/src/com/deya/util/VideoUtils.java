package com.deya.util;

import java.io.*;

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
        try {
            String oldPath = filePath+fileName;
            String pathName = fileName.substring(0,fileName.lastIndexOf("."));
            File currentDir = new File(filePath+pathName);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String ext = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            if(!ext.equals(".mp4")){
                String mp4Command = "ffmpeg -i "+oldPath+" -c:v libx264 -strict -2 "+filePath+pathName+"/"+pathName+".mp4";
                runCmd(mp4Command);
                File oldFile = new File(oldPath);
                oldFile.delete();
                oldPath = filePath+pathName+"/"+pathName+".mp4";
            }
            String commandTs = "ffmpeg -i "+oldPath+" -codec copy -vbsf h264_mp4toannexb -map 0 -f segment -segment_list "+filePath+pathName+"/out.m3u8 -segment_time 10 "+filePath+pathName+"/out%03d.ts";
            runCmd(commandTs);
            File oldFile = new File(oldPath);
            oldFile.delete();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void runCmd(String command) throws Exception {
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
    }
}
