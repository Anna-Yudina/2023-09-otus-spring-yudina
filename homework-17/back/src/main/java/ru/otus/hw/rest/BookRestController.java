package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepositoryData;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepositoryData bookRepository;

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") long id) {
        return bookRepository.findById(id)
                .map(BookDto::fromDomainObject)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/api/books")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAllBooks().map(BookDto::fromDomainObject);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBookById(@PathVariable("id") long id) {
        return bookRepository.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    public Mono<Book> editBookById(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        return bookRepository.save(id, bookDto.getTitle(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }

    @PostMapping("/api/books")
    public Mono<Book> saveBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookDto.getTitle(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }
}
