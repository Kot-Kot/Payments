package com.payments.objects;

import java.util.Objects;

public class UserBillingAddress {
    private long id;
    private String billingAddress;
    private String userContact;

    public UserBillingAddress(int userID, String billingAddress, String userContact) {
        this.id = userID;
        this.billingAddress = billingAddress;
        this.userContact = userContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBillingAddress that = (UserBillingAddress) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(billingAddress, that.billingAddress) &&
                Objects.equals(userContact, that.userContact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billingAddress, userContact);
    }
}
