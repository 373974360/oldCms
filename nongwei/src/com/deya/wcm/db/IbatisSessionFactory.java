package com.deya.wcm.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

/**
 * 
 * @author 
 * 
 */
public class IbatisSessionFactory implements SqlSessionFactory {

	/** 根据参数得到某个具体的iBatis数据源工厂实例 */
	public static synchronized SqlSessionFactory getInstance() {
		if (sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory;
	}

	private static void initSqlSessionFactory() {
		String resource = "SqlMapConfig.xml";
		Reader reader = null;
		try {			
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			
			sqlSessionFactory = builder.build(reader);
		} catch (IOException e) {
			System.out.println("创建iBATIS数据工厂时出现错误！");
			e.printStackTrace();
		}
	}

	// 真正“做事”的实例
	private static SqlSessionFactory sqlSessionFactory = null;

	public Configuration getConfiguration() {
		return sqlSessionFactory.getConfiguration();
	}

	public SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}

	public SqlSession openSession(boolean arg0) {
		return sqlSessionFactory.openSession(arg0);
	}

	public SqlSession openSession(Connection arg0) {
		return sqlSessionFactory.openSession(arg0);
	}

	public SqlSession openSession(ExecutorType arg0) {
		return sqlSessionFactory.openSession(arg0);
	}

	public SqlSession openSession(ExecutorType arg0, boolean arg1) {
		return sqlSessionFactory.openSession(arg0, arg1);
	}

	public SqlSession openSession(ExecutorType arg0, Connection arg1) {
		return sqlSessionFactory.openSession(arg0, arg1);
	}

	
	public SqlSession openSession(TransactionIsolationLevel arg0) {
		return sqlSessionFactory.openSession(arg0);
	}

	
	public SqlSession openSession(ExecutorType arg0,
			TransactionIsolationLevel arg1) {
		return sqlSessionFactory.openSession(arg0, arg1);
	}
}

