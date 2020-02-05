package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;
import com.sharemyhead.addressbook.service.AddressBookService;

import java.util.Set;

public class CompareBooksCommand implements ICommand {

    private AddressBookService addressBookService;
    private InputReader inputReader;

    public CompareBooksCommand(AddressBookService addressBookService, InputReader inputReader) {
        this.addressBookService = addressBookService;
        this.inputReader = inputReader;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Compare Books");
        String book1Name = inputReader.read("First Book Name");
        String book2Name = inputReader.read("Second Book Name");
        try {
            Set<Contact> notCommonContacts = addressBookService.compareContacts(book1Name, book2Name);
            notCommonContacts.forEach(System.out::println);
        } catch (NoSuchBookException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
