package com.payments;

import java.util.ArrayList;

public class StatusGenerator extends Thread {
    private boolean flag = true;
    StatusGenerator(String name){
        super(name);
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

