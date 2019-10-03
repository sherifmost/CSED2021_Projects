package eg.edu.alexu.csd.oop.db.cs05;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class DBMSConsole {

	// Console UI for the SQL queries

	public static void main(String[] args) {
		SqlEngine engine = new SqlEngine();// SQL engine
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose database (or enter new database name):");
		String db_name = sc.nextLine();
		engine.get_db(db_name);
		System.out.println();
		System.out.println("1:Choose existing table");
		System.out.println("2:Create new table with query");
		int i = sc.nextInt();
		while (i != 1 && i != 2) {
			System.out.println("invalid input");
			i = sc.nextInt();
		}
		sc.nextLine();
		String tbl_name = null;
		switch (i) {
		case 1:
			System.out.println("Enter the table name:");// get existing table
			tbl_name = sc.nextLine();
			if (new File(db_name + System.getProperty("file.separator") + tbl_name+".xml").exists()) {
				engine.get_tbl(tbl_name);
			} else {
				System.out.println("Table doesn't exist");
				main(args);// get the table again
			}
			break;

		case 2:
			System.out.println("Enter the query:");// form the table
			engine.perform_query(sc.nextLine());
			break;
		}
		int x = 1;
		while (x == 1) {// queries in a loop
			System.out.println("Enter 2 to get the column orders,other to continue:");
			if (sc.nextInt() == 2) {
				Map<String, String> cols = new DtdLoader().getColTypes(db_name, engine.get_tbl_name());
				for (@SuppressWarnings("rawtypes")
				Map.Entry s : cols.entrySet()) {// get the column names
					System.out.println(s.getKey());
				}
			}
			sc.nextLine();
			System.out.println("Enter query:");
			engine.perform_query(sc.nextLine());
			System.out.println("enter:1:continue,other:return");
			x = sc.nextInt();
			sc.nextLine();
			System.out.println();
		}
		main(args);// recursive call after finishing the queries

	}

}
