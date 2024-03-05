package ru.otus.hw.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "library-service")
public interface LibraryServiceProxi {

    @CircuitBreaker(name = "library-service", fallbackMethod = "fallbackGetAllAuthors")
    @GetMapping(value = "/api/authors")
    List<AuthorDto> getAllAuthors();

    @CircuitBreaker(name = "library-service", fallbackMethod = "fallbackGetAllBooks")
    @GetMapping(value = "/api/books")
    List<BookDto> getAllBooks();

    default List<AuthorDto> fallbackGetAllAuthors(Throwable throwable) {
        return Collections.emptyList();
    }

    default List<BookDto> fallbackGetAllBooks(Throwable throwable) {
        return Collections.emptyList();
    }
}
