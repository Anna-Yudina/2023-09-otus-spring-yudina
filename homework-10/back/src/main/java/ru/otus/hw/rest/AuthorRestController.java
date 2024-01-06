package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/authors/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") long id) {
        return AuthorDto.fromDomainObject(authorService.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found")));
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll()
                .stream().map(AuthorDto::fromDomainObject).collect(Collectors.toList());
    }

    @DeleteMapping("/api/authors/{id}")
    public AuthorDto deleteAuthorById(@PathVariable("id") long id) {
        return AuthorDto.fromDomainObject(authorService.deleteById(id));
    }

    @PutMapping("/api/authors/{id}")
    public AuthorDto editAuthorById(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        return AuthorDto.fromDomainObject(authorService.update(id, authorDto.getFullName()));
    }

    @PostMapping("/api/authors")
    public AuthorDto saveAuthor(@RequestBody AuthorDto authorDto) {
        return AuthorDto.fromDomainObject(authorService.insert(authorDto.getFullName()));
    }
}
