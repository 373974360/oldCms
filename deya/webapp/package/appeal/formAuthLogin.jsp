<%@ page import="org.apache.oltu.oauth2.client.request.OAuthClientRequest" %>
<%@ page import="org.apache.oltu.oauth2.common.message.types.GrantType" %>
<%@ page import="org.apache.oltu.oauth2.client.OAuthClient" %>
<%@ page import="org.apache.oltu.oauth2.client.URLConnectionClient" %>
<%@ page import="org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse" %>
<%@ page import="org.apache.oltu.oauth2.common.OAuth" %>
<%@ page import="org.apache.oltu.oauth2.common.exception.OAuthProblemException" %>
<%@ page import="org.apache.oltu.oauth2.common.exception.OAuthSystemException" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	String model_id = request.getParameter("model_id");
	String tm_id = request.getParameter("tm_id");
	String cat_id = request.getParameter("cat_id");
	String token = getToken(request);
	String userStr = getUserInfo(token);
	Map mapObj = JSONObject.parseObject(userStr,Map.class);
	session.setAttribute("user",JSONObject.parseObject(mapObj.get("loginUser").toString(),Map.class));
	out.println("<script>");
	out.println("location.href='/info/iList.jsp?tm_id="+tm_id+"&cat_id="+cat_id+"&model_id="+model_id+"'");
	out.println("</script>");
%>
<%!

	public static String clientId = "000000069";
	public static String clientSecret = "649326746804f45958471419a982013f";
	public static String tokenUrl = "http://sfrz.shaanxi.gov.cn/sysauthserver/accessToken";
	public static String userInfoUrl = "http://sfrz.shaanxi.gov.cn/sysauthserver/userInfo?access_token=";


	public static String getToken(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
		OAuthClientRequest oauthClientRequest = OAuthClientRequest
				.tokenLocation(tokenUrl)
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				.setClientId(clientId)
				.setClientSecret(clientSecret)
				.setRedirectURI("http://"+request.getServerName()+"/appeal/formAuthLogin.jsp")
				.setCode(request.getParameter("code"))
				.buildQueryMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oauthClientRequest, OAuth.HttpMethod.POST);
		return oAuthResponse.getAccessToken();
	}

	public static String getUserInfo(String token){
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			// 创建远程url连接对象
			URL url = new URL(userInfoUrl + token);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("GET");
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
			return response.toString();
		} catch (Exception e) {
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