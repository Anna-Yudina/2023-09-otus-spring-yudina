package ru.otus.hw.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 6;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    private static final String AUTHOR_FULL_NAME = "Author_1";

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        Optional<Author> optionalActualAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID);
        Author expectedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName(" должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        List<Author> authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> a.getFullName() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен корректно сохранять всю информацию об авторе")
    @Test
    void shouldSaveAllAuthorInfo() {
        Author author = new Author(0, AUTHOR_FULL_NAME);

        repositoryJpa.insert(author);
        assertThat(author.getId()).isGreaterThan(0);

        Author actualAuthor = testEntityManager.find(Author.class, author.getId());

        assertThat(actualAuthor).isNotNull().matches(a -> a.getFullName() != null);
    }

    @DisplayName(" должен удалять заданного автора по его id")
    @Test
    void shouldDeleteAuthorNameById() {
        Author firstAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(firstAuthor).isNotNull();
        testEntityManager.detach(firstAuthor);

        repositoryJpa.deleteById(FIRST_AUTHOR_ID);
        Author deletedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }
}
