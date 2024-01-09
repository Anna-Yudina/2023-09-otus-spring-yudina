package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepositoryData;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreRepositoryData genreRepository;

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll().map(GenreDto::fromDomainObject);
    }

    @DeleteMapping("/api/genres/{id}")
    public Mono<Void> deleteGenreById(@PathVariable("id") long id) {
        return genreRepository.deleteById(id);
    }

    @PostMapping("/api/genres")
    public Mono<Genre> saveGenre(@RequestBody GenreDto genreDto) {
        return genreRepository.save(genreDto.getName());
    }
}
