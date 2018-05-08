package com.deya.util.labelUtil;

import java.util.Arrays;
import java.util.List;

import com.deya.util.HtmlRegexpUtil;
import com.deya.util.labelUtil.TagsChecker;

/**
 * html内容自动翻页处理.
 * <p>Title: CicroDate</p>
 * <p>Description:html内容自动翻页处理.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 
 * @version 1.0
 * * 
 */
public class AutoPagerHandl {
	/**
     * 
     * @param String content 内容
     * @param int page_num 页数    如果 page_num 为0，返回总页数数值
     * @param text_count 翻页字数
     * @return String str 根据页数返回第几页的内容，如果 page_num 为0，返回总页数数值
     * */
	public static String splitContent(String content,int page_num,int text_count)
	{
		int start_num = 0;
    	int num = text_count;
    	int real_num = text_count;
    	boolean is_turn = true;
    	int page_count = 1;
    	String prev_content = "";
    	while(is_turn)
    	{    		    			
    		real_num = cutContentStr(num,content);
    		//自动分页字数 比 信息的字数多
    		if(real_num > content.length())
    		{
    			prev_content = content.substring(start_num);
    			break;
    		}
        	prev_content = content.substring(start_num,real_num);
        	content = content.substring(real_num);
        	       	
        	if(page_count == page_num)
        	{        		
        		break;
        	}
        	if(num > content.length())
        	{
        		prev_content = content.substring(start_num);
        		is_turn = false;
        	}
        	
        	String htmlFilter = "";
        	htmlFilter = HtmlRegexpUtil.filterHtml(prev_content).replaceAll("&nbsp;","");
        	if(htmlFilter.equals("")){
        		break;
    		}
        	
        	page_count+=1;
    	}
    	if(page_num == 0)
    		return page_count + "";
    	else
    	{
    		if(page_num > 1)
    		{
    			//第1页以后的数据，需要对标签进行补齐
    			return TagsChecker.fix(prev_content);
    		}
    		else    			
    			return prev_content;
    	}
	}
	
	//从开始数值往后截取数据，并判断此数据是否在<>标签中，如果是，往后移动20位
	public static int cutContentStr(int num,String s)
    {  
    	String cut_str = "";
    	String start_str = "";
    	if(s.length() > (num+1)){
    		cut_str = s.substring(num,num+1);
    		start_str = s.substring(0,num);
    	}
    	else
    		return num;
    	//if(HtmlRegexpUtil.tagsContainLabel(cut_str,s) || !isInfoEnd(cut_str))
    	//if(HtmlRegexpUtil.tagsContainLabel(cut_str,s))
    	if(!isInfoEnd(start_str))
    	{
    		//num += 10;
    		num += 1;
    		return cutContentStr(num,s);
    		
    	}else
    		return num;
    }
	  
	public static boolean isInfoEnd(String content){
		//System.out.println(content);
		String[] regEx2 = {"<p>","</p>","<br>","<br/>","<div>","</div>"};
		List<String> regExList = Arrays.asList(regEx2);
		boolean result = false;
		for(String pstr : regExList){
			if(content.toLowerCase().trim().replaceAll(" ","").endsWith(pstr)){
				result = true;
				break;
			}
		}
		
		if(result){
			//System.out.println("是段落结束");
			return true;
		}else{
			//System.out.println("不是段落结束");
			return false;
		}
	}
	
	public static void main(String args[])
	{
		String content = "<p>　昨日召开的全市政务服务工作培训会议透露,今年全市将推广实施市与开发区（区、县）&ldquo;大联动&rdquo;并联审批机制, 这将有效解决办事企业和群众在开发区（区、县）和市政务中心&ldquo;两边跑&rdquo;、审批难的问题。</p><p style=\"line-height: 18px; margin-top: 2px; margin-bottom: 2px\">　　我市从2008年起，在市级层面开始筹建政务服务中心，推行政务服务制度，全市政务服务体系建设取得了长足进步。截至目前，全市11个区县、4个开发区建立了政务服务中心，10 个市级部门建立了政务大厅，379个乡镇街办（站所）建立了便民服务大厅，343个社区、村（组）建立了服务代理点。</p><p style=\"line-height: 18px; margin-top: 2px; margin-bottom: 2px\">　　据了解，今年我市将继续扩大覆盖面，加快四级政务服务体系建设，年底基本建成覆盖市、区（县）、乡镇（街道）、村组（社区）的四级政务服务体系。会议要求周至、户县，经开区、曲江新区、沣渭新区、航空基地等还没有成立政务中心的县、开发区，务必在下半年完成筹建任务，年底前正式启动运行；已成立政务中心的区县和开发区，进一步搞好规范化建设，做到审批事项应进必进，审批流程科学规范。同时，指导乡镇（街道）、村组（社区）做好基层服务机构建设，尽最大努力方便群众办事。</p><p style=\"line-height: 18px; margin-top: 2px; margin-bottom: 2px\">　　此外，我市还将试行市政务服务中心与开发区（区、县）&ldquo;联动式&rdquo;并联审批机制。据介绍，市政务服务中心通过调研，初步建立了与开发区（区、县）&ldquo;联动式&rdquo;并联审批机制，对在开发区（区、县）和市政务中心都有审批内容的事项，通过联席会议，联合受理、同步办理，办结后统一发送审批结果，能够有效解决办事企业和群众在开发区（区、县）和市政务中心&ldquo;两边跑&rdquo;，审批难的问题。今年，将在全市推广实施市与开发区（区、县）&ldquo;大联动&rdquo;并联审批机制。</p><p>&nbsp;</p>";
		int page_num = 1;
		int text_count = 200;
		String str = splitContent(content,page_num,text_count);
		//str = HtmlRegexpUtil.filterHtml(str).replaceAll("&nbsp;","");
		System.out.println(str);
//		if(str.equals("")){
//			System.out.println("为空");
//		}
		
	}
}
