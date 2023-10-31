package ru.otus.spring.service;

public interface LocalizedMessagesService {

    String getMessage(String code, Object... args);
}
