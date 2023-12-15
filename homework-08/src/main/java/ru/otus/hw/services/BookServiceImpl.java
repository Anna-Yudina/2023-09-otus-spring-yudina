package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findByAuthorId(String authorId) {
        authorService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        return bookRepository.findBooksByAuthorId(authorId);
    }

    @Override
    public Book insert(String title, String authorId, String[] genres) {
        return save(null, authorId, title, genres);
    }

    @Override
    public Book update(String id, String title, String authorId, String[] genres) {
        return save(id, title, authorId, genres);
    }

    @Override
    public Book deleteById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        bookRepository.deleteById(id);
        return book;
    }

    private Book save(String id, String title, String authorId, String[] genres) {
        var author = authorService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));

        var book = new Book(title, author, Arrays.asList(genres));

        if (id != null) {
            bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));
            book.setId(id);
        }

        return bookRepository.save(book);
    }
}
