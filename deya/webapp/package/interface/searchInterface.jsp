<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="org.jdom.Document,org.jdom.Element,org.jdom.JDOMException" %>
<%@page import="org.jdom.input.SAXBuilder,org.xml.sax.InputSource,javax.servlet.ServletException,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.io.IOException"%>
<%@page import="java.io.InputStream,java.io.OutputStream,java.io.StringReader,java.net.HttpURLConnection"%>
<%@page import="java.net.URL,java.util.ArrayList,java.util.List"%>
<%!
	private static String wsdlUrl = "http://172.18.1.252:7005/trader/services/TraderService?wsdl";
    private static String targetNamespace = "http://service.core.trader.yinhai.com/";
    private static String txcode = "";

    //查询个人信息
    private static String wzcxmm = "";
	private static String pername = "";
	private static String percode = "";

    //重置密码
    private static String zjhm = "";
    private static String xingming = "";

    //验证旧密码
    private static String pwd = "";

    //查询合作楼盘
    private static String pertype = "";
    private static String proname = "";
    private static String depcode = "";
    private static String corpname = "";

%>
<%
	String method = request.getParameter("method");
	//查询个人信息
	if(method != null && "grxx".equals(method))
	{
		getPersonInfo(request,response);
	}
	//重置密码
	if(method != null && "resetPwd".equals(method)){
		resetPwd(request,response);
	}
	//验证旧密码是否正确
	if(method != null && "validateOldPwd".equals(method)){
		validateOldPwd(request,response);
	}
	//修改密码
	if(method != null && "changePwd".equals(method)){
		changePwd(request,response);
	}
	//合作楼盘信息
	if(method != null && "lpxx".equals(method)){
		getHouseInfo(request,response);
	}

