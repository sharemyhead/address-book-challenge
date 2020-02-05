package com.sharemyhead.addressbook.commands;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum Command {
    LIST,
    CREATE,
    ADD,
    COMPARE,
    CONTACTS,
    EXIT;

    public static boolean isValidCommand(String commandStr) {
        if (StringUtils.isEmpty(commandStr)) return false;
        Optional<Command> matchingCommand = stream(Command.values()).filter(command -> command.name().equalsIgnoreCase(commandStr)).findFirst();
        return matchingCommand.isPresent();
    }

    public boolean isExitCommand() {
        return this.name().equalsIgnoreCase(Command.EXIT.name());
    }
}
