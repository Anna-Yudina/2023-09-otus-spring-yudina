package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Comment;

public interface CommentRepositoryData extends ReactiveCrudRepository<Comment, Long> {

    @Query("insert into comments (text, book_id) values ($1, $2)")
    Mono<Comment> save(String text, long bookId);

    @Query("select * from comments where book_id = $1")
    Flux<Comment> findCommentsByBookId(long bookId);
}
