package com.payments;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PaymentLogger {
    //private final static Logger log = Logger.getLogger(PaymentLogger.class.getName());
    final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static void setupLogger(){
        LogManager.getLogManager().reset();
        log.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler("paymentLogger.log", true);
            //fileHandler.setFormatter();
            fileHandler.setLevel(Level.ALL);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "File logger not working" , e);
        }
    }

}
