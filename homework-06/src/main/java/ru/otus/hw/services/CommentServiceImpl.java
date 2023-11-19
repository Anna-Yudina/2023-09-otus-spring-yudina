package ru.otus.hw.services;

import jakarta.transaction.Transactional;
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
    @Transactional
    public Comment insert(String commentText, long bookId) {
        Comment comment = new Comment(0, commentText);
        Optional<Book> book = bookRepository.findById(bookId);
        comment.setBook(book.orElseThrow(() -> new EntityNotFoundException("Book not found")));
        return commentRepository.insert(comment);
    }

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return book.getComments();
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
