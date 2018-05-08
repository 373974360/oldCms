package com.deya.util;

import java.util.List;

public class DebugThread{
	public static String debugThreadHandl(String status){
		String message = "";
	    if(status==null||status.equals(""))
	      status="RUNNABLE";
	    int tc = Thread.activeCount();
	    Thread[] ts = new Thread[tc];
	    Thread.enumerate(ts);
	    List list = java.util.Arrays.asList(ts);
	    //System.out.println("name       | state   | className      |   stackTrace ");
	    message = "<table width=\"1000px\" border=\"1\"><tr><th>name</th><th>state</th><th>className</th><th>stackTrace</th></tr>";
	    
	    for (int i=0;i<list.size();i++) {
	      Thread t=(Thread)list.get(i);      
	      //System.out.println(t.getName()+"       | "+t.getState()+"   | "+t.getClass().getName()+"      |    "+t.getStackTrace());
	      message += "<tr><td>"+t.getName()+"</td><td>"+t.getState()+"</td><td>"+t.getClass().getName()+"</td><td>"+t.getStackTrace()+"</td></tr>";
	      StackTraceElement []trace = t.getStackTrace();
	      if(t.getState().toString().equals(status)){  
	        System.err.println("Error may be occurred in method: ");  
	        message += "<tr><td colspan=\"4\">Error may be occurred in method: </p>";
	        for(int j=0;j<trace.length;j++){
	          StackTraceElement ste = trace[j];
	          System.err.println("  at " + ste.getFileName()+"."+ste.getMethodName()+"():"+ste.getLineNumber());
	          message += ""+ste.getFileName()+" -- "+ste.getMethodName()+" -- "+ste.getLineNumber()+"</p>";
	        }
	        message += "</td></tr>";
	      }
	    }
	    return message+"</table>";
	  }
}


