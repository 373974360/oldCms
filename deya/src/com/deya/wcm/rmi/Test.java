package com.deya.wcm.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.deya.wcm.bean.cms.info.ArticleBean;

public class Test {

	public static final String URL = "rmi://192.168.12.18:1102/customRmi";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Context namingContext = null;
		try{
			namingContext = new InitialContext();
			ICustomFormRMI rmi = (ICustomFormRMI) namingContext.lookup(URL);
			
			String from_id = "1023";
			String model_name = "article"; 
			ArticleBean articleBean = new ArticleBean();
			articleBean.setAuthor("系统管理员");//作者
			articleBean.setCat_id(4801); //栏目Id
			articleBean.setEditor("");//编辑  
			articleBean.setInfo_content("信息内容");//信息内容
			articleBean.setDescription("内容摘要");//内容摘要
			articleBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
			//articleBean.setSite_id("HIWCMdemo");//站点id
			articleBean.setSite_id("CMSlisp");//站点id
			articleBean.setSource("政务门户演示网站");//来源
			articleBean.setThumb_url("");//缩略图 
			articleBean.setTitle("信息标题22");//标题 
			articleBean.setInput_dtime("2013-02-22 22:22:22");//添加时间
		    articleBean.setTop_title("");//上标题  
			articleBean.setSubtitle("");//副标题  
			boolean result = rmi.insertForm(articleBean, model_name,from_id);
			//articleBean.setInfo_id(8695);
			//boolean result = rmi.updateForm(articleBean, model_name,from_id);
			System.out.println(result);
			
			
//			 String model_name = "pic";
//			 PicBean picBean = new PicBean();
//			 picBean.setAuthor("系统管理员");//作者
//			 picBean.setCat_id(4801); //栏目Id
//			 picBean.setDescription("内容摘要");//内容摘要	 
//			 picBean.setEditor("");//编辑
//			 picBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 picBean.setPic_content("信息内容");//信息内容
//			 picBean.setSite_id("HIWCMdemo");//站点id
//			 picBean.setSource("政务门户演示网站");//来源
//			 picBean.setThumb_url("");//缩略图
//			 picBean.setTitle("组图标题1_1");//标题
//			 picBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 picBean.setTop_title("");//上标题
//			 picBean.setSubtitle("");//副标题
//			 //图片列表
//			 List<PicItemBean> item_list = new ArrayList<PicItemBean>();
//			 PicItemBean picItemBean1 = new PicItemBean();
//			 picItemBean1.setPic_title("aaa");//图片标题
//			 picItemBean1.setPic_note("aaaaaaaaaa");//图片描述
//			 picItemBean1.setPic_path("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 item_list.add(picItemBean1); 
//			 PicItemBean picItemBean2 = new PicItemBean();
//			 picItemBean2.setPic_title("bbb");//图片标题
//			 picItemBean2.setPic_note("bbbbbbbbbbb");//图片描述
//			 picItemBean2.setPic_path("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 item_list.add(picItemBean2);
//			 picBean.setItem_list(item_list);		
//			 //boolean result = rmi.insertForm(picBean, model_name);
//			 picBean.setInfo_id(8697);
//			 boolean result = rmi.updateForm(picBean, model_name);
//			 System.out.println(result);
			 
//			 String model_name = "link";
//			 InfoBean infoBean = new InfoBean();
//			 infoBean.setAuthor("系统管理员");//作者
//			 infoBean.setCat_id(4801); //栏目Id
//			 infoBean.setContent_url("www.hao123.com");//链接地址
//			 infoBean.setDescription("内容摘要");//内容摘要	
//			 infoBean.setEditor("");//编辑
//			 infoBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 infoBean.setSite_id("HIWCMdemo");//站点id
//			 infoBean.setSource("政务门户演示网站");//来源
//			 infoBean.setThumb_url("");//缩略图
//			 infoBean.setTitle("链接测试1_1");//标题
//			 infoBean.setInput_dtime("2013-02-20 22:40:22");//添加时间
//			 infoBean.setTop_title("");//上标题 
//			 infoBean.setSubtitle("");//副标题
//			 //boolean result = rmi.insertForm(infoBean, model_name);
//			 infoBean.setInfo_id(8702);
//			 boolean result = rmi.updateForm(infoBean, model_name);
//			 System.out.println(result);
			 
//			 String model_name = "video";
//			 VideoBean videoBean = new VideoBean();
//			 videoBean.setAuthor("系统管理员");//作者
//			 videoBean.setCat_id(4801); //栏目Id
//			 videoBean.setContent_url("");//链接地址
//			 videoBean.setDescription("内容摘要");//内容摘要	
//			 videoBean.setEditor("");//编辑
//			 videoBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 videoBean.setSite_id("HIWCMdemo");//站点id
//			 videoBean.setSource("政务门户演示网站");//来源
//			 videoBean.setThumb_url("");//缩略图
//			 videoBean.setTitle("视频测试1_1");//标题
//			 videoBean.setInput_dtime("2013-02-20 22:34:22");//添加时间
//			 videoBean.setTop_title("");//上标题
//			 videoBean.setSubtitle("");//副标题
//			 videoBean.setVideo_path("ssssssssssss");//视频地址
//			 videoBean.setInfo_content("视频内容视频内容视频内容");//视频内容
//			 //boolean result = rmi.insertForm(videoBean, model_name);
//			 videoBean.setInfo_id(8705);
//			 boolean result = rmi.updateForm(videoBean, model_name);
//			 System.out.println(result);		 
			
//			 String from_id = "1119"; 
//			 String model_name = "gkftygs";
//			 GKFtygsBean gkFtygsBean = new GKFtygsBean();
//			 gkFtygsBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFtygsBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFtygsBean.setAuthor("系统管理员");//作者
//			 gkFtygsBean.setCarrier_type("载体类型");//载体类型
//			 gkFtygsBean.setCat_id(4807); //栏目Id
//			 gkFtygsBean.setDescription("内容摘要");//内容摘要
//			 gkFtygsBean.setDoc_no("文号");//文号
//			 gkFtygsBean.setEditor("");//编辑
//			 gkFtygsBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFtygsBean.setGk_content("信息内容");//信息内容
//			 gkFtygsBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 //gkFtygsBean.setGk_format("html");//信息格式
//			 gkFtygsBean.setGk_index("");//索引号
//			 gkFtygsBean.setGk_input_dept("发布机构");//发布机构
//			 gkFtygsBean.setGk_proc("审核程序");//审核程序
//			 gkFtygsBean.setGk_range("面向社会");//公开范围
//			 gkFtygsBean.setGk_signer("签发人");//签发人
//			 gkFtygsBean.setGk_time_limit("长期公开");//公开时限
//			 gkFtygsBean.setGk_validity("有效");//信息有效性
//			 gkFtygsBean.setGk_way("政府网站");//公开形式
//			 gkFtygsBean.setGk_time_limit("长期公开");//公开时限
//			 gkFtygsBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFtygsBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFtygsBean.setLanguage("汉语");//语种
//			 gkFtygsBean.setPlace_key("位置关键词");//位置关键词
//			 gkFtygsBean.setServe_id(384);//服务对象分类ID
//			 gkFtygsBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFtygsBean.setSite_id("CMSlisp");//公开节点ID  ------ 要根据实际项目填写
//			 gkFtygsBean.setSource("政务门户演示网站");//来源
//			 gkFtygsBean.setTheme_id(370);//体裁分类ID
//			 gkFtygsBean.setTheme_name("命令");//体裁分类名称
//			 gkFtygsBean.setThumb_url("");//缩略图
//			 gkFtygsBean.setTitle("信息标题公开1111");//标题
//			 gkFtygsBean.setTop_title("");//上标题
//			 gkFtygsBean.setSubtitle("");//副标题
//			 gkFtygsBean.setTopic_id(404);//主题分类ID
//			 gkFtygsBean.setTopic_name("省政府");//主题分类名称
//			 boolean result = rmi.insertForm(gkFtygsBean, model_name,from_id);
//			 //gkFtygsBean.setInfo_id(8704);
//			 //boolean result = rmi.updateForm(gkFtygsBean, model_name);
//			 System.out.println(result);
//			 
//			
			
//			 String model_name = "gkfldcy";			
//			 GKFldcyBean gkFldcyBean = new GKFldcyBean();
//			 gkFldcyBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFldcyBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFldcyBean.setAuthor("系统管理员");//作者
//			 gkFldcyBean.setCarrier_type("载体类型");//载体类型
//			 gkFldcyBean.setCat_id(4807); //栏目Id
//			 gkFldcyBean.setDescription("内容摘要");//内容摘要
//			 gkFldcyBean.setDoc_no("文号");//文号
//			 gkFldcyBean.setEditor("");//编辑
//			 gkFldcyBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFldcyBean.setGk_content("信息内容");//信息内容
//			 gkFldcyBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 
//			 gkFldcyBean.setGk_dz("");//地址
//			 gkFldcyBean.setGk_email("");//EMAIl
//			 gkFldcyBean.setGk_grjl("");//个人简历
//			 gkFldcyBean.setGk_gzfg("");//工作分工
//			 gkFldcyBean.setGk_ldzw("");//领导职务
//			 gkFldcyBean.setGk_pic("http://www.st.gov.cn/rrr.jpg");//照片
//			 gkFldcyBean.setGk_tel("");//电话
//			 
//			 gkFldcyBean.setGk_format("html");//信息格式
//			 gkFldcyBean.setGk_index("A0300-010000-2013-000072");//索引号
//			 gkFldcyBean.setGk_input_dept("发布机构");//发布机构
//			 gkFldcyBean.setGk_proc("审核程序");//审核程序
//			 gkFldcyBean.setGk_range("面向社会");//公开范围
//			 gkFldcyBean.setGk_signer("签发人");//签发人
//			 gkFldcyBean.setGk_time_limit("长期公开");//公开时限
//			 gkFldcyBean.setGk_validity("有效");//信息有效性
//			 gkFldcyBean.setGk_way("政府网站");//公开形式
//			 gkFldcyBean.setGk_time_limit("长期公开");//公开时限
//			 gkFldcyBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFldcyBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFldcyBean.setLanguage("汉语");//语种
//			 gkFldcyBean.setPlace_key("位置关键词");//位置关键词
//			 gkFldcyBean.setServe_id(384);//服务对象分类ID
//			 gkFldcyBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFldcyBean.setSite_id("GKzfb");//公开节点ID  ------ 要根据实际项目填写
//			 gkFldcyBean.setSource("政务门户演示网站");//来源
//			 gkFldcyBean.setTheme_id(370);//体裁分类ID
//			 gkFldcyBean.setTheme_name("命令");//体裁分类名称
//			 gkFldcyBean.setThumb_url("");//缩略图
//			 gkFldcyBean.setTitle("信息标题领导成员1_1");//标题
//			 gkFldcyBean.setTop_title("");//上标题
//			 gkFldcyBean.setSubtitle("");//副标题
//			 gkFldcyBean.setTopic_id(404);//主题分类ID
//			 gkFldcyBean.setTopic_name("省政府");//主题分类名称
//			 //附件列表
//			 List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
//			 GKResFileBean gkResFileBean1 = new GKResFileBean();
//			 gkResFileBean1.setRes_name("aaab");//附件标题
//			 gkResFileBean1.setRes_url("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 file_list.add(gkResFileBean1);
//			 gkFldcyBean.setFile_list(file_list);
////			 boolean result = rmi.insertForm(gkFldcyBean, model_name);
////			 System.out.println(result);
//			 gkFldcyBean.setInfo_id(8717);
//			 boolean result = rmi.updateForm(gkFldcyBean, model_name);
//			 System.out.println(result);
			 
			 
//			 String model_name = "gkfbszn";	
//			 GKFbsznBean gkFbsznBean = new GKFbsznBean();
//			 gkFbsznBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFbsznBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFbsznBean.setAuthor("系统管理员");//作者
//			 gkFbsznBean.setCarrier_type("载体类型");//载体类型
//			 gkFbsznBean.setCat_id(4807); //栏目Id
//			 gkFbsznBean.setDescription("内容摘要");//内容摘要
//			 gkFbsznBean.setDoc_no("文号");//文号
//			 gkFbsznBean.setEditor("");//编辑
//			 gkFbsznBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFbsznBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 
//			 gkFbsznBean.setGk_bgsj("111");//办公时间及地址
//			 gkFbsznBean.setGk_bldx("22");//办理对象及范围
//			 gkFbsznBean.setGk_bllc("33");//办理流程
//			 gkFbsznBean.setGk_blshixian("44");//办理时限
//			 gkFbsznBean.setGk_blsx("55");//办理手续
//			 gkFbsznBean.setGk_bsjg("66");//办事机构
//			 gkFbsznBean.setGk_cclx("77");//乘车路线
//			 gkFbsznBean.setGk_fwlb("公共服务项目");//服务类别
//			 gkFbsznBean.setGk_gsfs("公示方式内容");//公示方式
//			 gkFbsznBean.setGk_jdjc("");//监督检查
//			 gkFbsznBean.setGk_jgwz("http://www.cicro.com");//机构网址
//			 gkFbsznBean.setGk_memo("88");//备注
//			 gkFbsznBean.setGk_sfbz("9");//收费标准
//			 gkFbsznBean.setGk_sfyj("0");//收费依据
//			 gkFbsznBean.setGk_sxyj("--");//事项依据
//			 gkFbsznBean.setGk_wsbl("dsa");//网上办理
//			 gkFbsznBean.setGk_wsts("");//网上投诉
//			 gkFbsznBean.setGk_wszx("");//网上咨询
//			 gkFbsznBean.setGk_zrzj("");//责任追究
//			 gkFbsznBean.setGk_ztcx("");//状态查询
//			 gkFbsznBean.setGk_zxqd("");//咨询渠道
//			 
//			 gkFbsznBean.setGk_format("html");//信息格式
//			 gkFbsznBean.setGk_index("A0300-010000-2013-000072");//索引号
//			 gkFbsznBean.setGk_input_dept("发布机构");//发布机构
//			 gkFbsznBean.setGk_proc("审核程序");//审核程序
//			 gkFbsznBean.setGk_range("面向社会");//公开范围
//			 gkFbsznBean.setGk_signer("签发人");//签发人
//			 gkFbsznBean.setGk_time_limit("长期公开");//公开时限
//			 gkFbsznBean.setGk_validity("有效");//信息有效性
//			 gkFbsznBean.setGk_way("政府网站");//公开形式
//			 gkFbsznBean.setGk_time_limit("长期公开");//公开时限
//			 gkFbsznBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFbsznBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFbsznBean.setLanguage("汉语");//语种
//			 gkFbsznBean.setPlace_key("位置关键词");//位置关键词
//			 gkFbsznBean.setServe_id(384);//服务对象分类ID
//			 gkFbsznBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFbsznBean.setSite_id("GKzfb");//公开节点ID  ------ 要根据实际项目填写
//			 gkFbsznBean.setSource("政务门户演示网站");//来源
//			 gkFbsznBean.setTheme_id(370);//体裁分类ID
//			 gkFbsznBean.setTheme_name("命令");//体裁分类名称
//			 gkFbsznBean.setThumb_url("");//缩略图
//			 gkFbsznBean.setTitle("信息标题办事指南1-1");//标题
//			 gkFbsznBean.setTop_title("");//上标题
//			 gkFbsznBean.setSubtitle("");//副标题
//			 gkFbsznBean.setTopic_id(404);//主题分类ID
//			 gkFbsznBean.setTopic_name("省政府");//主题分类名称
//			 //附件列表
//			 List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
//			 GKResFileBean gkResFileBean1 = new GKResFileBean();
//			 gkResFileBean1.setRes_name("aaa");//附件标题
//			 gkResFileBean1.setRes_url("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 file_list.add(gkResFileBean1);
////			 gkFbsznBean.setFile_list(file_list);
////			 boolean result = rmi.insertForm(gkFbsznBean, model_name);
////			 System.out.println(result);
//			 gkFbsznBean.setInfo_id(8722);
//			 boolean result = rmi.updateForm(gkFbsznBean, model_name);
//			 System.out.println(result); 
			 
			 
			 
//			 //删除
//			 String site_id = "HIWCMdemo";
//			 List<String> list = new ArrayList<String>();
//			 list.add("1000");
//			 boolean result = rmi.deleteForm(list,site_id);
//			 System.out.println(result);
			 
		}catch (Exception e) { 
			e.printStackTrace();
		}
		
	}

}
