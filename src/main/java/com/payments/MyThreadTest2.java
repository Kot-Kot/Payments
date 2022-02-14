package com.payments;

import java.util.HashMap;

public class MyThreadTest2 extends Thread {
    private volatile boolean flag = true;
    private volatile HashMap<Integer, String> hashMap;
    MyThreadTest2(String name, HashMap<Integer, String> hashMap){
        super(name);
        this.hashMap = hashMap;
    }
    @Override
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{

            System.out.println("new");
            Thread.sleep(2000);
            int number = (int)(Math.random() * 3) ;
            switch (number) {
                case 0:
                    System.out.println("new");
                    break;
                case 1:
                    System.out.println("failed");
                    break;
                case 3:
                    System.out.println("paid");
                    break;

            }

        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}
