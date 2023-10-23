package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final PrintStream printStream;

    private final Scanner scanner;

    @Autowired
    public IOServiceImpl(@Value("#{T(System).out}") PrintStream printStream,
                         @Value("#{T(System).in}") InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void printLine(String s) {
        printStream.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        printStream.printf(s + "%n", args);
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        printLine(prompt);
        return scanner.nextLine();
    }

    public int readString(int maxNumber) {
        String line = scanner.nextLine();
        List<String> requiredNumbers = new ArrayList<>();

        for (int i = 1; i <= maxNumber; i++) {
            requiredNumbers.add(i + "");
        }

        if (line.isEmpty() || !requiredNumbers.contains(line)) {
            throw new IllegalArgumentException("Error during reading string value");
        }
        return Integer.parseInt(line);
    }
}
