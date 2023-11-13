package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookRepositoryJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT books.id, books.title, authors.id, authors.full_name, genres.id, genres.name " +
                "FROM books " +
                "INNER JOIN authors " +
                "ON author_id = authors.id " +
                "INNER JOIN genres " +
                "ON genre_id = genres.id;", new BookRowMapper());
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            book = namedParameterJdbcOperations.queryForObject(
                    "SELECT books.id, books.title, authors.id, authors.full_name, genres.id, genres.name " +
                            "FROM books " +
                            "INNER JOIN authors " +
                            "ON author_id = authors.id " +
                            "INNER JOIN genres " +
                            "ON genre_id = genres.id " +
                            "WHERE books.id = :id", Map.of("id", id), new BookRowMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete books where id = :id", Map.of("id", id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }


    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update("insert into books (title, author_id, genre_id) " +
                "values (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update("update books set title = :title, author_id = :author_id, " +
                " genre_id = :genre_id where id = 1; ", params);
        return book;
    }

    public List<Book> findByAuthorId(long authorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", authorId);

        return namedParameterJdbcOperations.query(
                "SELECT books.id, books.title, authors.id, authors.full_name, genres.id, genres.name " +
                        "FROM books " +
                        "INNER JOIN authors " +
                        "ON author_id = authors.id " +
                        "INNER JOIN genres " +
                        "ON genre_id = genres.id " +
                        "WHERE authors.id = :id", params, new BookRowMapper());
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("authors.id"), rs.getString("full_name"));
            Genre genre = new Genre(rs.getLong("genres.id"), rs.getString("name"));
            return new Book(id, title, author, genre);
        }
    }
}
