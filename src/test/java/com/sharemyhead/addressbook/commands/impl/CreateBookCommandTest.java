package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.BookAlreadyExistsException;
import com.sharemyhead.addressbook.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateBookCommandTest {

    @InjectMocks
    private CreateBookCommand createBookCommand;

    @Mock
    private InputReader inputReader;

    @Mock
    private AddressBookService addressBookService;


    @Test
    public void executeShouldCreateTheBook() throws BookAlreadyExistsException {
        setupInputReader();
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(addressBookService).createAddressBook(anyString());
        createBookCommand.execute();
        checkAssertions(bookNameCaptor);

    }

    @Test
    public void executeShouldFailSilentlyWhenBookExists() throws BookAlreadyExistsException {
        setupInputReader();
        ArgumentCaptor<String> bookNameCaptor = ArgumentCaptor.forClass(String.class);
        doThrow(BookAlreadyExistsException.class).when(addressBookService).createAddressBook(anyString());
        createBookCommand.execute();
        checkAssertions(bookNameCaptor);
    }

    private void checkAssertions(ArgumentCaptor<String> bookNameCaptor) throws BookAlreadyExistsException {
        verify(addressBookService).createAddressBook(bookNameCaptor.capture());
        assertEquals("book1", bookNameCaptor.getValue());
    }

    private void setupInputReader() {
        when(inputReader.read("Book Name")).thenReturn("book1");
    }
}
