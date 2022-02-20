package com.payments.dao;

import java.sql.*;
import java.time.LocalDateTime;

public class AddressDAO {
    public void insert(Connection connection, String[] address) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users_billing_address (billing_address, contact) VALUES (?, ?);");
            preparedStatement.setString(1, address[1]);
            preparedStatement.setString(2, address[2]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (Exception e) {
            System.out.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoAddressTable " + LocalDateTime.now());
    }

    public void readAll(Connection connection) {
        System.out.println("Address Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select id, billing_address, contact from users_billing_address");
            while (rs.next()) {
                System.out.printf("%-20s%-50s%-20s\n",
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
