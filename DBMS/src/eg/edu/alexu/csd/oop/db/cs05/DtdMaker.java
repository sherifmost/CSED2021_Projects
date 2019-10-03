package eg.edu.alexu.csd.oop.db.cs05;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtdMaker {
	// till manipulation a relative path is given to the dtd file.
	// assumption : all Xml elements have no spaces....
	// each column has an attribute for its type and #Pcdata child.
	// a table may contain 0 or more rows and a row must contain each column once.
	// this class creates a dtd file whenever a new table is created.
	private static String element = "<!ELEMENT ";// element tag of dtd
	private static String attribute = "<!ATTLIST ";// attribute tag of dtd
	private static String child_col = "(#PCDATA)";// parsable data type
	private static String child_tbl = "(row*)";// contains 0 or more row tags
	private static String close_line = ">";// closing of a line
	private static String att_name = "type";// name of the column attribute
	private static String att_method = "#FIXED";// this attribute has a fixed value through the whole xml file
	private static String att_type = "CDATA";// the type of the attribute character data
	private static String enter = System.lineSeparator();
	private String tbl_name = null;// name of table
	private Map<String, String> col_type = new LinkedHashMap<>();// map of columns

	public String getTbl_name() {
		return tbl_name;
	}

	public void setTbl_name(String tbl_name) {
		this.tbl_name = tbl_name;
	}

	public Map<String, String> getCol_type() {
		return col_type;
	}

	public void setCol_type(Map<String, String> col_type) {
		this.col_type = col_type;
	}

	public void generate_dtd(String db_name, String table_name, Map<String, String> columns) {// generates the required
																								// dtd file
		setTbl_name(table_name);
		setCol_type(columns);
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(db_name + System.getProperty("file.separator") + table_name + ".dtd"));
			writer.write(getDtdString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getDtdString() {// generates the dtd string

		String s = "";
		s += element + tbl_name + " " + child_tbl + close_line + enter;
		s += element + "row" + " " + getRowChildren() + close_line + enter;
		s += getCol_att();
		return s;

	}

	private String getRowChildren() {// gets the columns
		String children = "(";
		int i = 0;
		if (col_type.size() == 0)
			return "()";
		for (@SuppressWarnings("rawtypes")
		Map.Entry s : col_type.entrySet()) {
			children += s.getKey();
			i++;
			if (i != col_type.size())
				children += ",";

		}
		children += ")";
		return children;
	}

	private String getCol_att() {// gets the columns and their attributes
		String data = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry s : col_type.entrySet()) {
			data += element + s.getKey() + " " + child_col + close_line + enter;
			data += attribute + s.getKey() + " " + att_name + " " + att_type + " " + att_method + " " + "\""
					+ s.getValue() + "\"" + close_line +enter;
			
		}
		return data;
	}

}
