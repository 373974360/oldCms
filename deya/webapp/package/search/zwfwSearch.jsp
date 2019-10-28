<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	String result = getZwfwSearch(request);
	String callback = request.getParameter("callback");
	if(callback != null && !"".equals(callback)){
		out.println(callback + "(" + result + ")");
	}else{
		out.println(result);
	}
%>
<%!
	public static String getZwfwSearch(HttpServletRequest request){
		String searchUrl = "http://zw.xixianxinqu.gov.cn/icity/c/api.server/execute?action=getSearchIndex&pagemode=serverV3P5&region_code=617000&key=" + request.getParameter("key");
		System.out.println(searchUrl);
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			// 创建远程url连接对象
			URL url = new URL(searchUrl);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
			// 发送请求
			connection.connect();
			InputStream in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in)); //将字节流转化成字符流
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = br.readLine())!= null) {
				response.append(line);
			}
			return response.toString();} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			connection.disconnect();// 关闭远程连接
		}
		return "connect timed out";
	}
%>
