package ru.otus.hw.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<String> genres;

    public Book(String title, Author author, List<String> genres) {
        this.title = title;
        this.author = author;
        this.genres = genres;
    }
}
