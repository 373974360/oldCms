//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.util.jconfig;

import com.deya.util.FormatUtil;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JconfigUtil {
	private static Logger logger = LoggerFactory.getLogger(JconfigUtil.class);
	static Map<String, String> variableMap = new HashMap();
	Map<String, LinkedHashMap<String, String>> valueMap = new HashMap();

	public JconfigUtil() {
	}

	public JconfigUtil(String path) {
		this.loadPropertyFile(path);
	}

	private void loadPropertyFile(String path) {
		try {
			path = FormatUtil.formatPath(path);
			File file = new File(path);
			if (!file.exists()) {
				logger.warn("配置文件不存在 : {}" + path);
			}

			Document document = (new SAXReader()).read(file);
			List<Node> list = document.selectNodes("/properties/variables/variable");
			Iterator var5 = list.iterator();

			String categoryName;
			while(var5.hasNext()) {
				Node node = (Node)var5.next();
				if (node instanceof Element) {
					String name = ((Element)node).attributeValue("name");
					categoryName = ((Element)node).attributeValue("value");
					variableMap.put(name, categoryName);
				}
			}

			List<Node> categoryList = document.selectNodes("/properties/category");
			Iterator var17 = categoryList.iterator();

			while(true) {
				Node category;
				do {
					if (!var17.hasNext()) {
						return;
					}

					category = (Node)var17.next();
				} while(!(category instanceof Element));

				categoryName = ((Element)category).attributeValue("name");
				LinkedHashMap<String, String> stringStringLinkedHashMap = new LinkedHashMap();
				List<Node> propertyList = category.selectNodes("./property");
				this.valueMap.put(categoryName, stringStringLinkedHashMap);
				Iterator var11 = propertyList.iterator();

				while(var11.hasNext()) {
					Node node = (Node)var11.next();
					if (node instanceof Element) {
						String key = ((Element)node).attributeValue("name");
						String value = ((Element)node).attributeValue("value");
						stringStringLinkedHashMap.put(key, value);
					}
				}
			}
		} catch (Exception var15) {
			var15.printStackTrace();
		}
	}

	public void loadPropertyFile(String path, String fileName) {
		this.loadPropertyFile((new File(path, fileName)).getAbsolutePath());
	}

	public String getProperty(String key, String defaultValue, String category) {
		LinkedHashMap<String, String> stringStringLinkedHashMap = (LinkedHashMap)this.valueMap.get(category);
		String value = null;
		if (stringStringLinkedHashMap != null) {
			value = (String)stringStringLinkedHashMap.get(key);
			if (value == null) {
				return defaultValue;
			}

			Iterator var6 = variableMap.keySet().iterator();

			while(value.contains("${")) {
				String varName = value.substring(value.indexOf("${") + "${".length(), value.indexOf("}", value.indexOf("${") + "${".length())).trim();
				String temp = "${" + varName + "}";
				boolean contains = value.contains(temp);
				if (contains) {
					String replacement = (String)variableMap.get(varName);
					if (replacement == null) {
						break;
					}

					value = value.replace(temp, replacement);
				}
			}
		}

		return value;
	}

	public Set<String> getCategorys() {
		return this.valueMap.keySet();
	}

	public Set<String> getPropertyNamesByCategory(String categoryName) {
		Map<String, String> stringStringMap = (Map)this.valueMap.get(categoryName);
		return stringStringMap == null ? Collections.EMPTY_SET : stringStringMap.keySet();
	}

	public Map getVariables() {
		return variableMap;
	}

	public void setParentConfig() {
	}

	public static void main(String[] args) {
		JconfigUtil jconfigUtil = new JconfigUtil("/Users/yangyan/git/cms/nongwei/src/main/resources/AllConfigDescrion_WCM.xml");
		System.out.println(jconfigUtil.getVariables());
	}
}
