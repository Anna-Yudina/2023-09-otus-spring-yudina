package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Comment;

import java.util.Optional;

public interface CommentRepositoryData extends CrudRepository<Comment, Long> {

    Comment save(Comment comment);

    void deleteById(long id);

    Optional<Comment> findById(long id);
}
