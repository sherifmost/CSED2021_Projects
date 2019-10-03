package eg.edu.alexu.csd.oop.db.cs05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParsing {

	// singelton design pattern
	private static SqlParsing instance = new SqlParsing();
	private SqlParsing() {};
	public static SqlParsing getinstance() {
		return instance;
	}
	
	private Matcher myMatcher;
	private String brackets = "[\\s]*\\([\\s]*(.*)[\\s]*\\)[\\s]*";
	private String where = "[\\s]*\\bwhere\\b[\\s]*(.*)[\\s]*";
	
	private boolean pattmatch(String compile,String query) {
		Pattern pattern = Pattern.compile(compile,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		if(matcher.find()) {
			myMatcher = matcher;
			return true;
		}
		return false;
		
	}

	// e.g columnname DataType
	private boolean createpattern(String query) {
		Pattern pattern = Pattern.compile("\\G[\\s]*(\\w+)[\\s]*(\\w+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		int c = 0;
		while (matcher.find()) {
			c++;
			if(!matcher.group(2).equalsIgnoreCase("int")&&!matcher.group(2).equalsIgnoreCase("varchar")) {
				return false;
			}
			if (matcher.end() == query.length()&&c-query.replaceAll("[^,]","").length()==1) {
				return true;
			}
		}
		return false;
	}

	private boolean wherestat(String query) {
		Pattern pattern = Pattern.compile("[\\s]*(\\w+)[\\s]*[=<>][\\s]*('[\\s\\w+@.]*'|[+-]?\\d+)[\\s]*",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		while (matcher.find()) {
			if (matcher.end() == query.length()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean setstat(String query) {
		Pattern pattern = Pattern.compile("\\G[\\s]*(\\w+)[\\s]*[=][\\s]*('[\\s\\w+@.]*'|[+-]?\\d+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		int c = 0;
		while (matcher.find()) {
			c++;
			if (matcher.end() == query.length()&&c-query.replaceAll("[^,]","").length()==1) {
				return true;
			}
		}
		return false;
	}
	

	private boolean columnstat(String query) {
		Pattern pattern = Pattern.compile("\\G[\\s]*(\\w+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		int c = 0;
		while (matcher.find()) {
			c++;
			if (matcher.end() == query.length()&&c-query.replaceAll("[^,]","").length()==1) {
				return true;
			}
		}
		return false;
	}
	
	private boolean valuesstat(String query) {
		Pattern pattern = Pattern.compile("\\G[\\s]*('[\\s\\w+@.]*'|[-+]?\\d+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		int c = 0;
		while (matcher.find()) {
			c++;
			if (matcher.end() == query.length()&&c-query.replaceAll("[^,]","").length()==1) {
				return true;
			}
		}
		return false;
	}

	private boolean count(String query1, String query2) {
		int c1 = 0;
		int c2 = 0;
		Pattern pattern1 = Pattern.compile("\\G[\\s]*(\\w+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Pattern pattern2 = Pattern.compile("\\G[\\s]*('[\\s\\w+@.]*'|[-+]?\\d+)[\\s]*,?",Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(query1);
		Matcher matcher2 = pattern2.matcher(query2);
		while (matcher1.find()) {
			c1++;
		}
		while (matcher2.find()) {
			c2++;
		}
		if (c1 == c2) {
			return true;
		}
		return false;

	}

	// create or drop
	public int executestruct(String query) {

		String ofcreatetable = "^[\\s]*(\\bcreate\\b)[\\s]*(\\btable\\b)[\\s]*(\\w+)" + brackets+"$";
		if(pattmatch(ofcreatetable, query)) {
			if (createpattern(myMatcher.group(4))) {
				return 4; // create table by giving columnnames and types
			}
		}
		
		String ofcreate = "^[\\s]*(\\bcreate\\b)[\\s]*(\\bdatabase\\b)[\\s]*([:\\w+\\\\]*)[\\s]*$";
		if(pattmatch(ofcreate, query)) {
			return 3;
		}

		String ofdrop = "^[\\s]*(\\bdrop\\b)[\\s]*(\\bdatabase\\b|\\btable\\b)[\\s]*([:\\w+\\\\]*)[\\s]*$";
		if(pattmatch(ofdrop, query)) {
			if (myMatcher.group(2).equalsIgnoreCase("database")) {
				return 1; // drop database
			} else {
				return 2; // drop table
			}
		}
		return 0; // no query matches (false)

	}

	// select
	public int executequery(String query) {

		String ofselstar = "^[\\s]*(\\bselect\\b)[\\s]*[\\*][\\s]*\\bfrom\\b[\\s]*(\\w+)[\\s]*";
		if(pattmatch(ofselstar+"$", query)) {
			return 1;
		}
		if(pattmatch(ofselstar + where+"$", query)) {
			if (wherestat(myMatcher.group(3))) {
				return 2;
			}
		}
	
		String ofselcolumns = "^[\\s]*(\\bselect\\b)[\\s]*(\\b[(\\w+)[\\s]*,?]*\\b)\\bfrom\\b[\\s]*(\\w+)[\\s]*";
		if(pattmatch(ofselcolumns+"$", query)) {
			if (columnstat(myMatcher.group(2))) {
				return 3;
			}
		}
		
		if(pattmatch(ofselcolumns + where+"$", query)) {
			if (columnstat(myMatcher.group(2)) && wherestat(myMatcher.group(4))) {
				return 4;
			}
		}
		
		return 0;

	}

	// insert or update or delete
	public int executeupdate(String query) {

		String ofupdate = "^[\\s]*(\\bupdate\\b)[\\s]*(\\w+)[\\s]*\\bset\\b[\\s]*([+-@.(\\w+)[\\s]*[=],']*)[\\s]*" ;
		if(pattmatch(ofupdate+ where+"$", query)) {
			if (setstat(myMatcher.group(3)) && wherestat(myMatcher.group(4))) {
				return 1;
			}
		}
		if(pattmatch(ofupdate+"$", query)) {
			if(setstat(myMatcher.group(3))) {
				return 4;
			}
		}
		
		String ofdelete = "^[\\s]*(\\bdelete\\b)[\\s]*\\bfrom\\b[\\s]*(\\w+)[\\s]*" ;
		if(pattmatch(ofdelete+ where+"$", query)) {
			if (wherestat(myMatcher.group(3))) {
				return 2;
			}
		}
		if(pattmatch(ofdelete+"$", query)) {
			return 5;
		}
		
		String ofinsert = "^[\\s]*(\\binsert\\b)[\\s]*\\binto\\b[\\s]*(\\w+)" + brackets + "\\bvalues\\b" + brackets+"$";
		String ofinsert2 = "^[\\s]*(\\binsert\\b)[\\s]*\\binto\\b[\\s]*(\\w+)[\\s]*" + "\\bvalues\\b" + brackets + "$";
		if(pattmatch(ofinsert, query)) {
			if (count(myMatcher.group(3), myMatcher.group(4))) {
				if (columnstat(myMatcher.group(3)) && valuesstat(myMatcher.group(4))) {
					return 3;
				}
			}
		}
		if(pattmatch(ofinsert2, query)) {
			if(valuesstat(myMatcher.group(3))) {
				return 6;
			}
		}
		return 0;

	}
	
}
