package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class InputValidatorImpl implements InputValidator {

    private final LocalizedIOService ioService;

    public String readAnswer(int maxInputNumber) {


        List<String> requiredNumbers = new ArrayList<>();

        for (int i = 1; i <= maxInputNumber; i++) {
            requiredNumbers.add(i + "");
        }

        String line = ioService.readString();

        if (line.isEmpty() || !requiredNumbers.contains(line)) {
            ioService.printFormattedLineLocalized("InputValidator.read.answer.error.text", maxInputNumber);
            ioService.printLineLocalized("InputValidator.try.again");
            line = readAnswer(maxInputNumber);
        }

        return line;
    }
}

