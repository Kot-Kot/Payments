package com.payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AddUser {
    public static void main(String[] args) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "new");
        hashMap.put(2, "new");
        hashMap.put(3, "new");
        hashMap.put(4, "new");
        for(Map.Entry<Integer, String> item : hashMap.entrySet()){
            //System.out.printf("Key: %d  Value: %s \n", item.getKey(), item.getValue());
        }
        new MyThreadTest1("T1", hashMap).start();
        //StatusGenerator newThread = new StatusGenerator("StatusGenerator");
        //newThread.start();

    }
}
