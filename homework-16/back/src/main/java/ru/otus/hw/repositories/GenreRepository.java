package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

    @Override
    List<Genre> findAll();
}
