package com.deya.wcm.services.sendInfo;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.deya.util.BeanToMapUtil;
import com.deya.util.CryptoTools;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.sendInfo.ReceiveInfoDAO;
import com.deya.wcm.services.control.site.SiteManager;

//接收信息处理
public class SendInfoDispose {
	/**
	 * 对接收的信息进行处理
	 * 
	 * @param String
	 *            xml
	 * @param String
	 *            user
	 * @return boolean
	 * */
	public static boolean sendInfoHandl(String xml, String user) {
		try {
			Node node = XmlManager.createNode(xml);
			String s_site_id = XmlManager.queryNodeValue(node, "//s_site_id");
			if(!keyIsConsistent(s_site_id,user))
			{
				return false;
			}
			String site_id = XmlManager.queryNodeValue(node, "//t_site_id");
			String app_id = XmlManager.queryNodeValue(node, "//t_app_id");
			String s_site_domain = XmlManager.queryNodeValue(node, "//s_site_domain");
			String s_site_name = XmlManager.queryNodeValue(node, "//s_site_name");
			String s_user_id = XmlManager.queryNodeValue(node, "//s_user_id");
			String s_user_name = XmlManager.queryNodeValue(node, "//s_user_name");
			String s_send_dtime = XmlManager.queryNodeValue(node, "//s_send_dtime");
			
			NodeList infoList = XmlManager.queryNodeList(node,"//info");
			if(infoList != null && infoList.getLength() > 0)
			{
				for(int i=0;i<infoList.getLength();i++)
				{
					Node cn = infoList.item(i);
					ReceiveInfoBean rifb = xmlToBean(XmlManager.queryNode(cn, "r_content"));
					System.out.println(rifb.getTitle());
					rifb.setCat_id(Integer.parseInt(XmlManager.queryNodeValue(cn, "r_cat_id")));
					rifb.setSend_record_id(Integer.parseInt(XmlManager.queryNodeValue(cn, "send_record_id")));
					rifb.setContent(XmlManager.node2String(XmlManager.queryNode(cn, "r_content")));
					rifb.setS_info_id(XmlManager.queryNodeValue(cn, "r_content/info_id"));
					rifb.setS_site_id(s_site_id);
					rifb.setS_site_name(s_site_name);
					rifb.setS_site_domain(s_site_domain);
					rifb.setS_user_id(Integer.parseInt(s_user_id));
					rifb.setS_user_name(s_user_name);
					rifb.setS_send_dtime(s_send_dtime);
					rifb.setSite_id(site_id);
					rifb.setApp_id(app_id);
					rifb.setS_content_url(XmlManager.queryNodeValue(cn, "r_content/content_url"));
					rifb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.RECEIVE_INFO_TABLE_NAME));
					ReceiveInfoDAO.insertReceiveInfo(rifb);				
				}
			}
			return true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 对接收的第三方信息进行处理
	 * 
	 * @param String         xml
	 * @param String         user
	 * @return boolean
	 *
	public static boolean sendInfoHandlFormThirdParty(String xml) 
	{
		try {
			Node node = XmlManager.createNode(xml);
				 System.out.println("node ========"+node);
			String s_site_id = "";
			String site_id = XmlManager.queryNodeValue(node,"//t_site_id");
			String s_site_domain = XmlManager.queryNodeValue(node,"//s_site_domain");
			String s_site_name = XmlManager.queryNodeValue(node,"//s_site_name");
			String s_user_name = XmlManager.queryNodeValue(node,"//s_user_name");
			String s_send_dtime = XmlManager.queryNodeValue(node,"//s_send_dtime");
			String s_user_id = "0";
			NodeList infoList = XmlManager.queryNodeList(node,"//info");
			if(infoList != null && infoList.getLength() > 0)
			{
				for(int i=0;i<infoList.getLength();i++)
				{
					Node cn = infoList.item(i);
					//System.out.println("sendInfoHandl****************---"+XmlManager.queryNodeValue(cn, "r_cat_id")+"---"+cn.toString());
					//ReceiveInfoBean rifb = xmlToBean(XmlManager.queryNode(cn, "r_content"));
					ReceiveInfoBean rifb  = new ReceiveInfoBean();
					
					String title = XmlManager.queryNodeValue(cn, "r_content/title");
				    String key_words = XmlManager.queryNodeValue(cn, "r_content/info_keywords");
				    String info_description = XmlManager.queryNodeValue(cn, "r_content/info_description");
				    int catId = Integer.parseInt(XmlManager.queryNodeValue(cn,"r_cat_id"));
				    String send_record_id =  XmlManager.queryNodeValue(cn,"send_record_id");
				    String catCname =  XmlManager.queryNodeValue(cn,"r_cat_name");
				    rifb.setContent(XmlManager.node2String(XmlManager.queryNode(cn, "r_content")));
					
				    if(key_words ==""){
				    	key_words = "";
				    }
				    if(info_description ==""){
				    	info_description = "";
				    }
				    if(send_record_id !=""){
				    	rifb.setSend_record_id(Integer.parseInt(send_record_id));
				    }else{
				    	rifb.setSend_record_id(0);
				    }
					rifb.setTitle(title);
					rifb.setCat_id(catId);
					rifb.setCat_cname(catCname);
					rifb.setInfo_description(info_description);
					rifb.setInfo_keywords(key_words);
					rifb.setS_info_id("0");
					rifb.setS_site_id(s_site_id);
					rifb.setS_site_name(s_site_name);
					rifb.setS_site_domain(s_site_domain);
					rifb.setS_user_id(0);
					rifb.setS_user_name(s_user_name);
					rifb.setS_send_dtime(s_send_dtime);
					rifb.setSite_id(site_id);
					rifb.setApp_id("cms");
					rifb.setS_content_url("");
					rifb.setAdopt_desc("");
					rifb.setAdopt_dtime("");
					rifb.setAdopt_status(0);
					rifb.setAdopt_user(0);
					rifb.setAuthor("");
					rifb.setEditor("");
					rifb.setInput_dtime("");
					rifb.setIs_am_tupage(0);
					rifb.setInput_user(0);
					rifb.setIs_pic(0);
					rifb.setPage_count(1);
					rifb.setIs_delete(0);
					
					rifb.setSend_record_id(0);
					rifb.setSubtitle("");
					rifb.setTupage_num(0);
					rifb.setScore(0);
					rifb.setModel_id(Integer.parseInt(XmlManager.queryNodeValue(cn,"r_content/model_id")));
					rifb.setAuthor(XmlManager.queryNodeValue(cn,"r_content/author"));
					rifb.setSource(XmlManager.queryNodeValue(cn,"r_content/source"));
					
					rifb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.RECEIVE_INFO_TABLE_NAME));
					ReceiveInfoDAO.insertReceiveInfo(rifb);				
				}
			}
			return true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	*/
	
	
	
	
	/**
	 * 对接收的第三方信息进行处理
	 * 
	 * @param String
	 *            xml
	 * @param String
	 *            user
	 * @return boolean
	 * */
	public static boolean sendInfoHandlFormThirdParty(String xml) 
	{
		try {
			Node node = XmlManager.createNode(xml);
		    //判断用户名密码是否正确
			String username = XmlManager.queryNodeValue(node,"//username");
		    String password = XmlManager.queryNodeValue(node,"//password");
		    CryptoTools tools = new CryptoTools();
			String de_pass = tools.decode(password);
			if(!"system".equals(username)||!"manager".equals(de_pass)){
				return false;
			}
			
			String s_site_id = "";
			String site_id = XmlManager.queryNodeValue(node,"//t_site_id");
			String s_site_domain = XmlManager.queryNodeValue(node,"//s_site_domain");
			String s_site_name = XmlManager.queryNodeValue(node,"//s_site_name");
			String s_user_name = XmlManager.queryNodeValue(node,"//s_user_name");
			String s_send_dtime = XmlManager.queryNodeValue(node,"//s_send_dtime");
			String s_user_id = "0";
			NodeList infoList = XmlManager.queryNodeList(node,"//info");
			if(infoList != null && infoList.getLength() > 0)
			{
				for(int i=0;i<infoList.getLength();i++)
				{
					Node cn = infoList.item(i);
					//System.out.println("sendInfoHandl****************---"+XmlManager.queryNodeValue(cn, "r_cat_id")+"---"+cn.toString());
					//ReceiveInfoBean rifb = xmlToBean(XmlManager.queryNode(cn, "r_content"));
					ReceiveInfoBean rifb  = new ReceiveInfoBean();
					
					String title = XmlManager.queryNodeValue(cn, "r_content/title");
				    String key_words = XmlManager.queryNodeValue(cn, "r_content/info_keywords");
				    String info_description = XmlManager.queryNodeValue(cn, "r_content/info_description");
				    int catId = Integer.parseInt(XmlManager.queryNodeValue(cn,"r_cat_id"));
				    String send_record_id =  XmlManager.queryNodeValue(cn,"send_record_id");
				    String catCname =  XmlManager.queryNodeValue(cn,"r_cat_name");
				    rifb.setContent(XmlManager.node2String(XmlManager.queryNode(cn, "r_content")));
					
				    if(key_words ==""){
				    	key_words = "";
				    }
				    if(info_description ==""){
				    	info_description = "";
				    }
				    if(send_record_id !=""){
				    	rifb.setSend_record_id(Integer.parseInt(send_record_id));
				    }else{
				    	rifb.setSend_record_id(0);
				    }
					rifb.setTitle(title);
					rifb.setCat_id(catId);
					rifb.setCat_cname(catCname);
					rifb.setInfo_description(info_description);
					rifb.setInfo_keywords(key_words);
					rifb.setS_info_id("0");
					rifb.setS_site_id(s_site_id);
					rifb.setS_site_name(s_site_name);
					rifb.setS_site_domain(s_site_domain);
					rifb.setS_user_id(0);
					rifb.setS_user_name(s_user_name);
					rifb.setS_send_dtime(s_send_dtime);
					rifb.setSite_id(site_id);
					rifb.setApp_id("cms");
					rifb.setS_content_url("");
					rifb.setAdopt_desc("");
					rifb.setAdopt_dtime("");
					rifb.setAdopt_status(0);
					rifb.setAdopt_user(0);
					rifb.setAuthor("");
					rifb.setEditor("");
					rifb.setInput_dtime("");
					rifb.setIs_am_tupage(0);
					rifb.setInput_user(0);
					rifb.setIs_pic(0);
					rifb.setPage_count(1);
					rifb.setIs_delete(0);
					  
					rifb.setSend_record_id(0);
					rifb.setSubtitle("");
					rifb.setTupage_num(0);
					rifb.setScore(0);
					rifb.setModel_id(Integer.parseInt(XmlManager.queryNodeValue(cn,"r_content/model_id")));
					rifb.setAuthor(XmlManager.queryNodeValue(cn,"r_content/author"));
					rifb.setSource(XmlManager.queryNodeValue(cn,"r_content/source"));
					        String str1 = PublicTableDAO.RECEIVE_INFO_TABLE_NAME;
					        int in2 = PublicTableDAO.getIDByTableName(str1);
					rifb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.RECEIVE_INFO_TABLE_NAME));
					ReceiveInfoDAO.insertReceiveInfo(rifb);	
					NodeList filesList = XmlManager.queryNodeList(node, "//files");
					if(filesList != null && filesList.getLength() > 0)
			        {
			          for (int m = 0; m < filesList.getLength(); ++m)
			          {
			            Node im = filesList.item(m);
			            String file_url = XmlManager.queryNodeValue(im, "f_content/file_url");
			            String file_name = XmlManager.queryNodeValue(im, "f_content/file_name");
			            String file_code = XmlManager.queryNodeValue(im, "f_content/file_code");
			            if(null==file_code||"".equals(file_code)){
			            	break; 
			            }
			            SiteBean siteBean = SiteManager.getSiteBeanBySiteID(site_id);
			            String uploadpath = siteBean.getSite_path();
			            file_url = uploadpath + file_url.substring(0, file_url.lastIndexOf("/") + 1);
			            SendInfoUtil.decoderBase64File(file_code, file_url, file_name);
			          }
				    }
				}
			}
			return true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将content标签的子内容转换成ReceiveInfoBean
	 * 
	 * @param Node 
	 * @param Map <String,String>
	 * @return 
	 * */
	public static ReceiveInfoBean xmlToBean(Node content_node)
	{
		Map<String,String> m = new HashMap<String,String>();
		xmlToMap(content_node,m);
		try {
			return (ReceiveInfoBean)BeanToMapUtil.convertMap(com.deya.wcm.bean.sendInfo.ReceiveInfoBean.class, m);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将content标签的子内容转换成map
	 * 
	 * @param Node 
	 * @param Map <String,String>
	 * @return 
	 * */
	public static void xmlToMap(Node content_node,Map<String,String> m)
	{
		try{System.out.println(m.size()+"  "+content_node.toString());
			for(int i=0;i<content_node.getChildNodes().getLength();i++)
			{
				Node n = content_node.getChildNodes().item(i);
				String keys = n.getNodeName();
				m.put(keys, XmlManager.queryNodeValue(n));				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 判断user字符串是否和报送的站点ID一致
	 * 
	 * @param String
	 *            xml
	 * @param String
	 *            user
	 * @return boolean
	 * */
	public static boolean keyIsConsistent(String s_site_id, String user) {
		try {
			CryptoTools ct = new CryptoTools();
			return s_site_id.equals(ct.decode(user));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		/*String xml = "<cicrowcm><t_site_id><![CDATA[HIWCMdemo]]></t_site_id><t_app_id>cms</t_app_id><s_site_id><![CDATA[HIWCMdemo]]></s_site_id><s_site_domain><![CDATA[demo.cicro.net]]></s_site_domain><s_site_name><![CDATA[政务门户演示网站]]></s_site_name><s_user_id><![CDATA[1]]></s_user_id><s_user_name><![CDATA[系统管理员]]></s_user_name><s_send_dtime><![CDATA[2012-07-03 14:29:29]]></s_send_dtime><infoList><info><r_cat_id>1020</r_cat_id><send_record_id>18</send_record_id><r_content><info_content><![CDATA[<p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 2004年12月24号，由西安市使用正版软件工作领导小组办公室、西安市经济委员会、西安市软件协会共同主办的《西安市政府部门使用正版软件培训会》圆满结束。此次参会单位包括西安市政府各工作部门、各直属机构共40多家政府部门，参会代表均为各单位的信息化建设领导及技术负责人。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 与会的软件公司包括微软（中国）、北京金山、北京冠群金辰、上海中标软件、时光软件、西安三茗科技等一批全国及本地的优秀知名软件企业，所涉及的软件应用领域涵盖操作系统、中间件、办公软件、网络安全软件等等，各家企业代表均发表了精彩的演讲。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 作为与会代表中，唯一一家专著于政府门户网站建设领域应用解决方案的软件提供商，时光软件公司代表孟冰丽女士在大会上发表了精彩的演讲，就政府信息门户（GIP）的建设、发展方向、解决方案等领域与各级政府部门参加培训人员进行了深入的探讨，对时光公司自主研发的动态网站建设平台产品Cicro 3e WS进行了详细的演示介绍，激发了与会代表的浓厚兴趣，并获得了与会代表的一致好评。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 会后，有多家政府部门领导主动与时光公司代表咨询产品的相关技术问题，并表示了长期合作的愿望，相信时光公司在各级政府部门领导的支持下，将凭借优秀的产品很快成长为全国政府门户网站建设领域的知名企业。</font></p> ]]></info_content><pre_title><![CDATA[]]></pre_title><info_keywords><![CDATA[]]></info_keywords><opt_dtime><![CDATA[]]></opt_dtime><month_hits><![CDATA[0]]></month_hits><info_description><![CDATA[]]></info_description><i_ver><![CDATA[0]]></i_ver><cat_id><![CDATA[]]></cat_id><modify_user><![CDATA[]]></modify_user><cat_cname><![CDATA[]]></cat_cname><description><![CDATA[ ]]></description><title_color><![CDATA[]]></title_color><title_hashkey><![CDATA[0]]></title_hashkey><is_pic><![CDATA[0]]></is_pic><modify_dtime><![CDATA[]]></modify_dtime><hits><![CDATA[]]></hits><editor><![CDATA[]]></editor><model_id><![CDATA[11]]></model_id><info_status><![CDATA[8]]></info_status><comment_num><![CDATA[0]]></comment_num><app_id><![CDATA[]]></app_id><prepage_wcount><![CDATA[0]]></prepage_wcount><is_allow_comment><![CDATA[0]]></is_allow_comment><weight><![CDATA[60]]></weight><thumb_url><![CDATA[]]></thumb_url><day_hits><![CDATA[0]]></day_hits><auto_desc><![CDATA[]]></auto_desc><page_count><![CDATA[1]]></page_count><top_title><![CDATA[]]></top_title><id><![CDATA[]]></id><author><![CDATA[时光软件]]></author><title><![CDATA[时光软件受邀参加西安市政府部门使用正版软件培训会]]></title><is_auto_up><![CDATA[0]]></is_auto_up><word_count><![CDATA[1130]]></word_count><week_hits><![CDATA[0]]></week_hits><step_id><![CDATA[]]></step_id><is_am_tupage><![CDATA[0]]></is_am_tupage><input_user><![CDATA[]]></input_user><released_dtime><![CDATA[]]></released_dtime><lasthit_dtime><![CDATA[]]></lasthit_dtime><is_host><![CDATA[]]></is_host><tags><![CDATA[时光新闻]]></tags><up_dtime><![CDATA[]]></up_dtime><down_dtime><![CDATA[]]></down_dtime><is_auto_down><![CDATA[]]></is_auto_down><info_id><![CDATA[1666]]></info_id><site_id><![CDATA[]]></site_id><input_dtime><![CDATA[]]></input_dtime><final_status><![CDATA[0]]></final_status><content_url><![CDATA[]]></content_url><source><![CDATA[市场部]]></source><from_id><![CDATA[]]></from_id><wf_id><![CDATA[]]></wf_id><subtitle><![CDATA[]]></subtitle><tupage_num><![CDATA[0]]></tupage_num></r_content></info><info><r_cat_id>1020</r_cat_id><send_record_id>19</send_record_id><r_content><info_content><![CDATA[<p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\">&nbsp;&nbsp;&nbsp; 近日，我公司与北京东方通科技发展有限责任公司（以下称东方通科技）达成共识，为了共同发展，双方在全国政府、公安、企业等领域进行战略合作，全面推广时光软件基于东方通科技中间件产品开发的应用集成业务系统，或者共同开发出的具有市场竞争力的信息化、业务应用解决方案。 </font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\">&nbsp;&nbsp;&nbsp; 据悉，双方将利用自己的市场资源和销售渠道，共同进行市场推广和销售。本次合作是双方在长期合作基础上的一次战略提升，为双方产品在各自领域的互补性作了很好的注解。</font></p> ]]></info_content><pre_title><![CDATA[]]></pre_title><info_keywords><![CDATA[]]></info_keywords><opt_dtime><![CDATA[]]></opt_dtime><month_hits><![CDATA[0]]></month_hits><info_description><![CDATA[]]></info_description><i_ver><![CDATA[0]]></i_ver><cat_id><![CDATA[]]></cat_id><modify_user><![CDATA[]]></modify_user><cat_cname><![CDATA[]]></cat_cname><description><![CDATA[ ]]></description><title_color><![CDATA[]]></title_color><title_hashkey><![CDATA[0]]></title_hashkey><is_pic><![CDATA[0]]></is_pic><modify_dtime><![CDATA[]]></modify_dtime><hits><![CDATA[]]></hits><editor><![CDATA[]]></editor><model_id><![CDATA[11]]></model_id><info_status><![CDATA[8]]></info_status><comment_num><![CDATA[0]]></comment_num><app_id><![CDATA[]]></app_id><prepage_wcount><![CDATA[0]]></prepage_wcount><is_allow_comment><![CDATA[0]]></is_allow_comment><weight><![CDATA[60]]></weight><thumb_url><![CDATA[]]></thumb_url><day_hits><![CDATA[0]]></day_hits><auto_desc><![CDATA[]]></auto_desc><page_count><![CDATA[1]]></page_count><top_title><![CDATA[]]></top_title><id><![CDATA[]]></id><author><![CDATA[时光软件]]></author><title><![CDATA[时光软件与东方通科技结成战略同盟]]></title><is_auto_up><![CDATA[0]]></is_auto_up><word_count><![CDATA[505]]></word_count><week_hits><![CDATA[0]]></week_hits><step_id><![CDATA[]]></step_id><is_am_tupage><![CDATA[0]]></is_am_tupage><input_user><![CDATA[]]></input_user><released_dtime><![CDATA[]]></released_dtime><lasthit_dtime><![CDATA[]]></lasthit_dtime><is_host><![CDATA[]]></is_host><tags><![CDATA[时光新闻]]></tags><up_dtime><![CDATA[]]></up_dtime><down_dtime><![CDATA[]]></down_dtime><is_auto_down><![CDATA[]]></is_auto_down><info_id><![CDATA[1665]]></info_id><site_id><![CDATA[]]></site_id><input_dtime><![CDATA[]]></input_dtime><final_status><![CDATA[0]]></final_status><content_url><![CDATA[]]></content_url><source><![CDATA[市场部]]></source><from_id><![CDATA[]]></from_id><wf_id><![CDATA[]]></wf_id><subtitle><![CDATA[]]></subtitle><tupage_num><![CDATA[0]]></tupage_num></r_content></info><info><r_cat_id>1021</r_cat_id><send_record_id>20</send_record_id><r_content><info_content><![CDATA[<p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 2004年12月24号，由西安市使用正版软件工作领导小组办公室、西安市经济委员会、西安市软件协会共同主办的《西安市政府部门使用正版软件培训会》圆满结束。此次参会单位包括西安市政府各工作部门、各直属机构共40多家政府部门，参会代表均为各单位的信息化建设领导及技术负责人。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 与会的软件公司包括微软（中国）、北京金山、北京冠群金辰、上海中标软件、时光软件、西安三茗科技等一批全国及本地的优秀知名软件企业，所涉及的软件应用领域涵盖操作系统、中间件、办公软件、网络安全软件等等，各家企业代表均发表了精彩的演讲。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 作为与会代表中，唯一一家专著于政府门户网站建设领域应用解决方案的软件提供商，时光软件公司代表孟冰丽女士在大会上发表了精彩的演讲，就政府信息门户（GIP）的建设、发展方向、解决方案等领域与各级政府部门参加培训人员进行了深入的探讨，对时光公司自主研发的动态网站建设平台产品Cicro 3e WS进行了详细的演示介绍，激发了与会代表的浓厚兴趣，并获得了与会代表的一致好评。</font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\" >&nbsp;&nbsp;&nbsp; 会后，有多家政府部门领导主动与时光公司代表咨询产品的相关技术问题，并表示了长期合作的愿望，相信时光公司在各级政府部门领导的支持下，将凭借优秀的产品很快成长为全国政府门户网站建设领域的知名企业。</font></p> ]]></info_content><pre_title><![CDATA[]]></pre_title><info_keywords><![CDATA[]]></info_keywords><opt_dtime><![CDATA[]]></opt_dtime><month_hits><![CDATA[0]]></month_hits><info_description><![CDATA[]]></info_description><i_ver><![CDATA[0]]></i_ver><cat_id><![CDATA[]]></cat_id><modify_user><![CDATA[]]></modify_user><cat_cname><![CDATA[]]></cat_cname><description><![CDATA[ ]]></description><title_color><![CDATA[]]></title_color><title_hashkey><![CDATA[0]]></title_hashkey><is_pic><![CDATA[0]]></is_pic><modify_dtime><![CDATA[]]></modify_dtime><hits><![CDATA[]]></hits><editor><![CDATA[]]></editor><model_id><![CDATA[11]]></model_id><info_status><![CDATA[8]]></info_status><comment_num><![CDATA[0]]></comment_num><app_id><![CDATA[]]></app_id><prepage_wcount><![CDATA[0]]></prepage_wcount><is_allow_comment><![CDATA[0]]></is_allow_comment><weight><![CDATA[60]]></weight><thumb_url><![CDATA[]]></thumb_url><day_hits><![CDATA[0]]></day_hits><auto_desc><![CDATA[]]></auto_desc><page_count><![CDATA[1]]></page_count><top_title><![CDATA[]]></top_title><id><![CDATA[]]></id><author><![CDATA[时光软件]]></author><title><![CDATA[时光软件受邀参加西安市政府部门使用正版软件培训会]]></title><is_auto_up><![CDATA[0]]></is_auto_up><word_count><![CDATA[1130]]></word_count><week_hits><![CDATA[0]]></week_hits><step_id><![CDATA[]]></step_id><is_am_tupage><![CDATA[0]]></is_am_tupage><input_user><![CDATA[]]></input_user><released_dtime><![CDATA[]]></released_dtime><lasthit_dtime><![CDATA[]]></lasthit_dtime><is_host><![CDATA[]]></is_host><tags><![CDATA[时光新闻]]></tags><up_dtime><![CDATA[]]></up_dtime><down_dtime><![CDATA[]]></down_dtime><is_auto_down><![CDATA[]]></is_auto_down><info_id><![CDATA[1666]]></info_id><site_id><![CDATA[]]></site_id><input_dtime><![CDATA[]]></input_dtime><final_status><![CDATA[0]]></final_status><content_url><![CDATA[]]></content_url><source><![CDATA[市场部]]></source><from_id><![CDATA[]]></from_id><wf_id><![CDATA[]]></wf_id><subtitle><![CDATA[]]></subtitle><tupage_num><![CDATA[0]]></tupage_num></r_content></info><info><r_cat_id>1021</r_cat_id><send_record_id>21</send_record_id><r_content><info_content><![CDATA[<p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\">&nbsp;&nbsp;&nbsp; 近日，我公司与北京东方通科技发展有限责任公司（以下称东方通科技）达成共识，为了共同发展，双方在全国政府、公安、企业等领域进行战略合作，全面推广时光软件基于东方通科技中间件产品开发的应用集成业务系统，或者共同开发出的具有市场竞争力的信息化、业务应用解决方案。 </font></p><p style=\"margin-top: 0px; margin-bottom: 20px; line-height: 25px\"><font style=\"font-size: 14px; letter-spacing: 0px\">&nbsp;&nbsp;&nbsp; 据悉，双方将利用自己的市场资源和销售渠道，共同进行市场推广和销售。本次合作是双方在长期合作基础上的一次战略提升，为双方产品在各自领域的互补性作了很好的注解。</font></p> ]]></info_content><pre_title><![CDATA[]]></pre_title><info_keywords><![CDATA[]]></info_keywords><opt_dtime><![CDATA[]]></opt_dtime><month_hits><![CDATA[0]]></month_hits><info_description><![CDATA[]]></info_description><i_ver><![CDATA[0]]></i_ver><cat_id><![CDATA[]]></cat_id><modify_user><![CDATA[]]></modify_user><cat_cname><![CDATA[]]></cat_cname><description><![CDATA[ ]]></description><title_color><![CDATA[]]></title_color><title_hashkey><![CDATA[0]]></title_hashkey><is_pic><![CDATA[0]]></is_pic><modify_dtime><![CDATA[]]></modify_dtime><hits><![CDATA[]]></hits><editor><![CDATA[]]></editor><model_id><![CDATA[11]]></model_id><info_status><![CDATA[8]]></info_status><comment_num><![CDATA[0]]></comment_num><app_id><![CDATA[]]></app_id><prepage_wcount><![CDATA[0]]></prepage_wcount><is_allow_comment><![CDATA[0]]></is_allow_comment><weight><![CDATA[60]]></weight><thumb_url><![CDATA[]]></thumb_url><day_hits><![CDATA[0]]></day_hits><auto_desc><![CDATA[]]></auto_desc><page_count><![CDATA[1]]></page_count><top_title><![CDATA[]]></top_title><id><![CDATA[]]></id><author><![CDATA[时光软件]]></author><title><![CDATA[时光软件与东方通科技结成战略同盟]]></title><is_auto_up><![CDATA[0]]></is_auto_up><word_count><![CDATA[505]]></word_count><week_hits><![CDATA[0]]></week_hits><step_id><![CDATA[]]></step_id><is_am_tupage><![CDATA[0]]></is_am_tupage><input_user><![CDATA[]]></input_user><released_dtime><![CDATA[]]></released_dtime><lasthit_dtime><![CDATA[]]></lasthit_dtime><is_host><![CDATA[]]></is_host><tags><![CDATA[时光新闻]]></tags><up_dtime><![CDATA[]]></up_dtime><down_dtime><![CDATA[]]></down_dtime><is_auto_down><![CDATA[]]></is_auto_down><info_id><![CDATA[1665]]></info_id><site_id><![CDATA[]]></site_id><input_dtime><![CDATA[]]></input_dtime><final_status><![CDATA[0]]></final_status><content_url><![CDATA[]]></content_url><source><![CDATA[市场部]]></source><from_id><![CDATA[]]></from_id><wf_id><![CDATA[]]></wf_id><subtitle><![CDATA[]]></subtitle><tupage_num><![CDATA[0]]></tupage_num></r_content></info></infoList></cicrowcm>";
		String user = "=#=v3BVN6dV/zm9aD65w1MEcw==";
		sendInfoHandl(xml, user);*/
		
		String xml = "<cicrowcm><t_site_id><![CDATA[CMS125]]></t_site_id><s_site_domain><![CDATA[www.cicro.com]]></s_site_domain><s_site_name><![CDATA[政务门户演示网站]]></s_site_name><s_user_name><![CDATA[系统管理员]]></s_user_name><s_send_dtime><![CDATA[2013-02-22 14:28:10]]></s_send_dtime><infoList><info><r_cat_id>10004</r_cat_id><send_record_id>87</send_record_id><r_content><info_content><![CDATA[<p><br />&nbsp;&nbsp;&nbsp; 在伟大的中国共产党成立90周年之际，我州各地的广大党员干部和普通百姓，都在以感恩的心情，学习和了解我们国家的执政党&mdash;&mdash;中国共产党的奋斗史、创业史和改革开放史。这是一件非常有意义的事。<br />&nbsp;&nbsp;&nbsp; 一位学者说，&ldquo;不懂历史的人没有根，淡忘历史的民族没有魂&rdquo;。我们应该记住这句名言。我们每个人都有了解党史、读懂党史、运用党史的责任。只有用党的&ldquo;三史&rdquo;来洗礼自己的思想，才能为&ldquo;做懂根的人，做有魂的民族&rdquo;打下思想基础。<br />&nbsp;&nbsp;&nbsp; &ldquo;以铜为镜可以正衣冠，以人为镜可以知得失，以史为镜可以知古今&rdquo;。学过党史的人知道，我们的党在已经走过的90年光辉历程中，真正做到了从无到有、由弱变强的转变，它带领全国各族人民战胜重重艰难险阻，成功地领导了两次革命，干了三件大事，实现了两次飞跃&hellip;&hellip;中国共产党的诞生和发展，扭转了近现代中国历史的走向，改变了中国人民的命运，对整个世界的格局和面貌也产生了广泛而深远的影响。<br />&nbsp;&nbsp;&nbsp; 了解了党史、学习了党史，可以得出这样的结沦：共产党没有自身的特殊利益，共产党的职能就是为人民服务。过去，没有共产党就没有新中国；今天，没有共产党就没有和谐发展的中国；未来，我们应该更加坚定一个信念：没有共产党就不会有未来屹立于世界强国之列的中国和中华民族。</p> ]]></info_content><info_keywords><![CDATA[]]></info_keywords><info_description><![CDATA[]]></info_description><title><![CDATA[铭记党史感党恩]]></title><model_id><![CDATA[11]]></model_id><info_status><![CDATA[8]]></info_status><author><![CDATA[余丽]]></author><final_status><![CDATA[0]]></final_status><content_url><![CDATA[/zwdt/bmzt/8573.htm]]></content_url><source><![CDATA[昌吉日报]]></source></r_content></info></infoList></cicrowcm>";
		sendInfoHandlFormThirdParty(xml);	
	}
}