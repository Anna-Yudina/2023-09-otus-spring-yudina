package ru.otus.hw.models.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("genres")
public class GenreMongo {

    @Id
    private String id;

    private String name;

    public GenreMongo(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
