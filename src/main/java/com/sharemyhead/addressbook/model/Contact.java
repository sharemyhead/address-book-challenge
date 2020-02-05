package com.sharemyhead.addressbook.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Contact implements Comparable<Contact> {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    public String getFullName() {
        return String.join(" ", firstName.trim(), lastName.trim());
    }

    @Override
    public int compareTo(Contact contact) {
        String compareStrCurrent = this.getFullName().toUpperCase() + this.getPhoneNumber();
        String compareStrContact = contact.getFullName().toUpperCase() + contact.getPhoneNumber();
        return compareStrCurrent.compareTo(compareStrContact);
    }
}
