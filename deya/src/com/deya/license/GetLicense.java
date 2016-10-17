package com.deya.license;

import com.deya.license.server.GetServerInfo;
import com.deya.license.tools.CryptoTools;
import com.deya.license.tools.DomParse;
import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.w3c.dom.Document;

public class GetLicense {
    private static String defaultLicensePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + "/license/license.data");
    private static File LicenseFile;
    private static Document licDom;

    public GetLicense(String filePath) {
        if ((filePath == null) || ("".equals(filePath))) {
            filePath = defaultLicensePath;
        }
        setLicenseFile(filePath);
    }

    private void setLicenseFile(String path) {
        LicenseFile = new File(path);
        if (isLicenseFile()) {
            String LicenseXml = "";
            try {
                CryptoTools ct = new CryptoTools();
                LicenseXml = ct.decode(getLicenseCode());
                licDom = DomParse.creaatDomByString(LicenseXml);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }

        } else if (defaultLicensePath.equals(path)) {
            System.out.println("license.data does not exist!");
        } else {
            System.out.println(path + " does not exist!");
            setLicenseFile(defaultLicensePath);
        }
    }

    public boolean isLicenseFile() {
        return LicenseFile.exists();
    }

    public String getLicenseCode() throws IOException {
        String strR = null;
        InputStream is = new BufferedInputStream(new FileInputStream(LicenseFile));
        int iLength = is.available();
        byte[] b = new byte[iLength];
        is.read(b);
        is.close();
        strR = new String(b);
        return strR;
    }

    public String getDeptNum() {
        return DomParse.getNodeItem(licDom, "dept_num");
    }

    public String getCPUidByLicense() {
        return DomParse.getNodeItem(licDom, "cpuid");
    }

    public String getMACByLicense() {
        return DomParse.getNodeItem(licDom, "mac");
    }

    public boolean ifServerIdentical() {
        return (GetServerInfo.getCPUID().equals(getCPUidByLicense())) && (GetServerInfo.getMac().equals(getMACByLicense()));
    }

    public String getLicenseItemValue(String itemName) {
        return DomParse.getNodeItem(licDom, itemName);
    }

    public String getLicenseItemValue(String parentName, String itemName) {
        return DomParse.getNodeItem(licDom, parentName, itemName);
    }

    public static void main(String[] arg) {
        GetLicense gl = new GetLicense("");
        //System.out.println(gl.getMACByLicense());
    }
}