package ru.otus.hw.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepositoryData;

@RestController
public class AuthorRestController {

    private final AuthorRepositoryData authorRepository;

    public AuthorRestController(AuthorRepositoryData authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors/{id}")
    public Mono<ResponseEntity<AuthorDto>> getAuthorById(@PathVariable("id") Long id) {
        return authorRepository.findById(id)
                .map(AuthorDto::fromDomainObject)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(AuthorDto::fromDomainObject);
    }

    @DeleteMapping("/api/authors/{id}")
    public Mono<Void> deleteAuthorById(@PathVariable("id") long id) {
        return authorRepository.deleteById(id);
    }

    @PutMapping("/api/authors/{id}")
    public Mono<Author> editAuthorById(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        return authorRepository.save(id, authorDto.getFullName());
    }

    @PostMapping("/api/authors")
    public Mono<Author> save(@RequestBody AuthorDto authorDto) {
        return authorRepository.save(authorDto.getFullName());
    }
}
