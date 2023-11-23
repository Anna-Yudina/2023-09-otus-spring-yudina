package ru.otus.hw.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({BookRepositoryJpa.class, GenreRepositoryJpa.class, AuthorRepositoryJpa.class})
class BookRepositoryJpaTest {

    private static final int EXPECTED_QUERIES_COUNT = 1;

    private static final long FIRST_BOOK_ID = 1L;

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final String BOOK_TITLE_1 = "Book_1";

    private static final String BOOK_TITLE_2 = "Book_2";

    private static final int EXPECTED_NUMBER_OF_BOOK = 6;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName(" должен загружать информацию о нужной книге по её id")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = bookRepositoryJpa.findWithAutorAndGenresById(FIRST_BOOK_ID);
        Book expectedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        List<Book> books = bookRepositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> !b.getAuthor().getFullName().equals(""))
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        Author author = authorRepositoryJpa.findById(FIRST_AUTHOR_ID).get();
        List<Genre> genres = List.of(genreRepositoryJpa.findById(FIRST_GENRE_ID).get());

        Book book1 = new Book(0, BOOK_TITLE_1, author, genres);
        bookRepositoryJpa.save(book1);
        assertThat(book1.getId()).isGreaterThan(0);

        Book actualBook = testEntityManager.find(Book.class, book1.getId());
        assertThat(actualBook).isNotNull().matches(b -> !b.getTitle().equals(""))
                .matches(b -> !b.getAuthor().getFullName().equals(""))
                .matches(b -> b.getGenres() != null && b.getGenres().size() > 0);
    }

    @DisplayName(" должен изменять что-либо у заданной книге по её id")
    @Test
    void shouldUpdateBookById() {
        Book firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = firstBook.getTitle();
        testEntityManager.detach(firstBook);
        Author author = authorRepositoryJpa.findById(FIRST_AUTHOR_ID).get();
        List<Genre> genres = List.of(genreRepositoryJpa.findById(FIRST_GENRE_ID).get());

        Book newBook = new Book(FIRST_BOOK_ID, BOOK_TITLE_2, author, genres);
        bookRepositoryJpa.save(newBook);
        Book updatedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);

        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(BOOK_TITLE_2);
    }

    @DisplayName(" должен удалять заданную книгу по её id")
    @Test
    void shouldDeleteBookNameById() {
        Book firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        testEntityManager.detach(firstBook);

        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        Book deletedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }
}