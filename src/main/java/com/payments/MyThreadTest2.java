package com.payments;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MyThreadTest2 extends Thread {
    private volatile boolean flag = true;
    private volatile HashMap<LocalDateTime, String> hashMap;
    MyThreadTest2(String name, HashMap<LocalDateTime, String> hashMap){
        super(name);
        this.hashMap = hashMap;
    }
    @Override
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            for(Map.Entry<LocalDateTime, String> item : hashMap.entrySet()){
                item.setValue("new");
                Thread.sleep(2000);
                item.setValue(statusGenerator());
                System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
            }
           }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    synchronized String statusGenerator() {
        int number = (int)(Math.random() * 3);
        String status = null;
        switch (number) {
            case 0:
                status = "new";
                break;
            case 1:
                status = "failed";
                break;
            case 3:
                status = "paid";
                break;

        }
        return status;
    }
}
