package com.webService;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


public class CsgjjSearchTest{

    public static void main(String[] args) {
        try{
            //服务的地址
            URL wsUrl = new URL("http://wx.csgjj.com.cn/dsbcs/services/RouterService?wsdl");
            String targetNamespace = "http://service.cs.yinhai.com/";

            String percode = "";
            String validateCode = "";
            String jsonStr = "";
            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

            OutputStream os = conn.getOutputStream();

            //请求体
            String soap = getSoapStr(targetNamespace,percode);

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
            //System.out.println(s);
            jsonStr = getResult(s);
            //System.out.println(jsonStr);
            is.close();
            os.close();
            conn.disconnect();
        }catch (Exception e){

        }
    }

    public static String getSoapStr(String targetNamespace,String percode) {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body><ser:doProtal><reqXml>");
        _xmlstr.append("<![CDATA[<data> <txcode><![CDATA[ELC0001]]]]>><![CDATA[</txcode>");
        _xmlstr.append("<source><![CDATA[04]]]]>><![CDATA[</source>");
        _xmlstr.append("<certcode><![CDATA[cG9ydGFsdGVzdDExMQ==]]]]>><![CDATA[</certcode>");
        //_xmlstr.append("<year><![CDATA[QY1857]]]]>><![CDATA[</year>");
        //_xmlstr.append("<procode><![CDATA[QY1857]]]]>><![CDATA[</procode>");
        //_xmlstr.append("<proname><![CDATA[时代]]]]>><![CDATA[</proname>");
        _xmlstr.append("<depcode><![CDATA[01060000]]]]>><![CDATA[</depcode>");
        //_xmlstr.append("<corpcode><![CDATA[QY1857]]]]>><![CDATA[</corpcode>");
        _xmlstr.append("<corpname><![CDATA[长沙鑫霖]]]]>><![CDATA[</corpname>");
        _xmlstr.append("]]>><![CDATA[</data>]]>");
        _xmlstr.append("</reqXml></ser:doProtal> </soapenv:Body> </soapenv:Envelope>");
        /*  depcode
        *   管理部代码	管理部名称
            01030000	天心区管理部
            01040000	岳麓区管理部
            01050000	开福区管理部
            01020000	芙蓉区管理部
            01060000	雨花区管理部
            01070000	望城区管理部
            01080000	长沙县管理部
            01090000	浏阳市管理部
            01100000	宁乡县管理部
            02010000	长铁本部
            02020000	怀化管理部
        * */
        return _xmlstr.toString();
    }

    public static String getResult(String xmlDoc)
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
            json_str = "{\"status\":\"1\",\"result\":\"[";
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