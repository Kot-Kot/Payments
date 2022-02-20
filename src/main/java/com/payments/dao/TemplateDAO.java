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

    public void readFromTemplatesTable(Connection connection) {
        System.out.println("Templates Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from templates");
            while (rs.next()) {
                System.out.println();
                System.out.printf("%s\t%s\t%s\t%s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
