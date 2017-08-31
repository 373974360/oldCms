package com.yinhai.webservice.client;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/22
 * Time: 11:28
 * Description: 用axis2调用.net发布的web services接口
 * Version: v1.0
 */

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

public class TestServiceClient {

    public static void main(String[] args) {

        String url = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";

        String action = "http://WebXml.com.cn/getWeatherbyCityName";

        String methodStr = "getWeatherbyCityName";

        String namespace = "http://WebXml.com.cn/";

        String tns = "xsd";

        String[] pars = {"theCityName"};

        String[] vals = {"杭州"};

        OMElement result = null;

        result = getWeather(url, action, methodStr, namespace, tns, pars, vals);

        System.out.println(result);

    }

    public static OMElement getWeather(String url, String action, String methodStr, String namespace, String tns, String[] pars, String[] vals) {

        OMElement result = null;

        try {

            ServiceClient client = new ServiceClient();

            client.setOptions(getClientOptions(url, action));

            result = client.sendReceive(getOMMethod(methodStr, namespace, tns, pars, vals));

        } catch (AxisFault e) {

            e.printStackTrace();

        }

        return result;

    }


    public static Options getClientOptions(String url, String action) {

        //端点引用 指接口位置

        EndpointReference targetEpr = new EndpointReference(url);

        //创建request soap包 请求选项

        Options options = new Options();

        //设置options的soapAction

        options.setAction(action);

        //设置request soap包的端点引用(接口地址)

        options.setTo(targetEpr);

        //如果报错提示Content-Length，请求内容长度

        options.setProperty(HTTPConstants.CHUNKED, "false");//把chunk关掉后，会自动加上Content-Length。

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

        }

        return method;

    }

}