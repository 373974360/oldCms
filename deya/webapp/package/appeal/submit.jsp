<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.bean.appeal.model.*,com.deya.wcm.services.appeal.model.*"%>
<%@page import="com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@ page import="com.deya.wcm.services.appeal.myddc.SqMyddcBean" %>
<%@ page import="com.deya.wcm.services.appeal.myddc.SqMyddcManager" %>
<%
	String action_type = request.getParameter("action_type"); 
	if("insertSQ".equals(action_type))
	{		
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		String auth_code = request.getParameter("auth_code");
		if(!auth_code.equals(codeSession))
		{
			out.println("<script>");
			out.println("top.alert('验证码不正确')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
		String me_id = FormatUtil.formatNullString(request.getParameter("me_id"));
		String do_dept = FormatUtil.formatNullString(request.getParameter("do_dept").trim());
		String sq_realname = FormatUtil.formatNullString(request.getParameter("sq_realname"));
		String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
		String sq_sex = FormatUtil.formatNullString(request.getParameter("sq_sex"));
		String sq_phone = FormatUtil.formatNullString(request.getParameter("sq_phone"));
		String sq_card_id = FormatUtil.formatNullString(request.getParameter("sq_card_id"));
		String sq_email = FormatUtil.formatNullString(request.getParameter("sq_email"));
		String sq_vocation = FormatUtil.formatNullString(request.getParameter("sq_vocation"));
		String sq_address = FormatUtil.formatNullString(request.getParameter("sq_address"));
		String pur_id = FormatUtil.formatNullString(request.getParameter("pur_id"));
		String sq_title = FormatUtil.formatNullString(request.getParameter("sq_title"));
		String sq_content = FormatUtil.formatNullString(request.getParameter("sq_content"));
		String is_open = FormatUtil.formatNullString(request.getParameter("is_open"));
		String submit_name = FormatUtil.formatNullString(request.getParameter("submit_name").trim());
		String sq_atta_name = FormatUtil.formatNullString(request.getParameter("sq_atta_name"));
		String sq_atta_path = FormatUtil.formatNullString(request.getParameter("sq_atta_path"));
		String area_id = FormatUtil.formatNullString(request.getParameter("area_id"));
		String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
		int mod_id = Integer.parseInt(model_id);
		
		if(sq_title.equals("")){
			out.println("<script>");
			out.println("top.alert('验证码不正确')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
		SQBean sb = new SQBean();
		SQAttachment sqa = new SQAttachment();
		if(sq_atta_path != null && !"".equals(sq_atta_path))
		{
			sqa.setAtt_path(sq_atta_path);
			sqa.setAtt_name(sq_atta_name);
		}
		if(area_id != null && !"".equals(area_id))
			sb.setArea_id(Integer.parseInt(area_id));

		if(cat_id != null && !"".equals(cat_id))
			sb.setCat_id(Integer.parseInt(cat_id));
		sb.setModel_id(mod_id);
		sb.setSq_realname(sq_realname);
		if(do_dept !=""){
			sb.setDo_dept(Integer.parseInt(do_dept));
		}else{
			sb.setDo_dept(0);
		}
		if(sq_sex !=""){
			sb.setSq_sex(Integer.parseInt(sq_sex));
		}else{
			sb.setSq_sex(0);
		}
		sb.setSq_phone(sq_phone);
		sb.setSq_card_id(sq_card_id);
		sb.setSq_email(sq_email);
		sb.setSq_vocation(sq_vocation);
		sb.setSq_address(sq_address);
		if(pur_id !=""){
			sb.setPur_id(Integer.parseInt(pur_id));
		}else{
			sb.setPur_id(0);
		}
		sb.setSq_title(sq_title);
		sb.setSq_content(sq_content);
		sb.setSubmit_name(submit_name);
		if(is_open !=""){
			sb.setIs_open(Integer.parseInt(is_open));
		}else{
			sb.setIs_open(0);
		}
		if(me_id !=""){
			sb.setMe_id(Integer.parseInt(me_id));
		}else{
			sb.setMe_id(0);
		}
		sb.setSq_ip(request.getRemoteAddr());
		if(do_dept !="")
		{
			SQBean newSB = SQManager.insertSQ(sb,sqa);
			if(newSB != null)
			{
				//根据业务得到得到扩展字段的英文名称
				List<CPFrom> l = CPFromManager.getCPFormListByModel(mod_id);
				List<SQCustom> sc_list = new ArrayList<SQCustom>();
				if(l != null && l.size() > 0)
				{
					for(int i=0;i<l.size();i++)
					{
						SQCustom sc = new SQCustom();
						String filed_ename = l.get(i).getField_ename();
						String cu_value = request.getParameter(filed_ename);
						if(cu_value != null && !"".equals(cu_value.trim()))
						{
							sc.setCu_key(filed_ename);
							sc.setCu_value(cu_value);
							sc.setModel_id(mod_id);
							sc.setSq_id(newSB.getSq_id());
							sc_list.add(sc);
						}
					}
					if(sc_list != null && sc_list.size() > 0)
						SQManager.insertSQCursom(sc_list);//插入扩展字段
				}
                /*
					try{
						HttpClient httpclient = new HttpClient();
						PostMethod post = new PostMethod("http://sx.ums86.com:8899/sms/Api/Send.do");//
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
						post.addParameter("SpCode", "218968");
						post.addParameter("LoginName", "wn_xxh");
						post.addParameter("Password", "wn1234");
						post.addParameter("MessageContent", "您的信件已提交成功，信件编号："+ newSB.getSq_code() + "，查询码：" + newSB.getQuery_code() + "，请等待回复处理，感谢您对我们工作的支持！");
						post.addParameter("UserNumber", sq_phone);
						post.addParameter("SerialNumber", "");
						post.addParameter("ScheduleTime", "");
						post.addParameter("whitevalid", "1");
						post.addParameter("f", "1");
						httpclient.executeMethod(post);
					}catch (Exception e) {
						e.printStackTrace();
					}
					*/
				out.println("<script>");
				out.println("top.$('#appealForm').remove()");		
				out.println("top.$('#result_div #result_sq_code').append('"+newSB.getSq_code()+"')");
				out.println("top.$('#result_div #result_query_code').append('"+newSB.getQuery_code()+"')");		
				out.println("top.$('#result_div').show()");
				out.println("</script>");
			}
		}
	}
	/*if("saveScore".equals(action_type))
	{
        String sq_code = FormatUtil.formatNullString(request.getParameter("sq_code"));
        String query_code = FormatUtil.formatNullString(request.getParameter("query_code"));
        String sq_id = FormatUtil.formatNullString(request.getParameter("sq_id"));
        String sat_score_str = "";
        String raty_score_str = "";
        String totle = FormatUtil.formatNullString(request.getParameter("totle"));
        if(totle != null && !"".equals(totle))
        {
            int size = Integer.parseInt(totle);
            for(int i = 1; i <= size; i++)
            {
                String sat_score = FormatUtil.formatNullString(request.getParameter("sat_score" + i));
                String raty_score = FormatUtil.formatNullString(request.getParameter("raty_score" + i));
                sat_score_str = sat_score_str + sat_score + ",";
                raty_score_str = raty_score_str + raty_score + ",";
            }
            sat_score_str = sat_score_str.substring(0, sat_score_str.length() - 1);
            raty_score_str = raty_score_str.substring(0, raty_score_str.length() - 1);
        }
        else
        {
            out.println("<script>");
            out.println("top.alert('投票失败')");
            out.println("</script>");
            return;
        }

        SQBean sb = SQManager.searchBrowserSQBean(sq_code,query_code);

        if(sb == null || !sq_id.equals(sb.getSq_id()+""))
        {
            out.println("<script>");
            out.println("top.alert('查询码及信件编码不正确')");
            out.println("</script>");
            return;
        }

        if(SQManager.saveScore(sq_id,sat_score_str,raty_score_str))
        {
            out.println("<script>");
            out.println("top.alert('投票成功')");
            out.println("top.$('BeginSatis_div').remove()");
            out.println("</script>");
            return;
        }else
        {
            out.println("<script>");
            out.println("top.alert('投票失败')");
            out.println("</script>");
            return;
        }
	}*/

	if("saveScore".equals(action_type))
	{
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		String auth_code = request.getParameter("auth_code");
		if(!auth_code.equals(codeSession))
		{
			out.println("<script>");
			out.println("top.alert('验证码不正确')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
		String sq_id = FormatUtil.formatNullString(request.getParameter("sq_id"));
		String myd = FormatUtil.formatNullString(request.getParameter("myd"));
		SqMyddcBean sqMyddcBean = new SqMyddcBean();
		sqMyddcBean.setSq_id(Integer.parseInt(sq_id));
		sqMyddcBean.setMyd(myd);
		boolean insertSqMyddc = SqMyddcManager.insertSqMyddc(sqMyddcBean, null);
		if(insertSqMyddc)
		{
			out.println("<script>");
			out.println("top.alert('投票成功')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}else
		{
			out.println("<script>");
			out.println("top.alert('投票失败')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
	}
%>