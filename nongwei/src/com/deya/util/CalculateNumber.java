/*
 * @(#) CalculateNumber.java 1.0 03/01/09
 *
 * Copyright(c) 2002-2007 Cicor Software.
 * Xi'an, Shannxi, China.
 * All rights reserved.
 *
 */

package com.deya.util;

import java.text.NumberFormat;   
/**
 * 进行数值计算.
 *
 * @version 1.0 03/01/09
 * @author 曲彦宾
 */
public class CalculateNumber{

	/**
	 * 计算百分率.
	 * @param strUp 分子
	 * @param strDown 分母
	 * @return 百分率
	 */
	public static String getRate(String strUp, String strDown){
		try{
			float floatUp = Float.parseFloat(strUp);
			float floatDown = Float.parseFloat(strDown);
			int intResult = (int)(floatUp/floatDown*10000.0);
			
			return "" + (float)intResult/100. + "%";
		}catch(Exception e){
			return "0.0%";
		}
	}
	
	/**
	 * 计算百分率.
	 * @param strUp 分子
	 * @param strDown 分母
	 * * @param int nums 小数点保留位数
	 * @return 百分率
	 */
	public static String getRate(String strUp, String strDown,int nums){
		float floatUp = Float.parseFloat(strUp);
		float floatDown = Float.parseFloat(strDown);
		
		NumberFormat nf =    NumberFormat.getPercentInstance();                                         
		nf.setMinimumFractionDigits(nums);    //设置两位小数位                                                                                 
		double tt = (double)floatUp/floatDown; 
		return nf.format(tt);
	}
	
	public static void main(String[] args){
		System.out.println(getRate("18","23",0));
	}
}