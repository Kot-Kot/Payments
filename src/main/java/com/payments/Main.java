package com.payments;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Payment> paymentList = new ArrayList<>();
        Payment payment = new Payment();
        String[] userArr = null;
        String[] billingAddressArr = null;
        String[] templateArr = null;
        String[] paymentArr = null;

        String str = "";
        ArrayList<String> stringsFromFile = new ArrayList<>();
        JDBC.createTables();
        try (FileReader reader = new FileReader("initdata.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    //System.out.println(str);
                    stringsFromFile.add(str);
                    str = "";
                }
                str += (char) c;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(s);
        //String[] words = s.split("\\|");
        for (String s : stringsFromFile) {
            System.out.println(s);
            if (s.contains("REGISTRATION")) {
                userArr = s.split("\\|");
                JDBC.insertIntoUserTable(userArr);
            }
            if (s.contains("ADDRESS")) {
                billingAddressArr = s.split("\\|");
                JDBC.insertIntoAddressTable(billingAddressArr);
            }
            if (s.contains("TEMPLATE")) {
                templateArr = s.split("\\|");
                JDBC.insertIntoTemplatesTable(templateArr);
            }
            if (s.contains("PAYMENT")) {
                System.out.println(s);
                paymentArr = s.split("\\|");
                for (String s1 : paymentArr) {
                    System.out.println(s1);
                }
                paymentArr[4] = paymentArr[4].replaceAll("[^A-Za-z0-9]", "");
                JDBC.insertIntoPaymentsTable(paymentArr);
            }

//            if (s.contains("PAYMENT")){
//                System.out.println(s);
//                paymentArr = s.split("\\|");
//                for (String s1 : paymentArr){
//                    System.out.println(s1);
//                }
//                paymentArr[5] = paymentArr[5].replaceAll("[^A-Za-z0-9]", "");
//                payment = new Payment(Integer.valueOf(paymentArr[1]), Integer.valueOf(paymentArr[2]), paymentArr[3], Double.valueOf(paymentArr[4]), paymentArr[5],
//                        LocalDateTime.now(),
//                        LocalDateTime.now());
//                paymentList.add(payment);
//
//
//                String sqlPayment = "INSERT INTO payments (id, template_id, card_number, p_sum, status, creation_dt, status_changed_dt)"
//
////                String sqlPayment = "INSERT INTO payments (id, template_id, card_number, p_sum, status, creation_dt, status_changed_dt)"
////                        + "VALUES ('"
////                        + paymentArr[1] + "','"
////                        + paymentArr[2] + "','"
////                        + paymentArr[3] + "','"
////                        + paymentArr[4] + "','"
////                        + paymentArr[5] + "','"
////                        + new Timestamp(System.currentTimeMillis()) + "','"
////                        + new Timestamp(System.currentTimeMillis()) + "');";
//                ConnectionJDBC.insertIntoTable(sqlPayment);
//            }
//        }
//        for (int i = 0; i < paymentList.size();i++)
//        {
//            System.out.println(paymentList.get(i));
//        }
//        MyThread myThread = new MyThread("MyThread", paymentList);
//        myThread.start();

        }


    }
}


