package eg.edu.alexu.csd.oop.jdbc.cs05;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs05.MyDatabase;

public class MyConnection implements java.sql.Connection {

	private Database db = new MyDatabase();
	private LinkedList<Statement> statements = new LinkedList<>();
	private boolean closed = false;

	public MyConnection(String path) {
		db.setDBName(path);
	}

	@Override
	public Statement createStatement() throws SQLException {// Creates a Statement object for sending SQL statements to
															// the database.
		if (closed == true) {
			throw new SQLException();
		}
		Statement s = new MyStatement(db, this);
		statements.add(s);
		return s;
	}

	@Override
	public void close() throws SQLException {// Releases this Connection object's database and JDBC resources
												// immediately
												// instead of waiting for them to be automatically released.
		closed = true;
		for (int i = 0; i < statements.size(); i++) {// make all the created statements equal null.
			@SuppressWarnings("unused")
			Statement temp = statements.get(i);
			temp = null;
		}
		statements = null;
		db = null;
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void commit() throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void rollback() throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		throw new java.lang.UnsupportedOperationException();

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

}
