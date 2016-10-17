package com.deya.license.tools;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;

public class CPUInfo
{
  private String getWindowsCPUID()
  {
    JNative n = null;
    String str = "";
    try {
      n = new JNative("Reg.dll", "GetCPUID");
      try {
        n.setRetVal(Type.STRING);
        n.invoke();
        str = str + n.getRetVal();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    catch (NativeException e)
    {
      e.printStackTrace();
    }
    return str;
  }

  private String getLinuxCPUID()
  {
    String cpuInfo = "";
    String[] LinuxCmd = { "/bin/sh", "-c", "dmidecode |grep -A42 \"Processor\"|more" };
     String[] SourceCmd = { "source /etc/profile" };
    try {
        Process proc2 = Runtime.getRuntime().exec(SourceCmd);
      Process proc = Runtime.getRuntime().exec(LinuxCmd);
      InputStreamReader ir = new InputStreamReader(proc.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);
      int i = 0;
      while (i++ < 6) {
        cpuInfo = input.readLine();
      }
      input.close();
      ir.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return proceesID(cpuInfo.trim().substring(4, 15));
  }

  private String proceesID(String cpuID)
  {
    String[] id = cpuID.split(" ");
    StringBuffer cpustr = new StringBuffer();
    for (int i = 0; i < id.length; i++)
      cpustr.append(id[i]);
    return cpustr.toString();
  }

  public String getCPUID()
  {
    String id = "";
    String os = System.getProperty("os.name").toLowerCase();
    if (os.startsWith("windows"))
      id = getWindowsCPUID();
    if (os.startsWith("linux"))
      id = getLinuxCPUID();
    return id;
  }
  public static void main(String[] args) {
    System.out.println(new CPUInfo().getCPUID());
  }
}
