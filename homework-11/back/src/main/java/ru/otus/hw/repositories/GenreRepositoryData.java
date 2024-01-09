package ru.otus.hw.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Genre;


public interface GenreRepositoryData extends ReactiveCrudRepository<Genre, Long> {

    @NotNull
    Flux<Genre> findAll();

    @Query("insert into genres (name) values ($1)")
    Mono<Genre> save(String name);
}
