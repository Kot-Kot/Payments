package com.payments.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoTemplatesTable " + LocalDateTime.now());
    }
}
