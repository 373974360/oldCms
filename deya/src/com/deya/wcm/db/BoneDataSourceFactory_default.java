package com.deya.wcm.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

//为改 c3p0连接池修改的文件
public class BoneDataSourceFactory_default {

    private DataSource dataSource;
    private static String data_type_name = "";
    
    static {
    	try{
    		InputStream input =new BoneDataSourceFactory_default().getClass().getClassLoader().getResourceAsStream("jdbc.properties_mysql");
    		Properties p =new Properties();
    		try {
    			p.load(input);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		data_type_name=p.getProperty("alias");
    		//System.out.println("data_type_name------" + data_type_name);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public BoneDataSourceFactory_default() {
        dataSource = new C3p0DataSource();
    }

    public DataSource getDataSource() {
		return dataSource;
	}
	
	public static String getDataTypeName()
	{		
		return data_type_name;
	}
	
	public static void setDataTypeName(String data_type_name2)
	{		
		data_type_name = data_type_name2;
	}
   
}