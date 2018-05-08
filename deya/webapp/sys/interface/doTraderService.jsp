<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="com.deya.util.DateUtil,org.dom4j.Document,org.dom4j.DocumentException,org.dom4j.DocumentHelper,org.dom4j.Element"%>
<%@page import="java.io.*"%>
<%@page import="java.net.HttpURLConnection,java.net.URL,java.util.Iterator"%>
<%!
	private static String wsdlUrl = "http://10.190.5.17:7003/trader/services/TraderService?wsdl";
    private static String targetNamespace = "http://service.core.trader.yinhai.com/";
    private static String methodName = "doTrader";
    private static String paramName = "paramXml";
    private static String forgcode = "19ceb94366931d8e2017";
    private static String certcode = "3b0184454f8cb24147d7";
    private static String type = "";
    private static String txcode = "";
    private static String percode = "";
    private static String idcard = "";
    private static String proname = "";
    private static String depcode = "";
    private static String corpname = "";

%>
<%
    String validateCode = request.getParameter("validateCode");
    String sessionCode = (String)request.getSession().getAttribute("valiCode");
    String jsonStr = "";
    if(sessionCode != null && sessionCode.equals(validateCode))
    {
        type = request.getParameter("type"); //type=2 查找个人信息，为空的话 查找楼盘信息
        if(type != null && !"".equals(type) && "2".equals(type))
        {
            txcode = "1PMS001";
            percode = request.getParameter("percode");
            idcard = request.getParameter("idcard");
            proname = "";
            depcode = "";
            corpname = "";
        }
        else {
            txcode = "ELC001";
            percode = "";
            idcard = "";
            proname = request.getParameter("proname");
            depcode = request.getParameter("depcode");
            corpname = request.getParameter("corpname");
        }
        jsonStr = getResult();
    }
    else {
        jsonStr = "{\"status\":\"0\"}";
    }
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(jsonStr);
    response.getWriter().flush();
    response.getWriter().close();

