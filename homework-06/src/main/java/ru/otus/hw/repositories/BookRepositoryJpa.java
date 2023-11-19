package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genres-comments-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(entityManager.find(Book.class, id, properties));
    }

    @Override
    public Book deleteById(long id) {
        Book book = findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        entityManager.remove(book);
        return book;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    private Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    private Book update(Book book) {
        entityManager.merge(book);
        return book;
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b join b.author a " +
                "where a.id = :id", Book.class).setParameter("id", authorId);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }
}