%>
<%!

    public static void resetFiled(){
        txcode = "";
        wzcxmm = "";
        pername = "";
        percode = "";
        zjhm = "";
        xingming = "";
        pwd = "";
        pertype = "";
        proname = "";
        depcode = "";
        corpname = "";
    }

    public static void resetPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
		    resetFiled();
            txcode = "PMC003";
			zjhm = request.getParameter("zjhm");
            xingming = request.getParameter("xingming");
            String s = getResultStr();
            System.out.println(s);
	        String jsonStr = getresetPwdResult(s);
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
            response.getWriter().flush();
            response.getWriter().close();
		}
	}

	public static String getresetPwdResult(String xmlDoc)
    {
        String json_str;
        List<Element> elementList = getElementList(xmlDoc);
        String sjhm = "";
        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("sjhm".equals(xet.getName()))
            {
                 sjhm = String.valueOf(xet.getContent());
            }
        }
        if(sjhm != null && !"".equals(sjhm)){

            json_str = "{\"status\":\"1\",\"sjhm\":\""+sjhm+"\"}";
        }else{
            json_str = "{\"status\":\"0\"}";
        }
        return json_str;
    }

    public static void validateOldPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
		    resetFiled();
            txcode = "PMC004";
			zjhm = request.getParameter("zjhm");
            pwd = request.getParameter("pwd");
            String s = getResultStr();
	        String jsonStr = getvalidateOldPwdResult(s);
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
            response.getWriter().flush();
            response.getWriter().close();
		}
	}

	public static String getvalidateOldPwdResult(String xmlDoc)
    {
        String json_str;
        List<Element> elementList = getElementList(xmlDoc);
        String isok = "";
        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("isok".equals(xet.getName()))
            {
                 isok = String.valueOf(xet.getContent());
            }
        }
        if(isok != null && !"".equals(isok)){

            json_str = "{\"status\":\"1\",\"isok\":\""+isok+"\"}";
        }else{
            json_str = "{\"status\":\"0\"}";
        }
        return json_str;
    }

    public static void changePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
		    resetFiled();
            txcode = "PMC005";
			xingming = request.getParameter("xingming");
            zjhm = request.getParameter("zjhm");
            wzcxmm = request.getParameter("wzcxmm");
            String s = getResultStr();
            System.out.println(s);
	        String jsonStr = getchangePwdResult(s);
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
            response.getWriter().flush();
            response.getWriter().close();
		}
	}

	public static String getchangePwdResult(String xmlDoc)
    {
        String json_str;
        List<Element> elementList = getElementList(xmlDoc);
        String isok = "";
        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("isok".equals(xet.getName()))
            {
                 isok = String.valueOf(xet.getContent());
            }
        }
        if(isok != null && !"".equals(isok)){

            json_str = "{\"status\":\"1\",\"isok\":\""+isok+"\"}";
        }else{
            json_str = "{\"status\":\"0\"}";
        }
        return json_str;
    }

    public static void getPersonInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
		    resetFiled();
            txcode = "PMC006";
			pername = request.getParameter("pername");
            percode = request.getParameter("percode");
            wzcxmm = request.getParameter("wzcxmm");
            pertype = "01";
            String s = getResultStr();
            System.out.println(s);
	        String jsonStr = getPersonResult(s);
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
            response.getWriter().flush();
            response.getWriter().close();
		}
	}

	public static String getPersonResult(String xmlDoc)
    {
        String json_str;
        List<Element> elementList = getElementList(xmlDoc);
        Element et = null;
        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("list".equals(xet.getName()))
            {
                et = elementList.get(j);
            }
        }
        elementList = et.getChildren();
        String pernameResult = "";
        String percodeResult = "";
        String accstate = ""; //账户状态
        String payendmnh = ""; //缴至年月
        String accbal = "";    //账户余额
        String corpcode = "";   //单位账号
        String perdepmny = "";  //月缴存额

        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("pername".equals(xet.getName()))
            {
                pernameResult = String.valueOf(xet.getContent());
            }
            if("percode".equals(xet.getName()))
            {
                percodeResult = String.valueOf(xet.getContent());
            }
            if("accstate".equals(xet.getName()))
            {
                accstate = String.valueOf(xet.getContent());
            }
            if("payendmnh".equals(xet.getName()))
            {
                payendmnh = String.valueOf(xet.getContent());
            }
            if("accbal".equals(xet.getName()))
            {
                accbal = String.valueOf(xet.getContent());
            }
            if("corpcode".equals(xet.getName()))
            {
                corpcode = String.valueOf(xet.getContent());
            }
            if("perdepmny".equals(xet.getName()))
            {
                perdepmny = String.valueOf(xet.getContent());
            }
        }
        System.out.println("++++++"+pername+"+++++++++"+pername.replace("[[Text: ","").replace("]]",""));
        if(pername != null && pername.equals(pernameResult.replace("[[Text: ","").replace("]]",""))){

            json_str = "{\"status\":\"1\",\"pername\":\""+pernameResult+"\",\"percode\":\""+percodeResult+"\",\"accstate\":\""+accstate+"\",\"payendmnh\":\""+payendmnh+"\",\"corpcode\":\""+corpcode+"\",\"perdepmny\":\""+perdepmny+"\",\"accbal\":\""+accbal+"\"}";
        }else{
            json_str = "{\"status\":\"2\"}";
        }
        return json_str;
    }

	public static void getHouseInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
		    resetFiled();
            txcode = "ELC0001";
            proname = request.getParameter("proname");
            depcode = request.getParameter("depcode");
            corpname = request.getParameter("corpname");
            String validateCode = request.getParameter("validateCode");
            String sessionCode = (String)request.getSession().getAttribute("valiCode");
            String jsonStr;
            if(sessionCode != null) {
                if (sessionCode.equals(validateCode)) {
                    String s = getResultStr();
                    System.out.println(s);
                    jsonStr = getHouseResult(s);
                }else {
                    jsonStr = "{\"status\":\"0\"}";
                }
            }else {
                jsonStr = "{\"status\":\"0\"}";
            }
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
            response.getWriter().flush();
            response.getWriter().close();
		}
    }

    public static String getHouseResult(String xmlDoc)
    {
        String json_str;
        List<Element> elementList = getElementList(xmlDoc);
        List<Element> etList = new ArrayList<>();
        for(int j=0;j<elementList.size();j++){
            Element xet = elementList.get(j);
            if("list".equals(xet.getName()))
            {
                Element et = elementList.get(j);
                etList.add(et);
            }
        }
        json_str = "{\"status\":\"1\",\"result\":[";
        if(etList.size() > 0)
        {
            for(Element e : etList)
            {
                elementList = e.getChildren();
                String proname = "";    //楼盘信息
                String corpname = "";   //开发商
                String xingming = "";    //法人
                String perphone = "";   //联系电话
                String address = "";    //地址
                String depname = "";    //管理部

                for(int j=0;j<elementList.size();j++){
                    Element xet = elementList.get(j);
                    if("proname".equals(xet.getName()))
                    {
                        proname = String.valueOf(xet.getContent());
                    }
                    if("corpname".equals(xet.getName()))
                    {
                        corpname = String.valueOf(xet.getContent());
                    }
                    if("xingming".equals(xet.getName()))
                    {
                        xingming = String.valueOf(xet.getContent());
                    }
                    if("perphone".equals(xet.getName()))
                    {
                        perphone = String.valueOf(xet.getContent());
                    }
                    if("address".equals(xet.getName()))
                    {
                        address = String.valueOf(xet.getContent());
                    }
                    if("depname".equals(xet.getName()))
                    {
                        depname = String.valueOf(xet.getContent());
                    }
                }
                json_str += "{\"proname\":\""+proname+"\",\"corpname\":\""+corpname+"\",\"xingming\":\""+xingming+"\",\"perphone\":\""+perphone+"\",\"address\":\""+address+"\",\"depname\":\""+depname+"\"},";
            }
            json_str = json_str.substring(0,json_str.length() - 1);
        }
        json_str += "]}";

        return json_str;
    }

    public static String getSoapStr() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body><ser:doTrader><paramXml>");
        _xmlstr.append("<![CDATA[<data><txcode>").append(txcode).append("</txcode>");
        _xmlstr.append("<source>04</source>");
		_xmlstr.append("<torgcode>5510gjj</torgcode>");
		_xmlstr.append("<forgcode>19ceb94366931d8e2017</forgcode>");
        _xmlstr.append("<certcode>3b0184454f8cb24147d7</certcode>");
        _xmlstr.append("<txchannel>1</txchannel>");
        _xmlstr.append("<reqident>19274635</reqident>");
        if(pername != null && !"".equals(pername))
        {
            _xmlstr.append("<pername>").append(pername).append("</pername>");
        }
        if(percode != null && !"".equals(percode))
        {
            _xmlstr.append("<percode>").append(percode).append("</percode>");
        }
        if(wzcxmm != null && !"".equals(wzcxmm))
        {
            _xmlstr.append("<wzcxmm>").append(wzcxmm).append("</wzcxmm>");
        }
        if(zjhm != null && !"".equals(zjhm))
        {
            _xmlstr.append("<zjhm>").append(zjhm).append("</zjhm>");
        }
        if(xingming != null && !"".equals(xingming))
        {
            _xmlstr.append("<xingming>").append(xingming).append("</xingming>");
        }
        if(pwd != null && !"".equals(pwd))
        {
            _xmlstr.append("<pwd>").append(pwd).append("</pwd>");
        }
        if(pertype != null && !"".equals(pertype))
        {
            _xmlstr.append("<pertype>").append(pertype).append("</pertype>");
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
        _xmlstr.append("</paramXml></ser:doTrader> </soapenv:Body> </soapenv:Envelope>");

        return _xmlstr.toString();
    }

    public static String getResultStr() throws ServletException, IOException{
        //服务的地址
		URL wsUrl = new URL(wsdlUrl);
		HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		String s = "";
		try {
            conn = (HttpURLConnection) wsUrl.openConnection();
            if(conn != null){
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                os = conn.getOutputStream();
                //请求体
                String soap = getSoapStr();
                System.out.println(soap);
                os.write(soap.getBytes());
                is = conn.getInputStream();
                byte[] b = new byte[1024];
                int len = 0;

                while((len = is.read(b)) != -1){
                    String ss = new String(b,0,len,"UTF-8");
                    s += ss;
                }
                s = s.replaceAll("&lt;","<").replaceAll("&gt;",">");
                if(is != null){is.close();}
                if(os != null){os.close();}
                if(conn != null){conn.disconnect();}
            }
		} catch (Exception e) {
		    e.printStackTrace();
			if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
		} finally{
		    if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
		}
		return s;
    }


    public static List<Element> getElementList(String xmlDoc){
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        List<Element> jiedian = new ArrayList<>();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Element et = jiedian.get(0);
            jiedian = et.getChildren();
            et = jiedian.get(0);
            jiedian = et.getChildren();
            et = jiedian.get(0);
            jiedian = et.getChildren();
            et = jiedian.get(0);
            jiedian = et.getChildren();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jiedian;
    }
%>
