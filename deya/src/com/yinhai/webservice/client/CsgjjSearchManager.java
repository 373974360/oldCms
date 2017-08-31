package com.yinhai.webservice.client;

import com.deya.util.Encode;
import com.deya.wcm.dataCollection.util.FormatString;
import com.deya.wcm.services.org.user.SessionManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description:
 * Version: v1.0
 */


public class CsgjjSearchManager extends HttpServlet {

    private static String wsdlUrl = "http://172.18.2.3/dsbcs/services/RouterService?wsdl";
    private static String targetNamespace = "http://service.cs.yinhai.com/";
    private static String txcode = "";
    private static String percode = "";
    private static String pertype = "";
    private static String proname = "";
    private static String depcode = "";
    private static String corpname = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getParameter("method");
        if(method != null && !"".equals(method) && "2".equals(method))
        {
            getHouseInfo(request,response);
        }
        else {
            getPersonInfo(request,response);
        }
    }

    public static void getPersonInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //服务的地址
        URL wsUrl = new URL(wsdlUrl);
        txcode = "PMC0002";
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
            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

            OutputStream os = conn.getOutputStream();

            //请求体
            String soap = getSoapStr();

            os.write(soap.getBytes());

            InputStream is = conn.getInputStream();

            byte[] b = new byte[1024];
            int len = 0;
            String s = "";
            while((len = is.read(b)) != -1){
                String ss = new String(b,0,len,"UTF-8");
                s += ss;
            }
            s = s.replaceAll("&lt;","<").replaceAll("&gt;",">");
            jsonStr = getPersonResult(s);
            is.close();
            os.close();
            conn.disconnect();
        }
        else {
            jsonStr = "{\"status\":\"0\"}";
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonStr);
    }

    public static void getHouseInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //服务的地址
        //request.setCharacterEncoding("UTF-8");
        URL wsUrl = new URL(wsdlUrl);
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
                HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

                OutputStream os = conn.getOutputStream();

                //请求体
                String soap = getSoapStr();
                //System.out.println(soap);
                os.write(soap.getBytes());

                InputStream is = conn.getInputStream();

                byte[] b = new byte[1024];
                int len = 0;
                String s = "";
                while ((len = is.read(b)) != -1) {
                    String ss = new String(b, 0, len, "UTF-8");
                    s += ss;
                }
                s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                jsonStr = getHouseResult(s);
                is.close();
                os.close();
                conn.disconnect();
            } else {
                jsonStr = "{\"status\":\"0\"}";
            }
        }
        else {
            jsonStr = "{\"status\":\"0\"}";
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonStr);
    }

    public static String getSoapStr() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body><ser:doProtal><reqXml>");
        _xmlstr.append("<![CDATA[<data> <txcode><![CDATA[").append(txcode).append("]]]]>><![CDATA[</txcode>");
        _xmlstr.append("<source><![CDATA[04]]]]>><![CDATA[</source>");
        _xmlstr.append("<certcode><![CDATA[cG9ydGFsdGVzdDExMQ==]]]]>><![CDATA[</certcode>");
        if(percode != null && !"".equals(percode))
        {
            _xmlstr.append("<percode><![CDATA[").append(percode).append("]]]]>><![CDATA[</percode>");
        }
        if(pertype != null && !"".equals(pertype))
        {
            _xmlstr.append("<idcard><![CDATA[").append(pertype).append("]]]]>><![CDATA[</idcard>");
        }
        if(proname != null && !"".equals(proname))
        {
            _xmlstr.append("<proname><![CDATA[").append(proname).append("]]]]>><![CDATA[</proname>");
        }
        if(depcode != null && !"".equals(depcode))
        {
            _xmlstr.append("<depcode><![CDATA[").append(depcode).append("]]]]>><![CDATA[</depcode>");
        }
        if(corpname != null && !"".equals(corpname))
        {
            _xmlstr.append("<corpname><![CDATA[").append(corpname).append("]]]]>><![CDATA[</corpname>");
        }
        _xmlstr.append("</data>]]>");
        _xmlstr.append("</reqXml></ser:doProtal> </soapenv:Body> </soapenv:Envelope>");

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
}