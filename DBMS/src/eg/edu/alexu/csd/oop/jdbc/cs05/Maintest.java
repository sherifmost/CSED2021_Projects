package eg.edu.alexu.csd.oop.jdbc.cs05;

import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.junit.Assert;

import eg.edu.alexu.csd.oop.TestRunner;
import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs05.MyDatabase;
import eg.edu.alexu.csd.oop.db.cs05.SqlParsing;

public class Maintest {

	
	 private static Connection createDatabase(String databaseName, boolean drop) throws SQLException{
	        Driver driver = (Driver)eg.edu.alexu.csd.oop.TestRunner.getImplementationInstanceForInterface(Driver.class);
	        Properties info = new Properties();
	        File dbDir = new File("sample" + System.getProperty("file.separator") + ((int)(Math.random() * 100000)));
	        info.put("path", dbDir.getAbsoluteFile());
	        Connection connection = driver.connect("jdbc:xmldb://localhost", info);
	        Statement statement = connection.createStatement();
	        statement.execute("DROP DATABASE " + databaseName);
	        if(drop)
	            statement.execute("CREATE DATABASE " + databaseName);
	        statement.close();
	        return connection;
	    }
	 
	 
	public static void main(String[] args) {
		/////////////////////////////////test scenario 1
		 Connection connection = null;
		try {
			connection = createDatabase("TestDB", true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        
	        try {
	            Statement statement = connection.createStatement();
	            statement.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
	            
	            int count1 = statement.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
	            Assert.assertNotEquals("Insert returned zero rows", 0, count1);
	            int count2 = statement.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value2', 'value3', 5)");
	            Assert.assertNotEquals("Insert returned zero rows", 0, count2);
	            int count3 = statement.executeUpdate("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value6', 'value4', 6)");
	            Assert.assertNotEquals("Insert returned zero rows", 0, count3);
	            
	            int count4 = statement.executeUpdate("DELETE From table_name1  WHERE coLUmn_NAME2=4");
	            Assert.assertEquals("Delete returned wrong number", 1, count4);
	            
	            ResultSet result = statement.executeQuery("SELECT column_name1 FROM table_name1 WHERE coluMN_NAME2 < 6");
	            int rows = 0;
	            while(result.next()) {   
	            	
	            	rows++;
	            	System.out.println("inc: "+rows);
	            }
	            result.previous();
	           // Assert.assertNotNull("Null result returned", result);
	           // Assert.assertEquals("Wrong number of rows", 1, rows);
	           // Assert.assertEquals("Wrong number of columns", 3, result.getMetaData().getColumnCount());
	            System.out.println("result: "+result);
	            System.out.println("rows: "+rows);
	            System.out.println("metadata1: "+result.getMetaData().getColumnCount());
	            System.out.println("metastring1: "+result.getString(1));
	            System.out.println("metadatatype1: "+result.getMetaData().getColumnType(1));
	            int count5 = statement.executeUpdate("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=10, column_name3='333333333' WHERE coLUmn_NAME2=5");
	            Assert.assertEquals("Update returned wrong number", 1, count5);
	            
	            ResultSet result2 = statement.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 > 4");
	            int rows2 = 0;
	            while(result2.next()) {
	            	rows2++;
	            	//System.out.println("inc: "+rows2);
	            }
	            Assert.assertNotNull("Null result returned", result2);
	            Assert.assertEquals("Wrong number of rows", 2, rows2);
	            Assert.assertEquals("Wrong number of columns", 3, result2.getMetaData().getColumnCount());
	            
	            /*System.out.println("result: "+result2);
	            System.out.println("rows: "+rows2);
	            System.out.println("metadata1: "+result2.getMetaData().getColumnCount());
	            System.out.println("metadatatype1: "+result2.getMetaData().getColumnType(1));*/
	            //Object column_2_object = result2[0][1];
	            while(result2.previous());
	            result2.next();
	            Object column_2_object = result2.getObject(2);
	            if (column_2_object instanceof String)
	                fail("This should be 'Integer', but found 'String'!");
	            else if (column_2_object instanceof Integer) {
	                int column_2 = (Integer) column_2_object;
	                Assert.assertEquals("Select did't return the updated record!", 10, column_2);
	            }
	            else
	                fail("This should be 'Integer', but what is found can't be identified!");
	            
	            statement.close();
	        } catch (Throwable e){
	            TestRunner.fail("Failed to complete scenario 3", e);
	        }
	        
	        try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
/////////////////////////////////test scenario 3
		
	}

}
