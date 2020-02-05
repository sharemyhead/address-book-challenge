package com.sharemyhead.addressbook;

import com.sharemyhead.addressbook.commands.Command;
import com.sharemyhead.addressbook.commands.ICommand;
import com.sharemyhead.addressbook.commands.impl.AddContactCommand;
import com.sharemyhead.addressbook.commands.impl.CompareBooksCommand;
import com.sharemyhead.addressbook.commands.impl.ContactsCommand;
import com.sharemyhead.addressbook.commands.impl.CreateBookCommand;
import com.sharemyhead.addressbook.commands.impl.ListBooksCommand;
import com.sharemyhead.addressbook.common.InputReader;
import com.sharemyhead.addressbook.service.AddressBookService;
import com.sharemyhead.addressbook.service.RedisService;
import redis.clients.jedis.Jedis;

import java.util.EnumMap;
import java.util.Scanner;

import static java.lang.System.getenv;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class Application {
    public static void main(String[] args) {
        String redisHost = isEmpty(getenv("redis.host")) ? "localhost" : getenv("redis.host");
        int redisPort = isNotEmpty(getenv("redis.port"))
                && isNumeric(getenv("redis.port")) ? Integer.parseInt(getenv("redis.port")) : 6379;
        RedisService redisService = new RedisService(new Jedis(redisHost, redisPort));
        AddressBookService addressBookService = new AddressBookService(redisService);
        addressBookService.loadPersistedData();
        EnumMap<Command, ICommand> commandsMap = setupApplication(addressBookService);
        printMainMenu();
        Scanner scan = new Scanner(System.in);
        while (true) {
            String commandInput = scan.nextLine();
            if (!Command.isValidCommand(commandInput)) {
                printMainMenu();
                continue;
            }
            Command command = Command.valueOf(commandInput.toUpperCase());
            if (command.isExitCommand()) {
                System.out.println("Thanks for using this program. Exiting...");
                System.exit(0);
            }
            ICommand commandImpl = commandsMap.get(command);
            commandImpl.execute();
            printMainMenu();
        }

    }

    private static EnumMap<Command, ICommand> setupApplication(AddressBookService addressBookService) {
        EnumMap<Command, ICommand> commandsMap = new EnumMap<>(Command.class);
        InputReader inputReader = new InputReader();
        commandsMap.put(Command.ADD, new AddContactCommand(addressBookService, inputReader));
        commandsMap.put(Command.COMPARE, new CompareBooksCommand(addressBookService, inputReader));
        commandsMap.put(Command.LIST, new ListBooksCommand(addressBookService));
        commandsMap.put(Command.CREATE, new CreateBookCommand(addressBookService, inputReader));
        commandsMap.put(Command.CONTACTS, new ContactsCommand(addressBookService, inputReader));
        return commandsMap;
    }

    private static void printMainMenu() {
        System.out.println("Address Book Main Menu");
        System.out.println("Choose from the following commands: [list | create | add | compare | exit]");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("    list                            list all books");
        System.out.println("    create                          create a new book");
        System.out.println("    add                             add a contact to a book");
        System.out.println("    contacts                        list all contacts in a book");
        System.out.println("    compare                         Compare 2 books to list the contacts that are unique to each address book'");
        System.out.println("    exit                            exit the program");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("\r\n");
    }
}
