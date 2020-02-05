package com.sharemyhead.addressbook.commands.impl;

import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.service.AddressBookService;

import java.util.Set;

public class ListBooksCommand implements ICommand {

    private AddressBookService addressBookService;

    public ListBooksCommand(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("List All Books");
        System.out.println();
        Set<String> allBookNames = addressBookService.getAllBookNames();
        if (allBookNames.isEmpty()) {
            System.out.println("No Books Found");
        } else {
            allBookNames.forEach(System.out::println);
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
