package com.payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyThreadTest1 extends Thread {
    private volatile HashMap<Integer, String> hashMap;
    private volatile boolean flag = true;

    MyThreadTest1(String name, HashMap<Integer, String> hashMap){
        super(name);
        this.hashMap = hashMap;
    }
    @Override
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{

            while(flag) {
                for(Map.Entry<Integer, String> item : hashMap.entrySet()){
                    if(item.getValue().equals("new")){

                        System.out.println(item.getValue());
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

    public void  shutdown() {
        this.flag = false;
    }
}
