package eg.edu.alexu.csd.oop.jdbc.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConsoleController {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ResultSet selected;
	private Driver driver;// should be injected.
	private Connection conn;
	private Properties prop;
	private Statement stat;
	private static String enter = System.lineSeparator();
	private static Stage primaryStage;// should be injected.
	@FXML
	private JFXTextArea SQLStatement;

	@FXML
	private JFXButton executeAllBtn;

	@FXML
	private JFXTextField dbName;

	@FXML
	private Label errorLabel;

	@FXML
	private JFXButton closeBtn;

	@FXML
	private JFXButton executeBtn;

	@FXML
	private JFXButton addBtn;

	@FXML
	private TextArea logger;

	@FXML
	private JFXButton createBtn;

	public ConsoleController(Stage primaryStage, Driver driver) {
		ConsoleController.primaryStage = primaryStage;
		this.driver = driver;
	}

	private String[] getallcols() {
		String[] arr = null;
		try {
			int size = selected.getMetaData().getColumnCount();
			arr = new String[size];
			for (int i = 0; i < size; i++) {
				int type = selected.getMetaData().getColumnType(i+1);
				if(type == 0)
				arr[i] = selected.getMetaData().getColumnName(i + 1)+" ( int )" ;
				else
				arr[i] = selected.getMetaData().getColumnName(i + 1)+" ( varchar )" ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	private Object[][] getalldata() {
		Object[][] arr = null;
		try {
			selected.beforeFirst();
			int size = selected.getMetaData().getColumnCount();
			int rowssize = 0;
			while (selected.next()) {
				rowssize++;
			}
			selected.beforeFirst();
			arr = new Object[rowssize][size];
			int i = 0;
			while (selected.next()) {
				for (int j = 0; j < size; j++) {
					arr[i][j] = selected.getObject(j + 1);
				}
				i++;
			}
			selected.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	@FXML
	private void addToBatch(ActionEvent event) {
		LOGGER.setLevel(Level.ALL);// set level of info to be presented in the log file.
		try {
			stat.addBatch(SQLStatement.getText());
			LOGGER.info("Sql statement" + " " + SQLStatement.getText() + " was added.");// log file.
			logger.setText(logger.getText() + enter + "Statement added.");// gui log.
			executeAllBtn.setDisable(false);
		} catch (SQLException e) {
			LOGGER.severe("Tried to add a statement to closed statement object.");
			e.printStackTrace();// statement was closed.
		}
		SQLStatement.clear();
	}

	@FXML
	private void executeBatch(ActionEvent event) {
		LOGGER.setLevel(Level.ALL);
		LOGGER.info("Started to execute the added statements.");
		try {
			int[] operations = stat.executeBatch();// to print the log operations...........................
			for (int i = 0; i < operations.length; i++) {
				switch (operations[i]) {
				case -1: {// operation failed.
					LOGGER.warning("operation failed.");// sql statement couldn't execute.
					logger.setText(logger.getText() + enter + "operation failed.");
					break;
				}
				case -2: {// successful structured query.
					LOGGER.info("structure query done successfuly.");
					logger.setText(logger.getText() + enter + "structure query done successfuly.");
					break;
				}
				case -3: {// select query.
					LOGGER.warning("Cannot support selection during batch execution.");
					logger.setText(logger.getText() + enter + "Cannot support selection during batch execution.");
					break;
				}
				default: {// update query.
					LOGGER.info("Update query done successfully updated rows:" + operations[i]);
					logger.setText(
							logger.getText() + enter + "Update query done successfully updated rows:" + operations[i]);
					break;
				}

				}
			}
			LOGGER.info("Batch completed.");// didn't stop intermediate.
		} catch (SQLException e) {
			LOGGER.severe(
					"error occured during batch execution due to wrong query syntax and batch failed to continue.");
			logger.setText(
					logger.getText() + enter + "error occured during batch execution and batch failed to continue.");
			e.printStackTrace();// error in one of the queries and stop execution.
		}

		executeAllBtn.setDisable(true);
		try {
			stat.clearBatch();
			LOGGER.info("Batch cleared.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.warning("failed to clear batch.");
			e.printStackTrace();
		}
		SQLStatement.clear();
	}

	@FXML
	private void executeQuery(ActionEvent event) {
		LOGGER.setLevel(Level.ALL);
		String temp = SQLStatement.getText();
		try {
			selected = stat.executeQuery(temp);
			LOGGER.info("Select query executed.");
			logger.setText(logger.getText() + enter + "selection successful.");
			try {
				// showing the result data in new window
				showTable(getallcols().length, getalldata(), getallcols());
				LOGGER.info("Selected table was shown.");
			} catch (IOException e) {
				LOGGER.severe("Selected table couldn't be shown.");
				e.printStackTrace();
			}
		} catch (Exception e2) {

			try {
				if (!stat.execute(temp)) {
					LOGGER.warning("Operation failed.");
					logger.setText(logger.getText() + enter + "Operation failed.");
				} else {
					LOGGER.info("Sql Query " + temp + " successfully executed.");
					logger.setText(logger.getText() + enter + "query successfully executed.");
				}

			} catch (SQLException e3) {

				LOGGER.severe("Query failed due to wrong syntax or nothing was selected.");
				logger.setText(logger.getText() + enter + "INVALID QUERY OR NOTHING WAS SELECTED.");

				e3.printStackTrace();// Invalid query.
			}
		}
		SQLStatement.clear();
	}

	@FXML
	private void createDB() {
		LOGGER.setLevel(Level.ALL);

		if (!dbName.getText().isEmpty()) {
			SQLStatement.setDisable(false);
			closeBtn.setDisable(false);
			addBtn.setDisable(false);
			executeBtn.setDisable(false);
			LOGGER.info("User entered database path " + dbName.getText());
			prop = new Properties();
			prop.put("path", "Databases" + System.getProperty("file.separator") + dbName.getText());

			try {
				conn = driver.connect("jdbc:xmldb://localhost", prop);
				LOGGER.info("Connection with database created successfully.");
				logger.setText(logger.getText() + enter + "Connection successful with database " + dbName.getText());
			} catch (SQLException e) {
				LOGGER.severe("Failed to create connection with the datbase.");
				e.printStackTrace();
			}
			try {
				stat = conn.createStatement();
				LOGGER.info("Ready to operate on sql statements.");
			} catch (SQLException e) {
				LOGGER.severe("Cannot operate on sql statements.");
				e.printStackTrace();
			}
			try {
				stat.execute(
						"create database " + "Databases" + System.getProperty("file.separator") + dbName.getText());
				LOGGER.info("Data base was successfully created or chosen.");
				logger.setText(logger.getText() + enter + "Data base was successfully created or chosen");
				createBtn.setDisable(true);
			} catch (SQLException e) {
				LOGGER.severe("Error in creating the database.");
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void close() {// close all data
		prop = null;
		try {
			if (selected != null) {
				selected.close();
			}
		} catch (SQLException e) {
			LOGGER.severe("failed to close the resultset.");
			e.printStackTrace();
		}
		try {
			stat.close();
		} catch (SQLException e) {
			LOGGER.severe("Failed to cose the statement.");
			e.printStackTrace();
		}
		try {
			conn.close();
			LOGGER.info("Connection successfully closed.");
			logger.setText(logger.getText() + enter + "Connection successfully closed");
			dbName.clear();
			SQLStatement.clear();
			createBtn.setDisable(false);
			executeAllBtn.setDisable(true);
			SQLStatement.setDisable(true);
			addBtn.setDisable(true);
			closeBtn.setDisable(true);
			executeBtn.setDisable(true);
		} catch (SQLException e) {
			LOGGER.severe("Failed to close the connection.");
			logger.setText(logger.getText() + enter + "Connection failed to close.");
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		executeAllBtn.setDisable(true);
		SQLStatement.setDisable(true);
		addBtn.setDisable(true);
		closeBtn.setDisable(true);
		executeBtn.setDisable(true);
	}

	private static void showTable(int colsNum, Object[][] data, String[] colsName) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ConsoleController.class.getResource("Table.fxml"));
		loader.setControllerFactory(c -> {
			return new TableController(colsNum, data, colsName);
		});
		AnchorPane newMessage = loader.load();
		Stage addDialogStage = new Stage();
		addDialogStage.setTitle("Data");
		// NONE means u can access anything else when this window is opened.
		addDialogStage.initModality(Modality.NONE);
		// initowner means that the new stage belong to our main stage.
		addDialogStage.initOwner(primaryStage);
		Scene scene = new Scene(newMessage);
		addDialogStage.setScene(scene);
		addDialogStage.show();
	}

}
