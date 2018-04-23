<%@ page import="org.apache.http.HttpEntity" %>
<%@ page import="org.apache.http.client.entity.UrlEncodedFormEntity" %>
<%@ page import="org.apache.http.client.methods.CloseableHttpResponse" %>
<%@ page import="org.apache.http.client.methods.HttpPost" %>
<%@ page import="org.apache.http.impl.client.CloseableHttpClient" %>
<%@ page import="org.apache.http.impl.client.HttpClients" %>
<%@ page import="org.apache.http.message.BasicNameValuePair" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%
    String url = request.getParameter("url");
    String token = request.getParameter("token");
    String content = request.getParameter("correct_content");
    Map<String, String> params = new HashMap<String, String>();
    params.put("article", content);
    params.put("token", token);
    String result = basicPost(url, params);
    System.out.println(">>>>"+result.toString());
    JSONObject jsonObject = JSONObject.fromObject(result);
    if(jsonObject.get("text")!=null){
        String resultHtml = jsonObject.get("text")+"innerHtml";
        String html = "";
        String check = jsonObject.get("check").toString();
        if(check!=null&&check!=""){
            JSONArray jsonArray = JSONArray.fromObject(check);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonArrayObj = jsonArray.getJSONObject(i);
                    resultHtml += "<p><span style='color:red;'>"+jsonArrayObj.get("word")+"</span><span style='color:red;padding-left:20px;'>"+jsonArrayObj.get("description")+"</span></p>";
                }
            }
        }
        System.out.println(">>>>"+resultHtml);
        out.println(resultHtml);
    }else{
        out.println(jsonObject.get("text"));
    }

%>
<%!
    public static String basicPost(final String url, final Map<String, String> params) throws IOException {
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        String result = null;
        if (params != null && !params.isEmpty()) {
            List<org.apache.http.NameValuePair> pairs = new ArrayList<org.apache.http.NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "utf-8");
        }
        HttpPost httpPost = new HttpPost(url);
        if (urlEncodedFormEntity != null) {
            httpPost.setEntity(urlEncodedFormEntity);
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
        } else {
            httpPost.abort();
        }
        response.close();
        return result;
    }
%>
