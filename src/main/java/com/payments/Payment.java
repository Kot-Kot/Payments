package com.payments;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private int id;
    private int templateId;
    private String cardNumber;
    private double sum;
//    private enum status {
////        NEW,
////        PAID,
////        FAILED
////    };
    private String status;
    private LocalDateTime creationDateTime;
    private LocalDateTime statusChangedDateTime;

    public Payment() {

    }

    public Payment(int id, int templateId, String cardNumber, double sum, String status, LocalDateTime creationDateTime, LocalDateTime statusChangedDateTime) {
        this.id = id;
        this.templateId = templateId;
        this.cardNumber = cardNumber;
        this.sum = sum;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.statusChangedDateTime = statusChangedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id &&
                Double.compare(payment.sum, sum) == 0 &&
                Objects.equals(templateId, payment.templateId) &&
                Objects.equals(cardNumber, payment.cardNumber) &&
                Objects.equals(creationDateTime, payment.creationDateTime) &&
                Objects.equals(statusChangedDateTime, payment.statusChangedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateId, cardNumber, sum, creationDateTime, statusChangedDateTime);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", templateId=" + templateId +
                ", cardNumber='" + cardNumber + '\'' +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", statusChangedDateTime=" + statusChangedDateTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getStatusChangedDateTime() {
        return statusChangedDateTime;
    }

    public void setStatusChangedDateTime(LocalDateTime statusChangedDateTime) {
        this.statusChangedDateTime = statusChangedDateTime;
    }
}
