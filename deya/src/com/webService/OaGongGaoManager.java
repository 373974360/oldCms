package com.webService;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description:
 * Version: v1.0
 */


import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OaGongGaoManager {

    public static void getOaData()
    {
        String url = "http://webserviceiis.xayc.cbpm:7010/OA.asmx";

        String action = "http://tempuri.org/GetOAContractByName";

        String methodStr = "GetOAContractByName";

        String namespace = "http://tempuri.org/";

        String tns = "";

        String[] pars = {"ViewName","ODBCName"};

        String[] vals = {"vwAllBoardDocWeb","LOTUSGONGGAO"};

        OMElement result = null;

        result = getOAContractByName(url,action, methodStr, namespace, tns, pars, vals);

        List<OaGongGaoBean> list = getResults(result);

        if(list != null && list.size() > 0)
        {
            System.out.println("获取OA信息总条数：" + list.size());
            for(OaGongGaoBean ggbean : list)
            {
                if(ggbean.getIsNew() == 1) {
                    if (ggbean.getUrl() != null && !"".equals(ggbean.getUrl())) {
                        if (!getInfoByUrl(ggbean.getUrl()) && ggbean.getTitle() != null && !"".equals(ggbean.getTitle())) {
                            GKInfoBean ifb = new GKInfoBean();
                            ifb.setInput_user(1);
                            ifb.setInfo_status(8);
                            ifb.setModel_id(12);
                            ifb.setApp_id("cms");
                            ifb.setSite_id("CMSxayc");
                            int id = InfoBaseManager.getNextInfoId();
                            ifb.setId(id);
                            ifb.setInfo_id(id);
                            ifb.setCat_id(10030);
                            ifb.setTitle(ggbean.getTitle());
                            ifb.setSource(ggbean.getDeptName());
                            ifb.setReleased_dtime(ggbean.getPublishTime());
                            ifb.setInfo_level("C");
                            ifb.setIsIpLimit("0");
                            ifb.setWeight(60);
                            ifb.setContent_url("http://oa.xayc.cbpm" + ggbean.getUrl());

                            if (ModelUtil.insert(ifb, "link", null)) {
                                System.out.print("*******************OA公告添加成功***************");
                            } else {
                                System.out.print("*******************OA公告添加失败***************");
                            }
                        }
                    }
                }
            }
        }
    }

    public static OMElement getOAContractByName(String url,String action,String methodStr,String namespace,String tns,String[] pars,String[] vals){

        OMElement result = null;

        try {

            RPCServiceClient serviceClient = new RPCServiceClient();

            serviceClient.setOptions(getClientOptions(url,action));

            result = serviceClient.sendReceive(getOMMethod(methodStr,namespace,tns,pars,vals));

        } catch (AxisFault e) {

            e.printStackTrace();

        }

        return result;

    }

    public static Options getClientOptions(String url,String action){

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

    public static OMElement getOMMethod(String methodStr,String namespace,String tns,String[] pars,String[] vals){

        //有抽象OM工厂获取OM工厂，创建request SOAP包

        OMFactory fac = OMAbstractFactory.getOMFactory();

        //创建命名空间

        OMNamespace nms = fac.createOMNamespace(namespace, tns);

        //创建OMElement方法 元素，并指定其在nms指代的名称空间中

        OMElement method = fac.createOMElement(methodStr, nms);

        //添加方法参数名和参数值

        for(int i=0;i<pars.length;i++){

            //创建方法参数OMElement元素

            OMElement param = fac.createOMElement(pars[i],nms);

            //设置键值对 参数值

            param.setText(vals[i]);

            //讲方法元素 添加到method方法元素中

            method.addChild(param);

            method.build();

        }

        return method;

    }

    //解析XML，将获取到的数据封装到list中

    public static List<OaGongGaoBean> getResults(OMElement element) {

        if (element == null) {

            return null;

        }

        List<OaGongGaoBean> list = new ArrayList<OaGongGaoBean>();

        Iterator iterator = element.getChildElements();

        while (iterator.hasNext()) {

            OMElement result = (OMElement) iterator.next();

            Iterator innerItr = result.getChildElements();

            while(innerItr.hasNext()){

                OMElement result2 = (OMElement) innerItr.next();

                Iterator innerItr2 = result2.getChildElements();

                while(innerItr2.hasNext()){

                    OMElement result3 = (OMElement) innerItr2.next();

                    Iterator innerItr3 = result3.getChildElements();

                    while(innerItr3.hasNext()){

                        OMElement result4 = (OMElement) innerItr3.next();

                        Iterator innerItr4 = result4.getChildElements();

                        int i = 0;

                        OaGongGaoBean ggbean = new OaGongGaoBean();

                        while(innerItr4.hasNext()){

                            OMElement result5 = (OMElement) innerItr4.next();

                            if(result5 != null)
                            {
                                String text = result5.getText();

                                if(i == 0)
                                {
                                    ggbean.setDeptName(text);
                                }
                                if(i == 1)
                                {
                                    ggbean.setTitle(text);
                                }
                                if(i == 2)
                                {
                                    ggbean.setPublishTime(text);
                                }
                                if(i == 3)
                                {
                                    ggbean.setIsNew(Integer.parseInt(text));
                                }
                                if(i == 4)
                                {
                                    ggbean.setUrl(text);
                                }
                            }

                            i = i + 1;
                        }

                        list.add(ggbean);
                    }
                }
            }
        }
        return list;
    }

    private static boolean getInfoByUrl(String url)
    {
        String count = InfoBaseManager.getInfoByUrl(url);
        if(count != null && !"".equals(count))
        {
            int result = Integer.parseInt(count);
            if(result > 0)
            {
                return true;
            }
            return  false;
        }
        return false;
    }
}
