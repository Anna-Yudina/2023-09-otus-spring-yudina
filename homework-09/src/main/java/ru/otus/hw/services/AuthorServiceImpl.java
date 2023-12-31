package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.NotFoundException;
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
        System.out.println("Зашел в метод сервиса");
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author insert(String authorName) {
        Author author = new Author(0, authorName);
        return authorRepository.save(author);
    }

    @Override
    public Author deleteById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
        authorRepository.deleteById(id);
        return author;
    }

    @Override
    public Author update(long id, String fullName) {
        Author author = new Author(id, fullName);
        author.setBooks(authorRepository.findById(id).get().getBooks());
        return authorRepository.save(author);
    }
}
