package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(long id);

    List<Book> findAll();

    Book insert(long authorId, String title, long[] genreIds);

    Book update(long id, long authorId, String title, long[] genreIds);

    Book deleteById(long id);

    List<Book> findByAuthorId(long authorId);
}
