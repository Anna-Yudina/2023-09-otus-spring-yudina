package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepositoryData;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryData authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public Author insert(String authorName) {
        Author author = new Author(0, authorName);
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author deleteById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        authorRepository.deleteById(id);
        return author;
    }
}
