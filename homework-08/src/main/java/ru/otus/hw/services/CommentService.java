package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentService {

    Comment insert(String comment, String bookId);

    List<Comment> findCommentsByBookId(String bookId);

    Comment deleteById(String id);

    Comment findById(String id);
}
