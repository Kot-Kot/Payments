package com.payments;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private Template template;
    private String cardNumber;
    private double sum;
    private enum status {
        NEW,
        PAID,
        FAILED
    };
    private LocalDateTime creationDateTime;
    private LocalDateTime statusChangedDateTime;

}
