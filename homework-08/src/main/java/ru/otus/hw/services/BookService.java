package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(String id);

    Book insert(String authorId, String title, String[] genres);

    Book update(String id, String title, String authorId, String[] genreIds);

    Book deleteById(String id);

    List<Book> findByAuthorId(String authorId);
}
