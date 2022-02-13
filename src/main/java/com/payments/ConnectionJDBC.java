package com.payments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;

public class ConnectionJDBC {
    public static void createTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");

        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        String sql = "DROP TABLE IF EXISTS users";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE users " +
                "( fio VARCHAR(100)," +
                " email VARCHAR(50) PRIMARY KEY, " +
                " phone VARCHAR(15), " +
                " tlm TIMESTAMP )";
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();
    } catch ( Exception e ) {
        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        System.exit(0);
    }
      System.out.println("Table created successfully");
    }
    public static void insertIntoTable(String strSql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
                            "postgres", "9090");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(strSql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}


