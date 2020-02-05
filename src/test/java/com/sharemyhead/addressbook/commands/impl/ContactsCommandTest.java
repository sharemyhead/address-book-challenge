package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsCommandTest {

    @InjectMocks
    private ContactsCommand contactsCommand;

    @Mock
    private InputReader inputReader;

    @Mock
    private AddressBookService addressBookService;

    @Test
    public void executeShouldListTheContacts() throws NoSuchBookException {
        setupInputReader();
        when(addressBookService.getAddressBook("book1")).thenReturn(new TreeSet<>());
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        contactsCommand.execute();
        checkAssertions(bookNameCaptor);

    }

    @Test
    public void executeShouldFailSilentlyWhenBookDoesNotExist() throws NoSuchBookException {
        setupInputReader();
        when(addressBookService.getAddressBook("book1")).thenThrow(NoSuchBookException.class);
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        contactsCommand.execute();
        checkAssertions(bookNameCaptor);
    }

    private void checkAssertions(ArgumentCaptor<String> bookNameCaptor) throws NoSuchBookException {
        verify(addressBookService).getAddressBook(bookNameCaptor.capture());
        assertEquals("book1", bookNameCaptor.getValue());
    }

    private void setupInputReader() {
        when(inputReader.read("Address Book Name")).thenReturn("book1");
    }
}
