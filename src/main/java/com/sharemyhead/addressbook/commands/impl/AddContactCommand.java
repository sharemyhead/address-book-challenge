package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.exception.NoSuchBookException;
import com.sharemyhead.addressbook.model.Contact;
import com.sharemyhead.addressbook.service.AddressBookService;

public class AddContactCommand implements ICommand {

    private AddressBookService addressBookService;

    private InputReader inputReader;

    public AddContactCommand(AddressBookService addressBookService, InputReader inputReader) {
        this.addressBookService = addressBookService;
        this.inputReader = inputReader;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Add Contact");
        String bookName = inputReader.read("Address Book Name");
        String firstName = inputReader.read("First Name");
        String lastName = inputReader.read("Last Name");
        String phoneNumber = inputReader.read("Phone Number");
        Contact contact = Contact.builder().firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).build();
        try {
            addressBookService.storeContact(bookName, contact);
            System.out.println("Contact Added Successfully");
        } catch (NoSuchBookException exception) {
            System.out.println("Address book named " + bookName + " does not exist. Going back to Main Menu");
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
