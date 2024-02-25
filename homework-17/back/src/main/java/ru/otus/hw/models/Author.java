package ru.otus.hw.models;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    private long id;

    @Column("full_name")
    private String fullName;

//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Book> books;

    public Author(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
