<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="org.jdom.Document,org.jdom.Element,org.jdom.JDOMException" %>
<%@page import="org.jdom.input.SAXBuilder,org.xml.sax.InputSource,javax.servlet.ServletException,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.io.IOException"%>
<%@page import="java.io.InputStream,java.io.OutputStream,java.io.StringReader,java.net.HttpURLConnection"%>
<%@page import="java.net.URL,java.util.ArrayList,java.util.List"%>
<%!
	private static String wsdlUrl = "http://172.18.1.252:7005/trader/services/TraderService?wsdl";
    private static String targetNamespace = "http://service.core.trader.yinhai.com/";
    private static String txcode = "";
    private static String percode = "";
    private static String pertype = "";
    private static String proname = "";
    private static String depcode = "";
    private static String corpname = "";

%>
<%
	String method = request.getParameter("method");
	if(method != null && !"".equals(method) && "2".equals(method))
	{
		getHouseInfo(request,response);
	}
	else {
		getPersonInfo(request,response);
	}
%>
<%!
	public static void getPersonInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//服务的地址
		URL wsUrl = new URL(wsdlUrl);
		HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		try {
            txcode = "pmc002";
            percode = request.getParameter("percode");
            pertype = "01";
            proname = "";
            depcode = "";
            corpname = "";
            String validateCode = request.getParameter("validateCode");
            String sessionCode = (String)request.getSession().getAttribute("valiCode");
            String jsonStr = "";
            if(sessionCode != null && sessionCode.equals(validateCode))
            {
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

					//ystem.out.println("********soap******" + soap);

					os.write(soap.getBytes());

					is = conn.getInputStream();

					byte[] b = new byte[1024];
					int len = 0;
					String s = "";
					while((len = is.read(b)) != -1){
						String ss = new String(b,0,len,"UTF-8");
						s += ss;
					}
					s = s.replaceAll("&lt;","<").replaceAll("&gt;",">");
					//System.out.println("**********ssssssss*****************"+s);
					jsonStr = getPersonResult(s);
					if(is != null){is.close();}
					if(os != null){os.close();}
					if(conn != null){conn.disconnect();}
				}
            }
            else {
                jsonStr = "{\"status\":\"0\"}";
            }
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
		} catch (Exception e) {
		    e.printStackTrace();
			if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
		    if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
            response.getWriter().flush();
            response.getWriter().close();
		}

	}

	public static void getHouseInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //服务的地址
        //request.setCharacterEncoding("UTF-8");
        URL wsUrl = new URL(wsdlUrl);
        HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		try {
            txcode = "ELC0001";
            percode = "";
            pertype = "";
            proname = request.getParameter("proname");
            depcode = request.getParameter("depcode");
            corpname = request.getParameter("corpname");
            String validateCode = request.getParameter("validateCode");
            String sessionCode = (String)request.getSession().getAttribute("valiCode");
            String jsonStr = "";
            if(sessionCode != null) {
                if (sessionCode.equals(validateCode)) {
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
						os.write(soap.getBytes());

						is = conn.getInputStream();

						byte[] b = new byte[1024];
						int len = 0;
						String s = "";
						while ((len = is.read(b)) != -1) {
							String ss = new String(b, 0, len, "UTF-8");
							s += ss;
						}
						s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
						jsonStr = getHouseResult(s);
						if(is != null){is.close();}
						if(os != null){os.close();}
						if(conn != null){conn.disconnect();}
					}
                } else {
                    jsonStr = "{\"status\":\"0\"}";
                }
            }
            else {
                jsonStr = "{\"status\":\"0\"}";
            }
            jsonStr = jsonStr.replaceAll("�","").replaceAll("\\\\"," ");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonStr);
		} catch (Exception e) {
		    e.printStackTrace();
		    if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
            response.getWriter().flush();
            response.getWriter().close();
		} finally{
		    if(is != null){is.close();}
		    if(os != null){os.close();}
            if(conn != null){conn.disconnect();}
            response.getWriter().flush();
            response.getWriter().close();
		}
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
        if(percode != null && !"".equals(percode))
        {
            _xmlstr.append("<percode>").append(percode).append("</percode>");
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

    public static String getPersonResult(String xmlDoc)
    {
        String json_str = "";
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Element et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            for(int j=0;j<jiedian.size();j++){
                Element xet = (Element) jiedian.get(j);
                if("list".equals(xet.getName()))
                {
                    et = (Element) jiedian.get(j);
                }
            }
            jiedian = et.getChildren();
            String pername = "";
            String percode = "";
            String accstate = ""; //账户状态
            String payendmnh = ""; //缴至年月
            String accbal = "";    //账户余额

            for(int j=0;j<jiedian.size();j++){
                Element xet = (Element) jiedian.get(j);
                if("pername".equals(xet.getName()))
                {
                    pername = String.valueOf(xet.getContent());
                }
                if("percode".equals(xet.getName()))
                {
                    percode = String.valueOf(xet.getContent());
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
            }

            json_str = "{\"status\":\"1\",\"pername\":\""+pername+"\",\"percode\":\""+percode+"\",\"accstate\":\""+accstate+"\",\"payendmnh\":\""+payendmnh+"\",\"accbal\":\""+accbal+"\"}";
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json_str;
    }

    public static String getHouseResult(String xmlDoc)
    {
        String json_str = "";
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        ArrayList<Element> etList = new ArrayList<Element>();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Element et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            et = (Element) jiedian.get(0);
            jiedian = et.getChildren();
            for(int j=0;j<jiedian.size();j++){
                Element xet = (Element) jiedian.get(j);
                if("list".equals(xet.getName()))
                {
                    et = (Element) jiedian.get(j);
                    etList.add(et);
                }
            }
            json_str = "{\"status\":\"1\",\"result\":[";
            if(etList.size() > 0)
            {
                for(Element e : etList)
                {
                    jiedian = e.getChildren();
                    String proname = "";    //楼盘信息
                    String corpname = "";   //开发商
                    String pername = "";    //法人
                    String perphone = "";   //联系电话
                    String address = "";    //地址
                    String depname = "";    //管理部

                    for(int j=0;j<jiedian.size();j++){
                        Element xet = (Element) jiedian.get(j);
                        if("proname".equals(xet.getName()))
                        {
                            proname = String.valueOf(xet.getContent());
                        }
                        if("corpname".equals(xet.getName()))
                        {
                            corpname = String.valueOf(xet.getContent());
                        }
                        if("pername".equals(xet.getName()))
                        {
                            pername = String.valueOf(xet.getContent());
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
                    json_str += "{\"proname\":\""+proname+"\",\"corpname\":\""+corpname+"\",\"pername\":\""+pername+"\",\"perphone\":\""+perphone+"\",\"address\":\""+address+"\",\"depname\":\""+depname+"\"},";
                }
                json_str = json_str.substring(0,json_str.length() - 1);
            }
            json_str += "]}";
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json_str;
    }

%>