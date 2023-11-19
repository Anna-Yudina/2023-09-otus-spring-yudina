package ru.otus.hw.repositories;

import ru.otus.hw.models.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment insert(Comment comment);

    void deleteById(long id);

    Optional<Comment> findById(long id);
}
