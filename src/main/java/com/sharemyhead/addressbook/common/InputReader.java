package com.sharemyhead.addressbook.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader() {
        scanner = new Scanner(System.in);
    }

    public String read(String fieldDescription) {
        String input = "";
        while (StringUtils.isEmpty(input)) {
            System.out.println("Enter " + fieldDescription + "(Not Empty)");
            input = scanner.nextLine();
        }
        return input;
    }
}
