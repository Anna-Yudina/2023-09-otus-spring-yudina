package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreRestController  {

    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return genreService.findAll()
                .stream().map(GenreDto::fromDomainObject).collect(Collectors.toList());
    }

    @DeleteMapping("/api/genres/{id}")
    public GenreDto deleteGenreById(@PathVariable("id") long id) {
        return GenreDto.fromDomainObject(genreService.deleteById(id));
    }

    @PostMapping("/api/genres")
    public GenreDto saveAuthor(@RequestBody GenreDto genreDto) {
        return GenreDto.fromDomainObject(genreService.insert(genreDto.getName()));
    }
}
