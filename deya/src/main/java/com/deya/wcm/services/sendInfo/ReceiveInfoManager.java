package com.deya.wcm.services.sendInfo;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.deya.util.BeanToMapUtil;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.PicItemBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.dao.sendInfo.ReceiveInfoDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelConfig;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.webServices.sendInfo.SendClient;

public class ReceiveInfoManager {
	/**
     * 得到接收的栏目列表
     * @param String site_id
     * @return List
     * */
	public static List<ReceiveInfoBean> getReceiveCateInfoList(String site_id)
	{
		List<ReceiveInfoBean> l = ReceiveInfoDAO.getReceiveCateInfoList(site_id);
		if(l != null && l.size() > 0)
		{
			for(ReceiveInfoBean rib : l)
			{
				try{
					CategoryBean cb = CategoryManager.getCategoryBeanCatID(rib.getCat_id(), site_id);
					rib.setCat_cname(cb.getCat_cname());
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
		return l;
	}
	
	/**
     * 得到报送到该站点的信息总数
     * @param 
     * @return String
     * */
	public static String getReceiveInfoCount(Map<String,String> m)
	{
		return ReceiveInfoDAO.getReceiveInfoCount(m);
	}
	
	/**
     * 得到报送到该站点的信息列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ReceiveInfoBean> getReceiveInfoList(Map<String,String> m)
	{
		return ReceiveInfoDAO.getReceiveInfoList(m);
	}
	
	/**
     * 删除报送信息
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean deleteReceiveInfo(Map<String,String> m,SettingLogsBean stl)
	{
		return ReceiveInfoDAO.deleteReceiveInfo(m, stl);
	}
	
	/**
     * 采用报送信息操作
     * @param Map
     * @return boolean
     * */
	public static boolean adoptReceiveInfo(Map<String,String> m,SettingLogsBean stl)
	{
		Map<String,String> result_map = new HashMap<String,String>();//返回结果的站点聚合,报送站点域名，记录表的ID
		
		m.put("adopt_user", stl.getUser_id()+"");
		m.put("adopt_dtime", DateUtil.getCurrentDateTime());
		if("1".equals(m.get("adopt_status")))
		{
			//处理信息采用
			if(!adoptReceiveInfoHandl(m,result_map,stl))
			{
				return false;
			}			
		}else
		{
			//不采用
			dontAdoptRInfoHandl(m,result_map,stl);
		}		
		if(ReceiveInfoDAO.adoptReceiveInfo(m, stl)){
			//提交采用记录
			returnRecord(m,result_map);
			return true;
		}else
			return false;
	}
	
	/**
     * 不采用操作
     * @param Map
     * @param Map<String,String> result_map
     * @return boolean
     * */
	public static void dontAdoptRInfoHandl(Map<String,String> m,Map<String,String> result_map,SettingLogsBean stl)
	{
		List<ReceiveInfoBean> l = ReceiveInfoDAO.getReceiveInfoListForIDS(m);
		if(l != null && l.size() > 0)
		{
			for(ReceiveInfoBean rib : l)
			{
				//没有记录过采用记录的
				if(rib.getIs_record() == 0)
				{
					if(result_map.containsKey("s_site_domain"))
					{
						String temp_id = result_map.get("domain")+","+rib.getSend_record_id();
						result_map.put(rib.getS_site_domain(), temp_id);
					}else
						result_map.put(rib.getS_site_domain(), rib.getSend_record_id()+"");
				}
			}
		}
	}
	
	/**
     * 采用操作
     * @param Map
     * @param Map<String,String> result_map
     * @return boolean
     * */
	public static boolean adoptReceiveInfoHandl(Map<String,String> m,Map<String,String> result_map,SettingLogsBean stl)
	{
		List<ReceiveInfoBean> l = ReceiveInfoDAO.getReceiveInfoListForIDS(m);
		if(l != null && l.size() > 0)
		{
			for(ReceiveInfoBean rib : l)
			{
				try{
					int new_id = InfoBaseManager.getNextInfoId();
					String model_ename = ModelManager.getModelEName(rib.getModel_id());
					Object o = xmlToObject(rib.getContent(),model_ename,new_id);
					if(o != null)
					{						
						BeanUtils.setProperty(o, "id", new_id);
						BeanUtils.setProperty(o, "info_id", new_id);
						BeanUtils.setProperty(o, "site_id", rib.getSite_id());
						BeanUtils.setProperty(o, "cat_id", rib.getCat_id());
						BeanUtils.setProperty(o, "input_user", m.get("adopt_user"));
						BeanUtils.setProperty(o, "editor", UserManager.getUserRealName(m.get("adopt_user")));
						BeanUtils.setProperty(o, "input_dtime", m.get("adopt_dtime"));
						BeanUtils.setProperty(o, "weight", 60);
						BeanUtils.setProperty(o, "info_status", m.get("info_status"));
						BeanUtils.setProperty(o, "app_id", "cms");
						BeanUtils.setProperty(o, "from_id", "0");
						//如果不是链接类型的，content_url清空
						if(!"12".equals(BeanUtils.getProperty(o, "model_id")))
							BeanUtils.setProperty(o, "content_url", "");
						if(ModelUtil.insert(o, model_ename, stl))
						{
							//没有记录过采用记录的
							if(rib.getIs_record() == 0)
							{
								if(result_map.containsKey("s_site_domain"))
								{
									String temp_id = result_map.get("domain")+","+rib.getSend_record_id();
									result_map.put(rib.getS_site_domain(), temp_id);
								}else
									result_map.put(rib.getS_site_domain(), rib.getSend_record_id()+"");
							}
						}
						
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
     * 将xml转为内容模型对象
     * @param String xml
     * @return Object
     * */
	public static Object xmlToObject(String xml,String model_ename,int new_id)
	{
		try {
			xml = xml.replaceAll("\n|\r\n", "");
			Node node = XmlManager.createNode(xml);
			
			Map<String,Object> info_map = new HashMap<String,Object>();
			xmlToMap(node, info_map,new_id);
			//System.out.println("info_map============="+info_map);
			//ModelConfig.getModelMap(model_ename);
			//System.out.println("getModelMap============="+ModelConfig.getModelMap(model_ename));
			return BeanToMapUtil.convertMap(Class.forName(ModelConfig.getModelMap(model_ename).get("class_name")), info_map);			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将content标签的子内容转换成map
	 * 
	 * @param Node 
	 * @param Map <String,String>
	 * @return 
	 * */
	@SuppressWarnings("unchecked")
	public static void xmlToMap(Node content_node,Map<String,Object> m,int new_id)
	{
		try{
			for(int i=0;i<content_node.getChildNodes().getLength();i++)
			{
				Node n = content_node.getChildNodes().item(i);
				String keys = n.getNodeName();
				if("item_list".equals(keys))
				{
					List<PicItemBean> item_list = new ArrayList<PicItemBean>();
					for(int j=0;j<n.getChildNodes().getLength();j++)
					{
						Node item_node = n.getChildNodes().item(j);
						PicItemBean pib = new PicItemBean();
						pib.setInfo_id(new_id);
						pib.setPic_path(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "pic_path")));
						pib.setPic_note(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "pic_note")));
						pib.setPic_sort(Integer.parseInt(XmlManager.queryNodeValue(item_node, "pic_sort")));
						pib.setPic_title(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "pic_title")));
						pib.setPic_url(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "pic_url")));
						//System.out.println(pib.getPic_url()+" "+pib.getPic_path()+" "+pib.getPic_note()+" "+pib.getPic_sort()+" "+pib.getPic_title());
						item_list.add(pib);
					}
					m.put("item_list", item_list);
					continue;
				}/*
				if("file_list".equals(keys))
				{
					List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
					for(int j=0;j<n.getChildNodes().getLength();j++)
					{
						Node item_node = n.getChildNodes().item(j);
						GKResFileBean fb = new GKResFileBean();
						fb.setInfo_id(new_id);
						fb.setRes_name(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "res_name")));
						fb.setRes_url(FormatUtil.formatNullString(XmlManager.queryNodeValue(item_node, "res_url")));
						fb.setSort_id(Integer.parseInt(XmlManager.queryNodeValue(item_node, "sort_id")));
						file_list.add(fb);
					}
					m.put("file_list", file_list);
					continue;
				}*/
				
				String values = XmlManager.queryNodeValue(n, "//"+keys);
				if(values != null)
					m.put(keys, values);				
			}
			//将图片详细内容写入到item列表的第一个对象中
			if(m.containsKey("item_list"))
			{
				List<PicItemBean> item_list = (List<PicItemBean>)m.get("item_list");
				if(item_list != null && item_list.size() > 0)
				{
					item_list.get(0).setPic_content(m.get("pic_content")+"");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回采用记录,调用webservices接口，将采用信息ID，采用时间，状态，意见返回过去
	 * @param Map 
	 * @return 
	 * */
	public static void returnRecord(Map<String,String> m,Map<String,String> result_map)
	{
		if(result_map != null && result_map.size() > 0)
		{
			String adopt_dtime = m.get("adopt_dtime");
			String adopt_desc = m.get("adopt_desc");
			String adopt_status = m.get("adopt_status");
			
			Set<String> domain_set = result_map.keySet();
			for(String domain : domain_set)
			{
				StringBuffer xml = new StringBuffer();
				String result_ids = result_map.get(domain);
				xml.append("<cicrowcm>");
				xml.append("<result_ids>"+result_ids+"</result_ids>");
				xml.append("<adopt_dtime>"+adopt_dtime+"</adopt_dtime>");
				xml.append("<adopt_desc>"+adopt_desc+"</adopt_desc>");
				xml.append("<adopt_status>"+adopt_status+"</adopt_status>");
				xml.append("</cicrowcm>");
				SendClient.getServicesObj(domain).recordAdoptStatus(xml.toString());
			}
		}
	}
	
	public static void main(String args[])
	{
		xmlToObject("<r_content><pre_title/><info_keywords/><opt_dtime/><month_hits>1</month_hits><info_description/><i_ver>0</i_ver><cat_id/><modify_user/><cat_cname/><description/><title_color/><title_hashkey>0</title_hashkey><is_pic>0</is_pic><item_list><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121007.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>1</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121022.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>2</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121024.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>3</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121027.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>4</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121030.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>5</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121033.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>6</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121036.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>7</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121040.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>8</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121043.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>9</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121047.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>10</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121051.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>11</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121055.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>12</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011121059.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>13</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122002.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>14</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122004.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>15</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122007.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>16</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122010.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>17</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122014.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>18</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122017.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>19</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122021.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>20</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122024.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>21</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122027.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>22</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122030.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>23</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122033.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>24</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122037.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>25</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122041.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>26</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122045.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>27</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122048.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>28</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item><pic_item><info_id/><att_id/><pic_path>http://img.cicro.net/HIWCMdemo/201110/201110011122051.jpg</pic_path><pic_note>09款雷克萨斯LS</pic_note><pic_url/><pic_sort>29</pic_sort><pic_title>雷克萨斯LS</pic_title></pic_item></item_list><modify_dtime/><pic_content>&lt;div&gt;雷克萨斯（Lexus）是日本丰田汽车公司旗下的豪华车品牌，它于1983年被首次提出，但仅用十几年的时间，自1999年起，在美国的销量超过奔驰、宝马，成为全美豪华车销量最大的品牌。过去，Lexus在国内的中文译名是凌志，2004年6月8日，丰田公司在北京宣布将Lexus的中文译名由“凌志”改为“雷克萨斯”，并开始在中国建立特许经销店，开始全面进军中国豪华车市。&lt;/div&gt;&lt;div&gt;&lt;div&gt;用椭圆环绕的L字母，根据美国丰田汽车销售公司的官方说法，这个椭圆弧度依照精确的数学公式修 &amp;nbsp;雷克萨斯标志&lt;/div&gt;&lt;div&gt;饰，动用三个以上的设计商和广告商，花了半年多的时间才完成：这个脱颖而出的标志，击败了五个设计比稿。1987年，摩利设计公司(Molly DesignsInc.)负责人摩利·山德斯(MollySanders)，花了三个月的时间精巧制作出这个别具特色的椭圆和L，取代原先最有希望获选的版本——一个没有圆圈环绕，看起来像海鸥翅膀的L。&amp;nbsp;&lt;/div&gt;&lt;/div&gt;&lt;div&gt;&lt;div&gt;豪华硬顶敞篷运动型轿车-雷克萨斯IS 300C(图库论坛)近乎完美的诠释了精致运动之美。精致，大到四 &amp;nbsp;雷克萨斯IS 300C&lt;/div&gt;&lt;div&gt;年十万公里免费保修保养、Mark Levinson顶级音响，硬顶敞篷双模式；小到为安全可单开的驾驶门，更有超越想象的G-BOOK智能副驾，LEXUS招牌式的精致与豪华体贴到骨头里。运动，它前225后245的宽胎，前双叉臂后多连杆的悬架系统，加上拨片换挡等跑车元素，让您更深刻的感受驾驭的乐趣。 　　激情，舒适，优雅，随性，雷克萨斯IS300C，为都市时尚女性准备的完美座驾。&lt;/div&gt;&lt;div&gt;&lt;br /&gt;&lt;/div&gt;&lt;/div&gt;&lt;div&gt;&lt;br /&gt;&lt;/div&gt;</pic_content><hits/><editor/><model_id>10</model_id><info_status>8</info_status><comment_num>0</comment_num><app_id/><is_allow_comment>0</is_allow_comment><weight>60</weight><thumb_url/><day_hits>1</day_hits><auto_desc/><page_count>0</page_count><top_title/><id/><author>宋玉锋</author><title>雷克萨斯LS 豪华硬顶敞篷运动型轿车</title><is_auto_up>0</is_auto_up><week_hits>1</week_hits><step_id/><is_am_tupage>0</is_am_tupage><input_user/><released_dtime/><lasthit_dtime/><is_host/><tags/><up_dtime/><is_auto_down/><down_dtime/><info_id>1282</info_id><site_id/><input_dtime/><final_status>0</final_status><content_url>/tmzf/zwdt/tpxw/1282.htm</content_url><source>时光研发布</source><from_id/><wf_id/><subtitle/><tupage_num>0</tupage_num></r_content>","article",15);
	}
}
