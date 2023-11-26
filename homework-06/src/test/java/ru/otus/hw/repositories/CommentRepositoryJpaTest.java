package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({CommentRepositoryJpa.class, BookRepositoryJpa.class})
public class CommentRepositoryJpaTest {

    private static final long FIRST_COMMENT_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private static final String COMMENT_TEXT = "Comment_1";

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName(" должен загружать информацию о нужном комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.findById(FIRST_COMMENT_ID);
        Comment expectedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName(" должен корректно сохранять всю информацию о комментарии")
    @Test
    void shouldSaveAllCommentInfo() {
        Book book = bookRepositoryJpa.findWithAutorAndGenresById(FIRST_BOOK_ID).get();
        Comment comment = new Comment(0, COMMENT_TEXT);
        comment.setBook(book);

        commentRepositoryJpa.insert(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment actualComment = testEntityManager.find(Comment.class, comment.getId());

        assertThat(actualComment).isNotNull().matches(c -> c.getText() != null)
                .matches(c -> c.getBook().getId() != 0);
    }

    @DisplayName(" должен удалять заданный комментарий по его id")
    @Test
    void shouldDeleteCommentNameById() {
        Comment firstComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        testEntityManager.detach(firstComment);

        commentRepositoryJpa.deleteById(FIRST_COMMENT_ID);
        Comment deletedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }
}
