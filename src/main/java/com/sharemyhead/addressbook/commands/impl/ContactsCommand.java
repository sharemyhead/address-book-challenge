package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;
import com.sharemyhead.addressbook.service.AddressBookService;

import java.util.Set;

public class ContactsCommand implements ICommand {
    private AddressBookService addressBookService;

    private InputReader inputReader;

    public ContactsCommand(AddressBookService addressBookService, InputReader inputReader) {
        this.addressBookService = addressBookService;
        this.inputReader = inputReader;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("List All Contacts");
        String bookName = inputReader.read("Address Book Name");
        try {
            Set<Contact> addressBook = addressBookService.getAddressBook(bookName);
            addressBook.forEach(System.out::println);
        } catch (NoSuchBookException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
