package com.payments;

import com.payments.dao.PaymentDAO;
import com.payments.objects.Payment;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ThreadReadPayments extends Thread {

    private Connection connection;
    private Random random = new Random();
    private boolean flag = true;
    private PaymentDAO paymentDAO;

    public ThreadReadPayments(String name, Connection connection){
        super(name);
        this.connection = connection;
    }
    @Override
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try {
            while (flag) {
                List<Payment> payments = paymentDAO.readWithStatusNew(connection);
                if (payments.isEmpty()) {
                    break;
                }
                System.out.println("payments.size()            =            " + payments.size());

                LocalDateTime now = LocalDateTime.now();
                for (Payment p : payments) {
                    System.out.println("time = " + p.getCreationDateTime().until(now, ChronoUnit.MILLIS));
                    if (p.getCreationDateTime().until(now, ChronoUnit.MILLIS) <= 2000) {
                        p.setStatus("new");
                    } else {
                        p.setStatus(statusGenerator());
                        System.out.println("statusGenerator  : " + p.getId() + "  " + p.getStatus());
                        paymentDAO.update(connection, p);
                    }
                }

                Thread.sleep(1000);
                System.out.println("Every 1 sec");
            }
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }


    private String statusGenerator() {
        int number = random.nextInt(3);
        switch (number) {
            case 0:
                return "paid";
            case 1:
                return "failed";
            case 2:
                return "new";

        }
        return null;
    }

}
