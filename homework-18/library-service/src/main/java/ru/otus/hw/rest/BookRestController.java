package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return BookDto.fromDomainObject(bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found")));
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll().stream().map(BookDto::fromDomainObject).collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBookById(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    public BookDto editBookById(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        return BookDto.fromDomainObject(bookService
                .update(id, bookDto.getAuthor().getId(), bookDto.getTitle(), bookDto.getGenre().getId()));
    }

    @PostMapping("/api/books")
    public BookDto saveBook(@RequestBody BookDto bookDto) {
        return BookDto.fromDomainObject(bookService
                .insert(bookDto.getAuthor().getId(), bookDto.getTitle(), bookDto.getGenre().getId()));
    }
}
