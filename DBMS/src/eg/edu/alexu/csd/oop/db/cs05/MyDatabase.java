package eg.edu.alexu.csd.oop.db.cs05;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import eg.edu.alexu.csd.oop.db.Database;

public class MyDatabase implements Database {
	SqlParsing parser = SqlParsing.getinstance();
	private String brackets = "[\\s]*\\([\\s]*(.*)[\\s]*\\)[\\s]*";
	private String where = "[\\s]*\\bwhere\\b[\\s]*(.*)[\\s]*";
	private String operation;
	private String db_name="";
	private String tbl_name="";
	private Map<String, String> col_types = null;
	

	public MyDatabase() {
	}
	private void setop(String op) {
		operation = op;
	}
	private String getop() {
		return operation;
	}
	private String columnstat() {
		return "\\G[\\s]*(\\w+)[\\s]*,?";
	}
	private String wherestat() {
		return "[\\s]*(\\w+)[\\s]*([=<>])[\\s]*('[\\s\\w+@.]*'|[+-]?\\d+)[\\s]*";
	}
	private String setstat() {
		return "\\G[\\s]*(\\w+)[\\s]*[=][\\s]*('[\\s\\w+@.]*'|[+-]?\\d+)[\\s]*,?";
	}
	private String valuestat() {
		return "\\G[\\s]*('[\\s\\w+@.]*'|[-+]?\\d+)[\\s]*,?";
	}
	private String createstat() {
		return "\\G[\\s]*(\\w+)[\\s]*(\\w+)[\\s]*,?";
	}

	@Override
	public String getTblName() {
		return tbl_name; 
	}

	@Override
	public String getDBName() {
		return db_name;
	}
	public void setTblName(String tbl_name) {
		this.tbl_name =  tbl_name;
	}

	@Override
	public void setDBName(String db_name) {
		this.db_name = db_name;
	}
	
	private void nametab(Pattern pattern,String query,int v) {
		Matcher matcher = pattern.matcher(query);
		if(matcher.find()) {
			setTblName(matcher.group(v));
		}
		
	}
	private void namedb(Pattern pattern,String query,int v) {
		Matcher matcher = pattern.matcher(query);
		if(matcher.find()) {
			setDBName(matcher.group(v));
		}
		
	}
	
