package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.jpa.BookPostgres;
import ru.otus.hw.models.mongo.AuthorMongo;
import ru.otus.hw.models.mongo.BookMongo;
import ru.otus.hw.models.mongo.GenreMongo;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class BookConverter implements Converter<BookPostgres, BookMongo> {

    private final DataSource dataSource;

    @Override
    public BookMongo convert(BookPostgres bookPostgres) {
        String bookMongoId = (new ObjectId()).toString();
        String authorMongoId = getAuthorMongoId(bookPostgres.getAuthor().getId());
        AuthorMongo authorMongo = new AuthorMongo(authorMongoId, bookPostgres.getAuthor().getFullName());

        String genreMongoId = getGenreMongoId(bookPostgres.getGenre().getId());
        GenreMongo genreMongo = new GenreMongo(genreMongoId, bookPostgres.getGenre().getName());

        return new BookMongo(bookMongoId,
                bookPostgres.getTitle(),
                authorMongo,
                genreMongo);
    }

    public String getAuthorMongoId(long id) {
        return new JdbcTemplate(dataSource).queryForObject(
                "select id_mongo from temp_author_cross_ids where id_postgres = ?",
                String.class, id);
    }

    public String getGenreMongoId(long id) {
        return new JdbcTemplate(dataSource).queryForObject(
                "select id_mongo from temp_genre_cross_ids where id_postgres = ?",
                String.class, id);
    }
}
