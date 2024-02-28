package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.jpa.GenrePostgres;
import ru.otus.hw.models.mongo.GenreMongo;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class GenreConverter implements Converter<GenrePostgres, GenreMongo> {

    private final DataSource dataSource;

    @Override
    public GenreMongo convert(GenrePostgres genrePostgres) {
        String mongoId = (new ObjectId()).toString();
        writeTempTable(genrePostgres.getId(), mongoId);
        return new GenreMongo(mongoId, genrePostgres.getName());
    }

    public void writeTempTable(long postgresId, String mongoId) {
        new JdbcTemplate(dataSource).update("insert into temp_genre_cross_ids(id_postgres, id_mongo)" +
                " values (?, ?)", postgresId, mongoId);
    }
}
