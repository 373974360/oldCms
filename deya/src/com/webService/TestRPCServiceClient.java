package com.webService;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/22
 * Time: 11:28
 * Description: 用axis2调用.net发布的web services接口
 * Version: v1.0
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TestRPCServiceClient {

    public static void main(String[] args) throws AxisFault {

        String url = "http://fy.webxml.com.cn/webservices/EnglishChinese.asmx?WSDL";

        String action = "http://WebXml.com.cn/TranslatorString";

        String methodStr = "TranslatorString";

        String namespace = "http://WebXml.com.cn/";

        String tns = "";

        String[] pars = {"wordKey"};

        String[] vals = {"随便"};

        OMElement result = null;

        result = translatorString(url, action, methodStr, namespace, tns, pars, vals);

        System.out.println(getResults(result));

        //System.out.println(result);
    }

    public static OMElement translatorString(String url, String action, String methodStr, String namespace, String tns, String[] pars, String[] vals) {

        OMElement result = null;

        try {

            RPCServiceClient serviceClient = new RPCServiceClient();

            serviceClient.setOptions(getClientOptions(url, action));

            result = serviceClient.sendReceive(getOMMethod(methodStr, namespace, tns, pars, vals));

        } catch (AxisFault e) {

            e.printStackTrace();

        }

        return result;

    }

    public static Options getClientOptions(String url, String action) {

        //有抽象OM工厂获取OM工厂，创建request SOAP包

        EndpointReference targetEpr = new EndpointReference(url);

        //创建request soap包 请求选项

        Options options = new Options();

        //设置options的soapAction

        options.setAction(action);

        //设置request soap包的端点引用(接口地址)

        options.setTo(targetEpr);

        //设置超时时间

        options.setTimeOutInMilliSeconds(6000000000L);

        //传输协议

        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

        return options;

    }

    public static OMElement getOMMethod(String methodStr, String namespace, String tns, String[] pars, String[] vals) {

        //有抽象OM工厂获取OM工厂，创建request SOAP包

        OMFactory fac = OMAbstractFactory.getOMFactory();

        //创建命名空间

        OMNamespace nms = fac.createOMNamespace(namespace, tns);

        //创建OMElement方法 元素，并指定其在nms指代的名称空间中

        OMElement method = fac.createOMElement(methodStr, nms);

        //添加方法参数名和参数值

        for (int i = 0; i < pars.length; i++) {

            //创建方法参数OMElement元素

            OMElement param = fac.createOMElement(pars[i], nms);

            //设置键值对 参数值

            param.setText(vals[i]);

            //讲方法元素 添加到method方法元素中

            method.addChild(param);

            method.build();

        }

        return method;

    }

    //解析XML，将获取到的数据封装到list中

    public static List<String> getResults(OMElement element) {

        if (element == null) {

            return null;

        }

        Iterator iterator = element.getChildElements();

        Iterator innerItr;

        List<String> list = new ArrayList<String>();

        OMElement result = null;

        while (iterator.hasNext()) {

            result = (OMElement) iterator.next();

            innerItr = result.getChildElements();

            while (innerItr.hasNext()) {

                OMElement result2 = (OMElement) innerItr.next();

                if (result2 != null) {

                    String text = result2.getText();

                    if (text != null && !("").equals(text)) {

                        list.add(text);

                    }
                }
            }
        }
        return list;
    }
}