package com.payments;

import java.time.LocalDateTime;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id &&
                Double.compare(payment.sum, sum) == 0 &&
                Objects.equals(template, payment.template) &&
                Objects.equals(cardNumber, payment.cardNumber) &&
                Objects.equals(creationDateTime, payment.creationDateTime) &&
                Objects.equals(statusChangedDateTime, payment.statusChangedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, template, cardNumber, sum, creationDateTime, statusChangedDateTime);
    }
}
