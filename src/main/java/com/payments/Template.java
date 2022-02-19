package com.payments;

import java.util.Objects;

public class Template {
    private long templateID;
    private String templateName;
    private String iban;
    private String paymentPurpose;
    private String userContact;

    public Template(long templateID, String templateName, String iban, String paymentPurpose, String userContact) {
        this.templateID = templateID;
        this.templateName = templateName;
        this.iban = iban;
        this.paymentPurpose = paymentPurpose;
        this.userContact = userContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(templateID, template.templateID) &&
                Objects.equals(templateName, template.templateName) &&
                Objects.equals(iban, template.iban) &&
                Objects.equals(paymentPurpose, template.paymentPurpose) &&
                Objects.equals(userContact, template.userContact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateID, templateName, iban, paymentPurpose, userContact);
    }
}
