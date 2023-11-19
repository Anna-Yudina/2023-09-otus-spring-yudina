package ru.otus.hw.repositories;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long id);

    Book save(Book book);

    Book deleteById(long id);

    List<Book> findByAuthorId(long authorId);
}
