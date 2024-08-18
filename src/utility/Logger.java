package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger  {
    private static final String LOG_FILE = "application.log";
    private static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void log(String message) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println(timeStamp + " - " + message);
        writer.flush();
    }
}
