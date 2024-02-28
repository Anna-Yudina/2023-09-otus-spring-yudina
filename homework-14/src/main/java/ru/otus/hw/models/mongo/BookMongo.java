package ru.otus.hw.models.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("books")
public class BookMongo {

    @Id
    private String id;

    private String title;

    private AuthorMongo author;

    private GenreMongo genres;

    public BookMongo(String id, String title, AuthorMongo author, GenreMongo genres) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genres = genres;
    }
}
