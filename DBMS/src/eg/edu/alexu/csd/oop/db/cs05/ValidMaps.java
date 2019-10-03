package eg.edu.alexu.csd.oop.db.cs05;

import java.util.Map;

public class ValidMaps {
	public boolean checkmaps(String dbname, String tbname,Map<String, String> currentmap) {
		DtdLoader loader = new DtdLoader();
		Map<String, String> loadedmap = loader.getColTypes(dbname, tbname);
		String [][] arr = toarray(currentmap);
		for(int i=0;i<arr.length;i++) {
			if(!found(arr[i][0], arr[i][1], loadedmap)) {
				return false;
			}
		}
		return true;
	}
	private boolean found (String s1,String s2 ,Map<String, String> data) { // search for element in map
		for(Map.Entry<String,String> entry: data.entrySet()) {
			if(s1.equalsIgnoreCase(entry.getKey())&& datatype(s2).equalsIgnoreCase(entry.getValue())) {
				return true;
			}
		}
		return false;
	}
	private String[][] toarray(Map<String, String> currentmap) {
		String [][] myarray = new String [currentmap.size()][2];
		int i = 0;
		for(Map.Entry<String, String> entry: currentmap.entrySet()) {
			myarray[i][0] = entry.getKey();
			myarray[i][1] = entry.getValue();
			i++;
		}
		return myarray;	
	}
	private String datatype(String string) {
		if(string.contains("'")) {
			return "varchar";
		}
		return "int";
	}

}
