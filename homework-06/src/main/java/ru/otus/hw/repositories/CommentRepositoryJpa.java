package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment insert(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(findById(id).get());
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }
}
