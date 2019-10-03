package eg.edu.alexu.csd.oop.db.cs05;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.test.TestRunner;

public class maintest {

	public static void main(String[] args) {
		
		
		MyDatabase db = new MyDatabase();
		db.createDatabase("testnew", true);

		try {
			db.executeStructureQuery("Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
			int count1 = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value4', 'value3', 4)");
			System.out.println(count1);
			int count2 = db.executeUpdateQuery("INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value8', 4)");
			System.out.println(count2);
			int count3 = db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VAlUES ('value2', 'value4', 5)");
			System.out.println(count3);
			Object[][] selected = db.executeQuery("SELECT * FROM table_name1 where column_name2 =4");
			int i;
			int j;
			for (i = 0; i < selected.length; i++) {
				for (j = 0; j < selected[i].length; j++) {
					//if (selected[i][j] != null) {
						if (selected[i][j] instanceof Integer) {
							System.out.print((Integer) selected[i][j] + " ");
						} else
							System.out.print((String) selected[i][j] + " ");
					//}
				}
				System.out.println();
			}
			
		} catch (Throwable e){
			TestRunner.fail("Failed to update table with mixing capital and small letters!", e);
		}
	}
}