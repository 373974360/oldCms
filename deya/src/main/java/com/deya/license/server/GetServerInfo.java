package com.deya.license.server;

import com.deya.license.tools.CPUInfo;
import com.deya.license.tools.CryptoTools;
import com.deya.license.tools.MacAddress;

import java.io.PrintStream;

public class GetServerInfo {
    private static String keyStrPrefix = "deyatechCMS";

    public static String getServerInfoCodeByEncrypt() {
        String code = "";
        try {
            String cpu = new CPUInfo().getCPUID();
            String mac = MacAddress.getMacAddress();
            code = keyStrPrefix + "$*$" + cpu + "$*$" + mac;
            CryptoTools ct = new CryptoTools();
            code = ct.encode(code);
            return code;
        } catch (Throwable e) {
            e.printStackTrace();
            //System.out.println(e);
        }
        return null;
    }

    public static String getCPUID() {
        return new CPUInfo().getCPUID();
    }

    public static String getMac() {
        return MacAddress.getMacAddress();
    }

    public static void main(String[] args) {
        //System.out.println("\r\n code:   " + getServerInfoCodeByEncrypt() + "\r\n");
    }
}