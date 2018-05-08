package com.deya.wcm.template.velocity;

/*
 * VelocityEngine模板解析引擎的唯一实例类，在此类中，设定引擎的一些初始参数及环境
 * 目前只做了一个简单的单例，以后可拓展
 */
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.deya.util.jconfig.JconfigUtilContainer;

/**
 * @author zhuliang
 * @version 1.0
 * @date   2011-4-19 下午03:02:51
 */
public class VelocityEngineInstance {

	private static VelocityEngine ve = null;
	
	public static VelocityEngine getInstance(){
		if(ve == null){
			ve = createVelocityEngineInstance();
		}
		return ve;
	}
	
	private static VelocityEngine createVelocityEngineInstance(){
		VelocityEngine velocityEngine = new VelocityEngine();
		//地址应该从配置文件取得，或者是一个服务器上的固定地址
		/*
		Properties p = new Properties();
		p.setProperty("velocity.properties", "/cicro/wcm/shared/classes/");
		String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
		p.setProperty("file.resource.loader.path", path);
		velocityEngine.init(p);
		*/
		String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
		velocityEngine.setProperty("input.encoding", "utf-8");
		velocityEngine.setProperty("output.encoding", "utf-8");
		velocityEngine.setProperty("file.resource.loader.path", path);
		velocityEngine.setProperty("velocimacro.library.autoreload", "true");
		velocityEngine.setProperty("resource.loader.cache", "false");
		velocityEngine.setProperty("velocimacro.max.depth", 100);
		velocityEngine.init();
		
		return velocityEngine;
	}
	
}
