package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.events.MongoAuthorCascadeDeleteEventsListener;
import ru.otus.hw.events.MongoBookCascadeDeleteEventsListener;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@Import({MongoAuthorCascadeDeleteEventsListener.class, MongoBookCascadeDeleteEventsListener.class})
public class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    AuthorRepository authorRepository;

    @DisplayName("при удалении автора должен удалить все книги и комментарии соответственно")
    @Test
    void shouldRemoveBookAndCommentFromAuthor() {
        List<Author> authors = authorRepository.findAll();
        String authorId = authors.get(0).getId();

        List<Book> deletingBooks = bookRepository.findBooksByAuthorId(authorId);
        assertThat(deletingBooks).isNotEmpty();

        for (Book book : deletingBooks) {
            assertThat(commentRepository.findCommentsByBookId(book.getId())).isNotEmpty();
        }

        authorRepository.deleteById(authorId);

        assertThat(bookRepository.findBooksByAuthorId(authorId)).isEmpty();

        for (Book book : deletingBooks) {
            assertThat(commentRepository.findCommentsByBookId(book.getId())).isEmpty();
        }
    }
}
