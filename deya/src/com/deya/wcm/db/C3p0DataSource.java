package com.deya.wcm.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//为改 c3p0连接池修改的文件
public class C3p0DataSource implements DataSource {

    private ComboPooledDataSource dataSource;
    public C3p0DataSource() {
        this.dataSource = new ComboPooledDataSource();
    }
     
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        return dataSource.getConnection(username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }
    
    
    public synchronized void setDriver(String driver) {
        try {
            dataSource.setDriverClass(driver);
        } catch (Exception e) {
        }
    }
    
    public void setUrl(String url) {
        dataSource.setJdbcUrl(url);
    }
    
    public void setUsername(String username) {
          dataSource.setUser(username);
    }

    public void setPassword(String password) {
        dataSource.setPassword(password);
    }
    
    public void setInitialPoolSize(int initialPoolSize) {
        dataSource.setInitialPoolSize(initialPoolSize);
    }
    
    public void setMaxPoolSize(int maxPoolSize) {
        dataSource.setMaxPoolSize(maxPoolSize);
    }
      
    public void setMinPoolSize(int minPoolSize) {
        dataSource.setMinPoolSize(minPoolSize);
    }
    
    public void setPreferredTestQuery(String preferredTestQuery) {
        dataSource.setPreferredTestQuery(preferredTestQuery);
    }
    
    public void setPoolPingQuery(String poolPingQuery) {
        dataSource.setPreferredTestQuery(poolPingQuery);
    }

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
