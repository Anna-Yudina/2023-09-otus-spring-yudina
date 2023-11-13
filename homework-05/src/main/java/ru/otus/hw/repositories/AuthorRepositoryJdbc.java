package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorRepositoryJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, full_name from authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            author = namedParameterJdbcOperations.queryForObject(
                    "select id, full_name from authors where id = :id", Map.of("id", id), new AuthorRowMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Author insert(Author author) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name", author.getFullName());
        namedParameterJdbcOperations.update("insert into authors (full_name) values (:full_name)",
                params, keyHolder, new String[]{"id"});

        author.setId(keyHolder.getKeyAs(Long.class));
        return author;
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete authors where id = :id", Map.of("id", id));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
