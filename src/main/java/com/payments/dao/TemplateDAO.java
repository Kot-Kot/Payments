package com.payments.dao;

import java.sql.*;
import java.time.LocalDateTime;

public class TemplateDAO {
    public void insertIntoTemplatesTable(Connection connection, String[] template) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO templates (template_name, iban, purpose, contact) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, template[1]);
            preparedStatement.setString(2, template[2]);
            preparedStatement.setString(3, template[3]);
            preparedStatement.setString(4, template[4]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (Exception e) {
            System.out.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoTemplatesTable " + LocalDateTime.now());
    }

    public void readAll(Connection connection) {
        System.out.println("Templates Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select id, template_name, iban, purpose, contact from templates");
            while (rs.next()) {
                System.out.println();
                System.out.printf("%-10s%-15s%-35s%-15s%-10s\n",
                        rs.getString("id"),
                        rs.getString("template_name"),
                        rs.getString("iban"),
                        rs.getString("purpose"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
