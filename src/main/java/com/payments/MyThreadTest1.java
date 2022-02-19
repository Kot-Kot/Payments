package com.payments;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyThreadTest1 extends Thread {
    private HashMap<LocalDateTime, String> hashMap;
    private boolean flag = true;
    private int count = 0;

    MyThreadTest1(String name, HashMap<LocalDateTime, String> hashMap){
        super(name);
        this.hashMap = hashMap;
    }
    @Override
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            while(flag) {
                 for(Map.Entry<LocalDateTime, String> item : hashMap.entrySet()){
                     long start = item.getKey().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                     long end = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                     System.out.println("end - start = " + (end - start));
                     if((end - start) >= 2000){
                         item.setValue(statusGenerator());
                         count++;
                     }else{
                         item.setValue("new");
                     }

                     System.out.println("status = " + item.getValue());


                }
                 if (count == hashMap.size()) shutdown();
                Thread.sleep(1000);
                System.out.println("Every 1 sec");
                System.out.println();
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

    String statusGenerator() {
        int number = (int)(Math.random() * 3);
        String status = null;
        switch (number) {
            case 0:
                status = "new";
                break;
            case 1:
                status = "failed";
                break;
            case 2:
                status = "paid";
                break;

        }
        return status;
    }
}
