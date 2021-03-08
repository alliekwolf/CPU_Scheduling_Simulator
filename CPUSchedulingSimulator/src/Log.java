
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//https://www.youtube.com/watch?v=4Bpg5i4tUFg
//https://stackoverflow.com/questions/2533227/how-can-i-disable-the-default-console-handler-while-using-the-java-logging-api
public class Log {
	public Logger logger;
	FileHandler fileHandler;
	
	public Log(String file_name) throws SecurityException, IOException {
		
		File f = new File(file_name);
		
		fileHandler = new FileHandler(file_name, true);
		logger = Logger.getLogger("test");
		logger.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.setUseParentHandlers(false);
	}
}
