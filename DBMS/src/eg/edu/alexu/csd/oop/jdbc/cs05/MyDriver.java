package eg.edu.alexu.csd.oop.jdbc.cs05;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import java.util.logging.Logger;


public class MyDriver implements java.sql.Driver {

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return true;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if(url==null) {
			throw new SQLException();
		}
		File dir = new File(info.get("path").toString());
		String path = dir.getAbsolutePath();
		return new MyConnection(path);
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo propertyInfo=new DriverPropertyInfo("path", info.getProperty("path"));
		DriverPropertyInfo[] arr = new DriverPropertyInfo[1];
		arr[0]=propertyInfo;
		return arr;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	@Override
	public int getMajorVersion() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}


}
