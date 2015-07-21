package org.auckland.ac.nz.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VIDIVOXLogger {
    
    private static VIDIVOXLogger instance = null;
    private static Logger logger = null;
    
    public static VIDIVOXLogger getInstance() {
        if(instance == null) {
           logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
           instance = new VIDIVOXLogger();
           
           logger.info("Logger init");
        }
        return instance;
     }
    
    public void logDebug(String logString) {
        logger.debug(logString);
    }
    
    public void logDebug(String logString, Object... arg1) {
        logger.debug(logString, arg1);
    }
    
    public void logInfo(String logString) {
        logger.info(logString);
    }
    
    public void logInfo(String logString, Object... arg1) {
        logger.info(logString, arg1);
    }
    
    public void logTrace(String logString, Object... arg1) {
        logger.trace(logString, arg1);
    }
    
    public void logWarn(String logString, Object... arg1) {
        logger.warn(logString, arg1);
    }
    
    public void logError(String logString) {
        logger.error(logString);
    }
    
    public void logError(String logString, Object... arg1) {
        logger.error(logString, arg1);
    }
}
