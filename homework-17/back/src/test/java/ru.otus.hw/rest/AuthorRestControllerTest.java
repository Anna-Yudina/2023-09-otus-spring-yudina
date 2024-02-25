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
import ru.otus.hw.repositories.AuthorRepositoryData;

import java.util.List;

import static org.mockito.BDDMockito.times;

@WebFluxTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepositoryData authorRepository;

    @Test
    void shouldReturnCorrectAuthorsList() throws Exception {
        List<Author> authors = List.of(new Author(1, "Author1"), new Author(2, "Author2"));

        Flux<Author> authorsFlux = Flux.fromIterable(authors);

        Mockito.when(authorRepository.findAll()).thenReturn(authorsFlux);

        webTestClient.get().uri("/api/authors")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteAuthor() {
        Mono<Void> voidReturn = Mono.empty();

        Mockito.when(authorRepository.deleteById(1L))
                .thenReturn(voidReturn);

        webTestClient.delete().uri("/api/authors/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldCreateAuthor() {
        String fullName = "Author1";
        Author author = new Author(1L, fullName);

        Mockito.when(authorRepository.save(fullName)).thenReturn(Mono.just(author));

        webTestClient.post()
                .uri("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(author))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(authorRepository, times(1)).save(fullName);
    }

    @Test
    public void shouldEditAuthor() {
        String fullName = "Author1";
        Author author = new Author(1L, fullName);

        Mockito.when(authorRepository.save(1L, fullName)).thenReturn(Mono.just(author));

        webTestClient.put()
                .uri("/api/authors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(author))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(authorRepository, times(1)).save(1L, fullName);
    }
}
