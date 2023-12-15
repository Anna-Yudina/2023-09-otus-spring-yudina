package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ba")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bid")
    public String findBookById(String id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(@ShellOption(help = "book's name") String title,
                             @ShellOption(help = "id author") String authorId,
                             @ShellOption(help = "genres separated by commas") String genresStr) {
        String[] genres = (genresStr.split(","));
        var savedBook = bookService.insert(authorId, title, genres);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(@ShellOption(help = "id book") String id,
                             @ShellOption(help = "book's name") String title,
                             @ShellOption(help = "id author") String authorId,
                             @ShellOption(help = "genres separated by commas") String genresStr) {
        String[] genres = (genresStr.split(","));
        var savedBook = bookService.update(id, authorId, title, genres);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Delete book by id", key = "bdelid")
    public String updateBook(String id) {
        try {
            return bookConverter.bookToString(bookService.deleteById(id)) + " is deleted";
        } catch (EntityNotFoundException e) {
            return "Book with id %s not found".formatted(id);
        }
    }

    @ShellMethod(value = "Find book by authorId", key = "baid")
    public String findBookByAuthorId(String id) {
        List<Book> books = bookService.findByAuthorId(id);
        if (books.size() == 0) {
            return "Books with autor id %s not found".formatted(id);
        }
        return bookService.findByAuthorId(id).stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
