package com.payments;

public class UserBillingAddress {
    private String userID;
    private String billingAddress;
    private String userContact;

    public UserBillingAddress(String userID, String billingAddress, String userContact) {
        this.userID = userID;
        this.billingAddress = billingAddress;
        this.userContact = userContact;
    }


}
