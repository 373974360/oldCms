package com.deya.wcm.services.system.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.deya.util.FormatUtil; 
import com.deya.util.RandomStrg; 
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.system.template.TemplateResourcesBean;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.control.site.SiteManager;
/**
 * 模板资源管理类
 * @author 朱亮
 * @version 1.0
 * @date   2011-3-24 上午11:46:50
 */


public class TemplateResourcesManager {
	//根据路径得到此路径下的所有文件
	public static List<TemplateResourcesBean> getResourcesListBySiteID(String path)
	{		
		List<String> l = FileOperation.getFileSinglList(path);		
		List<TemplateResourcesBean> file_list = new ArrayList<TemplateResourcesBean>();
		FileStringToList(l,file_list);
		return file_list;
	}
	
	public static List<TemplateResourcesBean> getResImageListBySiteID(String site_id)
	{
		String path = "";
		if("shared_res".equals(site_id))
			path = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path")+"/cms.files";//这个是共享资源的东东，放在cws.files目录下
		else
			path = SiteManager.getSiteBeanBySiteID(site_id).getSite_path();
		String img_path = FormatUtil.formatPath(path+"/images");
		
		List<String> l = FileOperation.getFileSinglList(img_path);		
		List<TemplateResourcesBean> file_list = new ArrayList<TemplateResourcesBean>();
		FileStringToList(l,file_list);
		return file_list;
	}
	
	public static void FileStringToList(List<String> l,List<TemplateResourcesBean> file_list)
	{
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{				
				try{						
					String name = "";
					if(ServerManager.isWindows())
					{
						name = l.get(i).substring(l.get(i).lastIndexOf("\\")+1);
					}else
						name = l.get(i).substring(l.get(i).lastIndexOf("/")+1);
					TemplateResourcesBean trb = new TemplateResourcesBean();
					trb.setFile_name(name);
					trb.setFile_type(name.substring(name.lastIndexOf(".")+1).toLowerCase());
					trb.setFile_size(FileOperation.getFileSize(l.get(i))+"K");
					trb.setFile_path(l.get(i));
					file_list.add(trb);
					
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	public static boolean deleteTemplateResources(String file_path)
	{
        //System.out.println("deleteTemplateResources----------------"+file_path);
		File f = new File(file_path);
		return f.delete();
	}
	
	public static boolean addTemplateResourcesFolder(String file_path)
	{
		File f = new File(file_path);
		return f.mkdir();
	}
	
	public static boolean updateResourcesFile(String file_path,String file_content) throws IOException
	{
		return FileOperation.writeStringToFile(file_path, file_content, false,"UTF-8");
	}
	
	public static String getResourcesFileContent(String file_path) throws IOException
	{
		return FileOperation.readFileToString(file_path,"UTF-8");
	}
	
	//根据站点ＩＤ得到资源目录，包括images.styles.js　目录
	public static String getFolderJSONStr(String site_id)
	{
		String path = "";
		if("shared_res".equals(site_id))
			path = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path")+"/cms.files";//这个是共享资源的东东，放在cws.files目录下
		else
			path = SiteManager.getSiteBeanBySiteID(site_id).getSite_path();
		String img_path = FormatUtil.formatPath(path+"/images");
		String style_path = FormatUtil.formatPath(path+"/styles");
		String js_path = FormatUtil.formatPath(path+"/js");
		
		String json = "";
		json += "{\"id\":0,\"text\":\"images\",\"attributes\":{\"url\":\""+img_path+"\"},\"children\":["+getFolderJSONStrForPath(img_path)+"]}";
		json += ",{\"id\":2,\"text\":\"styles\",\"attributes\":{\"url\":\""+style_path+"\"},\"children\":["+getFolderJSONStrForPath(style_path)+"]}";
		json += ",{\"id\":1,\"text\":\"js\",\"attributes\":{\"url\":\""+js_path+"\"},\"children\":["+getFolderJSONStrForPath(js_path)+"]}";
		
		if(ServerManager.isWindows())
		{
			json = json.replaceAll("\\\\", "\\\\\\\\");
		}
		return "["+json+"]";
	}
	
	public static String getFolderJSONStrForPath(String file_path)
	{
		String json_str = "";
		List<String> list = FileOperation.getFolderList(file_path);
		if(list != null && list.size() > 0)
		{
			json_str = getFolderJSONStrHandl(list);
		}
		return json_str;
	}
	
	public static String getFolderJSONStrHandl(List<String> l)
	{
		String json = "";
		for(int i=0;i<l.size();i++)
		{
			String text = l.get(i);
			text = text.substring(text.lastIndexOf(File.separator)+1);
			json += ",{\"id\":"+Integer.parseInt(RandomStrg.getRandomStr("0-9","4"))+",\"text\":\""+text+"\",\"attributes\":{\"url\":\""+l.get(i)+"\"}";
			List<String> list = FileOperation.getFolderList(l.get(i));
			if(list != null && list.size() > 0)
			{
				json += ",\"children\":["+getFolderJSONStrHandl(list)+"]";
			}
			json += "}";
		}
		if(json != "" && json != null)
			json = json.substring(1);
		
		return json;
	}
	
	public static void main(String[] args)
	{
		System.out.println(deleteTemplateResources("d:\\cicro\\cms\\vhosts\\www.cicrocms.com\\ROOT\\js\\photo\\img\\white\\Chrysanthemum.jpg"));
		
	}
}
