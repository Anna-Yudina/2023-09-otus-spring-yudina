package ru.otus.hw.mapping;

import io.r2dbc.spi.Row;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.function.BiFunction;


public class BookMapper implements BiFunction<Row, Object, Book> {

    @Override
    public Book apply(Row row, Object o) {
        System.out.println("зашел в Маппер");
        Long bookId = row.get("id", Long.class);
        String title = row.get("title", String.class);

        Long authorId = row.get("author_id", Long.class);
        String fullName = row.get("full_name", String.class);
        Author author = new Author(authorId, fullName);

        Long genreId = row.get("genre_id", Long.class);
        String name = row.get("name", String.class);
        Genre genre = new Genre(genreId, name);

        Book book = new Book(bookId, title, author, genre);
        return book;
    }
}
