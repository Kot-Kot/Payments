package com.payments;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MainMyThreadTest1 {
    public static void main(String[] args) {

        HashMap<LocalDateTime, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            try {
                hashMap.put(LocalDateTime.now(), "new");
                long start = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                Thread.sleep(100);
                long end = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                System.out.println("end - start  " + (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Map.Entry<LocalDateTime, String> item : hashMap.entrySet()){
            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
        }
        new MyThreadTest1("T1", hashMap).start();
        //new MyThreadTest2("T2", hashMap).start();
        //StatusGenerator newThread = new StatusGenerator("StatusGenerator");
        //newThread.start();

    }
}
