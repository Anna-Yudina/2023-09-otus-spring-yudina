package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;


public interface BookRepositoryData extends ReactiveCrudRepository<Book, Long>, BookCustomRepository {

    @Query("insert into books (title, author_id, genre_id) VALUES ($1, $2, $3)")
    Mono<Book> save(String title, long authorId, long genreId);

    @Query("update books set title = $2, author_id = $3, genre_id = $4 where id = $1")
    Mono<Book> save(long id, String title, long authorId, long genreId);
}
