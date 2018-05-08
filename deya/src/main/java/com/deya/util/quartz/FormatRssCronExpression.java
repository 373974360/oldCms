package com.deya.util.quartz;

public class FormatRssCronExpression{
	
	public static String formatCronExp(String time) {
		String tmp = "";
		if(time.contains("m")){
			tmp = time.replaceAll("m", "");			
			return "0 0/"+tmp+" * * * ?";
		}else if(time.contains("h")){
			tmp = time.replaceAll("h", "");
			if(Integer.parseInt(tmp) > 23)
				return "0 0 0/1 * * ?";
			else
			    return "0 0 0/"+tmp+" * * ?";
		}else if(time.contains("d")){
			tmp = time.replaceAll("d", "");
			if(Integer.parseInt(tmp) > 31)
				return "0 0 0 0/1 * ?";
			else
				return "0 0 0 0/"+tmp+" * ?";
		}else{
			return "0 0 0/1 * * ?";
		}
	}

	public static void main(String[] args){
		System.out.println(formatCronExp("1m"));
	}
}

