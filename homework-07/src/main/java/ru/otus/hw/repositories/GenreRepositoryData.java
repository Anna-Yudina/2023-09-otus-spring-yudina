package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryData extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
