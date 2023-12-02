package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(Book book) {
        return "Id: %d, title: %s, author: {%s}, genres: %s".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                book.getGenres().stream().map(genreConverter::genreToString).collect(Collectors.toList()));
    }

    public String bookTitleToString(Book book) {
        return "Book title: %s".formatted(book.getTitle());
    }

    public String bookTitleAndAuthorToString(Book book) {
        return "Book author: %s, Book title: %s".formatted(book.getAuthor().getFullName(), book.getTitle());
    }
}
