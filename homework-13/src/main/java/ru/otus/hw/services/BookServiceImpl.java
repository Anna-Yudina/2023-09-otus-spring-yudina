package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepositoryData;

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
        return bookRepository.findWithAutorAndGenresById(id);
    }

    @Transactional
    @Override
    public List<Book> findByAuthorId(long authorId) {
        Author author = authorService.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        List<Book> books = author.getBooks();
        Hibernate.initialize(books);
        return books;
    }

    @Override
    @Transactional
    public Book insert(long authorId, String title, long genreId) {
        return save(0, authorId, title, genreId);
    }

    @Override
    @Transactional
    public Book update(long id, long authorId, String title, long genreId) {
        return save(id, authorId, title, genreId);
    }

    @Override
    public Book deleteById(long id) {
        Book book = bookRepository.findWithoutDetailsById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        bookRepository.deleteById(id);
        return book;
    }

    private Book save(long id, long authorId, String title, long genreId) {
        var author = authorService.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));

        var genre = genreService.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(genreId)));

        var book = new Book(id, title, author, genre);

        if (id != 0) {
            bookRepository.findWithoutDetailsById(id)
                    .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
            List<Comment> comments = commentService.findCommentsByBookId(id);
            book.setComments(comments);
        }

        return bookRepository.save(book);
    }
}
