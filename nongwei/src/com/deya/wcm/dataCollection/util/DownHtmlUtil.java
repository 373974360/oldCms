package com.deya.wcm.dataCollection.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownHtmlUtil {
	public static String[] UserAgent = {
			"Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.2",
			"Mozilla/5.0 (iPad; U; CPU OS 3_2_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B500 Safari/531.21.11",
			"Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18121",
			// http://blog.csdn.net/yjflinchong
			"Nokia5700AP23.01/SymbianOS/9.1 Series60/3.0",
			"UCWEB7.0.2.37/28/998", "NOKIA5700/UCWEB7.0.2.37/28/977",
			"Openwave/UCWEB7.0.2.37/28/978",
			"Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/989" };

	public static String downLoadHtml(String StrUrl, String Encode) {
		String htmlStr = "";
		String str = "";
		HttpURLConnection con = null;
		String cookie = "";

		label160: try {
			if (StrUrl.length() > 0) {
				URL url = new URL(StrUrl);
				int temp = Integer.parseInt(Math.round(Math.random()*7)+"");
				con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestProperty("Pragma", "no-cache");
				con.setRequestProperty("Cache-Control", "no-cache");
				con.setRequestProperty("User-Agent", UserAgent[temp]); // 模拟手机系统
				con.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");// 只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些
				con.setConnectTimeout(50000);
				
				if (cookie.length() != 0)
					con.setRequestProperty("Cookie", cookie);
				con.setInstanceFollowRedirects(false);
				int httpCode = con.getResponseCode();
				System.out.println(httpCode
						+ "**********httpCode********************");
				if (httpCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					cookie += con.getHeaderField("Set-Cookie") + ";";
					System.out.println(cookie
							+ "******************cookie************");
				}
				if (httpCode != 200)
					break label160;
				InputStream in = con.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						in, Encode));
				while ((str = rd.readLine()) != null) {
					htmlStr = htmlStr + str;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		return htmlStr;
	}

	public static String buildNewHtml(String strHtml) {
		String newHtml = "";
		if (FormatString.strIsNull(strHtml)) {
			newHtml = "<html><head></head><body>" + strHtml + "</body></html>";
		}
		return newHtml;
	}

	public static boolean writePicToLocal(String urlStr, String site_id,
			String picName) {
		boolean flag = false;

		HttpURLConnection con = null;
		String picMkdir = picName.substring(0, picName.lastIndexOf("/"));
		String pic_name = picName.substring(picName.lastIndexOf("/") + 1,
				picName.length());

		String picPath = FormatString.getArtPicUploadPath(site_id) + picMkdir;
		try {
			File file = new File(picPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();

			String savePath = picPath + File.separator + pic_name;

			int code = con.getResponseCode();

			if (code == 200) {
				BufferedInputStream bis = new BufferedInputStream(
						con.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(savePath));

				byte[] b = new byte[1024];
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					bos.write(b, 0, len);
				}
				bos.flush();
				bis.close();
				bos.close();
				flag = true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		return flag;
	}

	public static void main(String[] args) {
		String str = "/96838/1.jpg";
		String picMkdir = str.substring(0, str.lastIndexOf("/"));
		String pic_name = str.substring(str.lastIndexOf("/") + 1, str.length());
		System.out.println(picMkdir + "      " + pic_name);
		str = "/articleimgs/2014_9/96218";
		String s = str.substring(0, str.lastIndexOf("/"));
		System.out.println(s);
	}
}

/*
 * Location: C:\Users\li\Desktop\dataCollection.zip Qualified Name:
 * dataCollection.util.DownHtmlUtil JD-Core Version: 0.6.2
 */