package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;

import java.util.Arrays;
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
    public String findBookById(long id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(@ShellOption(help = "id author") long authorId,
                             @ShellOption(help = "book's name") String title,
                             @ShellOption(help = "genres separated by commas") String genreIdsStr) {
        long[] genreIds = Arrays.stream(genreIdsStr.split(",")).mapToLong(Integer::parseInt).toArray();
        var savedBook = bookService.insert(authorId, title, genreIds);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(@ShellOption(help = "id book") long id,
                             @ShellOption(help = "id author") long authorId,
                             @ShellOption(help = "book's name") String title,
                             @ShellOption(help = "genres separated by commas") String genreIdsStr) {
        long[] genreIds = Arrays.stream(genreIdsStr.split(",")).mapToLong(Integer::parseInt).toArray();
        var savedBook = bookService.update(id, authorId, title, genreIds);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Delete book by id", key = "bdelid")
    public String updateBook(long id) {
        try {
            return bookConverter.bookTitleToString(bookService.deleteById(id)) + " is deleted";
        } catch (EntityNotFoundException e) {
            return "Book with id %d not found".formatted(id);
        }
    }

    @ShellMethod(value = "Find book by authorId", key = "baid")
    public String findBookByAuthorId(long id) {
        try {
            return bookService.findByAuthorId(id).stream()
                    .map(bookConverter::bookTitleAndAuthorToString)
                    .collect(Collectors.joining("," + System.lineSeparator()));
        } catch (EntityNotFoundException e) {
            return "Author with id %d not found".formatted(id);
        }
    }
}
