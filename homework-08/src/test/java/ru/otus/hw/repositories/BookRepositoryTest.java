package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.events.MongoBookCascadeDeleteEventsListener;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(MongoBookCascadeDeleteEventsListener.class)
public class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("при удалении книги должен удалить все комментарии")
    @Test
    void shouldRemoveCommentsFromBook() {
        List<Book> books = bookRepository.findAll();
        Book firstBook = books.get(0);

        List<Comment> beforeDeletingComments = commentRepository.findCommentsByBookId(firstBook.getId());
        assertThat(beforeDeletingComments).isNotEmpty();

        bookRepository.deleteById(firstBook.getId());

        List<Comment> afterDeletingComments = commentRepository.findCommentsByBookId(firstBook.getId());
        assertThat(afterDeletingComments).isEmpty();
    }
}
