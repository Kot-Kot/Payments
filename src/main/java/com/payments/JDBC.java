package com.payments;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JDBC {
    private static final Logger log = Logger.getLogger(JDBC.class.getName());
    private static void setupLogger(){
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

    public static void createTables() {
        JDBC.setupLogger();
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "9090";
        String s = new String();
        StringBuffer sb = new StringBuffer();
        FileReader fr = null;
        //Statement statement = null;
        //Connection connection = null;

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            fr = new FileReader(new File("sql.sql"));
            BufferedReader br = new BufferedReader(fr);


            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.valueOf(sb));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.log(Level.INFO, "Tables created" );
    }


    public static void createTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");

        System.out.println("Opened database successfully");

        statement = connection.createStatement();
        String sql = "DROP TABLE IF EXISTS users";
        statement.executeUpdate(sql);
        sql = "CREATE TABLE users " +
                "( fio VARCHAR(100)," +
                " email VARCHAR(50) PRIMARY KEY, " +
                " phone VARCHAR(15), " +
                " tlm TIMESTAMP )";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    } catch ( Exception e ) {
        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        System.exit(0);
    }
      System.out.println("Table created successfully");
    }
    public static void insertIntoTable(String strSql, String[] paymentArr) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            preparedStatement = connection.prepareStatement(strSql);
            preparedStatement.setInt(1, 200);
            preparedStatement.setString(2, "Петров");
            preparedStatement.setString(3, "990077");
            preparedStatement.executeUpdate();
//            statement = connection.createStatement();
//            statement.executeUpdate(strSql);
//            statement.close();
//            connection.commit();
//            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void insertIntoUserTable(String[] user) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            preparedStatement = connection.prepareStatement("INSERT INTO users (fio, email, phone) VALUES (?,?,?);");
            preparedStatement.setString(1, user[1]);
            preparedStatement.setString(2, user[2]);
            preparedStatement.setString(3, user[3]);
            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        log.info("Success insertIntoUserTable " + LocalDateTime.now());
    }

    public static void insertIntoAddressTable(String[] address) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            preparedStatement = connection.prepareStatement("INSERT INTO users_billing_address (billing_address, contact) VALUES (?, ?);");
            preparedStatement.setString(1, address[1]);
            preparedStatement.setString(2, address[2]);
            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        log.info("Success insertIntoAddressTable " + LocalDateTime.now());
    }

    public static void insertIntoTemplatesTable(String[] template) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            preparedStatement = connection.prepareStatement("INSERT INTO templates (template_name, iban, purpose, contact) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, template[1]);
            preparedStatement.setString(2, template[2]);
            preparedStatement.setString(3, template[3]);
            preparedStatement.setString(4, template[4]);

            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        log.info("Success insertIntoTemplatesTable " + LocalDateTime.now());
    }

    public static void insertIntoPaymentsTable(String[] payment) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            preparedStatement = connection.prepareStatement("INSERT INTO payments (template_id, card_number, p_sum, status, creation_dt, status_changed_dt) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setLong(1, Long.parseLong(payment[1]));
            preparedStatement.setString(2, payment[2]);
            preparedStatement.setDouble(3, Double.parseDouble(payment[3]));
            preparedStatement.setString(4, payment[4]);
            preparedStatement.setTimestamp (5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp (6, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
       log.info("Success insertIntoPaymentsTable " + LocalDateTime.now());
    }

    synchronized public static List<Payment> readFromPaymentsTable(){
        //Set<Payment> payments = new HashSet<>();
        ArrayList<Payment> payments = new ArrayList<>();
        //Payment payment = new Payment();
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "9090";

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("select * from payments");

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong(1));
                payment.setTemplateId(rs.getLong(2));
                payment.setCardNumber(rs.getString(3));
                payment.setSum(rs.getDouble(4));
                payment.setStatus(rs.getString(5));
                payment.setCreationDateTime(rs.getTimestamp(6).toLocalDateTime());
                payment.setStatusChangedDateTime(rs.getTimestamp(7).toLocalDateTime());
                if (payment.getStatus().equals("new")){
                    payments.add(payment);
                }

            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Return payments.size() from jdbc = " + payments.size());
        System.out.println();
        return payments;

    }


    synchronized public static void updatePaymentsTable(Payment payment) {
        //Set<Payment> payments = new HashSet<>();
        ArrayList<Payment> payments = new ArrayList<>();
        //Payment payment = new Payment();
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "9090";

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {


            ResultSet rs = statement.executeQuery("select * from payments");

            while (rs.next()) {
                if (rs.getLong(1) == payment.getId()){
                    rs.updateString("status", payment.getStatus());
                    rs.updateTimestamp("status_changed_dt", Timestamp.valueOf(LocalDateTime.now()));
                    rs.updateRow();}
//                }else if (rs.getLong(1) == payment.getId() || payment.getStatus().equals("failed")){
//                    rs.deleteRow();
//                }

            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated id = " + payment.getId());

    }
}




