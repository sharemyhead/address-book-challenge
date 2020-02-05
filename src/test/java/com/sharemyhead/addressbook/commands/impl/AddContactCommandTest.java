package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;
import com.sharemyhead.addressbook.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddContactCommandTest {

    @InjectMocks
    private AddContactCommand addContactCommand;

    @Mock
    private InputReader inputReader;

    @Mock
    private AddressBookService addressBookService;

    @Test
    public void executeShouldAddTheContactIfTheBookExists() throws NoSuchBookException {
        setupInputReader();
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        doNothing().when(addressBookService).storeContact(anyString(), any(Contact.class));
        addContactCommand.execute();
        checkAssertions(bookNameCaptor, contactCaptor);
    }

    @Test
    public void executeShouldFailSilentlyWhenTheBookExists() throws NoSuchBookException {
        setupInputReader();
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        doThrow(NoSuchBookException.class).when(addressBookService).storeContact(anyString(), any(Contact.class));
        addContactCommand.execute();
        checkAssertions(bookNameCaptor, contactCaptor);
    }

    private void checkAssertions(ArgumentCaptor<String> bookNameCaptor, ArgumentCaptor<Contact> contactCaptor) throws NoSuchBookException {
        verify(addressBookService).storeContact(bookNameCaptor.capture(), contactCaptor.capture());
        assertEquals("book1", bookNameCaptor.getValue());
        assertEquals("John", contactCaptor.getValue().getFirstName());
        assertEquals("Doe", contactCaptor.getValue().getLastName());
        assertEquals("0444444444", contactCaptor.getValue().getPhoneNumber());
    }

    private void setupInputReader() {
        when(inputReader.read("Address Book Name")).thenReturn("book1");
        when(inputReader.read("First Name")).thenReturn("John");
        when(inputReader.read("Last Name")).thenReturn("Doe");
        when(inputReader.read("Phone Number")).thenReturn("0444444444");
    }
}
