package com.deya.wcm.db;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class BoneDataSourceFactory_bak implements DataSourceFactory {

	private com.jolbox.bonecp.BoneCPDataSource dataSource;
	private static String data_type_name = "";

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

	public void setProperties(Properties props) {		
		BoneCPConfig bcpc = new BoneCPConfig();		
		bcpc.setJdbcUrl((String) props.getProperty("url"));
		bcpc.setUsername((String) props.getProperty("username"));
		bcpc.setPassword((String) props.getProperty("password"));
		bcpc.setMaxConnectionsPerPartition(Integer.parseInt((String) props.getProperty("maximum-connection-count")));
		bcpc.setMinConnectionsPerPartition(Integer.parseInt((String) props.getProperty("minimum-connection-count")));
		//bcpc.setMaxConnectionAge(2000);
		//dataSource.setMaxConnectionAge(Long.parseLong(maxWait),TimeUnit.SECONDS);
		
		data_type_name = (String) props.getProperty("alias");	
		dataSource = new BoneCPDataSource(bcpc);
		dataSource.setDriverClass((String) props.getProperty("driver"));
	}
}