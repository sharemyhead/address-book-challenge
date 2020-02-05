package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.BookAlreadyExistsException;
import com.sharemyhead.addressbook.service.AddressBookService;

public class CreateBookCommand implements ICommand {

    private AddressBookService addressBookService;
    private InputReader inputReader;

    public CreateBookCommand(AddressBookService addressBookService, InputReader inputReader) {
        this.addressBookService = addressBookService;
        this.inputReader = inputReader;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        String bookName = inputReader.read("Book Name");
        System.out.println();
        try {
            addressBookService.createAddressBook(bookName);
            System.out.println("Book Created Successfully");
        } catch (BookAlreadyExistsException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
