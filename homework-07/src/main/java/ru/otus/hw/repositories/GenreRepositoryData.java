package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreRepositoryData extends CrudRepository<Genre, Long> {

    List<Genre> findAll();
}
