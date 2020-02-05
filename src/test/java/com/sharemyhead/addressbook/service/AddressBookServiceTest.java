package com.sharemyhead.addressbook.service;

import com.sharemyhead.addressbook.exception.BookAlreadyExistsException;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookServiceTest {

    @InjectMocks
    private AddressBookService addressBookService;

    @Mock
    private RedisService redisService;

    @Test
    public void createAddressBookShouldCreateAddressBook() throws BookAlreadyExistsException, NoSuchBookException {
        addressBookService.createAddressBook("book1");
        Assert.assertNotNull(addressBookService.getAddressBook("book1"));
        verify(redisService).addAddressBook("book1");
    }

    @Test(expected = BookAlreadyExistsException.class)
    public void createAddressBookThrowsExceptionWhenBookExists() throws BookAlreadyExistsException {
        addressBookService.createAddressBook("book1");
        verify(redisService).addAddressBook("book1");
        addressBookService.createAddressBook("book1");
    }

    @Test
    public void getAddressBookReturnsAlreadyCreatedBook() throws NoSuchBookException, BookAlreadyExistsException {
        addressBookService.createAddressBook("book1");
        Assert.assertNotNull(addressBookService.getAddressBook("book1"));
    }

    @Test(expected = NoSuchBookException.class)
    public void getAddressBookThrowsExceptionWhenBookDoesNotExist() throws NoSuchBookException {
        addressBookService.getAddressBook("book1");
    }

    @Test
    public void loadPersistedDataShouldCallRedisService() {
        Mockito.when(redisService.getCompleteAddressBook()).thenReturn(Collections.emptyMap());
        addressBookService.loadPersistedData();
        verify(redisService).getCompleteAddressBook();
    }

    @Test
    public void storeContactShouldStoreSuccessfully() throws BookAlreadyExistsException, NoSuchBookException {
        addressBookService.createAddressBook("book1");
        Contact contact = Contact.builder().firstName("John").lastName("Doe").phoneNumber("0444444444").build();
        addressBookService.storeContact("book1", contact);
        Set<Contact> contacts = addressBookService.getAddressBook("book1");
        verify(redisService).storeContact("book1", contact);
        assertEquals(1, contacts.size());
        assertTrue(contacts.contains(contact));
    }

    @Test
    public void shouldReturnAllTheBookNames() throws BookAlreadyExistsException {
        addressBookService.createAddressBook("book1");
        addressBookService.createAddressBook("book2");
        Set<String> addressBooks = addressBookService.getAllBookNames();
        assertEquals(2, addressBooks.size());
        assertTrue(addressBooks.contains("book1"));
        assertTrue(addressBooks.contains("book2"));
    }

    @Test
    public void compareContactsShouldReturnTheCorrectContacts() throws BookAlreadyExistsException, NoSuchBookException {
        addressBookService.createAddressBook("book1");
        addressBookService.createAddressBook("book2");
        Contact contact1Book1 = Contact.builder().firstName("John").lastName("Doe").phoneNumber("0444444444").build();
        Contact contact2Book1 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444445").build();
        Contact contact3Book1 = Contact.builder().firstName("Jason").lastName("Doe").phoneNumber("0444444446").build();
        addressBookService.storeContact("book1", contact1Book1);
        addressBookService.storeContact("book1", contact2Book1);
        addressBookService.storeContact("book1", contact3Book1);

        Contact contact1Book2 = Contact.builder().firstName("John").lastName("BonJovi").phoneNumber("0444444447").build();
        Contact contact2Book2 = Contact.builder().firstName("Jane").lastName("Doe").phoneNumber("0444444445").build();
        Contact contact3Book2 = Contact.builder().firstName("Jason").lastName("Bourne").phoneNumber("0444444448").build();

        addressBookService.storeContact("book2", contact1Book2);
        addressBookService.storeContact("book2", contact2Book2);
        addressBookService.storeContact("book2", contact3Book2);

        Set<Contact> compareContacts = addressBookService.compareContacts("book1", "book2");
        assertEquals(4, compareContacts.size());
        assertTrue(compareContacts.contains(contact1Book1));
        assertTrue(compareContacts.contains(contact3Book1));
        assertTrue(compareContacts.contains(contact1Book2));
        assertTrue(compareContacts.contains(contact3Book2));
    }


}
