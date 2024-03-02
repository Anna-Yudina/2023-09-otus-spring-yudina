package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.AuthorMongo;
import ru.otus.hw.models.jpa.AuthorPostgres;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class AuthorConverter implements Converter<AuthorPostgres, AuthorMongo> {

    private final DataSource dataSource;

    @Override
    public AuthorMongo convert(AuthorPostgres authorPostgres) {
        String mongoId = (new ObjectId()).toString();
        writeTempTable(authorPostgres.getId(), mongoId);

        return new AuthorMongo(mongoId, authorPostgres.getFullName());
    }

    public void writeTempTable(long postgresId, String mongoId) {
        new JdbcTemplate(dataSource).update("insert into temp_author_cross_ids(id_postgres, id_mongo)" +
                " values (?, ?)", postgresId, mongoId);
    }
}
