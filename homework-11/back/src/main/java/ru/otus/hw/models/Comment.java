package ru.otus.hw.models;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    private long id;

    private String text;

    private Book book;

    public Comment(long id, String text) {
        this.id = id;
        this.text = text;
    }
}
