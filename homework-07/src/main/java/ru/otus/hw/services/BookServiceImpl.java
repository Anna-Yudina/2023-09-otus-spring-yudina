package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepositoryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryData bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        authorService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    @Transactional
    public Book insert(long authorId, String title, long[] genreIds) {
        return save(0, authorId, title, genreIds);
    }

    @Override
    @Transactional
    public Book update(long id, long authorId, String title, long[] genreIds) {
        return save(id, authorId, title, genreIds);
    }

    @Override
    @Transactional
    public Book deleteById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        bookRepository.deleteById(id);
        return book;
    }

    private Book save(long id, long authorId, String title, long[] genreIds) {
        var author = authorService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));

        List<Genre> genres = new ArrayList<>();

        for (long genreId : genreIds) {
            var genre = genreService.findById(genreId)
                    .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
            genres.add(genre);
        }

        var book = new Book(id, title, author, genres);

        if (id != 0) {
            bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
            List<Comment> comments = commentService.findCommentsByBookId(id);
            book.setComments(comments);
        }

        return bookRepository.save(book);
    }
}
