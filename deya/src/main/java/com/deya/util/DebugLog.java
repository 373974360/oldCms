package com.deya.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

public class DebugLog {
    private static Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");

    static {
        reloadLogConfig();
    }

    public static void reloadLogConfig() {
        JconfigUtil bc = JconfigFactory
                .getJconfigUtilInstance("bashConfig");
        //System.out.println(bc.getProperty("path", "", "log4jFile"));
        PropertyConfigurator.configure(bc.getProperty(
                "path", "", "log4jFile"));
    }

    public static void debug(String e) {
        mySonLogger.debug(e);
    }

    public static void info(String e) {
        mySonLogger.info(e);
    }

    public static void warn(String e) {
        mySonLogger.warn(e);
    }

    public static void error(String e) {
        mySonLogger.error(e);
    }

    public static void fatal(String e) {
        mySonLogger.fatal(e);
    }


    public static void main(String[] args) {
        info("info");
        fatal("aaaaaaaaaa");
    }
}
