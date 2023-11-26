package ru.otus.hw.services;


import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Comment insert(String commentText, long bookId) {
        Comment comment = new Comment(0, commentText);
        Book book = bookRepository.findWithoutDetailsById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        comment.setBook(book);
        return commentRepository.insert(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        Book book = bookRepository.findWithoutDetailsById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        List<Comment> comments = book.getComments();
        Hibernate.initialize(comments);
        return comments;
    }

    @Override
    @Transactional
    public Comment deleteById(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.deleteById(id);
        return comment;
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }
}
