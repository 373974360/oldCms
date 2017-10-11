package com.deya.wcm.template.velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.wcm.bean.template.ClientIPBean;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.system.template.TemplateUtils;


/*
  <category name="LoaderTemplateContextClass">
    <property name="Appeal" value="com.deya.wcm.template.velocity.data.AppealData"/>
    <property name="News" value="com.deya.wcm.template.velocity.data.NewsData"/>
  </category>
 */
/**
 * @author zhuliang
 * @version 1.0
 * @date   2011-4-22 下午03:26:28
 */
public abstract class VelocityContextAbstract {
	private static Map<String, Object> map = new HashMap<String, Object>();
	
	static {
		//从配置文件中得来
		try {
			JconfigUtil jc = JconfigFactory.getJconfigUtilInstance("velocityTemplate");
			Set<String> classes = jc.getPropertyNamesByCategory("LoaderTemplateContextClass");
			for(String name : classes){
				try{
					map.put(name, Class.forName(jc.getProperty(name, null, "LoaderTemplateContextClass")));
				}catch(ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String template_id = "";
	protected String site_id = "";
	
	protected VelocityContext vcontext = new VelocityContext();
	
	public VelocityContextAbstract()
	{}
	
	/**
	 * 处理request提交过来的参数，并put进vcontext
	 * @return boolean
	 */
	public VelocityContextAbstract(HttpServletRequest request)
	{
		inputParam(request);
	}
	
	public void inputParam(HttpServletRequest request)
	{  
		vcontext.put("v_session", request.getSession());
		vcontext.put("v_request", request);//request对象
		ClientIPBean cip = new ClientIPBean();
		String ip = FormatUtil.getIpAddr(request);
		cip.setIp(ip);
		cip.setContrey(com.deya.util.ip.Utils.getCountry(ip));
		cip.setArea(com.deya.util.ip.Utils.getArea(ip));
		vcontext.put("ClientIP", cip);//当前用户IP，地址对象
		
		site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());		
		
		String params = FormatUtil.getParameterStrInRequest(request);
		
		/*
		try {
			params = new String(params.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//System.out.println("params---------" + params);
		
		if(params != null && !"".equals(params) && FormatUtil.isValiditySQL(params))
		{
			String[] tempA = params.split("&");
			for (int i=0;i<tempA.length;i++)
			{
				int index_num = tempA[i].indexOf("=");
				String vals = tempA[i].substring(index_num+1);
				if(FormatUtil.isNumeric(vals))
				{
					try{
						vcontext.put(tempA[i].substring(0,index_num), Integer.parseInt(vals));
					}catch(NumberFormatException n)
					{
						vcontext.put(tempA[i].substring(0,index_num), vals);
					}
				}
				else
					vcontext.put(tempA[i].substring(0,index_num), vals);
			}
			if(params.indexOf("cur_page") == -1)
				vcontext.put("cur_page", 1);
			
			vcontext.put("requet_params", params);
		}
	}
	
	/**
	 * 模板解析并保存解析后 的文件
	 * @return boolean
	 */
	public String parseTemplate(){
		try{
		    if(vcontext.get("site_id") == null || "".equals(vcontext.get("site_id")) || "null".equals(vcontext.get("site_id")))
		    	vcontext.put("site_id", site_id);
		    
			Template template = VelocityEngineInstance.getInstance().getTemplate(getTemplateFilePath());
			Writer writer = new StringWriter();
			loadClassContext();
			template.merge(vcontext, writer);
			//save(writer.toString());
			
			return writer.toString();
		}catch(Exception e){
			e.printStackTrace();
			//return "<div style=\"display:none\">\n"+e.getLocalizedMessage()+"\n</div>";
			return "";
		}
	}
	
	public String parseTemplate(String ware_content)
	{		
		try{
			StringWriter w = new StringWriter();
			loadClassContext();
			VelocityEngineInstance.getInstance().evaluate( vcontext, w, "", ware_content ); 
			return w.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "<div style=\"display:none\">\n"+e.getLocalizedMessage()+"\n</div>";
		}
	}
	
	/**
	 * 加载所有前台类
	 *  void
	 */
	public void loadClassContext(){
		Set<String> keys = map.keySet();
		for(String key : keys){
			vcontext.put(key, map.get(key));
		}
	}
	
	public void setCcontext(String key,Object o)
	{
		vcontext.put(key, o);
	}
	
	/**
	 * 得到模板的保存路径,供解析时调用
	 * @return String
	 */
	public String getTemplateFilePath(){//根据模板template_id+"_"+site_id+"_"+app_id得到模板的保存路径,供解析时调用
		//return "./src/com/cicro/wcm/template/velocity/impl/HelloWorld.vm";
		//System.out.println("getTemplateFilePath----------"+template_id+"_"+site_id+"_"+app_id);		
		String path = TemplateUtils.getTemplatePth(template_id+"_"+site_id);		
		//System.out.println("getTemplateFilePath----"+template_id+"_"+site_id+"------"+path);
		if(ServerManager.isWindows())
			return path.substring(path.indexOf("\\vhost")+8);
		else
			return path.substring(path.indexOf("/vhost")+8);
		//return "\\WEB-INF\\classes\\test.vm";
	}
	
	/**
	 * 根据save_path和name把解析后的文件保存成htm
	 * @param content void
	 */
	public boolean save(String content){//根据save_path和name把解析后的文件保存成htm
		String t_root_path = SiteManager.getSiteTempletPath(site_id);
		String templateFile = TemplateUtils.getTemplatePth(template_id+"_"+site_id);
		String filePath = FormatUtil.formatPath(t_root_path+"/"+templateFile, false);
		try {
			//System.out.println("filePath = save template file path ===================================="+templateFile);
			File f = new File(templateFile);
			TemplateUtils.mkDirectory(f);
			FileOperation.writeStringToFile(f, content, false);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(content);
		return false;
	}
	
	public String getTemplate_id() {
		return template_id;
	}

	public String getSite_id() {
		return site_id;
	}

	

	public void setTemplate_id(String templateId) {
		template_id = templateId;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
}
