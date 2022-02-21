package com.payments;

import com.payments.dao.*;
import com.payments.objects.Payment;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class.getName());

    private static void setupLogger(){
        LogManager.getLogManager().reset();
        log.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler("paymentLogger.log");
            fileHandler.setLevel(Level.ALL);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "File logger not working" , e);
        }
    }

    public static void main(String[] args) {

        Main.setupLogger();
        UserDAO userDAO = new UserDAO();
        TemplateDAO templateDAO = new TemplateDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        AddressDAO addressDAO = new AddressDAO();

        ArrayList<Payment> paymentList = new ArrayList<>();
        Payment payment = new Payment();
        String[] userArr = null;
        String[] billingAddressArr = null;
        String[] templateArr = null;
        String[] paymentArr = null;
        Connection connection = connection();
        String str = "";
        ArrayList<String> stringsFromFile = new ArrayList<>();
        new MainDAO().createTables(connection());

        try (FileReader reader = new FileReader("initdata.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    //System.out.println(str);
                    stringsFromFile.add(str);
                    str = "";
                    continue;
                }
                str += (char) c;
            }
            log.log(Level.INFO, "Read from init file");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            log.log(Level.SEVERE, "Exception:", e);
        }

        for (String s : stringsFromFile) {
            System.out.println(s);
            if (s.contains("REGISTRATION")) {
                userArr = s.split("\\|");
                userDAO.insert(connection, userArr);
                continue;
            }
            if (s.contains("ADDRESS")) {
                billingAddressArr = s.split("\\|");
                addressDAO.insert(connection, billingAddressArr);
                continue;
            }
            if (s.contains("TEMPLATE")) {
                templateArr = s.split("\\|");
                templateDAO.insertIntoTemplatesTable(connection, templateArr);
                continue;
            }
            if (s.contains("PAYMENT")) {
                System.out.println(s);
                paymentArr = s.split("\\|");
                for (String s1 : paymentArr) {
                    System.out.println(s1);
                }
                paymentArr[4] = paymentArr[4].replaceAll("[^A-Za-z0-9]", "");
                paymentDAO.insert(connection, paymentArr);
            }

        }
        log.log(Level.INFO, "Write to database");
        ThreadReadPayments readPayments = new ThreadReadPayments("ThreadReadPayments", connection());
        readPayments.start();
        try {
            readPayments.join();
            log.log(Level.INFO, "Change payments status");
            userDAO.readAll2(connection);
            addressDAO.readAll(connection);
            templateDAO.readAll(connection);
            paymentDAO.readAll(connection);
            log.log(Level.INFO, "Read from database");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static Connection connection() {
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "9090";
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return connection;
    }
}


