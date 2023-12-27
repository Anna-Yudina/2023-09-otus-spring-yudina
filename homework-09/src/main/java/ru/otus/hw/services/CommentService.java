package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentService {
    Comment insert(String comment, long bookId);

    List<Comment> findCommentsByBookId(long bookId);

    Comment deleteById(long id);

    Comment findById(long id);
}
