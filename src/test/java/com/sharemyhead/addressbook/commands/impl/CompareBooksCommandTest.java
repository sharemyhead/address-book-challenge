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

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompareBooksCommandTest {

    @InjectMocks
    private CompareBooksCommand compareBooksCommand;

    @Mock
    private InputReader inputReader;

    @Mock
    private AddressBookService addressBookService;

    @Test
    public void executeShouldReturnTheComparedBooks() throws NoSuchBookException {
        setupInputReader();
        ArgumentCaptor<String> bookName1Captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bookName2Captor = ArgumentCaptor.forClass(String.class);
        when(addressBookService.compareContacts("book1", "book2")).thenReturn(Collections.emptySet());
        compareBooksCommand.execute();
        checkAssertions(bookName1Captor, bookName2Captor);
    }

    @Test
    public void executeShouldFailSilentlyWhenTheAddressBooksDoNotExist() throws NoSuchBookException {
        setupInputReader();
        ArgumentCaptor<String> bookName1Captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bookName2Captor = ArgumentCaptor.forClass(String.class);
        doThrow(NoSuchBookException.class).when(addressBookService).compareContacts("book1", "book2");
        compareBooksCommand.execute();
        checkAssertions(bookName1Captor, bookName2Captor);
    }

    private void checkAssertions(ArgumentCaptor<String> bookName1Captor, ArgumentCaptor<String> bookName2Captor) throws NoSuchBookException {
        verify(addressBookService).compareContacts(bookName1Captor.capture(), bookName2Captor.capture());
        assertEquals("book1", bookName1Captor.getValue());
        assertEquals("book2", bookName2Captor.getValue());
    }

    private void setupInputReader() {
        when(inputReader.read("First Book Name")).thenReturn("book1");
        when(inputReader.read("Second Book Name")).thenReturn("book2");
    }
}
