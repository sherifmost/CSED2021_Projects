package eg.edu.alexu.csd.oop.db.cs05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtdLoader {
	// this class loads the columns and their data types for further manipulation
	// we assume that the database and table are given and the table file exists
	private static String enter = System.lineSeparator();
	private String tb_name;//name of the required table to be loaded
	private String db_name;//the database
	
	public String getTb_name() {
		return tb_name;
	}

	public void setTb_name(String tb_name) {
		this.tb_name = tb_name;
	}

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	
	public Map<String,String> getColTypes(String db_name,String tb_name){//map giving each column as key and its type as value 
		setDb_name(db_name);
		setTb_name(tb_name);
	    String read = getDtdFromFile(db_name+System.getProperty("file.separator")+tb_name+".dtd");
	    String [] att = getAttLocations(read);
	    Map<String, String> result = new LinkedHashMap<>();
	    int i;
	    for(i = 0;i < att.length;i++) {
	    	String [] temp = att[i].split(" ");
	    	String col =  temp[1];
	    	String type = temp[5];
	    	type = type.substring(1, type.length()-2);
	    	result.put(col, type);
	    }
	   
	    return result;
	}
	
	
	private String[] getAttLocations(String all) {//String array having <!ATTLIST...
		String[] lines = all.split(enter);
		String[] result = new String[(lines.length - 2) / 2];
		int i;// first att line
		int j = 0;
		for (i = 3; i < lines.length; i += 2) {
			result[j] = lines[i];
			j++;

		}
		return result;

	}

	private String getDtdFromFile(String path) {//String dtd loaded from file
		File file = new File(path);
		String result = "";
		@SuppressWarnings("resource")
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;

			while ((st = br.readLine()) != null) {
				result += st;
				result += System.lineSeparator();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
