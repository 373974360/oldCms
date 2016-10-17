/*    */ package com.deya.license.tools;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.PrintStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class DomParse
/*    */ {
/* 12 */   private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*    */   private static DocumentBuilder builder;
/*    */   private static Document dom;
/*    */ 
/*    */   public static Document creaatDomByString(String str)
/*    */   {
/*    */     try
/*    */     {
/* 18 */       builder = factory.newDocumentBuilder();
/* 19 */       Document doc = builder.parse(new ByteArrayInputStream(str.getBytes()));
/* 20 */       return doc;
/*    */     } catch (Exception ex) {
/* 22 */       ex.printStackTrace(System.out);
/* 23 */     }return null;
/*    */   }
/*    */ 
/*    */   public static String getNodeItem(Document doc, String nodeName)
/*    */   {
/*    */     try
/*    */     {
/* 31 */       if (doc == null)
/*    */       {
/* 33 */         return null;
/*    */       }
/* 35 */       NodeList nl = doc.getElementsByTagName(nodeName);
/* 36 */       if (nl.getLength() > 0) {
/* 37 */         return nl.item(0).getFirstChild().getNodeValue();
/*    */       }
/* 39 */       return null;
/*    */     }
/*    */     catch (Exception ex) {
/* 42 */       ex.printStackTrace(System.out);
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getNodeItem(Document doc, String parentNode, String nodeName)
/*    */   {
/*    */     try
/*    */     {
/* 52 */       if (doc == null)
/*    */       {
/* 54 */         return null;
/*    */       }
/* 56 */       NodeList nl = doc.getElementsByTagName(parentNode);
/* 57 */       NodeList childNodes = nl.item(0).getChildNodes();
/*    */ 
/* 59 */       for (int j = 0; j < childNodes.getLength(); j++) {
/* 60 */         Node childNode = childNodes.item(j);
/*    */ 
/* 62 */         if (((childNode instanceof Element)) && 
/* 63 */           (nodeName.equals(childNode.getNodeName()))) {
/* 64 */           return childNode.getFirstChild().getNodeValue();
/*    */         }
/*    */       }
/* 67 */       return null;
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 71 */       System.out.println("getNodeItem --" + nodeName + "-- item value is null");
/* 72 */     }return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] arg)
/*    */   {
/* 79 */     String xml = "<license><cpuid><![CDATA[FD060000]]></cpuid><mac><![CDATA[001D9280DE2D005056C00001005056C00008]]></mac><cws><timelimit><![CDATA[1]]></timelimit><site_num>1</site_num><dept_num>5</dept_num></cws></license>";
/* 80 */     dom = creaatDomByString(xml);
/* 81 */     System.out.println(getNodeItem(dom, "cws", "timelimit"));
/*    */   }
/*    */ }

/* Location:           C:\Users\hp\Desktop\WCMLicense.jar
 * Qualified Name:     com.deya.license.tools.DomParse
 * JD-Core Version:    0.6.0
 */