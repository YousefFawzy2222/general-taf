package com.automationexercise.utils.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {
    // we commented this line below to avoid always initializing the logger before each call by  using a static function
    //private static final Logger logger = LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());

    private static Logger logger(){
        // this will get the class name of the caller class, so we can have different loggers for different classes
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }
    //info ("errorMessage"+ " " + e.getMessage); > info ("errorMessage, e.getMessage);
    public static void info(String... message){
        logger().info(String.join(" ", message));
    }
    public static void error(String... message){
        logger().error(String.join(" ", message));
    }
    public void warn (String... message){
        logger().warn(String.join(" ", message));
    }
    public static void debug(String... message){
        logger().debug(String.join(" ", message));
    }
    public void fatal (String... message){
        logger().fatal(String.join(" ", message));
    }
    public void trace (String... message){
        logger().trace(String.join(" ", message));
    }
}
