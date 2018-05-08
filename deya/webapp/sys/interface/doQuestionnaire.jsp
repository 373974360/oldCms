<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="com.deya.util.DateUtil,org.dom4j.Document,org.dom4j.DocumentException,org.dom4j.DocumentHelper,org.dom4j.Element"%>
<%@page import="java.io.*"%>
<%@page import="java.net.HttpURLConnection,java.net.URL,java.util.Iterator"%>
<%!
	private static String wsdlUrl = "http://35.10.28.126:8007/trader/services/TraderService?wsdl";
    private static String targetNamespace = "http://service.core.trader.yinhai.com/";
    private static String methodName = "doTrader";
    private static String paramName = "paramXml";
    private static String forgcode = "19ceb94366931d8e2017";
    private static String certcode = "3b0184454f8cb24147d7";
    private static String torgcode = "5510gjj";
    private static String txcode = "";
    private static String type = "";
    private static String ywlsh = "";
    private static String title = "";
    private static String stime = "";
    private static String etime = "";
    private static String state = "";

%>
<%
    String jsonStr;
    type = request.getParameter("type"); //type=2 更新问卷状态
    if (type != null && !"".equals(type) && "2".equals(type)) {
        txcode = "NLC068";
        ywlsh = request.getParameter("ywlsh");
        state = request.getParameter("state");
    } else {
        txcode = "NLC067";
        title = request.getParameter("title");
        stime = request.getParameter("stime");
        etime = request.getParameter("etime");
        state = request.getParameter("state");
    }
    jsonStr = getResult();
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
        _xmlstr.append("<torgcode>" + torgcode + "</torgcode>");
        _xmlstr.append("<forgcode>" + forgcode + "</forgcode>");
        _xmlstr.append("<certcode>" + certcode + "</certcode>");
        _xmlstr.append("<txchannel>1</txchannel>");
        _xmlstr.append("<reqident>mh_" + DateUtil.getCurrentDateTime("yyyyMMddhhmmssSSS") + "</reqident>");
        if (ywlsh != null && !"".equals(ywlsh)) {
            _xmlstr.append("<ywlsh>").append(ywlsh).append("</ywlsh>");
        }
        if (title != null && !"".equals(title)) {
            _xmlstr.append("<title>").append(title).append("</title>");
        }
        if (stime != null && !"".equals(stime)) {
            _xmlstr.append("<stime>").append(stime).append("</stime>");
        }
        if (etime != null && !"".equals(etime)) {
            _xmlstr.append("<etime>").append(etime).append("</etime>");
        }
        if (state != null && !"".equals(state)) {
            _xmlstr.append("<state>").append(state).append("</state>");
        }
        _xmlstr.append("</data>]]>");
        return _xmlstr.toString();
    }

    public static String getResult() {
        String jsonStr = "{\"status\":\"0\"}";
        try {
            String s = doHttpPost();
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            if (type != null && !"".equals(type) && "2".equals(type)) {

            } else {
                Iterator personInfos = rootElement.elementIterator("list");
                String ywlsh = "";
                String question_title = "";
                String question_desc = "";
                String create_date = "";
                String creator = "";
                String start_date = "";
                String end_date = "";
                String question_file_name = "";
                String state = "";
                if (personInfos != null && personInfos.hasNext()) {
                    Element questionnaire = (Element) personInfos.next();
                    if (questionnaire.element("ywlsh") != null) {
                        ywlsh = questionnaire.element("ywlsh").getTextTrim();
                    }
                    if (questionnaire.element("question_title") != null) {
                        question_title = questionnaire.element("question_title").getTextTrim();
                    }
                    if (questionnaire.element("question_desc") != null) {
                        question_desc = questionnaire.element("question_desc").getTextTrim();
                    }
                    if (questionnaire.element("create_date") != null) {
                        create_date = questionnaire.element("create_date").getTextTrim();
                    }
                    if (questionnaire.element("creator") != null) {
                        creator = questionnaire.element("creator").getTextTrim();
                    }
                    if (questionnaire.element("start_date") != null) {
                        start_date = questionnaire.element("start_date").getTextTrim();
                    }
                    if (questionnaire.element("end_date") != null) {
                        end_date = questionnaire.element("end_date").getTextTrim();
                    }
                    if (questionnaire.element("question_file_name") != null) {
                        question_file_name = questionnaire.element("question_file_name").getTextTrim();
                    }
                    if (questionnaire.element("state") != null) {
                        state = questionnaire.element("state").getTextTrim();
                    }
                    jsonStr = "{\"ywlsh\":\"" + ywlsh + "\",\"question_title\":\"" + question_title + "\",\"question_desc\":\""
                            + question_desc + "\"," + "\"create_date\":\"" + create_date + "\",\"creator\":\"" + creator +
                            "\",\"start_date\":\"" + start_date + "\",\"end_date\":\"" + end_date
                            + "\",\"question_file_name\":\"" + question_file_name + "\",\"state\":\"" + state + "\"}";
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
                closeConnect(conn, is, os);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeConnect(conn, is, os);
        } finally {
            closeConnect(conn, is, os);
        }
        return null;
    }

    public static void closeConnect(HttpURLConnection conn, InputStream is, OutputStream os) {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
%>