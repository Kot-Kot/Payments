package com.payments.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;

public class UserDAO {

    public void insertIntoUsersTable(Connection connection, String[] user) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users (fio, email, phone) VALUES (?,?,?);");
            preparedStatement.setString(1, user[1]);
            preparedStatement.setString(2, user[2]);
            preparedStatement.setString(3, user[3]);
            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
        } catch (Exception e) {
            System.out.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoUserTable " + LocalDateTime.now());
    }

    public void readFromUsersTable(Connection connection, String[] user) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users (fio, email, phone) VALUES (?,?,?);");
            preparedStatement.setString(1, user[1]);
            preparedStatement.setString(2, user[2]);
            preparedStatement.setString(3, user[3]);
            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.commit();
        } catch (Exception e) {
            System.out.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoUserTable " + LocalDateTime.now());
    }
}
