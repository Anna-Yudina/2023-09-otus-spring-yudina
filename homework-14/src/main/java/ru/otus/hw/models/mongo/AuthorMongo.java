package ru.otus.hw.models.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("authors")
public class AuthorMongo {

    @Id
    private String id;

    private String fullName;

    public AuthorMongo(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}

