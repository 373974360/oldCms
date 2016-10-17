/*
 * @(#)ZipManager.java   1.1 2004-06-17
 */

package com.deya.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * Title:zip类文件处理类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @version 1.1
 */
public class ZipManager {
	/**
	 * 创建Zip目录
	 * 
	 * @param strZipFileName
	 *            要创建的Zip文件名
	 * @param arrFile
	 *            文件列表（相对路径）
	 * @param strBaseDir
	 *            文件列表中文件的根目录
	 * @param strTarget
	 *            Zip文件的输出目录
	 * @return 是否成功
	 */
	@SuppressWarnings("unchecked")
	public static boolean compress(String strZipFileName, String[] arrFile,
			String strBaseDir, String strTarget) {
		try {
			// 创建Zip输出流
			ZipOutputStream jos = new ZipOutputStream(new FileOutputStream(
					strTarget + File.separator + strZipFileName));

			// 取得文件列表中目录列表保存在ArrayList中
			Set<String> set = new HashSet<String>();
			for (int i = 0; i < arrFile.length; i++) {
				String strDir = strBaseDir + File.separator + arrFile[i];
				if ((new File(strDir).isDirectory())) {
					set.add(arrFile[i]);
				}
			} // end for

			// 在Zip输出流中创建目录的Zip条目
			for (Iterator it = set.iterator(); it.hasNext();) {
				// History 2003-07-28 modify by kongxx
				// 对于空目录作为入口对象的必须以"/"做为结束符
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
			} // end for

			// 设置缓存
			byte[] buffer = new byte[4096];
			int bytesRead;
			for (int i = 0; i < arrFile.length; i++) {
				// 对于目录不作操作
				File file = new File(strBaseDir + File.separator + arrFile[i]);
				if (file.isDirectory()) {
					continue;
				}

				try {
					ZipEntry je = new ZipEntry(arrFile[i]);
					FileInputStream fis = new FileInputStream(file);
					jos.putNextEntry(je);
					while ((bytesRead = fis.read(buffer)) != -1) {
						jos.write(buffer, 0, bytesRead);
					}
					fis.close();
					jos.closeEntry();

				} catch (Exception ex) {

				}
			}
			jos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 压缩目录，包含去除项。
	 * 
	 * @param strZipFileName
	 *            ZIP文件名
	 * @param strDir
	 *            压缩目录
	 * @param strBaseDir
	 *            基目录
	 * @param strTarget
	 *            目标目录
	 * @return 是否压缩成功
	 */
	public static boolean compress(String strZipFileName, String strDir,
			String strBaseDir, String strTarget) {
		try {
			File f = new File(strDir);
			if (!f.exists()) {
				return false;
			}
			String[] arr = getFileList(strDir, strBaseDir);
			return compress(strZipFileName, arr, strBaseDir, strTarget);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 压缩目录，包含去除项。
	 * 
	 * @param strZipFileName
	 *            ZIP文件名
	 * @param strDir
	 *            压缩目录
	 * @param strBaseDir
	 *            基目录
	 * @param strTarget
	 *            目标目录
	 * @param exceptDir
	 *            去除目录项（相对路径，字符串包含）
	 * @return 是否压缩成功
	 */
	/*
	 * History 2003-12-02 Created by zhenggz
	 */
	public static boolean compress(String strZipFileName, String strDir,
			String strBaseDir, String strTarget, String[] exceptDir) {
		try {
			File f = new File(strDir);
			if (!f.exists()) {
				return false;
			}

			String[] arr = getFileList(strDir, strBaseDir);

			if (arr != null && exceptDir != null) {
				ArrayList<String> arrlist = new ArrayList<String>();
				for (int i = 0; i < arr.length; i++) {
					boolean flag = false;
					for (int j = 0; j < exceptDir.length; j++) {
						if (arr[i].indexOf(exceptDir[j]) != -1) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						arrlist.add(arr[i]);
					}
				}
				String[] ziparr = (String[]) arrlist.toArray(new String[arrlist
						.size()]);
				return compress(strZipFileName, ziparr, strBaseDir, strTarget);
			} else {
				return false;
			}
		} catch (Exception e) {
			// e.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 将strBaseDir下的arrFile列表中的文件加入到strZipFIle文件中
	 * 首先将strBaseDir下的arrFile列表中的文件压缩到一个临时Zip文件中
	 * 
	 * @param strZipFile
	 *            要创建的Zip文件名
	 * @param arrFile
	 *            文件列表（相对路径）
	 * @param strBaseDir
	 *            文件列表中文件的根目录
	 * @return 是否成功
	 */
	@SuppressWarnings("unchecked")
	public static boolean insert(String strZipFile, String[] arrFile,
			String strBaseDir) {
		try {
			ArrayList<String> arrlist1 = new ArrayList<String>(); // 保存原Zip文件中文件列表
			File f = new File(strZipFile);
			if (!f.exists()) {
				return false;
			}

			// 取得Zip文件中的文件列表
			ZipFile jf = new ZipFile(f);
			for (Enumeration enume = jf.entries(); enume.hasMoreElements();) {
				ZipEntry je = (ZipEntry) enume.nextElement();
				arrlist1.add(je.getName());
			}
			jf.close();

			// 将要压缩的文件列表的目录导入ArrayList，并将路径做替换
			ArrayList<String> arrlist2 = new ArrayList<String>();
			for (int i = 0; i < arrFile.length; i++) {
				String strDir;
				int index = arrFile[i].lastIndexOf(File.separator);
				if (index == -1) {
					continue;
				}
				strDir = arrFile[i].substring(0, index + 1);
				if (arrlist1.contains(strDir)) {
					continue;
				}
				strDir = strDir.replace('\\', '/');
				arrlist2.add(strDir);
			}

			// 创建临时Zip输出流
			ZipOutputStream jos = new ZipOutputStream(new FileOutputStream(
					strZipFile + ".tmp"));

			// 将目录插入到临时Zip文件
			for (int i = 0; i < arrlist2.size(); i++) {
				if (arrlist1.contains(arrlist2.get(i))) {
					ZipEntry je = new ZipEntry((String) arrlist2.get(i));
					jos.putNextEntry(je);
					jos.closeEntry();
				}
			}

			// 将文件列表中的文件插入到临时Zip文件中
			int byteRead;
			byte[] buffer = new byte[4096];
			for (int i = 0; i < arrFile.length; i++) {
				f = new File(arrFile[i]);
				if (!f.exists()) {
					return false;
				}
				FileInputStream fis = new FileInputStream(f);
				ZipEntry je = new ZipEntry(arrFile[i]);
				jos.putNextEntry(je);
				while ((byteRead = fis.read(buffer)) != -1) {
					jos.write(buffer, 0, byteRead);
				}
				fis.close();
				jos.closeEntry();
			}

			// 将原来Zip包中的文件压缩到临时Zip文件中
			jf = new ZipFile(new File(strZipFile));
			for (Enumeration enume = jf.entries(); enume.hasMoreElements();) {
				ZipEntry je = (ZipEntry) enume.nextElement();
				InputStream is = jf.getInputStream(je);
				jos.putNextEntry(je);
				while ((byteRead = is.read(buffer)) != -1) {
					jos.write(buffer, 0, byteRead);
				}
				is.close();
				jos.closeEntry();
			}
			jos.close();
			jf.close();

			// 删除原Zip文件，将临时Zip文件更名
			f = new File(strZipFile);
			if (f.exists()) {
				f.delete();
			}
			f = new File(strZipFile + ".tmp");
			f.renameTo(new File(strZipFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 解压缩Zip文件
	 * 
	 * @param strZipFile
	 *            Zip文件（包括路径）
	 * @param strTarget
	 *            解压后文件存放路径
	 * @return 是否成功
	 */
	@SuppressWarnings("unchecked")
	public static boolean decompress(String strZipFile, String strTarget) {
		try {

			int bytesRead;
			byte[] buffer = new byte[4096];

			ZipFile jf = new ZipFile(new File(strZipFile));
			Enumeration enume = jf.entries();
			while (enume.hasMoreElements()) {
				ZipEntry je = new ZipEntry((ZipEntry) enume.nextElement());
				String strFile = strTarget + File.separator + je.getName();

				if (File.separatorChar == '\\') {
					strFile = strFile.replace('/', '\\');
				} else {
					strFile = strFile.replace('\\', '/');
				}

				File f = new File(strFile);
				if (strFile.endsWith("/") || strFile.endsWith("\\")) {
					f.mkdirs();
					continue;
				} else {
					int index = strFile.lastIndexOf(File.separator);
					if (index != -1) {
						f = new File(strFile.substring(0, index + 1));
						f.mkdirs();
					}
				}

				try {
					FileOutputStream fos = new FileOutputStream(strFile);
					InputStream is = jf.getInputStream(je);

					while ((bytesRead = is.read(buffer)) != -1) {
						fos.write(buffer, 0, bytesRead);
					}
					is.close();
					fos.close();

				} catch (Exception ex) {
					ex.printStackTrace(System.out);
				}
			} // end while
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 解压缩文件并保存到指定文件.
	 * 
	 * <pre>
	 * boolean bln = decompressFile(&quot;e:\\test.zip&quot;, &quot;ttt1/ttt2.xml&quot;,
	 * 		&quot;e:\\temp\\ttt2.xml&quot;);
	 * </pre>
	 * 
	 * @param zipfile
	 *            要解压的zip文件
	 * @param entry
	 *            要解压的文件相对路径(在zip包中的路径)
	 * @param target
	 *            要保存到的文件绝对路径
	 * @return true-成功 false-失败
	 * @throws IOException
	 * 
	 * History 2003-07-22 add by kongxx
	 */
	public static boolean decompressFile(String zipfile, String entry,
			String target) throws IOException {

		InputStream is = decompressFile2Stream(zipfile, entry);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(target));

		int bytesRead;
		byte[] buffer = new byte[4096];

		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		is.close();
		os.close();
		return true;
	}

	/**
	 * 解压缩文件到输入流.
	 * 
	 * <pre>
	 * InputStream is = decompressFile(&quot;e:\\test.zip&quot;, &quot;ttt1/ttt2.xml&quot;,
	 * 		&quot;e:\\temp\\ttt2.xml&quot;);
	 * </pre>
	 * 
	 * @param zipfile
	 *            要解压的zip文件
	 * @param entry
	 *            要解压的文件相对路径(在zip包中的路径)
	 * @return 文件输出流
	 * @throws IOException
	 * 
	 * History 2003-07-22 add by kongxx
	 */
	public static InputStream decompressFile2Stream(String zipfile, String entry)
			throws IOException {
		InputStream is = null;
		ZipFile zf = new ZipFile(new File(zipfile));
		ZipEntry ze = new ZipEntry(entry);
		is = new BufferedInputStream(zf.getInputStream(ze));
		return is;
	}

	/**
	 * 获取指定目录下文件列表.
	 * 
	 * @param strDir
	 *            指定的目录
	 * @param strBaseDir
	 *            获取列表中文件的相对目录
	 * @return 目录下文件相对目录数组
	 */
	protected static String[] getFileList(String strDir, String strBaseDir) {
		String[] files = getFileList(strDir);
		int length = strBaseDir.length();

		/* History 2003-11-13 Added by zhenggz */
		/* 处理Windows平台的兼容性问题。 */
		if (File.separator.equals("\\")) {
			strBaseDir = strBaseDir.replaceAll("/", "\\\\");
		}
		/* End History 2003-11-13 Added by zhenggz */

		for (int i = 0; i < files.length; i++) {			
			int index = files[i].indexOf(strBaseDir);
			if (index != -1) {
				files[i] = files[i].substring(index + 1 + length);
			}
		}
		return files;
	}

	/**
	 * 获得指定目录下文件列表.
	 * 
	 * @param strDir
	 *            目录路径
	 * @return 目录下文件数组
	 */
	protected static String[] getFileList(String strDir) {
		ArrayList<String> arrlist = new ArrayList<String>();
		String[] tempA = strDir.split(",");		
		for(int k=0;k<tempA.length;k++)
		{
			File f = new File(tempA[k]);
			if (f.exists()) {
				File[] arrF = f.listFiles();
				for (int i = 0; i < arrF.length; i++) {
					if (arrF[i].isFile()) {
						arrlist.add(arrF[i].getAbsolutePath());
					} else {
						arrlist.add(arrF[i].getAbsolutePath());
						String[] arr = getFileList(arrF[i].getAbsolutePath());
						for (int j = 0; j < arr.length; j++) {
							arrlist.add(arr[j]);
						}
					} // end if
				} // end for
			} // end if
		}//end for
		return (String[]) arrlist.toArray(new String[arrlist.size()]);
	}
}
