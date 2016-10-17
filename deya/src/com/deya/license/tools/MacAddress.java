package com.deya.license.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacAddress {
    private static final String[] windowsCommand = {"ipconfig", "/all"};
    private static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", 2);

    private static final List<String> getMacAddressList() throws IOException {
        ArrayList macAddressList = new ArrayList();

        String os = System.getProperty("os.name");
        String[] command;
        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else {
            if (os.startsWith("Linux"))
                command = linuxCommand;
            else
                throw new IOException("Unknown operating system: " + os);
        }
        Process process = Runtime.getRuntime().exec(command);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = reader.readLine()) != null; ) {
            Matcher matcher = macPattern.matcher(line);
            if (!matcher.matches())
                continue;
            macAddressList.add(matcher.group(1).replaceAll("[-:]", ""));
        }

        reader.close();
        return macAddressList;
    }

    public static String getMacAddress() {
        try {
            List addressList = getMacAddressList();
            StringBuffer sb = new StringBuffer();
            for (Iterator iter = addressList.iterator(); iter.hasNext(); ) {
                sb.append((String) iter.next());
            }
            return sb.toString();
        } catch (IOException e) {
        }
        return null;
    }

    public static final void main(String[] args) {
        try {
            //System.out.println("  MAC Address: " + getMacAddress());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}