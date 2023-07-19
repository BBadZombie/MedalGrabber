import java.util.logging.*;
import java.io.IOException;

/**
 * Class whos methods can be used in any other class for logging purposes
 *
 * @author Anthony Lopez
 * @version0 7.03.23
 */
public interface LoggingUtility
{
    Logger logger = Logger.getLogger("MyLogger");

    /**
     * Method to initialize logger
     */
    default void initializeLogger(){
        try{
            FileHandler fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method that writes a Severe log message to the log.txt file
     */
    public static void logSevere(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Method that writes information regarding the normal execution of a method
     */
    public static void logInfo(String message){
        logger.log(Level.INFO, message);
    }

    /**
     * Method that writes information regarding the configurations of a method when run
     */
    public static void logConfig() {
        logger.log(Level.CONFIG, "Logging configuration information");
    }

    /**
     * Method that takes another method as a parameter and executes it on shutdown/error
     */
    public static void logConfigOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                    logConfig();
                }
            });
    }

    /**
     * Method that takes 2 methods as parameters and executes them on shutdown/error
     */
    public static void onShutdown(Runnable method1, Runnable method2) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    method1.run();
                    method2.run();
                }
            });
    }
}
