package com.deya.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件基本操作类.
 * 用来替换com.deya.cws.util.FileManager类,FileManager类的包路径不合适
 * <p>Title: FileManager</p>
 * <p>Description: 包括问文件读、写、拷贝、复制等。</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Cicro</p>
 * @author kongxx
 * @version 1.0
 */
/*
 * History 2003-07-22 create by kongxx
 */
public class FileOperation {
    /**
     * <pre>
     * 将strSource文件内容拷贝到strTarget中.
     * 例如：
     * 将e:\\temp\\test1.java复制成e:\\temp\\test2.java，
     * 如果目标文件已经存在则覆盖
     * copyFile("e:\\temp\\test1.java","e:\\temp\\test2.java",true);
     * </pre>
     * @param strSource 源文件
     * @param strTarget 目标文件
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean copyFile(String strSource,
                                   String strTarget,
                                   boolean blnOverWrite) throws IOException {
        File fSource = new File(strSource);
        File fTarget = new File(strTarget);

        return copyFile(fSource, fTarget, blnOverWrite);
    }

    /**
     * <pre>
     * 将fSource文件内容拷贝到fTarget中.
     * 例如：
     * 将e:\\temp\\test1.java复制成e:\\temp\\test2.java，
     * 如果目标文件已经存在则覆盖
     * File fSource = new File("e:\\temp\\test1.java");
     * File fTarget = new File("e:\\temp\\test2.java");
     * copyFile(fSource ,fTarget ,true);
     * </pre>
     * @param fSource 源文件
     * @param fTarget 目标文件
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    /* History 2003-03-31 modify by kongxx
     *         2003-05-15 modify by kongxx
     *         修改了对目标目录不存在的处理
     */
    public static boolean copyFile(File fSource,
                                   File fTarget,
                                   boolean blnOverWrite) throws IOException {
        //当目标文件已经存在并且不覆盖时返回true
        if (fTarget.exists() && blnOverWrite != true) {
            return true;
        }

        File fParent = new File(fTarget.getParent());
        if (!fParent.exists()) {
            fParent.mkdirs();
        }

        try {
            //获取源文件输入流
            InputStream is = new BufferedInputStream(new FileInputStream(
                fSource));

            if (!fTarget.exists()) {
                fTarget.createNewFile();
            }

            //获取目标文件输出流
            OutputStream os = new BufferedOutputStream(new FileOutputStream(
                fTarget));

            int bytes;
            byte[] buffer = new byte[4096];
            while ( (bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }

            is.close();
            os.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * <pre>
     * 复制目录，将strSource复制到strTarget下.
     * 例如：
     * 将e:\\temp\\test1目录复制到e:\\share目录下,如果
     * 目标文件已经存在，则覆盖
     * copyDir("e:\\temp\\test1","e:\\share",true);
     * 复制后的目标目录结构：e:\\share\test1
     * </pre>
     * @param strSource 源目录
     * @param strTarget 目标目录
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    /*
     * History 2003-12-30  Modified by Sunyi  支持通配符"*"
     */
    public static boolean copyDir(String strSource,
                                  String strTarget,
                                  boolean blnOverWrite) throws IOException {
        /* History 2003-12-30  Added by Sunyi */
        /* 支持通配符“*” */
        //是否使用通配符
        boolean isWildcard = false;
        if (strSource.endsWith("*")) {
            isWildcard = true;
            strSource = strSource.substring(0, strSource.length() - 2);
        }

        if (File.separator.equals("\\")) {
            strSource = strSource.replaceAll("/", "\\\\");
            strTarget = strTarget.replaceAll("/", "\\\\");
        }

        File fSource = new File(strSource);       

        //取得源目录下文件列表
        String[] arr = getFileList(strSource);

        //String strBaseDir = fSource.getParent() + File.separator;
        String strBaseDir = fSource.getParent();
        int iLength = strBaseDir.length();

        if (isWildcard) {
            iLength += fSource.getName().length();
        }
        /* End History 2003-12-30  Added by Sunyi */

        int iCount = arr.length;
        int iErr = 0;
        File file = null;

        //取得源目录下的目录列表并在目标目录下创建新目录
        for (int i = 0; i < iCount; i++) {
            file = new File(arr[i]);
            if (file.isDirectory()) {
                //构造目标目录描述串，并创建目录
                String strT = strTarget + File.separator
                    + arr[i].substring(iLength, arr[i].length());
                file = new File(strT);
                if (!file.exists()) {
                    iErr += (file.mkdirs() ? 0 : 1);
                }
            }
        }

        //取得源目录下的文件列表并在目标目录下创建新文件
        for (int i = 0; i < iCount; i++) {
            file = new File(arr[i]);
            if (file.isFile()) {
                //构造目标文件描述串，并复制文件
                String strT = strTarget + File.separator
                    + arr[i].substring(iLength, arr[i].length());
                iErr += (copyFile(arr[i], strT, blnOverWrite) ? 0 : 1);
            }
        }
        return (iErr == 0) ? true : false;
    }

    /**
     * <pre>
     * 移动文件strSource到strTarget.
     * 例如：
     * moveFile(e:\\test1\\test1.txt,e:\\test2\\test2,true);
     * </pre>
     * @param strSource 源文件
     * @param strTarget 目标文件
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean moveFile(String strSource,
                                   String strTarget,
                                   boolean blnOverWrite) throws IOException {
        File fSource = new File(strSource);
        File fTarget = new File(strTarget);

        return moveFile(fSource, fTarget, blnOverWrite);
    }

    /**
     * <pre>
     * 移动文件fSource到fTarget.
     * 例如：
     * File fSource = new File("e:\\test1\\test1.txt");
     * File fTarget = new File("e:\\test2\\test2");
     * moveFile(fSource ,fTarget ,true);
     * </pre>
     * @param fSource 源文件
     * @param fTarget 目标文件
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean moveFile(File fSource,
                                   File fTarget,
                                   boolean blnOverWrite) throws IOException {
        boolean bln = copyFile(fSource, fTarget, blnOverWrite);
        if (bln != true) {
            return false;
        }

        fSource.deleteOnExit();
        return true;
    }

    /**
     * <pre>
     * 移动目录，将strSource移动到strTarget下.
     * 例如：
     * 将e:\\temp\\test1目录移动到e:\\share目录下
     * moveDir("e:\\temp\\test1","e:\\share",true);
     * </pre>
     * @param strSource 源目录
     * @param strTarget 目标目录
     * @param blnOverWrite 是否覆盖
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean moveDir(String strSource,
                                  String strTarget,
                                  boolean blnOverWrite) throws IOException {
        File fSource = new File(strSource);  
        boolean bln = copyDir(strSource, strTarget, blnOverWrite);
        if (bln != true) {
            return false;
        }

        fSource.deleteOnExit();
        return true;
    }

    /**
     * <pre>
     * 将指定文件内容读到字符串中.
     * 例如：
     * String strR = readFileToString("e:\\test\\test1.txt");
     * </pre>
     * @param strFile 文件路径
     * @return String - 文件内容
     * @throws IOException
     */
    public static String readFileToString(String strFile) throws IOException {
        File file = new File(strFile);
        return readFileToString(file);
    }

    /**
     * <pre>
     * 将指定文件内容读到字符串中.
     * 例如：
     * File file = new File("e:\\test\\test1.txt");
     * String strR = readFileToString(file);
     * </pre>
     * @param file 文件对象
     * @return String - 文件内容
     * @throws IOException
     */
    public static String readFileToString(File file) throws IOException {
        String strR = null;
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        int iLength = is.available();
        byte[] b = new byte[iLength];
        is.read(b);
        is.close();
        strR = new String(b);
        return strR;
    }

    /**
     * 把文件读成字节
     * @param file File
     * @throws IOException
     * @return byte[]
     */
    public static byte[] readFileToBytes(File file) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        int iLength = is.available();
        byte[] b = new byte[iLength];
        is.read(b);
        is.close();
        return b;
    }

    /**
     * 把文件读成字节
     * @param strFile String
     * @throws IOException
     * @return byte[]
     */
    public static byte[] readFileToBytes(String strFile) throws IOException {
        File file = new File(strFile);
        return readFileToBytes(file);
    }

    /**
     * <pre>
     * 将指定文件内容读到字符串中.
     * 例如：
     * File file = new File("e:\\test\\test1.txt");
     * String strR = readFileToString(file);
     * </pre>
     * @param file 文件对象
     * @param charsetName String 编码
     * @return String - 文件内容
     * @throws IOException
     */
    public static String readFileToString(File file, String charsetName) throws IOException {
        String strR = null;
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        int iLength = is.available();
        byte[] b = new byte[iLength];
        is.read(b);
        is.close();
        strR = new String(b, charsetName);
        return strR;
    }

    /**
     * <pre>
     * 将指定文件内容读到字符串中.
     * 例如：
     * File file = new File("e:\\test\\test1.txt");
     * String strR = readFileToString(file);
     * </pre>
     * @param file String 文件路径
     * @param charsetName String 编码
     * @return String - 文件内容
     * @throws IOException
     */
    public static String readFileToString(String strFile, String charsetName) throws IOException {
        File file = new File(strFile);
        return readFileToString(file, charsetName);
    }

    /**
     * <pre>
     * 向指定文件中写入内容.
     * 例如：
     * boolean bln = writeStringToFile("e:\\test\\test1.txt","测试",true);
     * </pre>
     * @param strFile 文件路径
     * @param strContent 要写入的字符串
     * @param blnAppend 是否追加在源文件后
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean writeStringToFile(String strFile,
                                            String strContent,
                                            boolean blnAppend) throws
        IOException {
        File file = new File(strFile);
        return writeStringToFile(file, strContent, blnAppend);
    }

    /**
     * 用指定编码向指定文件中写入内容.
     * @param strFile String
     * @param strContent String
     * @param blnAppend boolean
     * @param charsetName String
     * @throws IOException
     * @return boolean
     */
    public static boolean writeStringToFile(String strFile,
                                            String strContent,
                                            boolean blnAppend, String charsetName) throws
        IOException {
        File file = new File(strFile);
        return writeStringToFile(file, strContent, blnAppend, charsetName);
    }

    /**
     * <pre>
     * 向指定文件中写入内容.
     * 例如：
     * File file = new File("e:\\test\\test1.txt");
     * boolean bln = writeStringToFile(file,"测试",true);
     * </pre>
     * @param file 文件对象
     * @param strContent 要写入的字符串
     * @param blnAppend 是否追加在源文件后
     * @return boolean 操作是否成功
     * @throws IOException
     */
    public static boolean writeStringToFile(File file,
                                            String strContent,
                                            boolean blnAppend) throws
        IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, blnAppend);
        fw.write(strContent);
        fw.close();
        return true;
    }

