package ru.otus.spring.service;

public interface LocalizedIOService extends LocalizedMessagesService, IOService {

    void printLineLocalized(String s);

    void printFormattedLineLocalized(String s, Object... args);

    String readStringWithPromptLocalized(String prompt);
}
