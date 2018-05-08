/*
 * @(#)JarManager.java   1.1 2004-06-17
 */

package com.deya.util;

import java.io.*;
import java.util.*;
import java.util.jar.*;


/**
 * <p>Title: Jar文件操作类</p>
 * <p>Description: 提供对Jar文件的常规操作。</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kongxx
 * @author zhenggz
 * @version 1.1
 */
/*
 * @version 1.1, 2004-06-17 Modified by zhenggz
 */
public class JarManager
    extends ZipManager {
    /**
     *
     * @param strJarFileName 要压缩的Jar文件名
     * @param arrFile        要压缩目录下的文件列表(相对路径)
     * @param strBaseDir     文件列表中文件的根目录(Jar文件中)
     * @param strTarget      Jar文件输入目录
     * @return true-成功 false-失败
     */
    @SuppressWarnings("unchecked")
	public static boolean compress(String strJarFileName,
                                   String[] arrFile,
                                   String strBaseDir,
                                   String strTarget) {
        //创建Jar输出流
        JarOutputStream jos = null;
        try {
            jos = new JarOutputStream(
                new FileOutputStream(
                strTarget + File.separator + strJarFileName));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return false;
        }

        //取得文件列表中目录列表保存在ArrayList中
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < arrFile.length; i++) {
            String strDir = strBaseDir + File.separator + arrFile[i];
            if ( (new File(strDir).isDirectory())) {
                    set.add(arrFile[i]);
            }
        } //end for

        //在Jar输出流中创建目录的Jar条目
        for (Iterator it=set.iterator();it.hasNext();) {
            //History 2003-07-28 modify by kongxx
            //对于空目录作为入口对象的必须以"/"做为结束符
            String strRntryName = (String) it.next();
            strRntryName = strRntryName.replace('\\', '/') + "/";
            JarEntry tentry = new JarEntry(strRntryName);
            try {
                jos.putNextEntry(tentry);
                jos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace(System.out);
                return false;
            }
        } //end for

        //设置缓存
        byte[] buffer = new byte[4096];
        int bytesRead;
        for (int i = 0; i < arrFile.length; i++) {
            //对于目录不作操作
        	
            File file = new File(strBaseDir + File.separator + arrFile[i]);
            if (file.isDirectory()) {
                continue;
            }            
            JarEntry je = new JarEntry(arrFile[i]);
            try {
                FileInputStream fis = new FileInputStream(file);
                jos.putNextEntry(je);
                while ( (bytesRead = fis.read(buffer)) != -1) {
                    jos.write(buffer, 0, bytesRead);
                }
                fis.close();
                jos.closeEntry();
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                return false;
            }
        }
        try {
            jos.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    /**
     * 压缩指定目录下所以文件.
     * @param strJarFileName Jar文件名
     * @param strDir 要压缩的目录,可多个，用,号分隔开
     * @param strBaseDir 要压缩文件的根目录
     * @param strTarget Jar文件输出目录
     * @return true-成功 false-失败
     *
     */
    public static boolean compress(String strJarFileName,
                                   String strDir,
                                   String strBaseDir,
                                   String strTarget) {
        try {
        	/*
            File f = new File(strDir);
            if (!f.exists()) {
                return false;
            }*/
            String[] arr = getFileList(strDir, strBaseDir);
            compress(strJarFileName, arr, strBaseDir, strTarget);
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 将strBaseDir下的arrFile列表中的文件加入到strJarFIle文件中
     * 首先将strBaseDir下的arrFile列表中的文件压缩到一个临时Jar文件中
     * 然后
     * @param strJarFile Jar文件名
     * @param arrFile 文件数组(相对目录结构)
     * @param strBaseDir  要压缩文件的根目录
     * @return true-成功 false-失败
     */
    @SuppressWarnings("unchecked")
	public static boolean insert(String strJarFile,
                                 String[] arrFile,
                                 String strBaseDir) {
        try {        	
            List<String> arrlist1 = new ArrayList<String>(); //保存原Jar文件中文件列表
            File f = new File(strJarFile);
            if (!f.exists()) {
                 return false;
            }

            //取得Jar文件中的文件列表
            JarFile jf = new JarFile(f);
            for (Enumeration enume = jf.entries(); enume.hasMoreElements(); ) {
                JarEntry je = (JarEntry) enume.nextElement();
                arrlist1.add(je.getName());
            }
            jf.close();

            //将要压缩的文件列表的目录导入ArrayList，并将路径做替换
            Set<String> set = new HashSet<String>();
            for (int i = 0; i < arrFile.length; i++) {
                String strDir;
                int index = arrFile[i].lastIndexOf(File.separator);
                if (index == -1) {
                    continue;
                }
                strDir = arrFile[i].substring(0, index + 1);
                strDir = strDir.replace('\\', '/');
                set.add(strDir);
            }

            //创建临时Jar输出流
            Manifest m = new Manifest();
            JarOutputStream jos =
                new JarOutputStream(
                new FileOutputStream(
                strJarFile + ".tmp"), m);

            //将目录插入到临时Jar文件
            for (Iterator it=set.iterator();it.hasNext();) {
                if (arrlist1.contains(it.next())) {
                    JarEntry je = new JarEntry( (String) it.next());
                    jos.putNextEntry(je);
                    jos.closeEntry();
                }
            }

            //将文件列表中的文件插入到临时Jar文件中
            int byteRead;
            byte[] buffer = new byte[4096];
            for (int i = 0; i < arrFile.length; i++) {
                f = new File(strBaseDir + File.separator + arrFile[i]);
                if (!f.exists()) {
                    return false;
                }
                FileInputStream fis = new FileInputStream(f);
                JarEntry je = new JarEntry(arrFile[i]);
                jos.putNextEntry(je);
                while ( (byteRead = fis.read(buffer)) != -1) {
                    jos.write(buffer, 0, byteRead);
                }
                fis.close();
                jos.closeEntry();
            }

            //将原来Jar包中的文件压缩到临时Jar文件中
            jf = new JarFile(new File(strJarFile));
            for (Enumeration enume = jf.entries(); enume.hasMoreElements(); ) {
                JarEntry je = (JarEntry) enume.nextElement();
                InputStream is = jf.getInputStream(je);
                jos.putNextEntry(je);
                while ( (byteRead = is.read(buffer)) != -1) {
                    jos.write(buffer, 0, byteRead);
                }
                is.close();
                jos.closeEntry();
            }
            jos.close();
            jf.close();

            //删除原Jar文件，将临时Jar文件更名
            f = new File(strJarFile);
            if (f.exists()) {
                f.delete();
            }
            f = new File(strJarFile + ".tmp");
            f.renameTo(new File(strJarFile));
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 解压缩Jar文件
     * @param strJarFile Jar文件（包括路径）
     * @param strTarget 解压后文件存放路径
     * @return true-成功 false-失败
     */
    @SuppressWarnings("unchecked")
	public static boolean decompress(String strJarFile,
                                     String strTarget) {
        try {
            
            int bytesRead;
            byte[] buffer = new byte[4096];

            JarFile jf = new JarFile(new File(strJarFile));
            Enumeration enume = jf.entries();
            while (enume.hasMoreElements()) {
                JarEntry je = new JarEntry( (JarEntry) enume.nextElement());
                String strFile = strTarget + File.separator + je.getName();

                if (File.separatorChar == '\\') {
                    strFile = strFile.replace('/', '\\');
                }
                else {
                    strFile = strFile.replace('\\', '/');
                }

                File f = new File(strFile);
                if (strFile.endsWith("/") || strFile.endsWith("\\")) {
                    f.mkdirs();
                    continue;
                }
                else {
                    int index = strFile.lastIndexOf(File.separator);
                    if (index != -1) {
                        f = new File(strFile.substring(0, index + 1));
                        f.mkdirs();
                    }
                }

                FileOutputStream fos = new FileOutputStream(strFile);
                InputStream is = jf.getInputStream(je);

                while ( (bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                is.close();
                fos.close();

                
            } //end while
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * 解压缩文件并保存到指定文件.
     * <pre>
     * boolean bln = decompressFile("e:\\test.jar" ,"ttt1/ttt2.xml" ,"e:\\temp\\ttt2.xml");
     * </pre>
     * @param jarfile 要解压的jar文件
     * @param entry   要解压的文件相对路径(在jar包中的路径)
     * @param target  要保存到的文件绝对路径
     * @return true-成功 false-失败
     * @throws IOException
     *
     * History 2003-07-22 add by kongxx
     */
    public static boolean decompressFile(String jarfile, String entry, String target) throws
        IOException {
        InputStream is = decompressFile2Stream(jarfile, entry);
        if (is == null) {
            return false;
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(target));
        int bytesRead;
        byte[] buffer = new byte[4096];
        while ( (bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        is.close();
        os.close();
        return true;
    }

    /**
     * 解压缩文件到输入流.
     * <pre>
     * InputStream is = decompressFile("e:\\test.jar" ,"ttt1/ttt2.xml" ,"e:\\temp\\ttt2.xml");
     * </pre>
     * @param jarfile 要解压的jar文件
     * @param entry   要解压的文件相对路径(在jar包中的路径)
     * @return 文件输出流.
     * @throws IOException
     *
     * History 2003-07-22 add by kongxx
     */
    public static InputStream decompressFile2Stream(String jarfile, String entry) throws
        IOException {
        InputStream is = null;
        JarFile jf = new JarFile(new File(jarfile));
        JarEntry je = new JarEntry(entry);
        is = new BufferedInputStream(jf.getInputStream(je));
        return is;
    }

    public static void main(String args[])
    {
    	/*
    	@param strJarFileName Jar文件名
        * @param strDir 要压缩的目录
        * @param strBaseDir 要压缩文件的根目录
        * @param strTarget Jar文件输出目录
        * */
    	//compress("ttt.jar","D:\\work\\WCM\\WebRoot\\manager\\cms,D:\\work\\WCM\\WebRoot\\manager\\appeal,D:\\work\\WCM\\WebRoot\\manager\\system","D:\\work\\WCM\\WebRoot\\manager","F:\\temp");
    	//compress("ttt.jar","D:\\work\\WCM\\WebRoot\\manager\\appeal","D:\\work\\WCM\\WebRoot\\manager","F:\\temp");
    	//decompress("F:\\temp\\ttt.jar","F:\\temp");
    	
    }
}