%>
<%!
	public static String getSoapStr() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body>");
        _xmlstr.append("<ser:").append(methodName).append(">");
        _xmlstr.append("<").append(paramName).append(">");
        _xmlstr.append(getparamValue());
        _xmlstr.append("</").append(paramName).append(">");
        _xmlstr.append("</ser:").append(methodName).append(">");
        _xmlstr.append("</soapenv:Body> </soapenv:Envelope>");
        return _xmlstr.toString();
    }

    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<![CDATA[<data><txcode>" + txcode + "</txcode>");
        _xmlstr.append("<source>02</source>");
		_xmlstr.append("<torgcode>cdgjj</torgcode>");
		_xmlstr.append("<forgcode>" + forgcode + "</forgcode>");
		_xmlstr.append("<certcode>" + certcode + "</certcode>");
        _xmlstr.append("<txchannel>1</txchannel>");
        _xmlstr.append("<reqident>nt_ident1480583303863</reqident>");
        _xmlstr.append("<syspostcode>999999998</syspostcode>");
        _xmlstr.append("<usertype>2</usertype>");
        _xmlstr.append("<txtime>171541</txtime>");
        _xmlstr.append("<prepicnt1>1</prepicnt1>");
        _xmlstr.append("<enccode></enccode>");
        _xmlstr.append("<logintype>1</logintype>");
        _xmlstr.append("<txdate>" + DateUtil.getCurrentDateTime("yyyyMMdd") +"</txdate>");
        if(percode != null && !"".equals(percode))
        {
            _xmlstr.append("<percode>").append(percode).append("</percode>");
        }
        if(idcard != null && !"".equals(idcard))
        {
            _xmlstr.append("<idcard>").append(idcard).append("</idcard>");
        }
        if(proname != null && !"".equals(proname))
        {
            _xmlstr.append("<proname>").append(proname).append("</proname>");
		}
        if(depcode != null && !"".equals(depcode))
        {
            _xmlstr.append("<depcode>").append(depcode).append("</depcode>");
        }
        if(corpname != null && !"".equals(corpname))
        {
            _xmlstr.append("<corpname>").append(corpname).append("</corpname>");
        }
        _xmlstr.append("</data>]]>");
        return _xmlstr.toString();
    }

    public static String getResult()
    {
        String jsonStr = "{\"status\":\"0\"}";
        try {
            String s = doHttpPost();
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            if(type != null && !"".equals(type) && "2".equals(type)){
                Iterator personInfos = rootElement.elementIterator("list");
                String pername = "";
                String percode = "";
                String accstate = ""; //账户状态
                String payendmnh = ""; //缴至年月
                String accbal = "";    //账户余额
                if (personInfos != null && personInfos.hasNext()) {
                    Element personInfo = (Element) personInfos.next();
                    if(personInfo.element("pername") != null){
                        pername = personInfo.element("pername").getTextTrim();
                    }
                    if(personInfo.element("percode") != null){
                        percode = personInfo.element("percode").getTextTrim();
                    }
                    if(personInfo.element("accstate") != null){
                        accstate = personInfo.element("accstate").getTextTrim();
                    }
                    if(personInfo.element("payendmnh") != null){
                        payendmnh = personInfo.element("payendmnh").getTextTrim();
                    }
                    if(personInfo.element("accmny") != null){
                        accbal = personInfo.element("accmny").getTextTrim();
                    }
                    jsonStr = "{\"status\":\"1\",\"pername\":\""+pername+"\",\"percode\":\""+percode+"\"," +
                            "\"accstate\":\""+accstate+"\",\"payendmnh\":\""+payendmnh+"\",\"accbal\":\""+accbal+"\"}";
                }
            }else{
                Iterator houseInfos = rootElement.elementIterator("list");
                String proname = "";    //楼盘信息
                String corpname = "";   //开发商
                String pername = "";    //法人
                String perphone = "";   //联系电话
                String address = "";    //地址
                String depname = "";    //管理部
                if (houseInfos != null) {
                    jsonStr = "{\"status\":\"1\",\"result\":[";
                    boolean hasResult = false;
                    while (houseInfos.hasNext()){
                        hasResult = true;
                        Element houseInfo = (Element) houseInfos.next();
                        if(houseInfo.element("coophousesname") != null){
                            proname = houseInfo.element("coophousesname").getTextTrim();
                        }
                        if(houseInfo.element("corpname") != null){
                            corpname = houseInfo.element("corpname").getTextTrim();
                        }
                        if(houseInfo.element("pername") != null){
                            pername = houseInfo.element("pername").getTextTrim();
                        }
                        if(houseInfo.element("perphone") != null){
                            perphone = houseInfo.element("perphone").getTextTrim();
                        }
                        if(houseInfo.element("coophousesaddr") != null){
                            address = houseInfo.element("coophousesaddr").getTextTrim();
                        }
                        if(houseInfo.element("depcode") != null){
                            depname = houseInfo.element("depcode").getTextTrim();
                        }
                        jsonStr += "{\"proname\":\""+proname+"\",\"corpname\":\""+corpname+"\",\"pername\":\""+pername+"\"," +
                                "\"perphone\":\""+perphone+"\",\"address\":\""+address+"\",\"depname\":\""+depname+"\"},";
                    }
                    if(hasResult){
                        jsonStr = jsonStr.substring(0,jsonStr.length() - 1) + "]}";
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static String doHttpPost() {
        //服务的地址
        URL wsUrl = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            wsUrl = new URL(wsdlUrl);
            conn = (HttpURLConnection) wsUrl.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                os = conn.getOutputStream();
                //请求体
                String soap = getSoapStr();
                os.write(soap.getBytes());
                is = conn.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String s = sb.toString();
                s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                closeConnect(conn,is,os);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeConnect(conn,is,os);
        } finally {
            closeConnect(conn,is,os);
        }
        return null;
    }

    public static void closeConnect(HttpURLConnection conn,InputStream is,OutputStream os){
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
         catch (IOException e1) {
            e1.printStackTrace();
        }
    }
%>