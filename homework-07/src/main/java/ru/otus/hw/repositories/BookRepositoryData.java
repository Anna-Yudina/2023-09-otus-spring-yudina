package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryData extends CrudRepository<Book, Long> {

    @EntityGraph(value = "book-author-genres-entity-graph")
    List<Book> findAll();

    @EntityGraph(value = "book-author-genres-comments-entity-graph")
    Optional<Book> findById(long id);

    Book save(Book book);

    Book deleteById(long id);

    @EntityGraph(value = "book-author-genres-entity-graph")
    List<Book> findByAuthorId(long authorId);
}
