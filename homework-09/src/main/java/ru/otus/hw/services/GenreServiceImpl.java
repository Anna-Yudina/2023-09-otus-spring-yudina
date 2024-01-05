package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepositoryData;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepositoryData genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre insert(String genreName) {
        Genre genre = new Genre(0, genreName);
        return genreRepository.save(genre);
    }

    @Override
    public Genre deleteById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found"));
        genreRepository.deleteById(id);
        return genre;
    }
}
