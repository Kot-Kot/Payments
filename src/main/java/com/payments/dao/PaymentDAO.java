package com.payments.dao;

import com.payments.objects.Payment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PaymentDAO {
    public void insertIntoPaymentsTable(Connection connection, String[] payment) {
        PreparedStatement preparedStatement;
        try {
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
       } catch (Exception e) {
            //log.log(Level.SEVERE, "Exception: ", e);
            System.out.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoPaymentsTable " + LocalDateTime.now());
    }

    synchronized public List<Payment> readFromPaymentsTable(Connection connection){
        //Set<Payment> payments = new HashSet<>();
        ArrayList<Payment> payments = new ArrayList<>();
        //Payment payment = new Payment();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select id, template_id, card_number, p_sum, status, creation_dt, status_changed_dt  from payments");
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong("id"));
                payment.setTemplateId(rs.getLong("template_id"));
                payment.setCardNumber(rs.getString("card_number"));
                payment.setSum(rs.getDouble("p_sum"));
                payment.setStatus(rs.getString("status"));
                payment.setCreationDateTime(rs.getTimestamp("creation_dt").toLocalDateTime());
                payment.setStatusChangedDateTime(rs.getTimestamp("status_changed_dt").toLocalDateTime());
                if (payment.getStatus().equals("new")){
                    payments.add(payment);
                }

            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Return payments.size() from jdbc = " + payments.size());
        System.out.println();
        return payments;

    }

    synchronized public void updatePaymentsTable(Connection connection, Payment payment) {
        ArrayList<Payment> payments = new ArrayList<>();
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = statement.executeQuery("select * from payments");

            while (rs.next()) {
                if (rs.getLong(1) == payment.getId()){
                    rs.updateString("status", payment.getStatus());
                    rs.updateTimestamp("status_changed_dt", Timestamp.valueOf(LocalDateTime.now()));
                    rs.updateRow();}
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated id = " + payment.getId());

    }
}
