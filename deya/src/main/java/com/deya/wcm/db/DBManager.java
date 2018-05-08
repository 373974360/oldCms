package com.deya.wcm.db;

import java.util.*;
import org.apache.ibatis.session.SqlSession; 
/**
 * 数据库操作基础类.
 * <p>Title: CicroDate</p>
 * <p>Description: 数据库操作</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * * 
 */
/**
 * 数据库操作基础类.
 * <p>Title: CicroDate</p>
 * <p>Description: 数据库操作</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * * 
 */
public class DBManager{	
	
	/**
     * 执行sql返回一个字段的值.
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象，用于传入查询参数
     * @return 字段的值
     *
     * History 2010-04-16 zhuliang
     */
	public static String getString(String sqlName,Object o)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			Object tempO = s.selectOne(getSqlNameByDataType(sqlName),o);
			if(tempO == null)
				return "";
			else
				return tempO.toString();	
		}finally { 
			s.close(); 
		}		
	}
	
	/**
     * 获取单个对象值
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return Object
     *
     * History 2010-04-16 zhuliang
     */
	public static Object queryFObj(String sqlName,Object o){	
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			return s.selectOne(getSqlNameByDataType(sqlName),o);			
		}finally { 
			s.close(); 
		}
	}
	/**
     * 获取列表值
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return List
     *
     * History 2010-04-16 zhuliang
     */
	@SuppressWarnings("unchecked")
	public static List queryFList(String sqlName,Object o){	
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{			
			return s.selectList(getSqlNameByDataType(sqlName),o);
		}finally { 
			s.close(); 
		}
	}
	
	/**
     * 添加记录方法　
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return boolean 成功或失败
     *
     * History 2010-04-16 zhuliang
     */
	public static boolean insert(String sqlName,Object o)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			s.insert(getSqlNameByDataType(sqlName), o);
			
			s.commit();			
		}catch(Exception ex){				
			ex.printStackTrace();
			return false;
		}
		finally { 
			s.close(); 
		}
		return true;
	}
	
	/**
     * 修改记录方法　
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return boolean 成功或失败
     *
     * History 2010-04-16 zhuliang
     */
	public static boolean update(String sqlName,Object o)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			s.update(getSqlNameByDataType(sqlName), o);
			s.commit();			
		}catch(Exception ex){				
			ex.printStackTrace();
			return false;
		}
		finally { 
			s.close(); 
		}
		return true;
	}
	/**
     * 删除记录方法　
     * @param sqlName sql别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return boolean 成功或失败
     *
     * History 2010-04-16 zhuliang
     */
	public static boolean delete(String sqlName,Object o)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();				
		try{
			s.delete(getSqlNameByDataType(sqlName), o);
			s.commit();			
		}catch(Exception ex){				
			ex.printStackTrace();
			return false;
		}
		finally { 
			s.close(); 
		}
		return true;
	}
	
	/******************* 以下为存储过程方法 ********************************/
	/**
     * 执行存储过程（带参数的）
     * @param proName 存储别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return 空
     *
     * History 2010-04-16 zhuliang
     */
	public static boolean executeProcedure(String proName,Object o)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{			
			s.selectOne(proName,o);	
			s.commit();
			return true;
		}catch(Exception ex){				
			ex.printStackTrace();
			return false;	
		}     
		finally {
			s.close(); 
		}	
	}
	/**
     * 执行存储过程（不带参数的）
     * @param proName 存储别名，对应xml配置文件中的id
     * @param Object o 字符串或对象或map，用于传入查询参数
     * @return 空
     *
     * History 2010-04-16 zhuliang
     */
	public static boolean executeProcedure(String proName)
	{
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			s.selectOne(proName);
			s.commit();
			return true;
		}catch(Exception ex){				
			ex.printStackTrace();
			return false;	
		}finally { 
			s.close(); 
		}	
	}
	
	/**
     * 根据数据库类型得到匹配的sql名称
     * @param String sqlName sql名称
     * @return String 返回真实的sql名称
     *
     * History 2010-04-16 zhuliang
     */
	public static String getSqlNameByDataType(String sqlName)
	{		
		String data_type_name = BoneDataSourceFactory.getDataTypeName();
		//oracle为默认数据库，后面不会跟数据库别名
		if(!"oracle".equals(data_type_name) && IbatisSessionFactory.getInstance().getConfiguration().hasStatement(sqlName+"_"+data_type_name))
			sqlName = sqlName+"_"+data_type_name;
		return sqlName;
	}
	
	public static void main(String[] args)
	{	 
		
	
	}
}

