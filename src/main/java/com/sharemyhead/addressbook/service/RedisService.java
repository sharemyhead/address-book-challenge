package com.sharemyhead.addressbook.service;

import com.google.gson.Gson;
import com.sharemyhead.addressbook.model.Contact;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RedisService {

    private Jedis jedis;

    private static final String ADDRESS_BOOK_NAME_PREFIX = "addr:";

    private static final String FIRST_ENTRY_FOR_ADDRESS_BOOK = "FIRST_ENTRY";

    public RedisService(Jedis jedis) {
        this.jedis = jedis;
    }

    public void storeContact(String bookName, Contact contact) {
        Gson gson = new Gson();
        String contactStr = gson.toJson(contact);
        jedis.sadd(ADDRESS_BOOK_NAME_PREFIX + bookName, contactStr);
    }

    public void addAddressBook(String bookName) {
        jedis.sadd(ADDRESS_BOOK_NAME_PREFIX + bookName, FIRST_ENTRY_FOR_ADDRESS_BOOK);
    }

    public Map<String, SortedSet<Contact>> getCompleteAddressBook() {
        Set<String> allAddressBookKeys = jedis.keys(ADDRESS_BOOK_NAME_PREFIX + "*");
        Set<String> addressBookNames = allAddressBookKeys
                .stream()
                .map(addressBookName -> addressBookName.replaceAll(ADDRESS_BOOK_NAME_PREFIX, ""))
                .collect(Collectors.toSet());
        Map<String, SortedSet<Contact>> completeAddressBook = new HashMap<>();
        addressBookNames.forEach(addressBookName -> completeAddressBook.put(addressBookName, getAllContacts(addressBookName)));
        return completeAddressBook;
    }

    SortedSet<Contact> getAllContacts(String bookName) {
        Set<String> contactsStr = jedis.smembers(ADDRESS_BOOK_NAME_PREFIX + bookName);
        return new TreeSet<>(contactsStr
                .stream()
                .filter(contactStr -> !FIRST_ENTRY_FOR_ADDRESS_BOOK.equals(contactStr))
                .map(contactStr -> new Gson().fromJson(contactStr, Contact.class))
                .collect(Collectors.toSet()));
    }

}
