
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//https://www.youtube.com/watch?v=4Bpg5i4tUFg
//https://stackoverflow.com/questions/2533227/how-can-i-disable-the-default-console-handler-while-using-the-java-logging-api


/**
 * Creates a logging tool to log the scheduler run to an external file.
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 */
public class Log {
	public Logger logger;
	FileHandler fileHandler;
	
	/**
	 * Constructor for Log object
	 * @param file_name
	 * @throws SecurityException
	 * @throws IOException
	 */
	public Log(String file_name) throws SecurityException, IOException {
		
		File f = new File(file_name);
		
		fileHandler = new FileHandler(file_name, false);
		logger = Logger.getLogger("test");
		logger.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.setUseParentHandlers(false);
	}
}
