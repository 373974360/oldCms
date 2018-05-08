package com.deya.license.tools;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomParse {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder builder;
    private static Document dom;

    public static Document creaatDomByString(String str) {
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(str.getBytes()));
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    public static String getNodeItem(Document doc, String nodeName) {
        try {
            if (doc == null) {
                return null;
            }
            NodeList nl = doc.getElementsByTagName(nodeName);
            if (nl.getLength() > 0) {
                return nl.item(0).getFirstChild().getNodeValue();
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    public static String getNodeItem(Document doc, String parentNode, String nodeName) {
        try {
            if (doc == null) {
                return null;
            }
            NodeList nl = doc.getElementsByTagName(parentNode);
            NodeList childNodes = nl.item(0).getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);

                if (((childNode instanceof Element)) &&
                        (nodeName.equals(childNode.getNodeName()))) {
                    return childNode.getFirstChild().getNodeValue();
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("getNodeItem --" + nodeName + "-- item value is null");
        }
        return null;
    }

    public static void main(String[] arg) {
        String xml = "<license><cpuid><![CDATA[FD060000]]></cpuid><mac><![CDATA[001D9280DE2D005056C00001005056C00008]]></mac><cws><timelimit><![CDATA[1]]></timelimit><site_num>1</site_num><dept_num>5</dept_num></cws></license>";
        dom = creaatDomByString(xml);
        //System.out.println(getNodeItem(dom, "cws", "timelimit"));
    }
}