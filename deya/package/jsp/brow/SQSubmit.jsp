<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.bean.appeal.model.*,com.deya.wcm.services.appeal.model.*"%>
<%@page import="com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.util.Encode"%>
<%	
  try{
	//System.out.println("------------------start----------------------------");
	response.setContentType("application/json");
	String jsoncallback = (String)request.getParameter("callback");
	
	String me_id = FormatUtil.formatNullString(request.getParameter("me_id"));
	String do_dept = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("do_dept")));
	if(do_dept.trim().equals("")){
		do_dept ="0";
	}
	String sq_realname = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_realname")));
	String model_id = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("model_id")));
	String sq_sex = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_sex")));
	String sq_phone = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_phone")));
	String sq_card_id = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_card_id")));
	String sq_email = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_email")));
	String sq_vocation = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_vocation")));
	String sq_address = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_address")));
	String pur_id = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("pur_id")));
	String sq_title = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_title")));
	String sq_content = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_content")));
	String is_open = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("is_open")));
	if(is_open.trim().equals("")){
		is_open ="1";
	}
	String submit_name = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submit_name")));
	if(submit_name.trim().equals("")){
		submit_name  = com.deya.wcm.services.appeal.cpDept.CpDeptManager.getCpDeptName(Integer.parseInt(do_dept));
	}
	String sq_atta_name = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_atta_name")));
	String sq_atta_path = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("sq_atta_path")));
	int mod_id = Integer.parseInt(model_id);
	
	SQBean sb = new SQBean();
	SQAttachment sqa = new SQAttachment();
	if(sq_atta_path != null && !"".equals(sq_atta_path))
	{
		sqa.setAtt_path(sq_atta_path);
		sqa.setAtt_name(sq_atta_name);
	}
	
	//System.out.println("do_dept----" + do_dept);
	//System.out.println("submit_name----" + submit_name);
	//System.out.println("sq_sex----" + sq_sex);
	//System.out.println("is_open----" + is_open);
	//System.out.println("me_id----" + me_id);
	
	sb.setModel_id(mod_id);
	sb.setSq_realname(sq_realname);
	sb.setDo_dept(Integer.parseInt(do_dept));
	sb.setSq_sex(Integer.parseInt(sq_sex));
	sb.setSq_phone(sq_phone);
	sb.setSq_card_id(sq_card_id);
	sb.setSq_email(sq_email);
	sb.setSq_vocation(sq_vocation);
	sb.setSq_address(sq_address);
	sb.setPur_id(Integer.parseInt(pur_id));
	sb.setSq_title(sq_title);
	sb.setSq_content(sq_content);
	sb.setSubmit_name(submit_name);
	sb.setIs_open(Integer.parseInt(is_open));
	sb.setMe_id(Integer.parseInt(me_id));
	sb.setSq_ip(request.getRemoteAddr());
	sb.setSq_type(10);//表示是从手机客户端上提交的

	SQBean newSB = SQManager.insertSQ(sb,sqa);
	if(newSB != null)
	{
		out.println(jsoncallback+"([{\"sq_code\":\""+newSB.getSq_code()+"\",\"query_code\":\""+newSB.getQuery_code()+"\"}])");
	}else
		out.println(jsoncallback+"([{\"error\":\"提交失败\"}])");
  }catch(Exception e){
	  e.printStackTrace();
  }
%>