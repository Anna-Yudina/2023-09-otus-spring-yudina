package ru.otus.hw.services;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author insert(String authorName);

    Author deleteById(long id);

    Author update(long id, String fullName);
}
