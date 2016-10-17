package com.deya.util;

/**
 * 字符串处理类.
 * <p>Title: 字符串处理类</p>
 * <p>Description: 字符串处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kongxx
 * @version 1.0
 */
public class StringManager
{
	/**
	 * 子串的查找和替换
	 * @param     strValue 原串
	 *            strS 要查找的子串
	 *            strT 需要最后使用的子串
	 * @return    替换后的字符串
	 * @exception 
	 * @see       
	 */
	public static String replace(String strValue , String strS ,String strT)
	{
		int nPosition;
		String strTemp1 = "";
		String strTemp2 = strValue;

		String strReturn="";
		
		if(strTemp2 == null ) return strValue;
		
		nPosition = strTemp2.indexOf(strS);
		while(nPosition != -1)
		{
			strTemp1 = strTemp2.substring(0,nPosition);
			strTemp2 = strTemp2.substring(nPosition + strS.length() , strTemp2.length());
			strReturn += strTemp1+ strT;
			nPosition = strTemp2.indexOf(strS);
		}
		strReturn += strTemp2;
		
		return strReturn;
	}
	
	/**
	 * 得到字符串真实长度　
	 * 
	 * @param pressText
	 *            文字
	 * @return int 字符串真实长度           
	 */
	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
	public static void main(String[] args){
		System.out.println(replace("AAAA{#subStr_@_回忆三部曲 (合订版)二最}难懂的动画片--二_@_12#}BBB","{#subStr_@_回忆三部曲 (合订版)二最}难懂的动画片--二_@_12#}","111(2{33}22)11"));
	}
}