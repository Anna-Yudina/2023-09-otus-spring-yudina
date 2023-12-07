package ru.otus.hw.services;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Author insert(String authorName);

    Optional<Author> findById(String id);

    Author deleteById(String id);
}
