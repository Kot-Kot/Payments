package com.payments.dao;

import com.payments.objects.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserDAO {

    public void insert(Connection connection, String[] user) {
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


    public void readAll2(Connection connection) {
        System.out.println();
        System.out.println("Users Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select fio, email, phone from users");
            while (rs.next()) {
                System.out.printf("%-10s%-20s%-35s\n",
                        rs.getString("fio"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
