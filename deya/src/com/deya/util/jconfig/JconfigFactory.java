//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.util.jconfig;

import com.deya.util.FormatUtil;
import java.io.File;

public class JconfigFactory {
	private static String configFileLocation = "/deya/cms/shared/classes";
	private static String defaultPath;
	private static JconfigUtil jfu;

	private JconfigFactory() {
	}

	public static JconfigUtil getJconfigUtilInstance(String configName) {
		if (jfu == null) {
			jfu = new JconfigUtil(defaultPath);
		}

		String path = jfu.getProperty("path", (String)null, configName);
		return new JconfigUtil((new File(configFileLocation, path)).getAbsolutePath());
	}

	public static String getAllConfigPath() {
		return defaultPath;
	}

	public static void main(String[] args) {
		System.out.println(FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "log4jFile")));
	}

	static {
		defaultPath = (new File(configFileLocation, "AllConfigDescrion_WCM.xml")).getAbsolutePath();
		jfu = null;
	}
}
