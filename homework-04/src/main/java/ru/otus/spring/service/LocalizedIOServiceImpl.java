package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final LocalizedMessagesService localizedMessagesService;

    private final IOService ioService;


    @Override
    public void printLine(String s) {
        ioService.printLine(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        ioService.printFormattedLine(s, args);
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        return ioService.readStringWithPrompt(prompt);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public String getMessage(String code, Object... args) {
        return localizedMessagesService.getMessage(code, args);
    }

    @Override
    public void printLineLocalized(String s) {
        ioService.printLine(localizedMessagesService.getMessage(s));
    }

    @Override
    public void printFormattedLineLocalized(String s, Object... args) {
        ioService.printFormattedLine(localizedMessagesService.getMessage(s, args));
    }

    @Override
    public String readStringWithPromptLocalized(String prompt) {
        return ioService.readStringWithPrompt(localizedMessagesService.getMessage(prompt));
    }
}
