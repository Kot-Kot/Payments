package com.payments.dao;

import com.payments.objects.Payment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PaymentDAO {
    public void insert(Connection connection, String[] payment) {
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

    public List<Payment> readWithStatusNew(Connection connection){
        ArrayList<Payment> payments = new ArrayList<>();
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
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Return payments.size() from jdbc = " + payments.size());
        return payments;

    }

    public void update(Connection connection, Payment payment) {
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
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated id = " + payment.getId());

    }

    public void readAll(Connection connection) {
        System.out.println();
        System.out.println("Payments Table");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from payments");
            while (rs.next()) {
                System.out.println();
                System.out.printf("%-15s%-15s%-20s%-10s%-10s%-40s%-40s\n",
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getTimestamp(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
