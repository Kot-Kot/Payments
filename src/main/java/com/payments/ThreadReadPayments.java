package com.payments;

import com.payments.dao.PaymentDAO;
import com.payments.objects.Payment;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadReadPayments extends Thread {
    private Connection connection;
    private ArrayList<Payment> payments;
    private boolean flag = true;
    private int count = 0;
    private Long startTimestamp;
    private Long currentTimestamp;
    public ThreadReadPayments(String name, ArrayList<Payment> payments){
        super(name);
        this.payments = payments;
    }
    public ThreadReadPayments(String name, Connection connection){
        super(name);
        this.connection = connection;
    }
    @Override
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            startTimestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            while(flag) {
                payments = new ArrayList<>(new PaymentDAO().readWithStatusNew(connection));
                if(payments.size() == 0) flag = false;
                System.out.println( "payments.size()            =            " + payments.size());
                currentTimestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                if (currentTimestamp - startTimestamp < 2000){
                    for (Payment p : payments) {
                        p.setStatus("new");
                    }
//                    for (int i = 0; i<payments.size();i++){
//                        payments.get(i).setStatus("new");
//                    }
                }else{
                    for (Payment p : payments) {
                        p.setStatus(statusGenerator());
                        System.out.println("statusGenerator  : " + p.getId() + "  "+ p.getStatus());
                        new PaymentDAO().update(connection, p);
                    }
//                    for (int i = 0; i<payments.size();i++){
//                        payments.get(i).setStatus(statusGenerator1());
//                        new PaymentDAO().updatePaymentsTable(connection, payments.get(i));
//                    }
                }

                Thread.sleep(1000);
                System.out.println("Every 1 sec");
            }

        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    public void shutdown() {
        this.flag = false;
    }

    synchronized void readStatus (HashMap<LocalDateTime, String> hashMap) {
        for(Map.Entry<LocalDateTime, String> item : hashMap.entrySet()){
            if(item.getValue().equals("new")){
                System.out.println(item.getValue());
            }
        }

    }

    synchronized String statusGenerator() {
        int number = (int)(Math.random() * 3);
        switch (number) {
            case 0:
                return  "paid";
            case 1:
                return "failed";
            case 2:
                return "new";

    }
        return null;
    }

    synchronized String statusGenerator1() {
        int number = (int)(Math.random() * 3);
        String string = "null";
        switch (number) {
            case 0:
                string = "paid";
                break;
            case 1:
                string = "failed";
                break;
            case 2:
                string = "new";
                break;

        }
        return string;
    }
}
