/*
 * @(#) XmlManager.java 1.0 02/12/06
 *
 * Copyright (c) 2002-2007 Cicro Software.
 * Xi'an, Shannxi, China.
 * All rights reserved.
 *
 */

package com.deya.util.xml;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Properties;
import java.util.Vector;


/**
 * xml操作类. xml的创建及读取操作
 * 
 * @version 1.0 02/12/06
 * @author 曲彦宾
 */
public class XmlManager {
	private static DocumentBuilderFactory dbf;

	// Remove by sman on 2003-9-3
	// 为了解决org.xml.sax.SAXException: Parser is already in use问题
	// private static DocumentBuilder db;
	// Remove by sman on 2003-9-3

	static {
		dbf = new DocumentBuilderFactoryImpl();
		dbf.setCoalescing(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);

	}

	/**
	 * 利用JAXP构造一个空Document对象.
	 * 
	 * @return Document对象
	 * @throws ParserConfigurationException
	 */
	public static Document createDOM() throws ParserConfigurationException {
		try {
			return dbf.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 利用JAXP构造Document对象.
	 * 
	 * @param String
	 *            xml文件名
	 * @return Document对象
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDOM(String strFileName)
			throws ParserConfigurationException, SAXException, IOException {
		File fileObj = new File(strFileName);
		return createDOMByFile(fileObj);
	}

	/**
	 * 根据给定文件创建Document对象.
	 * 
	 * @param String
	 *            XML文件
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDOMByFile(String strFile)
			throws ParserConfigurationException, SAXException, IOException {
		File file = new File(strFile);
		return createDOMByFile(file);
	}

	/**
	 * 根据给定文件创建Document对象.
	 * 
	 * @param File
	 *            XML文件对象
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDOMByFile(File file)
			throws ParserConfigurationException, SAXException, IOException {

		try {
			return dbf.newDocumentBuilder().parse(file);
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 根据InputStream创建Docuemnt对象. *
	 * 
	 * @param InputStream
	 *            输入流
	 * @return Document对象
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document createDomByInputStream(InputStream is)
			throws IOException, SAXException {
		try {
			return dbf.newDocumentBuilder().parse(is);
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 利用JAXP构造Document对象.
	 * 
	 * @param String
	 *            xml内容
	 * @return Document对象
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDOMByString(String strDOMContent)
			throws ParserConfigurationException, SAXException, IOException {
		try {			
			return dbf.newDocumentBuilder().parse(
					new InputSource(new StringReader(strDOMContent)));

		} catch (Exception ex) {
			// Debug.error("将字符串转换为DOM时出错：[" + strDOMContent + "]");
			ex.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 根据给定的xml字符串创建一个NodeListgetWareInstances(strSiteID).
	 * 
	 * @param String
	 *            xml字符串
	 * @return NodeList
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static NodeList createNodeList(String strXML)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {
		strXML = "<xml>\n" + strXML + "\n</xml>";
		Document doc = createDOMByString(strXML);
		NodeList nodelist = null;
		nodelist = XPathAPI.selectNodeList(doc, "/xml/*");
		return nodelist;
	}

	/**
	 * 根据给定xml字符串创建一个NodegetWareInstances(strSiteID).
	 * 
	 * @param String
	 *            xml字符串
	 * @return Node
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static Node createNode(String strXML)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {
		try {
			NodeList nodelist = createNodeList(strXML);
			Node node = null;
			if (nodelist != null && nodelist.getLength() > 0) {
				node = nodelist.item(0);
			}
			return node;
		} catch (Exception ex) {
			// Debug.error("将字符串转换为节点时出错：[" + strXML + "]");
			ex.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 利用JAXP转换器查询Node节点, 对所有Node类型都有效.
	 * 
	 * @param strXPath
	 *            XPath查询语句
	 * @param node
	 *            Node节点
	 * @return 查询结果
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String[] doXMLQueryString(String strXPath, Node node)
			throws TransformerConfigurationException, TransformerException {
		Vector<String> vect = new Vector<String>();
		Transformer serializer = TransformerFactory.newInstance()
				.newTransformer();
		serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		NodeIterator nl = XPathAPI.selectNodeIterator(node, strXPath);
		Node n;
		while ((n = nl.nextNode()) != null) {
			StringWriter strWr = new StringWriter();
			serializer.transform(new DOMSource(n), new StreamResult(strWr));
			vect.add(strWr.toString());
		}
		return (String[]) vect.toArray(new String[vect.size()]);
	}

	/**
	 * 将Node对象转换为字符串.
	 * 
	 * @param Node
	 *            Node对象
	 * @return String　返回字符串
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String node2String(Node node)
			throws TransformerConfigurationException, TransformerException {	
		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		String strReturn = null;
		try {
			if (node instanceof Document)
				node = ((Document) node).getDocumentElement();
			StringWriter strWr = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.transform(new DOMSource(node), new StreamResult(strWr));
			strReturn = strWr.toString();
			return strReturn.replaceFirst("\\<\\?[ ]*xml.*\\?\\>", "");
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return strReturn;
		}

	}

	/**
	 * 将Node对象转换为指定编码的字符串.
	 * 
	 * @param Node
	 *            Node对象
	 * @param String  
	 *            指定编码
	 * @return String　返回字符串
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String node2String(Node node, String encode)
			throws TransformerConfigurationException, TransformerException {
		String strReturn = null;
		if (node instanceof Document)
			node = ((Document) node).getDocumentElement();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Transformer serializer = TransformerFactory.newInstance()
				.newTransformer();
		serializer.setOutputProperty("indent", "yes");
		serializer.setOutputProperty("encoding", encode);
		serializer.transform(new DOMSource(node), new StreamResult(out));

		strReturn = out.toString();
		return strReturn;
	}

	/**
	 * 将Node对象转换为指定编码的字符串.（用于Websphere环境下）
	 * 
	 * @param Node
	 *            Node对象
	 * @return String　返回字符串
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String node2StringParseByByte(Node node)
			throws TransformerConfigurationException, TransformerException {
		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null");
		} else {
			String strReturn = null;
			if (node instanceof Document)
				node = ((Document) node).getDocumentElement();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.setOutputProperty("indent", "yes");
			serializer.setOutputProperty("encoding", "ISO-8859-1");
			serializer.transform(new DOMSource(node), new StreamResult(out));
			strReturn = out.toString();
			return strReturn.replaceFirst("\\<\\?[ ]*xml.*\\?\\>", "");
		}
	}

	/**
	 * 将Node对象写入文件getWareInstances(strSiteID).
	 * 
	 * @param node
	 *            要转化的节点
	 * @param strFileName
	 *            存入的文件名
	 * @param strEnCoding
	 *            编码方式（默认为UTF-8）
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static void node2File(Node node, String strFileName,
			String strEnCoding) throws TransformerConfigurationException,
			TransformerException {
		if (node == null) {
			return;
		}
		if (strEnCoding == null || strEnCoding.trim().length() == 0) {
			strEnCoding = "utf-8";
		}
		File outFile = new File(strFileName);

		Transformer serializer = TransformerFactory.newInstance()
				.newTransformer();
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.ENCODING, strEnCoding);
		serializer.transform(new DOMSource(node), new StreamResult(outFile));
	}

	/**
	 * 序列化一个节点，在生成的字符串中有头部信息getWareInstances(strSiteID).
	 * 
	 * @param node
	 *            要序列化的节点
	 * @return String
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String serializeNode(Node node)
			throws TransformerConfigurationException, TransformerException {
		if (node == null) {
			return null;
		}
		if (node instanceof Document)
			node = ((Document) node).getDocumentElement();
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		StringWriter sw = new StringWriter();
		transformer.transform(new DOMSource(node), new StreamResult(sw));
		return sw.toString();
	}

	/**
	 * <pre>
	 * 按照给定的属性序列化一个节点，在生成的字符串中有头部信息
	 * getWareInstances(strSiteID).
	 * 例如：
	 * Properties property = new Properties();
	 * //默认编码为UTF-8
	 * property.setProperty(&quot;encoding&quot;,&quot;gb2312&quot;);
	 * serializeNode(node,property);
	 * </pre>
	 * 
	 * @param node
	 *            要序列化的节点
	 * @param property
	 *            需要设置的属性
	 * @return String
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static String serializeNode(Node node, Properties property)
			throws TransformerConfigurationException, TransformerException {
		if (node == null) {
			return null;
		}
		if (node instanceof Document)
			node = ((Document) node).getDocumentElement();
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		// 设置属性
		transformer.setOutputProperties(property);
		StringWriter sw = new StringWriter();
		transformer.transform(new DOMSource(node), new StreamResult(sw));
		return sw.toString();
	}

	/**
	 * 在指定节点里用指定XPath查询节点列表getWareInstances(strSiteID).
	 * 
	 * @param node
	 *            要查询的节点
	 * @param strXPath
	 *            查询XPath
	 * @return NodeList 查询结果的列表
	 * @throws TransformerException
	 */
	public static NodeList queryNodeList(Node node, String strXPath)
			throws TransformerException {
		if (node == null) {
			return null;
		}
		NodeList nl = null;
		nl = XPathAPI.selectNodeList(node, strXPath);
		return nl;
	}

	/**
	 * 在指定节点里用指定XPath查询节点列表. 并返回第一个节点getWareInstances(strSiteID).
	 * 
	 * @param node
	 *            要查询的节点
	 * @param strXPath
	 *            查询XPath
	 * @return Node 查询结果的节点
	 * @throws TransformerException
	 */
	public static Node queryNode(Node node, String strXPath)
			throws TransformerException {
		if (node == null) {
			return null;
		}
		NodeList nl = queryNodeList(node, strXPath);
		Node n = null;
		if (nl != null && nl.getLength() > 0) {
			n = nl.item(0);
		}
		return n;
	}

	/**
	 * 在指定节点里用指定XPath查询指定节点的值getWareInstances(strSiteID).
	 * 
	 * @param node
	 *            要查询的节点
	 * @param strXPath
	 *            查询XPath
	 * @return String 查询结果节点的值
	 * @throws TransformerException
	 */
	public static String queryNodeValue(Node node, String strXPath)
			throws TransformerException {
		if (node == null) {
			return null;
		}
		Node n = queryNode(node, strXPath);
		String strR = null;
		if (n != null) {
			strR = queryNodeValue(n);
		}
		return strR;
	}

	/**
	 * 查询节点值.
	 * 
	 * @param node
	 *            要查询的节点
	 * @return 节点值
	 * @throws TransformerException
	 */
	public static String queryNodeValue(Node node) throws TransformerException {
		String strR = null;
		if (node != null) {
			if (node.getFirstChild() != null
					&& node.getNodeType() == Node.ELEMENT_NODE) {
				strR = node.getFirstChild().getNodeValue();
			} else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
				strR = node.getNodeValue();
			}
		}
		return strR;
	}

	/**
	 * 查询节点值,返回列表.
	 * 
	 * @param node
	 *            要查询节点
	 * @param strXPath
	 *            XPath路径
	 * @return 节点值列表
	 * @throws TransformerException
	 */
	public static String[] queryNodeValues(Node node, String strXPath)
			throws TransformerException {
		if (node == null) {
			return null;
		}
		NodeList nl = queryNodeList(node, strXPath);
		int iLength = nl.getLength();
		String[] strArr = new String[iLength];
		for (int i = 0; i < nl.getLength(); i++) {
			strArr[i] = queryNodeValue(nl.item(i));
		}
		return strArr;
	}

	/**
	 * 在parentNode节点下追加childNode.
	 * 
	 * @param parentNode
	 *            父节点
	 * @param childNode
	 *            子节点
	 * @return boolean
	 */
	public static boolean insertNode(Node parentNode, Node childNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("The parentnode is null!");
		}
		if (childNode == null) {
			throw new IllegalArgumentException("The childnode is null!");
		}
		Document dom = null;
		if (parentNode instanceof Document) {
			dom = (Document) parentNode;
			parentNode = dom.getDocumentElement();
		} else {
			dom = parentNode.getOwnerDocument();
		}
		Node node = childNode.cloneNode(true);
		node = dom.importNode(node, true);
		parentNode.appendChild(node);
		return true;
	}

