package com.payments;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadReadPayments extends Thread {
    private ArrayList<Payment> payments;
    private boolean flag = true;
    private int count = 0;
    private Long startTimestamp;
    private Long currentTimestamp;
    ThreadReadPayments(String name, ArrayList<Payment> payments){
        super(name);
        this.payments = payments;
    }
    ThreadReadPayments(String name){
        super(name);
    }
    @Override
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            startTimestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            while(flag) {
            payments = new ArrayList<>(JDBC.readFromPaymentsTable());
                if(payments.size() == 0) shutdown();
            System.out.println( "payments.size()            =            " + payments.size());


            //while(flag) {

                //if((LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - startTimestamp) > 5500) { shutdown(); }
                //payments = new ArrayList<>(JDBC.readFromPaymentsTable());
//                for (int i = 0; i < payments.size();i++)
//                {

                    currentTimestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    if (currentTimestamp - startTimestamp < 2000){
                        for (int i = 0; i < payments.size();i++) {
                            payments.get(i).setStatus("new");
                        }
                    }else{
                        for (int i = 0; i < payments.size();i++) {
                        payments.get(i).setStatus(statusGenerator());
                        System.out.println("statusGenerator  : " + payments.get(i).getId() + "  "+ payments.get(i).getStatus());
                        JDBC.updatePaymentsTable(payments.get(i));
                        }
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
        String status = null;
        switch (number) {
            case 0:
                status = "paid";
                break;
            case 1:
                status = "failed";
                break;
            case 2:
                status = "new";
                break;

        }
        return status;
    }
}
