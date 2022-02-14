package com.payments;

import java.util.ArrayList;

public class MyThread extends Thread {
    private ArrayList<Payment> paymentList;
    private volatile boolean flag = true;

    MyThread(String name, ArrayList<Payment> payments){
        super(name);
        this.paymentList = payments;
    }
    @Override
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{


            while(flag) {
                for (int i = 0; i < paymentList.size(); i++){
                    if(paymentList.get(i).getStatus().equals("new")){
                        System.out.println(paymentList.get(i));
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
