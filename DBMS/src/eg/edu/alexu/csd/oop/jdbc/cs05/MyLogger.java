package eg.edu.alexu.csd.oop.jdbc.cs05;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {// this class is used to implement the logging configuration.
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	public static void setup() throws IOException {

		// get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		File file = new File("logs");
		if (!file.isDirectory())
			file.mkdirs();
		int size = file.list().length + 1;
		File temp = new File("logs" + System.getProperty("file.separator") + "Log" + size);
		while (temp.exists()) {
			size++;
			temp = new File("logs" + System.getProperty("file.separator") + "Log" + size);
		}
		fileTxt = new FileHandler("logs" + System.getProperty("file.separator") + "Log" + size);
		// create a TXT formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
	}
}
