package com.deya.wcm.dataCollection.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParserUtils {

	/**
	 * 提取某个属性值的标签列表
	 * @param <T>
	 * @param html 要提取的html文本
	 * @param tagType 标签类型  div
	 * @param attrbuteName 标签的属性名称 class
	 * @param attrbuteValue 标签的属性值  wrap
	 * @return 含有此标签的列表
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType, final String attrbuteName,final String attrbuteValue)
	{
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);
			
			NodeList tagsList = parser.parse(new NodeFilter(){
				public boolean accept(Node nodes){
					if(nodes.getClass() == tagType)  //内部类引用外部类的参数,参数类型必须是final类型
					{
						T tn = (T)nodes;
						if(attrbuteName==null)
						{
							return true;
						}
						String attrbuteValue_1 = tn.getAttribute(attrbuteName);
						if(attrbuteValue_1 != null && attrbuteValue_1.equals(attrbuteValue))
						{
							return true;
						}
					}
					return false;
				}
			});
			List<T> tags = new ArrayList<T>();
			for(int i=0;i<tagsList.size();i++)
			{
				T t = (T)tagsList.elementAt(i);
				tags.add(t);
			}
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取某一类型的标签列表
	 * @param <T>
	 * @param html
	 * @param tagType
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType)
	{
		return parseTags(html,tagType,null,null);
	}
	
	
	public static <T extends TagNode> T parseTag(String html, final Class<T> tagType, final String attrbuteName,final String attrbuteValue)
	{
		List<T> tags = parseTags(html, tagType, attrbuteName, attrbuteValue);
		if(tags != null && tags.size()>0)
		{
			return tags.get(0);
		}
		return null;
	}
	
	/**
	 * 获取某一类型的标签
	 * @param <T>
	 * @param html
	 * @param tagType
	 * @return
	 */
	public static <T extends TagNode> T parseTag(String html, final Class<T> tagType)
	{
		return parseTag(html,tagType,null,null);
	}
}
