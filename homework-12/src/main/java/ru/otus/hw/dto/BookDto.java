package ru.otus.hw.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 150, message = "Название книги должно быть не менее 2 и не более 150 символов")
    private String title;

    @NotNull(message = "Автор должен быть заполнен")
    private Author author;

    @NotNull(message = "Жанр должен быть заполнен")
    private Genre genre;

    private List<Comment> comments;

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments());
    }
}
