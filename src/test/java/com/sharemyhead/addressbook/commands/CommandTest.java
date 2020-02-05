package com.sharemyhead.addressbook.commands;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandTest {

    @Test
    public void isValidCommand() {
        assertTrue(Command.isValidCommand("list"));
        assertTrue(Command.isValidCommand("create"));
        assertTrue(Command.isValidCommand("add"));
        assertTrue(Command.isValidCommand("compare"));
        assertTrue(Command.isValidCommand("exit"));
        assertTrue(Command.isValidCommand("contacts"));
        assertFalse(Command.isValidCommand("exitdsad"));
    }

    @Test
    public void isExitCommand() {
        assertFalse(Command.ADD.isExitCommand());
        assertTrue(Command.EXIT.isExitCommand());
    }
}