	/**
	 * 在指定节点下添加一个节点.
	 * 
	 * @param parentnode
	 *            要添加子节点的节点
	 * @param nodexml
	 *            要添加的子节点的xml描述
	 * @return true-成功 false-失败
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean insertNode(Node parentnode, String nodexml)
			throws ParserConfigurationException, TransformerException,
			IOException, SAXException {
		if (parentnode == null) {
			throw new IllegalArgumentException("The parentnode is null!");
		}
		Node childnode = null;
		childnode = XmlManager.createNode(nodexml);
		return insertNode(parentnode, childnode);
	}

	/**
	 * 在指定节点前插入新节点
	 * Inserts the node newChild(include its children) before the existing child node refChild. If the refChild node is null ,insert the newChild at the end of the list of children . If the newChild is already in the tree, it is first removed.
	 * @param Node
	 *            父节点Node对象
	 * @param Node
	 *            新节点（要插入的Node节点对象）
	 * @param Node
	 *            标识节点对象（在该节点前插入）
	 * @return true-succese false-failed
	 */
	public static boolean insertNodeBefore(Node parentnode, Node newChild,
			Node refChild) {
		if (parentnode == null) {
			throw new IllegalArgumentException("The parentnode is null!");
		}
		if (newChild == null) {
			throw new IllegalArgumentException("The new child node is null!");
		}

		if (refChild == null) {
			insertNode(parentnode, newChild);
		}
		// get the parentnode's Document
		Document dom = null;
		if (parentnode instanceof Document) {
			dom = (Document) parentnode;
			parentnode = dom.getDocumentElement();
		} else {
			dom = parentnode.getOwnerDocument();
		}
		// import the new child node
		newChild = dom.importNode(newChild, true);

		parentnode.insertBefore(newChild, refChild);
		return true;
	}

