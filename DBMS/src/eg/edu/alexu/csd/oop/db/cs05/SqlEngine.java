package eg.edu.alexu.csd.oop.db.cs05;



public class SqlEngine {
	// class for operating the SQL queries.
	private MyDatabase db = new MyDatabase();


	public SqlEngine() {// Constructor

	}

	public void get_db(String db_name) {// selected existing database
		db.createDatabase(db_name, false);
	}

	public void get_tbl(String tbl_name) {// selected existing table
		db.setTblName(tbl_name);
	}

	public String get_tbl_name() {
		return db.getTblName();
	}

	public void perform_query(String query) {// nested try catch to get the correct operation
		try {
			db.executeStructureQuery(query);
		} catch (Exception e1) {
			try {
				db.executeUpdateQuery(query);
			} catch (Exception e2) {
				try {
					Object[][] print = db.executeQuery(query);
					print_selected(print);
				} catch (Exception e3) {
					System.out.println("INVALID QUERY");
				}
			}

		}

	}

	private void print_selected(Object[][] selected) {// prints the selected 2d array
		int i;
		int j;
		for (i = 0; i < selected.length; i++) {
			for (j = 0; j < selected[i].length; j++) {
				if (selected[i][j] != null) {
					if (selected[i][j] instanceof Integer) {
						System.out.print((Integer) selected[i][j] + " ");
					} else
						System.out.print((String) selected[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}
