package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Comment insert(String commentText, String bookId) {
        Comment comment = new Comment(commentText);
        Optional<Book> book = bookRepository.findById(bookId);
        comment.setBook(book.orElseThrow(() -> new EntityNotFoundException("Book not found")));
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByBookId(String bookId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return commentRepository.findCommentsByBookId(bookId);
    }

    @Override
    public Comment deleteById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.deleteById(id);
        return comment;
    }

    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }
}
