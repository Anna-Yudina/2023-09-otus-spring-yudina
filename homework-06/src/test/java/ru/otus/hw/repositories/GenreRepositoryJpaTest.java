package ru.otus.hw.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final long FIRST_GENRE_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        Optional<Genre> optionalActualGenre = repositoryJpa.findById(FIRST_GENRE_ID);
        Genre expectedGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        List<Genre> genres = repositoryJpa.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(a -> a.getName() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
