package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.emptySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListBooksCommandTest {

    @Mock
    private AddressBookService addressBookService;

    @InjectMocks
    private ListBooksCommand listBooksCommand;

    @Test
    public void executeShouldCallTheAddressBookService() {
        when(addressBookService.getAllBookNames()).thenReturn(emptySet());
        listBooksCommand.execute();
        verify(addressBookService).getAllBookNames();
    }
}
