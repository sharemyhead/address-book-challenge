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
    public void compareTo() {
        Contact contact1 = Contact.builder().firstName("John ").lastName("   Doe  ").build();
        Contact contact2 = Contact.builder().firstName("Jane").lastName("Doe").build();
        assertTrue(contact1.compareTo(contact2) > 1);
    }
}
