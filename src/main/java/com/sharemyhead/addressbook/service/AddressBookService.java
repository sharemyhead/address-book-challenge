package com.sharemyhead.addressbook.service;

import com.sharemyhead.addressbook.exception.BookAlreadyExistsException;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AddressBookService {

    private RedisService redisService;

    Map<String, SortedSet<Contact>> addressBook = new HashMap<>();

    public AddressBookService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void loadPersistedData() {
        addressBook = redisService.getCompleteAddressBook();
    }

    public void createAddressBook(String bookName) throws BookAlreadyExistsException {
        if (addressBook.containsKey(bookName)) {
            throw new BookAlreadyExistsException("Book " + bookName + " already exists");
        }
        addressBook.put(bookName, new TreeSet<>());
        redisService.addAddressBook(bookName);
    }

    public void storeContact(String bookName, Contact contact) throws NoSuchBookException {
        Set<Contact> contacts = this.getAddressBook(bookName);
        contacts.add(contact);
        redisService.storeContact(bookName, contact);
    }

    public Set<String> getAllBookNames() {
        return addressBook.keySet();
    }

    public Set<Contact> compareContacts(String bookName1, String bookName2) throws NoSuchBookException {
        Map<Contact, Integer> contactCounter = new HashMap<>();
        Set<Contact> contacts1 = this.getAddressBook(bookName1);
        Set<Contact> contacts2 = this.getAddressBook(bookName2);
        contacts1.forEach(contact -> contactCounter.put(contact, contactCounter.getOrDefault(contact, 0) + 1));
        contacts2.forEach(contact -> contactCounter.put(contact, contactCounter.getOrDefault(contact, 0) + 1));
        return contactCounter.entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public SortedSet<Contact> getAddressBook(String bookName) throws NoSuchBookException {
        if (!addressBook.containsKey(bookName)) {
            throw new NoSuchBookException("Book " + bookName + " not found");
        }
        return addressBook.get(bookName);
    }
}
