package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Override
    List<Book> findAll();
}
