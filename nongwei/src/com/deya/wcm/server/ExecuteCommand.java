package com.deya.wcm.server;

import java.io.IOException;
import java.io.*;

public class  ExecuteCommand{
	public static void executeCommandHandl(String[] windowsCommand,String[] linuxCommand)throws IOException
	{
		final String os = System.getProperty("os.name");
		final String[] command;
		  if (ServerManager.isWindows()) {
		   command = windowsCommand;
		  } else if (os.startsWith("Linux")) {
		   command = linuxCommand;
		  } else {
		   throw new IOException("Unknown operating system: " + os);
		  }
		 
		  final Process process = Runtime.getRuntime().exec(command);
		  // Discard the stderr
		  new Thread() {
		   public void run() {
		    try {
		     InputStream errorStream = process.getErrorStream();
		     while (errorStream.read() != -1) {}
		     errorStream.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }
		  }.start();
	}
	
	public static String executeCommandHandlRStr(String[] windowsCommand,String[] linuxCommand)throws IOException
	{
		final String os = System.getProperty("os.name");
		final String[] command;
		  if (os.startsWith("Windows")) {
		   command = windowsCommand;
		  } else if (os.startsWith("Linux")) {
		   command = linuxCommand;
		  } else {
		   throw new IOException("Unknown operating system: " + os);
		  }
		 
		  final Process process = Runtime.getRuntime().exec(command);
		 
		  new Thread() {
		   public void run() {
		    try {
		     InputStream errorStream = process.getErrorStream();
		     while (errorStream.read() != -1) {}
		     errorStream.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }
		  }.start();
		
		  BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		  String r = reader.readLine();
		  reader.close();
		  return r;
	}
}
