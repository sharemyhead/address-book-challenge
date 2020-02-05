package com.sharemyhead.addressbook.service;

import com.google.gson.Gson;
import com.sharemyhead.addressbook.model.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RedisServiceTest {

    @Mock
    private Jedis jedis;

    @InjectMocks
    private RedisService redisService;

    @Test
    public void storeContact() {
        Contact contact = Contact
                .builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("0444444444")
                .build();
        String contactJson = new Gson().toJson(contact);

        redisService.storeContact("book1", contact);
        verify(jedis).sadd("addr:book1", contactJson);
    }

    @Test
    public void getAllContacts() {
        Contact contact1 = Contact.builder().firstName("John").lastName("Doe").phoneNumber("0444444444").build();
        Contact contact2 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444445").build();
        Contact contact3 = Contact.builder().firstName("Jason").lastName("Doe").phoneNumber("0444444446").build();
        Set<String> contactsJson = new HashSet<>();
        Gson gson = new Gson();
        contactsJson.add(gson.toJson(contact1));
        contactsJson.add(gson.toJson(contact2));
        contactsJson.add(gson.toJson(contact3));
        contactsJson.add("FIRST_ENTRY");
        when(jedis.smembers("addr:book1")).thenReturn(contactsJson);

        Set<Contact> contacts = redisService.getAllContacts("book1");

        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact1));
        assertTrue(contacts.contains(contact2));
        assertTrue(contacts.contains(contact3));
    }

    @Test
    public void getCompleteAddressBook() {
        Contact contact1 = Contact.builder().firstName("John").lastName("Doe").phoneNumber("0444444444").build();
        Contact contact2 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444445").build();
        Contact contact3 = Contact.builder().firstName("Jason").lastName("Doe").phoneNumber("0444444446").build();
        Set<String> contactsBook1Json = new HashSet<>();
        Set<String> contactsBook2Json = new HashSet<>();
        Gson gson = new Gson();
        contactsBook1Json.add(gson.toJson(contact1));
        contactsBook1Json.add(gson.toJson(contact2));
        contactsBook2Json.add(gson.toJson(contact3));
        Set<String> keysSet = new HashSet<>();
        keysSet.add("addr:book1");
        keysSet.add("addr:book2");

        when(jedis.keys("addr:*")).thenReturn(keysSet);
        when(jedis.smembers("addr:book1")).thenReturn(contactsBook1Json);
        when(jedis.smembers("addr:book2")).thenReturn(contactsBook2Json);

        Map<String, SortedSet<Contact>> completeAddressBook = redisService.getCompleteAddressBook();
        assertEquals(2, completeAddressBook.size());
        assertTrue(completeAddressBook.containsKey("book1"));
        assertTrue(completeAddressBook.containsKey("book2"));
        assertEquals(2, completeAddressBook.get("book1").size());
        assertEquals(1, completeAddressBook.get("book2").size());
    }

    @Test
    public void addAddressBook() {
        redisService.addAddressBook("book1");
        verify(jedis).sadd("addr:book1", "FIRST_ENTRY");
    }
}
