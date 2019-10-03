package eg.edu.alexu.csd.oop.jdbc.cs05;

import java.sql.SQLException;
import java.util.Map;
import eg.edu.alexu.csd.oop.db.Database;

public class MyResultSetMetaData implements java.sql.ResultSetMetaData {

	private Database mydb;
	private Object [][] data;

	public MyResultSetMetaData(Database mydb,Object [][] data) {
		this.mydb = mydb;
		this.data = data;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return data[0].length;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		Map<String, String> tableStructure = mydb.dtdmap();
		int counter = 1;
		for (Map.Entry<String, String> tblStruct : tableStructure.entrySet()) {
			if (column == counter) {
				return tblStruct.getKey();
			}
			counter++;
		}
		return null;
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		return getColumnLabel(column);
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		Map<String, String> datastruct = mydb.dtdmap();
		int counter = 1;
		for (Map.Entry<String, String> entry : datastruct.entrySet()) {
			if (column == counter) {
				if (entry.getValue().equalsIgnoreCase("int")) {
					return 0;
				} else if (entry.getValue().equalsIgnoreCase("varchar")) {
					return 1;
				} else {
					return -1;// another type
				}
			}
			counter++;
		}
		return -1;// column doesnt exist
	}

	@Override
	public String getTableName(int column) throws SQLException {
		Map<String, String> datastruct = mydb.dtdmap();
		if (column > 0 && column <= datastruct.size()) {
			return mydb.getTblName();
		}
		return "";
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getScale(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

}
