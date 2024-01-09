package ru.otus.hw.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepositoryData;

import java.util.List;

import static org.mockito.Mockito.times;

@WebFluxTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepositoryData bookRepository;

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                        new Genre(1L, "genre1")),
                new Book(2L, "BookTitle2", new Author(2L, "Author2"),
                        new Genre(2L, "genre2")));

        Flux<Book> booksFlux = Flux.fromIterable(books);

        Mockito.when(bookRepository.findAllBooks()).thenReturn(booksFlux);

        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void shouldDeleteBook() {
        Mono<Void> voidReturn = Mono.empty();

        Mockito.when(bookRepository.deleteById(1L))
                .thenReturn(voidReturn);

        webTestClient.delete().uri("/api/books/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void shouldCreateBook() {
        String title = "BookTitle1";
        Book book = new Book(1L, title, new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));

        Mockito.when(bookRepository.save(title, 1L, 1L)).thenReturn(Mono.just(book));

        webTestClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(bookRepository, times(1)).save(title, 1L, 1L);
    }

    @Test
    public void shouldEditBook() {
        String title = "BookTitle1";
        Book book = new Book(1L, title, new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));

        Mockito.when(bookRepository.save(1L, title, 1L, 1L)).thenReturn(Mono.just(book));

        webTestClient.put()
                .uri("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(bookRepository, times(1)).save(1L, title, 1L, 1L);
    }
}
