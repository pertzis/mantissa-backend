package me.pertzis.mantissa;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MantissaLogger {

    Logger logger;

    public MantissaLogger(String loggerName) throws IOException {
        logger = Logger.getLogger(loggerName);
        FileHandler fh = new FileHandler("/Users/pertzis/mantissa/log.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public void log(Level level, String message) {
        logger.log(level, message);
    }

    public Logger getLogger() {
        return logger;
    }
}
