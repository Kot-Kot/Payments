package com.payments.dao;

import java.sql.*;
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

    public void readFromUsersTable(Connection connection) {
        System.out.println("Users Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                System.out.printf("%s\t%s\t%s",
                        rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
