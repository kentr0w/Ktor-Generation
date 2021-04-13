package Copy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLogger {
    
    private static final String logFile = "logs.log";
    private static String path;
    private static final CustomLogger logger = new CustomLogger();
    
    public static boolean initPath(String path) {
        logger.path = path + File.separator + logFile;
        File file = new File(logger.path);
        try {
            return file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public static void writeLog(LogType logType, String text) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String realText = dtf.format(LocalDateTime.now()) + ": " + logType.toString() + ": " + text + System.lineSeparator();
        try {
            Files.write(Path.of(logger.path), realText.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
    }
}
