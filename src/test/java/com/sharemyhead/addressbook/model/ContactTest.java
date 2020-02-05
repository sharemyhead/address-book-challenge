package com.sharemyhead.addressbook.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactTest {

    @Test
    public void getFullName() {
        Contact contact = Contact.builder().firstName("John ").lastName("   Doe  ").build();
        assertEquals("John Doe", contact.getFullName());
    }

    @Test
    public void compareToReturnsTheCorrectResponseForDifferentContacts() {
        Contact contact1 = Contact.builder().firstName("John ").lastName("   Doe  ").phoneNumber("0444444444").build();
        Contact contact2 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444445").build();
        assertTrue(contact1.compareTo(contact2) > 1);
    }

    @Test
    public void compareToReturnsTheCorrectResponseForSameContact() {
        Contact contact1 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444444").build();
        Contact contact2 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("1234567890").build();
        assertTrue(contact1.compareTo(contact2) < 1);
    }
}
