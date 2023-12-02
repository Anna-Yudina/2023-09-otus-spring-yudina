package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepositoryData;
import ru.otus.hw.repositories.CommentRepositoryData;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryData commentRepository;

    private final BookRepositoryData bookRepository;

    @Override
    public Comment insert(String commentText, long bookId) {
        Comment comment = new Comment(0, commentText);
        Optional<Book> book = bookRepository.findWithoutDetailsById(bookId);
        comment.setBook(book.orElseThrow(() -> new EntityNotFoundException("Book not found")));
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        Book book = bookRepository.findWithoutDetailsById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        List<Comment> comments = book.getComments();
        Hibernate.initialize(comments);
        return comments;
    }

    @Override
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