    /**
     * 用指定编码向指定文件中写入内容.
     * @param file File
     * @param strContent String
     * @param blnAppend boolean
     * @param charsetName String
     * @throws IOException
     * @return boolean
     */
    public static boolean writeStringToFile(File file,
                                            String strContent,
                                            boolean blnAppend, String charsetName) throws
        IOException {
        if (!file.exists()) {
        	file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file, blnAppend),
            charsetName);
        fw.write(strContent);
        fw.close();
        return true;
    }

    /**
     * 把以后直接数组写入到指定文件
     * @param file File
     * @param bytes byte[]
     * @param blnAppend boolean
     * @throws IOException
     * @return boolean
     */
    public static boolean writeBytesToFile(File file,
                                           byte[] bytes,
                                           boolean blnAppend) throws
        IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fw = new FileOutputStream(file, blnAppend);
        fw.write(bytes);
        fw.close();
        return true;
    }

    /**
     * 把以后直接数组写入到指定文件
     * @param filePath String
     * @param bytes byte[]
     * @param blnAppend boolean
     * @throws IOException
     * @return boolean
     */
    public static boolean writeBytesToFile(String filePath,
                                           byte[] bytes,
                                           boolean blnAppend) throws
        IOException {
        File file = new File(filePath);
        return writeBytesToFile(file, bytes, blnAppend);
    }

    /**
     * 删除目录(包括目录下所有文件).
     * @param strDir 要删除的目录(绝对路径)
     * @return true-成功 false-失败
     * History 2003-05-01 add by kongxx
     */
    public static boolean deleteDir(String strDir) {
        String[] arrFile = getFileList(strDir);
        File file = null;
        for (int i = 0; i < arrFile.length; i++) {
            file = new File(arrFile[i]);
            if (file.isFile()) {
                file.delete();
            }
            else if (file.isDirectory()) {
                deleteDir(arrFile[i]);
            }
        }
        file = new File(strDir);
        file.delete();
        return true;
    }

    /**
     * <pre>
     * 获得给定目录下所有文件列表包括目录.
     * 例如：
     * String []arr = getFileList("e:\\test");
     * </pre>
     * @param strDir  给定目录
     * @return String[]-文件集合
     */
    public static String[] getFileList(String strDir) {
        ArrayList<String> arrlist = new ArrayList<String>();
        File f = new File(strDir);
        if (f.exists()) {
            File[] arrF = f.listFiles();
            for (int i = 0; i < arrF.length; i++) {
                if (arrF[i].isFile()) {
                    arrlist.add(arrF[i].getAbsolutePath());
                }
                else {
                    arrlist.add(arrF[i].getAbsolutePath());
                    String[] arr = getFileList(arrF[i].getAbsolutePath());
                    for (int j = 0; j < arr.length; j++) {
                        arrlist.add(arr[j]);
                    }
                } //end if
            } //end for
        } //end if
        return (String[]) arrlist.toArray(new String[arrlist.size()]);
    }

    /**
     * 获取文件或目录所占空间大小(单位Byte).
     * @param strfile 文件或目录路径
     * @return 文件或目录占用空间大小
     */    
    public static long getFileSize(String strfile) {
        return getFileSize(new File(strfile));
    }

    /**
     * 获取文件或目录所占空间大小(单位Byte).
     * @param file 文件或目录对象
     * @return 文件或目录占用空间大小
     */   
    public static long getFileSize(File file) {
        long size = 0;
        if (file.isFile()) {
            try {
                InputStream is = new FileInputStream(file);
                size = (long) is.available();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            String[] stafiles = getFileList(file.getAbsolutePath());
            for (int i = 0; i < stafiles.length; i++) {
                File f = new File(stafiles[i]);
                if (f.isDirectory()) {
                    size += 4096;
                }
                else {
                    try {
                        InputStream is = new FileInputStream(f);
                        size += (long) is.available();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return size;
    }

    /**
     * 将对象写入到指定文件
     * @param File 要保存的文件对象
     * @param object 要写入的数据对象
     * @return boolean　成功或失败
     */ 	
    public static boolean writeObjectToFile(File file,Object object) {
        if(object==null){
            return false;
        }
        ObjectOutputStream out=null;
        try{
            out =new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            return true;
        }catch(Exception e){
            e.printStackTrace(System.out);
            return false;
        }finally{
            try{
                out.close();
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }
    }
    
    /**
     * 将对象写入到指定文件
     * @param String 要保存的文件路径
     * @param object 要写入的数据对象
     * @return boolean　成功或失败
     */ 
    public static boolean writeObjectToFile(String file_path,Object object) {
        File file=new File(file_path);
        return writeObjectToFile(file,object);
    }

    /**
     * 读取文件到数据对象
     * @param File 要读取的文件对象
     * @return Object　数据对象
     */ 
    public static Object readFileToObject(File file) {
        if(!file.exists()){
            return null;
        }
        ObjectInputStream in=null;
        try{
            in =new ObjectInputStream(new FileInputStream(file));
            return in.readObject();
        }catch(Exception e){
            e.printStackTrace(System.out);
            return null;
        }finally{
            try{
                in.close();
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }
    }
    
    /**
     * 读取文件到数据对象
     * @param String 要读取的文件路径
     * @return Object　数据对象
     */ 
    public static Object readFileToObject(String file_path) {
        File file=new File(file_path);
        return readFileToObject(file);
    }

    
    public static List<String> getFileSinglList(String strDir)
    {
      ArrayList arrlist = new ArrayList();
      File f = new File(strDir);
      if (f.exists()) {
        File[] arrF = f.listFiles();
        for (int i = 0; i < arrF.length; ++i) {
          if (arrF[i].isFile()) {
            arrlist.add(arrF[i].getAbsolutePath());
          }
        }
      }
      return arrlist;
    }
    
    public static List<String> getFolderList(String strDir)
    {
      ArrayList arrlist = new ArrayList();
      File f = new File(strDir);
      if (f.exists()) {
        File[] arrF = f.listFiles();
        for (int i = 0; i < arrF.length; ++i) {
          if (!(arrF[i].isFile())) {
            arrlist.add(arrF[i].getAbsolutePath());
          }
        }
      }
      return arrlist;
    }
    /**
     * 删除文件
     * @param sPath
     * @return boolean
     */
    public static boolean deleteFile(String sPath) {   
		boolean flag = false; 
		File file = new File(sPath);   
	    if (file.isFile() && file.exists()) {
	        file.delete();   
	        flag = true;   
	    }   
	    return flag;   
	}
    public static void main(String[] args)throws Exception{
        //String s="问过无人管";
        //FileOperation.writeObjectToFile("c:/aaa.obj",s);
    	byte[] b = FileOperation.readFileToBytes("F:\\temp\\ttt.jar");
    	FileOperation.writeBytesToFile("F:\\temp\\222.jar", b, true);
    }


}
