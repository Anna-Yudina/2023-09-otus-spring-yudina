package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorRepositoryData extends CrudRepository<Author, Long> {

    List<Author> findAll();
}
