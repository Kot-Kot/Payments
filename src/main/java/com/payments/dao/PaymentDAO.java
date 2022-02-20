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
            connection.close();
        } catch (Exception e) {
            //log.log(Level.SEVERE, "Exception: ", e);
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //log.info("Success insertIntoPaymentsTable " + LocalDateTime.now());
    }

    public synchronized static List<Payment> readFromPaymentsTable(Connection connection){
        //Set<Payment> payments = new HashSet<>();
        ArrayList<Payment> payments = new ArrayList<>();
        //Payment payment = new Payment();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select id, template_id, card_number, p_sum, status, creation_dt, status_changed_dt  from payments");
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong(1));
                payment.setTemplateId(rs.getLong(2));
                payment.setCardNumber(rs.getString(3));
                payment.setSum(rs.getDouble(4));
                payment.setStatus(rs.getString(5));
                payment.setCreationDateTime(rs.getTimestamp(6).toLocalDateTime());
                payment.setStatusChangedDateTime(rs.getTimestamp(7).toLocalDateTime());
                if (payment.getStatus().equals("new")){
                    payments.add(payment);
                }

            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Return payments.size() from jdbc = " + payments.size());
        System.out.println();
        return payments;

    }

    public synchronized void updatePaymentsTable(Connection connection, Payment payment) {
        //Set<Payment> payments = new HashSet<>();
        ArrayList<Payment> payments = new ArrayList<>();
        //Payment payment = new Payment();
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = statement.executeQuery("select * from payments");

            while (rs.next()) {
                if (rs.getLong(1) == payment.getId()){
                    rs.updateString("status", payment.getStatus());
                    rs.updateTimestamp("status_changed_dt", Timestamp.valueOf(LocalDateTime.now()));
                    rs.updateRow();}
//                }else if (rs.getLong(1) == payment.getId() || payment.getStatus().equals("failed")){
//                    rs.deleteRow();
//                }

            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Updated id = " + payment.getId());

    }
}
