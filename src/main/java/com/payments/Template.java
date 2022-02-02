package com.payments;

public class Template {
    private String templateID;
    private String templateName;
    private String iban;
    private String paymentPurpose;
    private String userContact;

    public Template(String templateID, String templateName, String iban, String paymentPurpose, String userContact) {
        this.templateID = templateID;
        this.templateName = templateName;
        this.iban = iban;
        this.paymentPurpose = paymentPurpose;
        this.userContact = userContact;
    }
}
