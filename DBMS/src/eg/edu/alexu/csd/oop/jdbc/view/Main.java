package eg.edu.alexu.csd.oop.jdbc.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.cs05.MyDriver;
import eg.edu.alexu.csd.oop.jdbc.cs05.MyLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class Main extends Application {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Stage primaryStage;
	private static BorderPane mainLayout;

	public static void main(String[] arg0) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MyLogger.setup();// setup the logger file.
		LOGGER.setLevel(Level.ALL);
		LOGGER.info("Program started.");
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("JDBC");
		showConsole();
	}

	public static void showConsole() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Console.fxml"));
		loader.setControllerFactory(c -> {
			return new ConsoleController(Main.primaryStage, new MyDriver());
		});
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
