<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.util.FormatUtil,com.deya.project.zjzf.*,com.deya.util.OutExcel,com.deya.util.jconfig.JconfigUtilContainer,com.deya.wcm.services.appeal.count.CountUtil,java.io.File"%>
<%
	String[] path = getFileUrl();
	String[] head = getExcelHeadName();	
	Map<String,String> m = new HashMap<String,String>();
	m.put("start_num", "0");	
	m.put("page_size", "10000");
	m.put("type", "1");
	m.put("orderby", "id desc");
	List<ZJZFBean> l = ZJZFManager.getGongMinList(m);
	String[][] data = new String[l.size()][head.length];
	int i=0;
	try{					
		for(ZJZFBean zjxf : l)
		{
			data[i][0] = zjxf.getName();
			data[i][1] = zjxf.getXb();
			data[i][2] = zjxf.getCsny();
			data[i][3] = zjxf.getMz();
			data[i][4] = zjxf.getZzmm();
			data[i][5] = zjxf.getCard();
			data[i][6] = zjxf.getGzdw();
			data[i][7] = zjxf.getZhiwu();
			data[i][8] = zjxf.getZhicheng();
			data[i][9] = zjxf.getHkszd();
			data[i][10] = zjxf.getPhone();
			data[i][11] = zjxf.getTel();
			data[i][12] = zjxf.getAddress();
			data[i][13] = zjxf.getTxdz();
			data[i][14] = zjxf.getPostcode();
			data[i][15] = zjxf.getEmail();
			
			i++;
		}		
		
		OutExcel oe=new OutExcel("报名信息表");
		oe.doOut(path[0],head,data);
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	//return path[1];
	//out.println("<script>window.location.href='"+path[1]+"'</script>");
	out.println("<script>document.write('<a href=\""+path[1]+"\" target=\"_blank\">下载文件</a>')</script>");
%>
<%!
//删除今天以前的文件夹  并 创建今天的文件夹和xls文件
	public static String[] getFileUrl(){
		//删除今天以前的文件夹
		String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
		String path = FormatUtil.formatPath(root_path + "/project/wnzjzf/export");  
		//System.out.println("path===" + path);
		CountUtil.deleteFile(path);
		
		//创建今天的文件夹和xls文件
		String nowDate = CountUtil.getNowDayDate();
		String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
		//System.out.println("fileTemp2===" + fileTemp2);
		File file2 = new File(fileTemp2);
		if(!file2.exists()){
			file2.mkdir();
		}
		String nowTime = CountUtil.getNowDayDateTime();
		String xls = nowTime + CountUtil.getEnglish(1)+".xls";
		String xlsFile = fileTemp2+File.separator+xls;
		String urlFile = "/manager/project/wnzjzf/export/"+nowDate+File.separator+xls;
		//System.out.println("xlsFile===" + xlsFile);
		String[] str = {xlsFile,urlFile};
		
		return str;
	}

	public static String[] getExcelHeadName()
	{
		String[] head = "姓名,性别,出生年月,民族,政治面貌,身份证,工作单位,职务,职称,户口所在地,手机号码,固定电话,常住地址,通信地址,邮编,电子邮箱".split(",");
		
		return head;
	}
%>
