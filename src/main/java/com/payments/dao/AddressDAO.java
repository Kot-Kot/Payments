package com.payments.dao;

import com.payments.Main;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressDAO {
    private static final Logger LOG = Logger.getLogger(Main.class.getSimpleName());
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
        LOG.log(Level.INFO, "Success insert into users_billing_address table");
    }

    public void readAll(Connection connection) {
        System.out.println();
        System.out.println("Address Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select id, billing_address, contact from users_billing_address");
            while (rs.next()) {
                System.out.printf("%-20s%-50s%-20s\n",
                        rs.getLong("id"),
                        rs.getString("billing_address"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.log(Level.INFO, "Success readAll from users_billing_address table");
    }
}
