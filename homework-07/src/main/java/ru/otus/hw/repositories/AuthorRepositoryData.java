package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryData extends CrudRepository<Author, Long> {

    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author author);

    void deleteById(long id);
}
