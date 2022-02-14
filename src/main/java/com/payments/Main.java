package com.payments;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        String str = "";
        ArrayList<String> strings = new ArrayList<>();
        try(FileReader reader = new FileReader("initdata1.txt"))
        {
            // читаем посимвольно
            int c;

            while((c=reader.read())!=-1){
                if((char)c == '\n'){
                    //System.out.println(str);
                    strings.add(str);
                    str = "";
                }

                //System.out.print((char)c);
                str += (char)c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());

        }
        ArrayList<Payment> paymentList = new ArrayList<>();


        Payment payment = new Payment();
        String[] userRegistration = null;
        String[] billingAddress = null;
        String[] template = null;
        String[] paymentArr = null;
        //System.out.println(s);
        //String[] words = s.split("\\|");
        for (String s : strings) {
            System.out.println(s);
            if (s.contains("REGISTRATION")){
               userRegistration = s.split("\\|");


                String sqlUser = "INSERT INTO users (fio, email, phone, tlm) "
                        + "VALUES ('"
                        + userRegistration[1] + "','"
                        + userRegistration[2] + "','"
                        + userRegistration[3] + "','"
                        + new Timestamp(System.currentTimeMillis()) + "');";
                ConnectionJDBC.insertIntoTable(sqlUser);

            }
            if (s.contains("ADDRESS")){
                billingAddress = s.split("\\|");

                String sqlUserBillingAddress = "INSERT INTO users_billing_address (id, billing_address, contact) "
                        + "VALUES ('"
                        + billingAddress[1] + "','"
                        + billingAddress[2] + "','"
                        + billingAddress[3] + "');";
                ConnectionJDBC.insertIntoTable(sqlUserBillingAddress);
            }
            if (s.contains("TEMPLATE")){
                template = s.split("\\|");

                String sqlUserBillingAddress = "INSERT INTO templates (id, template_name, iban, purpose, contact) "
                        + "VALUES ('"
                        + template[1] + "','"
                        + template[2] + "','"
                        + template[3] + "','"
                        + template[4] + "','"
                        + template[5] + "');";
                ConnectionJDBC.insertIntoTable(sqlUserBillingAddress);
            }
            if (s.contains("PAYMENT")){
                System.out.println(s);
                paymentArr = s.split("\\|");
                for (String s1 : paymentArr){
                    System.out.println(s1);
                }
                paymentArr[5] = paymentArr[5].replaceAll("[^A-Za-z0-9]", "");
                payment = new Payment(Integer.valueOf(paymentArr[1]), Integer.valueOf(paymentArr[2]), paymentArr[3], Double.valueOf(paymentArr[4]), paymentArr[5],
                        LocalDateTime.now(),
                        LocalDateTime.now());
                paymentList.add(payment);



                String sqlPayment = "INSERT INTO payments (id, template_id, card_number, p_sum, status, creation_dt, status_changed_dt)"
                        + "VALUES ('"
                        + paymentArr[1] + "','"
                        + paymentArr[2] + "','"
                        + paymentArr[3] + "','"
                        + paymentArr[4] + "','"
                        + paymentArr[5] + "','"
                        + new Timestamp(System.currentTimeMillis()) + "','"
                        + new Timestamp(System.currentTimeMillis()) + "');";
                ConnectionJDBC.insertIntoTable(sqlPayment);
            }
        }
        for (int i = 0; i < paymentList.size();i++)
        {
            System.out.println(paymentList.get(i));
        }
        MyThread myThread = new MyThread("MyThread", paymentList);
        myThread.start();

    }



}


