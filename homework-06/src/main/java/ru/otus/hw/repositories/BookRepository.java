package ru.otus.hw.repositories;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findWithAutorAndGenresById(long id);

    Book save(Book book);

    void deleteById(long id);

    Optional<Book> findWithoutDetailsById(long id);
}
