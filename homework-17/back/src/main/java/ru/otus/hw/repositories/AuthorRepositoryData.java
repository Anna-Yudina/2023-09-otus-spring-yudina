package ru.otus.hw.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;

public interface AuthorRepositoryData extends ReactiveCrudRepository<Author, Long> {

    @NotNull
    Flux<Author> findAll();

    @Query("insert into authors (full_name) values ($1)")
    Mono<Author> save(String fullName);

    @Query("update authors set full_name = $2 where id = $1")
    Mono<Author> save(long id, String fullName);
}