	private void setdtdmap(Map<String, String> col_types) {
		this.col_types = col_types;
	}
	@Override
	public Map<String, String> dtdmap (){
		//if (col_types.equals(null)) {
			/*col_types =*/return col_types;//new DtdLoader().getColTypes(getDBName(), getTblName());
			//return col_types;
	//	}
		//return col_types;
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		File f = new File(databaseName);

		if (!f.exists()) {
			try {
				executeStructureQuery("create database " + databaseName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			if (dropIfExists) {
				try {
					executeStructureQuery("drop database " + databaseName);
					executeStructureQuery("create database " + databaseName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
                  setDBName(f.getAbsolutePath());
			}
		}
		return f.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		Files files = new Files();
		if (parser.executestruct(query) == 1) {// drop database
			String ofdrop = "^[\\s]*(\\bdrop\\b)[\\s]*(\\bdatabase\\b)[\\s]*([:\\w+\\\\]*)[\\s]*$";
			Pattern pattern = Pattern.compile(ofdrop,Pattern.CASE_INSENSITIVE);
			namedb(pattern, query, 3);
			File f = new File(getDBName());
			if (files.Delete(f)) {
				return true;
			} else {
				return false;
			}
		} else if (parser.executestruct(query) == 2) {// drop table
			String ofdrop = "^[\\s]*(\\bdrop\\b)[\\s]*(\\btable\\b)[\\s]*(\\w+)[\\s]*$";
			Pattern pattern = Pattern.compile(ofdrop,Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 3);
			File f = new File(getDBName() + System.getProperty("file.separator") + getTblName() + ".xml");
			return files.Delete(f);
		} else if (parser.executestruct(query) == 3) {// create database
			String ofcreate = "^[\\s]*(\\bcreate\\b)[\\s]*(\\bdatabase\\b)[\\s]*([:\\w+\\\\]*)[\\s]*$";
			Pattern pattern = Pattern.compile(ofcreate,Pattern.CASE_INSENSITIVE);
			namedb(pattern, query, 3);
			File file = new File(getDBName());
			if (file.exists()) {
				return true;
			}
			return file.mkdirs();

		} else if (parser.executestruct(query) == 4) {// create table3.
			if (getDBName() == "") {
				throw new SQLException();
			}
			DtdMaker createdtd = new DtdMaker();
			Pattern pattern = Pattern.compile("^[\\s]*(\\bcreate\\b)[\\s]*(\\btable\\b)[\\s]*(\\w+)" + brackets + "$",
					Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 3);
			String path = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			Map<String, String> createdata = abbreviation(pattern, query, createstat(), 4);
			if (new File(path).exists()) {
				return false;
			}
			if (getTblName() != "" && getDBName() != "") {
				createdtd.generate_dtd(getDBName(), getTblName(), createdata);
				files.CreateTableXMLFile(getDBName(),getTblName());

				return true;
			} else {
				return false;
			}
		}
		throw new SQLException();
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		Files files = new Files();
		ValidMaps vMaps = new ValidMaps();
		if (parser.executequery(query) == 1) {// *
			String ofselstar = "^[\\s]*(\\bselect\\b)[\\s]*[\\*][\\s]*\\bfrom\\b[\\s]*(\\w+)[\\s]*$";
			Pattern pattern = Pattern.compile(ofselstar,Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			setdtdmap(new DtdLoader().getColTypes(getDBName(), getTblName()));
			return files.SelectAllFromXML(xmlFilePath);
		}
		if (parser.executequery(query) == 2) {// * where
			String ofselect = "^[\\s]*(\\bselect\\b)[\\s]*[\\*][\\s]*\\bfrom\\b[\\s]*(\\w+)[\\s]*" + where + "$";
			Pattern pattern = Pattern.compile(ofselect, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			Map<String, String> wheredata = abbreviation(pattern, query, wherestat(), 3);
			String operator = getop();
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			setdtdmap(new DtdLoader().getColTypes(getDBName(), getTblName()));
			if(vMaps.checkmaps(getDBName(), getTblName(), wheredata)) {
			return files.SelectAllWhereFromXML(xmlFilePath, wheredata, operator);
			}
		}
		if (parser.executequery(query) == 3) {// cols
			String ofselect = "^[\\s]*(\\bselect\\b)[\\s]*(\\b[(\\w+)[\\s]*,?]*\\b)\\bfrom\\b[\\s]*(\\w+)[\\s]*$";
			Pattern pattern = Pattern.compile(ofselect, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 3);
			LinkedList<String> cols = abbreviation2(pattern, query, columnstat(), 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			setdtdmap(converttomap(cols));
			return files.SelectColsFromXML(xmlFilePath, cols);
		}
		if (parser.executequery(query) == 4) {// cols where
			String ofselect = "^[\\s]*(\\bselect\\b)[\\s]*(\\b[(\\w+)[\\s]*,?]*\\b)\\bfrom\\b[\\s]*(\\w+)[\\s]*" + where
					+ "$";
			Pattern pattern = Pattern.compile(ofselect, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 3);
			LinkedList<String> cols = abbreviation2(pattern, query, columnstat(), 2);
			Map<String, String> wheredata = abbreviation(pattern, query, wherestat(), 4);
			String operator = getop();
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			setdtdmap(converttomap(cols));
			if(vMaps.checkmaps(getDBName(), getTblName(), wheredata)) {
			return files.SelectColsWhereFromXML(xmlFilePath, wheredata, cols, operator);
			}
		}
		throw new SQLException();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		Files files = new Files();
		ValidMaps vMaps = new ValidMaps();
		if (parser.executeupdate(query) == 3) {
			String ofinsert = "^[\\s]*(\\binsert\\b)[\\s]*\\binto\\b[\\s]*(\\w+)" + brackets + "\\bvalues\\b" + brackets
					+ "$";
			Pattern pattern = Pattern.compile(ofinsert, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			LinkedList<String> columns = abbreviation2(pattern, query, columnstat(), 3);
			LinkedList<String> values = abbreviation2(pattern, query, valuestat(), 4);
			Map<String, String> insertmap = new HashMap<>();
			for (int i = 0; i < columns.size(); i++) {
				insertmap.put(columns.get(i), values.get(i));
			}
			if (insertmap.isEmpty()) {
				return 0;
			}
			
			if(!new File(xmlFilePath).exists()) {
				//System.out.println(getDBName()+"  "+getTblName());
				throw new SQLException();
			}
			if (!vMaps.checkmaps(getDBName(),getTblName(),insertmap)) {
				return 0;
			}
			DtdLoader dLoader = new DtdLoader();
			Map<String, String> attributed = dLoader.getColTypes(getDBName(), getTblName());
			files.AppendToXMLFile(xmlFilePath, mapssize(insertmap, attributed),getTblName(),attributed);
			return 1;
		} else if (parser.executeupdate(query) == 6) {
			DtdLoader dtdLoader = new DtdLoader();
			String ofinsert = "^[\\s]*(\\binsert\\b)[\\s]*\\binto\\b[\\s]*(\\w+)[\\s]*" + "\\bvalues\\b" + brackets
					+ "$";
			Pattern pattern = Pattern.compile(ofinsert, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			LinkedList<String> values = abbreviation2(pattern, query, valuestat(), 3);
			Map<String, String> data = dtdLoader.getColTypes(getDBName(), getTblName());
			if (data.size() != values.size()) {
				throw new SQLException();
			}
			Map<String, String> newdata = new LinkedHashMap<>();
			int i = 0;
			for (Map.Entry<String, String> entry : data.entrySet()) {
				newdata.put(entry.getKey(), values.get(i));
				i++;
			}
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			if (!vMaps.checkmaps(getDBName(),getTblName(),newdata)) {
				return 0;
			}
			DtdLoader dLoader = new DtdLoader();
			Map<String, String> attributed = dLoader.getColTypes(getDBName(), getTblName());
			files.AppendToXMLFile(xmlFilePath, mapssize(newdata, attributed),getTblName(), attributed);
			return 1;
		} else if (parser.executeupdate(query) == 2) {
			String ofdelete = "^[\\s]*(\\bdelete\\b)[\\s]*\\bfrom\\b[\\s]*(\\w+)" + where + "$";
			Pattern pattern = Pattern.compile(ofdelete, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			Map<String, String> delmap = abbreviation(pattern, query, wherestat(), 3);
			String operator = getop();
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			if(vMaps.checkmaps(getDBName(), getTblName(), delmap)) {
			return files.DeleteRowFromXML(delmap, operator, xmlFilePath,getTblName());
			}

		} else if (parser.executeupdate(query) == 5) {
			String ofdelete = "^[\\s]*(\\bdelete\\b)[\\s]*\\bfrom\\b[\\s]*(\\w+)[\\s]*$";
			Pattern pattern = Pattern.compile(ofdelete, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			Map<String, String> delmap = new LinkedHashMap<>();
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			return files.DeleteRowFromXML(delmap, "", xmlFilePath,getTblName());

		} else if (parser.executeupdate(query) == 1) {
			String ofupdate = "^[\\s]*(\\bupdate\\b)[\\s]*(\\w+)[\\s]*\\bset\\b[\\s]*([+-@.(\\w+)[\\s]*[=],']*)" + where
					+ "$";
			Pattern pattern = Pattern.compile(ofupdate, Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			Map<String, String> updatedata = abbreviation(pattern, query, setstat(), 3); // data after set in query
			Map<String, String> wheredata = abbreviation(pattern, query, wherestat(), 4); // data after where statement
			String operator = getop(); // operation of where statement
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			if(vMaps.checkmaps(getDBName(), getTblName(), updatedata)&&vMaps.checkmaps(getDBName(), getTblName(), wheredata)) {
			return files.UpdateRowInXML(xmlFilePath, updatedata, wheredata, operator,getTblName());
			}
		} else if (parser.executeupdate(query) == 4) {
			String ofupdate = "^[\\s]*(\\bupdate\\b)[\\s]*(\\w+)[\\s]*\\bset\\b[\\s]*([+-@.(\\w+)[\\s]*[=],']*)[\\s]*";
			Pattern pattern = Pattern.compile(ofupdate+"$", Pattern.CASE_INSENSITIVE);
			nametab(pattern, query, 2);
			String xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			Map<String, String> updatedata = abbreviation(pattern, query, setstat(), 3); // data after set in query
			Map<String, String> wheredata = new HashMap<>(); // data after where statement
			xmlFilePath = getDBName() + System.getProperty("file.separator") + getTblName() + ".xml";
			if(!new File(xmlFilePath).exists()) {
				throw new SQLException();
			}
			if(vMaps.checkmaps(getDBName(), getTblName(), updatedata)) {
			return files.UpdateRowInXML(xmlFilePath, updatedata, wheredata, "",getTblName());
			}
		}
		throw new SQLException();
	}

	private Map<String, String> abbreviation(Pattern pattern, String query, String stat, int v) {
		Matcher matcher = pattern.matcher(query);
		Pattern pattern1 = Pattern.compile(stat, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = null;
		if (stat.equals(setstat())) {
			if (matcher.find()) {
				matcher1 = pattern1.matcher(matcher.group(v));
			}
			Map<String, String> data = new LinkedHashMap<>();
			while (matcher1.find()) {
				data.put(matcher1.group(1).toLowerCase(), matcher1.group(2).toLowerCase());
			}
			return data;
		}
		if (stat.equals(wherestat())) {
			if (matcher.find()) {
				matcher1 = pattern1.matcher(matcher.group(v));
			}
			Map<String, String> data = new LinkedHashMap<>();
			while (matcher1.find()) {
				data.put(matcher1.group(1).toLowerCase(), matcher1.group(3).toLowerCase());
				setop(matcher1.group(2));
			}
			return data;
		}
		if (stat.equals(createstat())) {
			if (matcher.find()) {
				matcher1 = pattern1.matcher(matcher.group(v));
			}
			Map<String, String> data = new LinkedHashMap<>();
			while (matcher1.find()) {
				data.put(matcher1.group(1).toLowerCase(), matcher1.group(2).toLowerCase());
			}
			return data;
		}
		return null;
	}

	private LinkedList<String> abbreviation2(Pattern pattern, String query, String stat, int v) {
		Matcher matcher = pattern.matcher(query);
		Pattern pattern1 = Pattern.compile(stat, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = null;
		if (stat.equals(columnstat()) || stat.equals(valuestat())) {
			if (matcher.find()) {
				matcher1 = pattern1.matcher(matcher.group(v));
			}
			LinkedList<String> data = new LinkedList<>();
			while (matcher1.find()) {
				data.add(matcher1.group(1).toLowerCase());
			}
			return data;
		}
		return null;
	}
	
	private Map<String, String>mapssize(Map<String, String> currentmap,Map<String,String> dtdmap) {
		for(Map.Entry<String, String> entry: dtdmap.entrySet()) {
			boolean enter = false;
			for(Map.Entry<String, String> entry1:currentmap.entrySet()) {
				if(entry1.getKey().equalsIgnoreCase(entry.getKey())) {
					enter = true;
					break;
				}
			}
			if(!enter) {
				currentmap.put(entry.getKey() ,null);
			}
		}
		return currentmap;
	}

	private Map<String, String> converttomap(LinkedList<String> colnames){
		Map<String, String > mymap = new LinkedHashMap<>();
		Map<String, String> dtdmap = new DtdLoader().getColTypes(getDBName(), getTblName());
		for(int i=0;i<colnames.size();i++) {
			String s = colnames.get(i);
			for(Map.Entry<String, String> entry: dtdmap.entrySet()) {
				if(colnames.get(i).equalsIgnoreCase(entry.getKey())) {
					mymap.put(s, entry.getValue());
				}
			}
		}
		return mymap;
	}
}