	/**
	 * 在父节点第一个位置中插入新节点
	 * Inserts the node newChild(include its children) before the existing first
	 * child of parentnode.
	 * 
	 * @param Node
	 *            父节点Node对象
	 * @param Node
	 *            要插入的节点Node对象
	 * @return true-succese false-failed
	 */
	public static boolean insertNodeFirst(Node parentnode, Node newChild) {
		if (parentnode == null) {
			throw new IllegalArgumentException("The parentnode is null!");
		}
		if (newChild == null) {
			throw new IllegalArgumentException("The new child node is null!");
		}

		Document dom = null;
		if (parentnode instanceof Document) {
			dom = (Document) parentnode;
			parentnode = dom.getDocumentElement();
		} else {
			dom = parentnode.getOwnerDocument();
		}

		NodeList nl = parentnode.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			Node node = nl.item(0);
			insertNodeBefore(parentnode, newChild, node);
		} else {
			insertNode(parentnode, newChild);
		}
		return true;
	}

	/**
	 * 将指定节点替换成新节点
	 * @param Node
	 *            父节点Node对象
	 * @param Node
	 *            新节点（要替换的Node节点对象）
	 * @param Node
	 *            标识节点对象（被替换的Node节点对象）
	 * @return true-succese false-failed
	 */
	public static boolean replaceNode(Node parentNode, Node newNode,
			Node oldNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("The parent node is null!");
		}
		if (newNode == null) {
			throw new IllegalArgumentException("The new node is null!");
		}
		if (oldNode == null) {
			throw new IllegalArgumentException("The old node is null!");
		}
		Document dom = null;
		if (parentNode instanceof Document) {
			dom = (Document) parentNode;
			parentNode = dom.getDocumentElement();
		} else {
			dom = parentNode.getOwnerDocument();
		}
		newNode = newNode.cloneNode(true);
		newNode = dom.importNode(newNode, true);
		parentNode.replaceChild(newNode, oldNode);
		return true;
	}
	
	/**
	 * 将字符串替换成新节点
	 * @param Node
	 *            父节点Node对象
	 * @param String
	 *            要替换的字符串
	 * @param Node
	 *            标识节点对象（被替换的Node节点对象）
	 * @return true-succese false-failed
	 */
	public static boolean replaceNode(Node parentNode, String newxml,
			Node oldNode) throws ParserConfigurationException,
			TransformerException, IOException, SAXException {
		if (parentNode == null) {
			throw new IllegalArgumentException("The parent node is null!");
		}
		if (newxml == null) {
			throw new IllegalArgumentException("The new xml is null!");
		}
		if (oldNode == null) {
			throw new IllegalArgumentException("The old node is null!");
		}
		Document dom = null;
		if (parentNode instanceof Document) {
			dom = (Document) parentNode;
			parentNode = dom.getDocumentElement();
		} else {
			dom = parentNode.getOwnerDocument();
		}
		Node newNode = createNode(newxml);
		Node node = newNode.cloneNode(true);
		node = dom.importNode(node, true);
		parentNode.replaceChild(newNode, oldNode);
		return true;
	}

	/**
	 * 移除一个节点. 当节点为空时,不能移除. 当节点没有父节点时,不能移除.
	 * 
	 * @param node
	 *            要移除的节点
	 * @return true-移除成功 false-移除失败
	 * 
	 * History 2003-06-05 add by kongxx
	 */
	public static boolean removeNode(Node node) {
		// if the node is null,throw IllegalArgumentException
		if (node == null) {
			throw new IllegalArgumentException(
					"The remove node can not is null!");
		}

		// if the node is root node,throw IllegalArgumentException
		if (node.getParentNode() == null) {
			throw new IllegalArgumentException(
					"The remove node is root node , can not remove");
		}

		// remove the current node
		node = node.getParentNode().removeChild(node);

		return true;
	}

	/**
	 * 根据字符串数组数据创建成Node对象
	 * @param String[][]
	 *            字符串数组
	 * @param String[]
	 *            标签名数组
	 * @param String docName
	 *            第一级标签名称
	 * @param String rootName
	 *            第二级标签名称
	 * @param String rowName
	 *            第三级标签名称  
	 * @param String colName
	 *  		　末级循环的标签名称
	 * @param String colNum
	 *            循环次数       
	 * 例：String[][] arr = new String[2][2];
			arr[0][0] = "00";
			arr[0][1] = "01";
			arr[1][0] = "10";
			arr[1][1] = "11";
			String[] arr2 = {"straCol","zl"};			
			Node n = createNodeFromArray(arr,arr2,"docName","rootName","rowName","colName","4");
		返回：<docName><rootName><rowName><colName><straCol>00</straCol><zl>01</zl></colName><colName><straCol>10</straCol><zl>11</zl></colName><colName><straCol/><zl/></colName><colName><straCol/><zl/></colName></rowName></rootName></docName>	                
	 * @return Node　返回Node对象
	 */
	public static Node createNodeFromArray(String[][] stra, String[] straCol,
			String docName, String rootName, String rowName, String colName,
			String colNum) {
		if (stra == null) {
			return null;
		}
		int intColNum = Integer.parseInt(colNum);

		int intRowCount = stra.length;
		int intRecordColNum = straCol.length;

		int intRow = intRowCount / intColNum;
		if (intRowCount % intColNum != 0) {
			intRow++;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<");
		strBuffer.append(docName);
		strBuffer.append(">");
		strBuffer.append("<");
		strBuffer.append(rootName);
		strBuffer.append(">");

		for (int i = 0; i < intRow; i++) {
			strBuffer.append("<");
			strBuffer.append(rowName);
			strBuffer.append(">");
			for (int ii = i * intColNum; ii < (i + 1) * intColNum; ii++) {
				strBuffer.append("<");
				strBuffer.append(colName);
				strBuffer.append(">");
				// String strFileName = "";
				for (int j = 0; j < intRecordColNum; j++) {
					String strTemp;
					try {
						strTemp = stra[ii][j];
					} catch (Exception strE) {
						strTemp = "";
					}
					if (strTemp == null) {
						strTemp = "";
					}
					if (j == 1) {
						// strFileName = strTemp;
					}
					strBuffer.append("<");
					strBuffer.append(straCol[j]);
					strBuffer.append(">");
					strBuffer.append(strTemp);
					strBuffer.append("</");
					strBuffer.append(straCol[j]);
					strBuffer.append(">");
				}
				strBuffer.append("</");
				strBuffer.append(colName);
				strBuffer.append(">");
			}
			strBuffer.append("</");
			strBuffer.append(rowName);
			strBuffer.append(">");
		}
		strBuffer.append("</");
		strBuffer.append(rootName);
		strBuffer.append(">");
		strBuffer.append("</");
		strBuffer.append(docName);
		strBuffer.append(">");

		try {
			return createDOMByString(strBuffer.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String arg[]) {
		//String xml = "<info>111</info>";
		try {/*
			String[][] arr = new String[2][2];
			arr[0][0] = "00";
			arr[0][1] = "01";
			arr[1][0] = "10";
			arr[1][1] = "11";
			String[] arr2 = {"straCol","zl"};			
			Node n = createNodeFromArray(arr,arr2,"docName","rootName","rowName","colName","4");			
			System.out.println(node2String(n));*/
			
			String xml = "<cicroxml><infoTree infoId=\"12856\" treeId=\"5267\" flag=\"0\" id=\"12857\" time=\"2009-11-24 09:44:13\"/><infoTree infoId=\"12856\" treeId=\"1083\" flag=\"1\" id=\"12858\" time=\"2009-11-24 09:45:30\"/></cicroxml>";
			Node node = XmlManager.createNode(xml);
			NodeList infoNode = XmlManager.queryNodeList(node, "//infoTree");
			for(int i=0;i<infoNode.getLength();i++)
			{
				//String infoID = infoNode.item(i).getAttributes().getNamedItem("id").getNodeValue().trim();
				System.out.println(infoNode.item(i).toString());
			}
		} catch (Exception e) {

		}
	}
}
